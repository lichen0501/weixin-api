package com.onedt.wx.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 模板消息实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-18 上午11:22:21
 */
public class TemplateMessage extends BaseWechatEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String msgid;// 服务器返回的消息标识
    private String touser;// 接收方的Openid
    private String templateId;// 模板标识
    private String url;// 模板跳转链接,非必填
    private Object miniprogram;// 小程序，暂且无用，非必填
    private Map<String, Message> data;// 消息具体内容,封装成微信要求的格式

    /** 自定义参数 */
    private List<String> messageList;// 消息具体内容集合，自定义的格式

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(Object miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, Message> getData() {
        return data;
    }

    public void setData(Map<String, Message> data) {
        this.data = data;
    }

    public static class Message implements Serializable {
        /**
     * 
     */
        private static final long serialVersionUID = 1L;
        private String value;// 消息文本
        private String color;// 消息颜色

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

    }
}
