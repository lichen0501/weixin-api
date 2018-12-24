package com.onedt.wx.entity;

/**
 * 微信html页面jssdk签名实体类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-13 下午7:26:13
 */
public class Signature extends BaseWechatEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String appId;// 微信公众号AppID
    private String ticket;// 摇一摇每次产生的Ticket
    private String nonceStr;// 随机字符串
    private String signature;// 页面签名
    private String timestamp;// 时间戳

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
