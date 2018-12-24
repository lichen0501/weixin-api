package com.onedt.wx.agent;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.BaseMessage;
import com.onedt.wx.service.IMessageHandleService;
import com.onedt.wx.service.IMessageHandleService.IImageHandleService;
import com.onedt.wx.service.IMessageHandleService.ILinkHandleService;
import com.onedt.wx.service.IMessageHandleService.ILocationEventService;
import com.onedt.wx.service.IMessageHandleService.ILocationHandleService;
import com.onedt.wx.service.IMessageHandleService.IMenuEventService;
import com.onedt.wx.service.IMessageHandleService.IShortvideoHandleService;
import com.onedt.wx.service.IMessageHandleService.ISubHandleService;
import com.onedt.wx.service.IMessageHandleService.ISubParamHandleService;
import com.onedt.wx.service.IMessageHandleService.ITemplateSendFinishService;
import com.onedt.wx.service.IMessageHandleService.ITextHandleService;
import com.onedt.wx.service.IMessageHandleService.IUnSubHandleService;
import com.onedt.wx.service.IMessageHandleService.IVideoHandleService;
import com.onedt.wx.service.IMessageHandleService.IVoiceHandleService;
import com.onedt.wx.utils.XmlUtils;

/**
 * 处理微信公众号发过来的微信用户消息业务类
 */
