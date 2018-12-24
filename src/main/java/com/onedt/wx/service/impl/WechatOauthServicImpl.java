package com.onedt.wx.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.Signature;
import com.onedt.wx.entity.WechatOauthInfo;
import com.onedt.wx.entity.WechatUserInfo;
import com.onedt.wx.service.IWechatOauthService;
import com.onedt.wx.service.IWechatUserService;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.WechatUtlis;

/**
 * 微信公众页面授权业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月8日 下午5:32:19
 */
@Service
public class WechatOauthServicImpl implements IWechatOauthService {
    private static Logger logger = LoggerFactory.getLogger(WechatOauthServicImpl.class);
    @Autowired
    private IWechatUserService userService;

    /**
     * 先获取授权的code值
     * 
     * @param info
     * @return
     * @throws Exception
     */
    public WechatOauthInfo getOpenid(WechatOauthInfo info) throws Exception {
        if (info != null && StringUtils.isNotEmpty(info.getUrl())) {
            String url = info.getUrl();
            logger.info("info.getUrl()="+info.getUrl());
            String encodeUrl = null;
            encodeUrl = URLDecoder.decode(url, WechatConstants.ENCODE_TYPE);
            Map<String, String> map = WechatUtlis.buildParamMap(encodeUrl);
            String code = map.get(WechatConstants.REQUEST_PARAM_CODE);
            if (StringUtils.isEmpty(code)) {
                info.setUrl(WechatUtlis.buildOauthUrl(url));
                logger.info("[getOpenid]获取用户openid时code为空"+"|url="+url +"|跳转地址：" + info.getUrl());
            } else {
                WechatUserInfo oauthInfo = userService.getOauthOpenid(code);
                if (oauthInfo != null && StringUtils.isNotEmpty(oauthInfo.getOpenid())) {
                    info.setOpenid(oauthInfo.getOpenid());
                    logger.info("[getOpenid]获取用户openid时code=" + code + "，openid=" + oauthInfo.getOpenid());
                } else {
                    logger.error("[getOpenid]根据code获取openid失败,code=" + code);
                    logger.error("[getOpenid]根据code获取openid失败:" + JacksonUtils.getJsonCamelLower(oauthInfo));
                }
            }
        } else {
            logger.error("[getOpenid]发生异常,请求地址参数不能为空");
        }
        return info;
    }

    /**
     * 获取网页jssdk签名
     * 
     * @param info
     * @return Signature
     */
    public Signature getSignature(WechatOauthInfo info) throws Exception {
        Signature result = null;
        if (StringUtils.isNotEmpty(info.getUrl())) {
            String encodeUrl = URLDecoder.decode(info.getUrl(), WechatConstants.ENCODE_TYPE);
            result = WechatUtlis.getSignature(encodeUrl);
        } else {
            logger.error("[getSignature]失败：url参数为空");
        }
        return result;
    }

    /**
     * 同步微信公众号用户数据,保持微信已关注用户与后台数据库一致
     * 
     * @return
     */
    public List<String> syncUsers() throws Exception {
        List<String> openids = userService.queryUsers();
        if (openids != null && openids.size() > 0) {
            String str = openids.toString();
            str = str.replace("[", "");
            str = str.replace("]", "");
            String filePath = WechatConstants.TEMPLATE_PATH.substring(0, WechatConstants.TEMPLATE_PATH.lastIndexOf("/"));
            File file = new File(filePath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    logger.info("[syncUsers]创建用户openid文件目录成功");
                } else {
                    logger.error("[syncUsers]创建用户openid文件目录失败，路径：" + filePath);
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = format.format(new Date()) + ".txt";
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath + File.separator + fileName), "utf-8"));
            out.println(str);
            out.flush();
            out.close();
        }
        return openids;
    }

    public void setUserService(IWechatUserService userService) {
        this.userService = userService;
    }

}
