package com.onedt.wx.entity;

/**
 * jsapi_ticket是公众号用于调用微信JS接口的临时票据,有效期7200秒，不能每次用时都更新
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-13 下午7:37:56
 */
public class JsTicket extends BaseWechatEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String ticket;
    private long expiresIn;
    /** 最后一次请求时间戳 */
    private long lastRequest;
    /** 有效期 */
    private long validity;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(long lastRequest) {
        this.lastRequest = lastRequest;
    }

    public long getValidity() {
        return validity;
    }

    public void setValidity(long validity) {
        this.validity = validity;
    }

}
