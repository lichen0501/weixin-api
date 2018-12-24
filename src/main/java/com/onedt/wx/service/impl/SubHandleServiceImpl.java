package com.onedt.wx.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onedt.wx.client.WechatUserClient;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.BaseMessage;
import com.onedt.wx.entity.MessageText;
import com.onedt.wx.entity.WechatSysUser;
import com.onedt.wx.entity.WechatUserInfo;
import com.onedt.wx.entity.sys.ReqBodyObj;
import com.onedt.wx.entity.sys.RespBodyObj;
import com.onedt.wx.service.IMessageHandleService;
import com.onedt.wx.service.IWechatUserService;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.PropertiesConfig;

/**
 * 用户关注公众号事件业务处理类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-15 下午2:25:54
 */
@Service
public class SubHandleServiceImpl implements IMessageHandleService.ISubHandleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubHandleServiceImpl.class);
	@Autowired
	private IWechatUserService service;
	@Autowired(required = false)
	private WechatUserClient wechatUserClient;

	/**
	 * 业务处理
	 */
	public BaseMessage invoke(Map<String, String> params) throws Exception {
		StringBuffer logStr = new StringBuffer();
		MessageText result = null;
		String openid = params.get(BaseMessage.Property.fromUserName);
		logStr.append("微信用户[" + openid + "]关注公众号开始");
		try {
			if (StringUtils.isNotEmpty(openid)) {
				WechatUserInfo user = service.getUser(openid);
				if (wechatUserClient != null && user != null) {
					ReqBodyObj<WechatSysUser> reqObj = new ReqBodyObj<WechatSysUser>();
					WechatSysUser reqUser = new WechatSysUser();
					reqUser.setOpenId(openid);
					RespBodyObj<?> addResult = null;
					if ((PropertiesConfig.getValue("wechat.title").indexOf("ET")>-1)) {// ET健康管理服务站
						// ET健康管理服务站与一体车联，深圳垃圾分类的昵称字段不一致
						logStr.append("|" + PropertiesConfig.getValue("wechat.title"));
						reqUser.setNickName(user.getNickname());
						reqUser.setWechatUrl(user.getHeadimgurl());
						reqObj.setData(reqUser);
						addResult = wechatUserClient.registerUser(reqObj, PropertiesConfig.getValue("web.from"),
								PropertiesConfig.getValue("web.version"), openid);
					} else {
						logStr.append("|" + PropertiesConfig.getValue("wechat.title"));
						reqUser.setNikeName(user.getNickname());
						reqObj.setData(reqUser);
						addResult = wechatUserClient.registerUser(reqObj);
					}
					if (addResult != null && addResult.getCode() == 1) {
						logStr.append("|微信用户[" + openid + "]添加入库成功,用户信息：" + JacksonUtils.getJsonCamelLower(reqObj));
					} else {
						logStr.append("|微信用户[" + openid + "]添加入库失败，失败消息:" + JacksonUtils.getJsonCamelLower(addResult));
						logStr.append("|微信用户[" + openid + "]添加入库失败，用户信息:" + JacksonUtils.getJsonCamelLower(reqObj));
					}
				} else {
					logStr.append("|微信用户[" + openid + "]添加入库失败,参数为空:" + JacksonUtils.getJsonCamelLower(user));
				}
				result = new MessageText();
				result.setToUserName(openid);
				result.setFromUserName(params.get(BaseMessage.Property.toUserName));
				result.setCreateTime(WechatConstants.PARAM_NOTE + System.currentTimeMillis());
				result.setMsgType(WechatConstants.MESSAGE_TYPE_TEXT);
				result.setContent(PropertiesConfig.getValue("wechat.welcome_msg"));
				logStr.append("|返回服务器信息：" + JacksonUtils.getJsonCamelLower(result));
			}
		} finally {
			LOGGER.info(logStr.toString());
		}
		return result;
	}

	public void setservice(IWechatUserService service) {
		this.service = service;
	}

	public void setWechatUserClient(WechatUserClient wechatUserClient) {
		this.wechatUserClient = wechatUserClient;
	}

}
