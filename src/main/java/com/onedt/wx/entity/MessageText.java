package com.onedt.wx.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 公众号文本消息实体
 * 
 * @author Tian Bing
 * @version 1.0
 * @date 2017-4-14 下午3:18:21
 */
@XStreamAlias("xml")
public class MessageText extends BaseMessage {

    private static final long serialVersionUID = 988429290133895173L;
    private String Content;// 内容
    private String MsgId;// 消息标号

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

}
