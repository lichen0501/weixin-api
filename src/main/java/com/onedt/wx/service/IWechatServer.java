package com.onedt.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信服务器往后台系统推送事件
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月8日 下午3:05:10
 */
public interface IWechatServer {
    /**
     * 处理用户消息和开发者需要的事件推送
     * 
     * @param request
     * @param response
     */
    public void invoke(HttpServletRequest request, HttpServletResponse response);
}