public class MessageResponseAgent {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageResponseAgent.class);
	private static final MessageResponseAgent responseAgent = new MessageResponseAgent();
	/** 业务处理集合 */
	private static Map<String, IMessageHandleService> callbackMap = new HashMap<String, IMessageHandleService>();

	private MessageResponseAgent() {
	}

	/** 获取消息处理类的实例对象 */
	public static MessageResponseAgent getInstance() {
		return responseAgent;
	}

	/**
	 * 注册处理微信公众号用户发过来的消息的业务接口实现类. 系统会根据实现类实现的接口不同, 消息处理采用不同的接口.
	 * 业务实现类只需注册一次。若重复注册，之前注册过的业务接口会被覆盖。业务接口的实现类可以实现多个业务接口, 则对应的消息业务接口都会被注册。
	 * 建议：在项目启动后即注册需要的业务接口。
	 */
	public void registerService(IMessageHandleService service) {
		if (service != null) {
			if (service instanceof ITextHandleService) {
				callbackMap.put(ITextHandleService.class.getSimpleName(), service);
			}
			if (service instanceof IImageHandleService) {
				callbackMap.put(IImageHandleService.class.getSimpleName(), service);
			}
			if (service instanceof IVoiceHandleService) {
				callbackMap.put(IVoiceHandleService.class.getSimpleName(), service);
			}
			if (service instanceof IVideoHandleService) {
				callbackMap.put(IVideoHandleService.class.getSimpleName(), service);
			}
			if (service instanceof IShortvideoHandleService) {
				callbackMap.put(IShortvideoHandleService.class.getSimpleName(), service);
			}
			if (service instanceof ILocationHandleService) {
				callbackMap.put(ILocationHandleService.class.getSimpleName(), service);
			}
			if (service instanceof ILinkHandleService) {
				callbackMap.put(ILinkHandleService.class.getSimpleName(), service);
			}
			if (service instanceof ISubHandleService) {
				callbackMap.put(ISubHandleService.class.getSimpleName(), service);
			}
			if (service instanceof IUnSubHandleService) {
				callbackMap.put(IUnSubHandleService.class.getSimpleName(), service);
			}
			if (service instanceof ISubParamHandleService) {
				callbackMap.put(ISubParamHandleService.class.getSimpleName(), service);
			}
			if (service instanceof ILocationEventService) {
				callbackMap.put(ILocationEventService.class.getSimpleName(), service);
			}
			if (service instanceof IMenuEventService) {
				callbackMap.put(IMenuEventService.class.getSimpleName(), service);
			}
			if (service instanceof ITemplateSendFinishService) {
				callbackMap.put(ITemplateSendFinishService.class.getSimpleName(), service);
			}
		}
	}

	/**
	 * 取消注册过的消息业务接口
	 * 
	 * @param 是指IMessageHandleService下面的子接口
	 * @return boolean 取消业务成功与否
	 */
	public boolean unregisterService(Class<IMessageHandleService> classType) {
		if (classType != null) {
			Object object = callbackMap.remove(classType.getSimpleName());
			if (object != null) {
				return true;
			}
		}
		return false;
	}

	/** 微信公众号消息处理并回复消息 */
	public String response(Map<String, String> params) throws Exception {
		String msgType = params.get(BaseMessage.Property.msgType);
		String resultData = "";
		if (StringUtils.isNotEmpty(msgType)) {
			String key = "";
			if (WechatConstants.MESSAGE_TYPE_TEXT.equals(msgType)) {// 文本消息
				key = ITextHandleService.class.getSimpleName();
			} else if (WechatConstants.MESSAGE_TYPE_IMAGE.equals(msgType)) {// 图片消息
				key = IImageHandleService.class.getSimpleName();
			} else if (WechatConstants.MESSAGE_TYPE_VOICE.equals(msgType)) {// 语音
				key = IVoiceHandleService.class.getSimpleName();
			} else if (WechatConstants.MESSAGE_TYPE_VIDEO.equals(msgType)) {// 视频
				key = IVideoHandleService.class.getSimpleName();
			} else if (WechatConstants.MESSAGE_TYPE_SHORTVIDEO.equals(msgType)) {// 小视频
				key = IShortvideoHandleService.class.getSimpleName();
			} else if (WechatConstants.MESSAGE_TYPE_LOCATION.equals(msgType)) {// 地理位置
				key = ILocationHandleService.class.getSimpleName();
			} else if (WechatConstants.MESSAGE_TYPE_LINK.equals(msgType)) {// 链接
				key = ILinkHandleService.class.getSimpleName();
			} else if (WechatConstants.MESSAGE_TYPE_EVENT.equals(msgType)) {// 事件
				String event = params.get(BaseMessage.Property.event);
				if (StringUtils.isNotEmpty(event)) {
					if (WechatConstants.MESSAGE_TYPE_EVENT_SUBSCRIBE.equals(event)
							&& StringUtils.isEmpty(params.get(BaseMessage.Property.eventKey))) {// 关注公众号事件
						// 包含手动搜索和扫描二维码的情况,但都不带参数
						key = ISubHandleService.class.getSimpleName();
					} else if (WechatConstants.MESSAGE_TYPE_EVENT_UNSUBSCRIBE.equals(event)) {// 取消关注公众号事件
						key = IUnSubHandleService.class.getSimpleName();
					} else if ((WechatConstants.MESSAGE_TYPE_EVENT_SCAN.equals(event)
							|| WechatConstants.MESSAGE_TYPE_EVENT_SUBSCRIBE.equals(event))
							&& StringUtils.isNotEmpty(params.get(BaseMessage.Property.eventKey))) {
						// 带有参数的扫描公众号关注事件
						key = ISubParamHandleService.class.getSimpleName();
					} else if (WechatConstants.MESSAGE_TYPE_EVENT_LOCATION.equals(event)) {// 上报地理位置事件
						key = ILocationEventService.class.getSimpleName();
					} else if (WechatConstants.MESSAGE_TYPE_EVENT_CLICK.equals(event)) {// 点击自定义菜单事件
						key = IMenuEventService.class.getSimpleName();
					} else if (WechatConstants.MESSAGE_TYPE_TEMPLATE_SEND.equals(event)) {// 发送模板通知事件
						key = ITemplateSendFinishService.class.getSimpleName();
					}
				}
			}
			IMessageHandleService service = callbackMap.get(key);
			if (service != null) {
				BaseMessage messageResponse = service.invoke(params);
				if (messageResponse != null) {
					resultData = XmlUtils.toXmlCdata(messageResponse);
				}
			} else {
				LOGGER.info("系统找不到处理此公众号消息的业务接口,消息类型：" + msgType + ",来自用户："
						+ params.get(BaseMessage.Property.fromUserName));
			}
		}
		return resultData;
	}

	/**
	 * 统计有多少个业务注册了
	 * 
	 * @return
	 */
	public int countSerivces() {
		return callbackMap.size();
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.isNotEmpty(null));
	}
}
