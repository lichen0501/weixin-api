package com.onedt.wx.entity;

/**
 * 微信页面页面端授权请求的参数实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-13 下午2:11:41
 */
public class WechatOauthInfo extends BaseWechatEntity {

	private static final long serialVersionUID = 1L;
	private String url;// 页面地址
	private String openid;// 用户标识
	private Object data;// 参数

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
