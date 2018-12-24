package com.onedt.wx.init;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onedt.wx.agent.MessageResponseAgent;
import com.onedt.wx.service.IMessageHandleService.ISubHandleService;
import com.onedt.wx.service.IMessageHandleService.ITemplateSendFinishService;
import com.onedt.wx.service.IMessageHandleService.IUnSubHandleService;
import com.onedt.wx.utils.TemplateUtils;

/**
 * 微信消息处理初始化
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-15 下午4:48:51
 */
@Component
public class WechatInit {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatInit.class);

    @Autowired
    private ISubHandleService subService;
    @Autowired
    private IUnSubHandleService unSubSerivce;
    @Autowired
    private ITemplateSendFinishService sendFinishService;

    /*
     * @Autowired private ITextHandleService textService;
     */
    @PostConstruct
    public void init() {
        // MessageResponseAgent.getInstance().registerService(textService); // 文本消息回复
        MessageResponseAgent.getInstance().registerService(subService);// 关注事件
        MessageResponseAgent.getInstance().registerService(unSubSerivce);// 取消关注事件
        MessageResponseAgent.getInstance().registerService(sendFinishService);// 模板通知发送事件
        LOGGER.info("[公众号消息]微信消息处理初始化完成" + MessageResponseAgent.getInstance().countSerivces() + "个业务");
        try {
            TemplateUtils.initTemplate();
            LOGGER.error("[微信通知模板]初始化微信通知模板成功：");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[微信通知模板]初始化微信通知模板发生异常：" + e.getMessage());
        }
        // LOGGER.info("[------------测试发送验证码通知---------------]" +
        // TemplateUtils.sendCodeMessage("o1Gahtya0tvMScBFMjPlTtfoZtPg","http://baidu.com", "张三通知", "5326", "赶紧使用"));
    }

}
