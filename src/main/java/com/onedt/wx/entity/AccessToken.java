package com.onedt.wx.entity;

/**
 * 微信公众号accesstoken对象实体, 公众号的全局唯一接口调用凭据,access_token的有效期目前为2个小时,不能每次用时都更新
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-13 上午9:47:46
 */
public class AccessToken extends BaseWechatEntity {
    private static final long serialVersionUID = 1L;
    /** accesstoken */
    private String accessToken;
    /** accesstoken有效时间，单位秒 */
    private long expiresIn;
    /** 最后一次请求时间戳 */
    private long lastRequest;
    /** 有效期 */
    private long validity;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
