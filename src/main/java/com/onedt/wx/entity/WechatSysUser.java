package com.onedt.wx.entity;

import java.io.Serializable;

/**
 * 与业务系统对接的微信用户数据模型
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月8日 下午5:05:32
 */
public class WechatSysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户微信标识
	 */
	private String openId;// 用户微信标识
	/**
	 * 标签标识
	 */
	private Integer tagId;// 标签标识

	/** 用户昵称 */
	private String nikeName;
	/** 用户昵称 */
	private String nickName;
	/** 验证码 */
	private String smsCode;
	/** 微信用户头像 */
	private String wechatUrl;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	/**
	 * 一车车联和深圳垃圾分类的昵称
	 * 
	 * @param nickName
	 */
	public String getNikeName() {
		return nikeName;
	}

	/**
	 * 一车车联和深圳垃圾分类的昵称
	 * 
	 * @param nickName
	 */
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getWechatUrl() {
		return wechatUrl;
	}

	public void setWechatUrl(String wechatUrl) {
		this.wechatUrl = wechatUrl;
	}

	/**
	 * ET健康管理服务站的昵称
	 * 
	 * @param nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * ET健康管理服务站的昵称
	 * 
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
