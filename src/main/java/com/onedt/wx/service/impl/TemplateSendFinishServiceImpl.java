package com.onedt.wx.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onedt.wx.entity.BaseMessage;
import com.onedt.wx.service.IMessageHandleService;

/**
 * 发送模板通知后事件处理业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-18 下午3:45:59
 */
@Service
public class TemplateSendFinishServiceImpl implements IMessageHandleService.ITemplateSendFinishService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateSendFinishServiceImpl.class);

    /**
     * 业务处理
     */
    public BaseMessage invoke(Map<String, String> params) throws Exception {
        LOGGER.info("[发送公众号模板通知]用户openid=" + params.get(BaseMessage.Property.fromUserName) + ",发送状态=" + params.get(BaseMessage.Property.status));
        return null;
    }

}
