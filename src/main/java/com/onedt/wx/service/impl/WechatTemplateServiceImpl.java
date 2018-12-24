package com.onedt.wx.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onedt.wx.cache.WechatCacher;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.WechatTemplate;
import com.onedt.wx.entity.WechatTemplate.Template;
import com.onedt.wx.service.IWechatTemplateService;
import com.onedt.wx.utils.HttpUtils;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.WechatUtlis;

/**
 * 微信公众号模板业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 下午4:33:50
 */
public class WechatTemplateServiceImpl implements IWechatTemplateService {
    private Logger logger = LoggerFactory.getLogger(WechatTemplateServiceImpl.class);

    /**
     * 获取模板列表
     * 
     * @return
     * @throws Exception
     */
    public List<Template> getTemplateList() throws Exception {
        List<Template> result = null;
        if (WechatUtlis.checkAccessTokenValid()) {
            String url = WechatUtlis.buildTemplateListUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpGet(url);
            WechatTemplate wt = JacksonUtils.getObjectUnderLower(WechatTemplate.class, json);
            if (wt != null) {
                result = wt.getTemplateList();
            }
        }
        return result;
    }

    /**
     * 创建xml文件目录
     * 
     * @param filePath
     * @return
     */
    private boolean createDir(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file = new File(filePath.substring(0, filePath.lastIndexOf("/")));
                if (!file.exists()) {
                    if (file.mkdirs()) {
                        logger.info("[syncUsers]创建用户openid文件目录成功");
                    } else {
                        logger.error("[syncUsers]创建用户openid文件目录失败，路径：" + filePath);
                    }
                }
            } else {
                return true;
            }
            Document document = DocumentHelper.createDocument();
            document.setXMLEncoding(WechatConstants.ENCODE_TYPE);
            Element root = document.addElement("templates");
            root.addAttribute("refresh", "true");
            root.setText("");
            OutputFormat formatter = OutputFormat.createPrettyPrint();// 设置XML格式
            formatter.setEncoding(WechatConstants.ENCODE_TYPE);
            formatter.setIndent("  ");
            FileWriter filename = new FileWriter(filePath);
            XMLWriter writer = new XMLWriter(filename, formatter);
            writer.write(document);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error("[createDir]创建xml目录发送异常：" + e.getMessage());
            logger.error("[createDir]创建xml目录发送异常：filePath=" + filePath);
            return false;
        }
        return true;
    }

    /**
     * 创建微信通知模板xml
     * 
     * @param templates
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void updateTemplate(List<WechatTemplate.Template> templates) throws Exception {
        if (createDir(WechatConstants.TEMPLATE_PATH)) {
            logger.info("[updateTemplate]创建微信通知模板目录成功");
        } else {
            logger.error("[updateTemplate]创建微信通知模板目录失败");
            return;
        }
        File stream = new File(WechatConstants.TEMPLATE_PATH);
        SAXReader reader = new SAXReader();
        reader.setEncoding(WechatConstants.ENCODE_TYPE);
        Document document = reader.read(stream);
        Element root = document.getRootElement();
        String refresh = root.attributeValue(WechatConstants.WECHAT_TEMPLATE_REFRESH);
        if (refresh != null && !Boolean.parseBoolean(refresh)) {
            logger.info("[updateTemplate]微信公众号通知模板不执行更新操作");
            return;
        } else {
            logger.info("[updateTemplate]微信公众号通知模板开始更新");
        }
        List<Element> elementList = root.elements(WechatConstants.WECHAT_TEMPLATE_TEMPLATE);
        if (elementList != null && elementList.size() > 0) {
            for (Element element : elementList) {
                root.remove(element);// 删除原来的模板再添加新的模板，等价于刷新此公众号的模板
            }
        }
        if (templates != null && templates.size() > 0) {
            Element templateEle = null;
            for (int i = 0; i < templates.size(); i++) {
                WechatTemplate.Template template = templates.get(i);
                templateEle = root.addElement(WechatConstants.WECHAT_TEMPLATE_TEMPLATE);
                templateEle.addAttribute(WechatConstants.WECHAT_TEMPLATE_TEMPLATEID, template.getTemplateId());
                templateEle.addAttribute(WechatConstants.WECHAT_TEMPLATE_TITLE, template.getTitle());
                templateEle.addAttribute(WechatConstants.WECHAT_TEMPLATE_PRIMARYINDUSTRY, template.getPrimaryIndustry());
                templateEle.addAttribute(WechatConstants.WECHAT_TEMPLATE_DEPUTYINDUSTRY, template.getDeputyIndustry());
                templateEle.addAttribute(WechatConstants.WECHAT_TEMPLATE_TYPE, String.valueOf(i + 1));
                String textes = template.getContent();
                if (template.getTitle().indexOf(WechatConstants.TEMPLATE_KEY_BOOKING) > -1) {// 预约成功
                    WechatConstants.TEMPLATE_ID_BOOKING_SUCCESS = template.getTemplateId();
                } else if (template.getTitle().indexOf(WechatConstants.TEMPLATE_KEY_CODE) > -1) {// 验证码
                    WechatConstants.TEMPLATE_ID_CODE = template.getTemplateId();
                } else if (template.getTitle().indexOf(WechatConstants.TEMPLATE_KEY_ORDER_NEW) > -1) {// 新订单
                    WechatConstants.TEMPLATE_ID_ORDER_NEW = template.getTemplateId();
                } else if (template.getTitle().indexOf(WechatConstants.TEMPLATE_KEY_ORDER_FINISH) > -1) {// 订单完成
                    WechatConstants.TEMPLATE_ID_ORDER_FINISH = template.getTemplateId();
                } else if (template.getTitle().indexOf(WechatConstants.TEMPLATE_KEY_ORDER_STATUS) > -1) {// 订单状态
                    WechatConstants.TEMPLATE_ID_ORDER_STATUS = template.getTemplateId();
                }
                List<String> textsList = splitContent(textes);
                if (textsList != null && textsList.size() > 0) {
                    Element content = templateEle.addElement(WechatConstants.WECHAT_TEMPLATE_CONTENT);
                    content.setText(textes);
                    content.addAttribute(WechatConstants.WECHAT_TEMPLATE_DESCRIPTION, "格式：{{参数名.DATA}}，各参数区分先后顺序");
                    Element messages = templateEle.addElement(WechatConstants.WECHAT_TEMPLATE_MESSAGE_LIST);
                    messages.addComment("下面参数节点区分先后顺序，与上面的content节点一致。参数节点值是此模板消息显示时的颜色值。");
                    if (textsList != null && textsList.size() > 0) {
                        for (String item : textsList) {
                            Element texter = messages.addElement(item);
                            texter.setText(WechatConstants.WECHAT_TEMPLATE_COLOR);
                        }
                    }
                    WechatCacher.putTemplateElements(template.getTemplateId(), messages.elements());
                }
            }
        }
        OutputFormat formatter = OutputFormat.createPrettyPrint();// 设置XML格式
        formatter.setEncoding(WechatConstants.ENCODE_TYPE);
        formatter.setIndent("  ");
        //FileWriter filename = new FileWriter(stream);
       // XMLWriter writer = new XMLWriter(filename, formatter);
        //XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(stream,"gb2312"),formatter);
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(stream),WechatConstants.ENCODE_TYPE),formatter);
        writer.write(document);
        
        writer.flush();
        writer.close();
    }

    /**
     * 拆分通知模板可填写的参数
     * 
     * @param textes
     * @return
     */
    public static List<String> splitContent(String textes) {
        List<String> textsList = null;
        if (StringUtils.isNotEmpty(textes)) {
            textsList = new ArrayList<String>();
            String[] texts = textes.split("\\.DATA");
            for (int j = 0; j < texts.length - 1; j++) {
                String text = texts[j];
                if (text.indexOf("{") > -1) {
                    text = text.substring(text.lastIndexOf("{") + 1).trim();
                    textsList.add(text);
                }
            }
        }
        return textsList;
    }
}
