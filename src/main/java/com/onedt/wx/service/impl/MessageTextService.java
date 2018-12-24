package com.onedt.wx.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.BaseMessage;
import com.onedt.wx.entity.MessageText;
import com.onedt.wx.service.IMessageHandleService;

/**
 * 公众号文本消息处理业务类
 * 
 * @author Tian Bing
 * @version 1.0
 * @date 2017-4-14 下午3:15:42
 */
@Service
public class MessageTextService implements IMessageHandleService.ITextHandleService {

    /**
     * 业务处理
     */
    public BaseMessage invoke(Map<String, String> params) throws Exception {
        MessageText message = new MessageText();
        message.setFromUserName(params.get(BaseMessage.Property.toUserName));
        message.setToUserName(params.get(BaseMessage.Property.fromUserName));
        message.setCreateTime(WechatConstants.PARAM_NOTE + System.currentTimeMillis());
        message.setMsgType(WechatConstants.MESSAGE_TYPE_TEXT);
        message.setContent(params.get(BaseMessage.Property.content));
        return message;
    }
}
