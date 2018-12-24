package com.onedt.wx.service.impl;

import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onedt.wx.agent.MessageResponseAgent;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.service.IWechatServer;
import com.onedt.wx.utils.SpringUtil;
import com.onedt.wx.utils.WechatUtlis;
import com.onedt.wx.utils.XmlUtils;

/**
 * 微信服务器往后台系统推送事件
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月8日 下午3:08:09
 */
@Service
public class WechatServerImpl implements IWechatServer {
	public static final Logger LOGGER = LoggerFactory.getLogger(WechatServerImpl.class);
	private static final ExecutorService executorService = Executors.newCachedThreadPool();

	/**
	 * 处理用户消息和开发者需要的事件推送
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 */
	@Override
	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			final PrintWriter out = response.getWriter();
			final long startTime = System.currentTimeMillis();
			String resultData = "";
			final long time = WechatConstants.WECHAT_RESP_TIME;// 5秒
			// 程序在5秒内没有回复微信服务器
			Runnable autoTask = new Runnable() {
				public void run() {
					try {
						Thread.sleep(time);
						LOGGER.error("后台服务器响应微信服务器时间超过5秒");
						LOGGER.info("后台控制器延时自动回复公众号的信息");
						out.write("");
						out.flush();
						out.close();
					} catch (InterruptedException e) {
					}
				}
			};
			Future<?> future = executorService.submit(autoTask);
			try {
				// 业务逻辑开始
				String rquestMethod = request.getMethod();
				if (WechatConstants.HTTP_METHOD_GET.equalsIgnoreCase(rquestMethod)) {
					resultData = WechatUtlis.checkWechatRequest(request);
				} else if (WechatConstants.HTTP_METHOD_POST.equalsIgnoreCase(rquestMethod)) {
					Map<String, String> params = XmlUtils.toMapDirect(request.getInputStream());
					if (params != null && params.size() > 0) {
						resultData = MessageResponseAgent.getInstance().response(params);
					} else {
						LOGGER.error("公众号发送消息参数不全");
					}
				}
				// 业务逻辑结束
			} catch (Exception e) {
				LOGGER.error("[invoke]后台处理公众号消息业务时发生异常：" + SpringUtil.getMessage(e));
			} finally {
				long endTime = System.currentTimeMillis();
				if ((startTime + time) > endTime) {// 5秒内就不需要自动回复微信公众号平台了
					future.cancel(true);// 取消定时回复的线程
					LOGGER.info("后台控制器回复公众号的信息resultData=" + resultData);
					out.write(resultData);
					out.flush();
					out.close();
				}
			}

		} catch (Exception e) {
			LOGGER.error("[invoke]后台处理公众号消息控制器发生异常：" + e.getMessage());
		}
	}

}
