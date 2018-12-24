package com.onedt.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onedt.wx.service.IWechatServer;
import com.onedt.wx.utils.PropertiesConfig;

/**
 * 处理微信公众号消息处理控制器
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月11日 上午11:00:01
 */
@Controller
@RequestMapping("/wx")
public class WechatMessageController {
	@Autowired
	private IWechatServer wechatServer;

	@RequestMapping("${wechat_api_url}")
	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		wechatServer.invoke(request, response);
	}
}
