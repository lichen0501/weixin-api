package com.onedt.wx.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onedt.wx.entity.BaseMessage;
import com.onedt.wx.service.IMessageHandleService;

/**
 * 用户取消关注事件
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-15 下午3:25:17
 */
@Service
public class UnSubHandleServiceImpl implements IMessageHandleService.IUnSubHandleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnSubHandleServiceImpl.class);

    /**
     * 业务处理
     */
    public BaseMessage invoke(Map<String, String> params) throws Exception {
        LOGGER.info("微信用户[" + params.get(BaseMessage.Property.fromUserName) + "]取消关注公众号[" + params.get(BaseMessage.Property.toUserName) + "]");
        return null;
    }

}
