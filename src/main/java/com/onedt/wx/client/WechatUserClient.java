package com.onedt.wx.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onedt.wx.entity.WechatSysUser;
import com.onedt.wx.entity.sys.ReqBodyObj;
import com.onedt.wx.entity.sys.RespBodyObj;
import com.onedt.wx.hystrix.WechatUserHystrix;

/**
 * 微信用户在手机端使用公众号时产生的业务,需要业务系统去实现
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月8日 上午10:20:06
 */
@FeignClient(name = "wechaUser", url = "${sys_api_url}", fallbackFactory = WechatUserHystrix.class)
@RequestMapping("${sys_api_addUser}") 
public interface WechatUserClient {
	/**
	 * 微信用户在手机端关注公众号时的用户信息
	 * 
	 * @param info
	 */
	@PostMapping("/register") // 一体车联和深圳垃圾分类
	public RespBodyObj<?> registerUser(@RequestBody ReqBodyObj<WechatSysUser> info);

	@PostMapping("/save") // ET健康管理服务站
	public RespBodyObj<?> registerUser(@RequestBody ReqBodyObj<WechatSysUser> info,
			@RequestHeader(value = "from") String from, @RequestHeader(value = "version") String version,
			@RequestHeader(value = "openId") String openId);

}
