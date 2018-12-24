package com.onedt.wx.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.onedt.wx.client.WechatUserClient;
import com.onedt.wx.entity.WechatSysUser;
import com.onedt.wx.entity.sys.ReqBodyObj;
import com.onedt.wx.entity.sys.RespBodyObj;

import feign.hystrix.FallbackFactory;

/**
 * 微信用户远程调用错误异常处理类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月15日 上午9:37:26
 */
@Component
public class WechatUserHystrix implements FallbackFactory<WechatUserClient> {
	private Logger logger = LoggerFactory.getLogger(WechatUserHystrix.class);

	@Override
	public WechatUserClient create(Throwable cause) {
		logger.error("远程调用微信用户注册服务发生异常:" + cause.toString());
		logger.error(cause.getMessage());
		return new WechatUserClient() {
			public RespBodyObj<?> registerUser(ReqBodyObj<WechatSysUser> info) {
				return RespBodyObj.error("[WechatUserClient.registerUser]远程调用微信用户注册服务发生异常:" + cause.toString());
			}

			public RespBodyObj<?> registerUser(ReqBodyObj<WechatSysUser> info, String from, String version,
					String openid) {
				return RespBodyObj.error("[WechatUserClient.registerUser]远程调用微信用户注册服务发生异常:from=" + from + ",version="
						+ version + ",openid=" + openid + "||" + cause.toString());
			}

		};
	}

}
