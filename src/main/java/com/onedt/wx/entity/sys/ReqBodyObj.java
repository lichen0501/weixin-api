package com.onedt.wx.entity.sys;

import java.io.Serializable;

/**
 * 请求报文体
 * @author hy
 */
public class ReqBodyObj<T> implements Serializable{
	private static final long serialVersionUID = 2314713014045156176L;
	
	private T data;//请求数据
	
	public T getData() {
		return data;
	}

	public void setData( T data) {
		this.data = data;
	}
}
