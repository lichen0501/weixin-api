package com.onedt.wx.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onedt.wx.cache.WechatCacher;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.TemplateMessage;
import com.onedt.wx.entity.WechatTemplate.Template;
import com.onedt.wx.service.IWechatTemplateService;
import com.onedt.wx.service.impl.WechatTemplateServiceImpl;

/**
 * 微信公众号发送服务通知业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月8日 上午10:56:42
 */
public class TemplateUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtils.class);

	/**
	 * 加载所有公众号的模板
	 * 
	 * @param condition
	 *            condition.templateId必填
	 * @return List<Element>
	 */
	public static void initTemplate() throws Exception {
		IWechatTemplateService service = new WechatTemplateServiceImpl();
		List<Template> templates = service.getTemplateList();
		service.updateTemplate(templates);
	}

	/**
	 * 发送验证码
	 * 
	 * @param toUser
	 *            接受者openid ,必填
	 * @param code
	 *            验证码，必填
	 * @param link
	 *            消息通知点击跳转的链接url,非必填
	 * @param header
	 *            消息头,非必填。默认值："尊敬的客户"
	 * @param remark
	 *            备注,有效期等信息,非必填
	 * @return
	 */
	public static boolean sendCodeMessage(String toUser, String code, String link, String header, String remark) {
		TemplateMessage message = new TemplateMessage();
		message.setTouser(toUser);
		message.setTemplateId(WechatConstants.TEMPLATE_ID_CODE);
		message.setUrl(link);
		List<String> messageList = new ArrayList<String>();
		messageList.add(header == null ? "尊敬的客户" : header);
		messageList.add(code == null ? "无效" : code);
		messageList.add(remark == null ? "转发无效，如有需要，请重新请求下发" : remark);
		message.setMessageList(messageList);
		return sendMessagerRepetition(message);
	}

	/**
	 * 新订单通知
	 * 
	 * @param toUser
	 *            接受者openid,必填
	 * @param tradeDateTime
	 *            提交订单的时间 ,必填
	 * @param orderType
	 *            订单类型描述,必填
	 * @param customer
	 *            客户信息，必填。包括有姓名、电话和地址
	 * @param orderInfo
	 *            订单信息，必填。包括预约时间和收运内容
	 * @param link
	 *            消息通知点击跳转的链接url，非必填
	 * @param header
	 *            消息头，非必填。 默认值：“您收到了一条新的订单”
	 * @param remark
	 *            备注,非必填
	 * @return
	 */
	public static boolean sendNewOrder(String toUser, String tradeDateTime, String orderType, String customer,
			String orderInfo, String link, String header, String remark) {
		TemplateMessage message = new TemplateMessage();
		message.setTouser(toUser);
		message.setTemplateId(WechatConstants.TEMPLATE_ID_ORDER_NEW);
		message.setUrl(link);
		List<String> messageList = new ArrayList<String>();
		messageList.add(header == null ? "您收到了一条新的订单" : header);
		messageList.add(tradeDateTime == null ? "" : tradeDateTime);
		messageList.add(orderType == null ? "" : orderType);
		messageList.add(customer == null ? "" : customer);
		messageList.add("预约信息");
		messageList.add(orderInfo == null ? "" : orderInfo);
		messageList.add(remark == null ? "" : remark);
		message.setMessageList(messageList);
		return sendMessagerRepetition(message);
	}

	/**
	 * 发送订单完成通知
	 * 
	 * @param toUser
	 *            接受者openid,必填
	 * @param orderNo
	 *            订单编号,必填
	 * @param orderTime
	 *            订单完成时间,必填
	 * @param link
	 *            链接地址，非必填
	 * @param header
	 *            消息头，非必填，默认值：“尊敬的客户，您的订单已完成”
	 * @param 备注，非必填
	 * @return
	 */
	public static boolean sendOrderFinish(String toUser, String orderNo, String orderTime, String link, String header,
			String remark) {
		TemplateMessage message = new TemplateMessage();
		message.setTouser(toUser);
		message.setTemplateId(WechatConstants.TEMPLATE_ID_ORDER_FINISH);
		message.setUrl(link);
		List<String> messageList = new ArrayList<String>();
		messageList.add(header == null ? "尊敬的客户，您的订单已完成" : header);
		messageList.add(orderNo == null ? "" : orderNo);
		messageList.add(orderTime == null ? "" : orderTime);
		messageList.add(remark == null ? "" : remark);
		message.setMessageList(messageList);
		return sendMessagerRepetition(message);
	}

	/**
	 * 发送订单状态变更通知
	 * 
	 * @param toUser
	 *            接受者openid,必填
	 * @param orderNo
	 *            订单编号,必填
	 * @param status
	 *            订单状态，必填
	 * @param link
	 *            链接地址，非必填
	 * @param header
	 *            消息头，非必填，默认值：“尊敬的客户，您的订单状态已变更”
	 * @param remark
	 *            备注，非必填
	 * @return
	 */
	public static boolean sendOrderStatus(String toUser, String orderNo, String status, String link, String header,
			String remark) {
		TemplateMessage message = new TemplateMessage();
		message.setTouser(toUser);
		message.setTemplateId(WechatConstants.TEMPLATE_ID_ORDER_STATUS);
		message.setUrl(link);
		List<String> messageList = new ArrayList<String>();
		messageList.add(header == null ? "您的订单状态已变更。物业人员请及时称重" : header);
		messageList.add(orderNo == null ? "" : orderNo);
		messageList.add(status == null ? "" : status);
		messageList.add(remark == null ? "" : remark);
		message.setMessageList(messageList);
		return sendMessagerRepetition(message);
	}

	/**
	 * 发送预约成功通知
	 * 
	 * @param toUser
	 *            接收者openid必填
	 * @param handleTime
	 *            预约时间，必填
	 * @param content
	 *            预约内容，必填
	 * @param status
	 *            预约状态,必填
	 * @param link
	 *            链接，非必填
	 * @param header
	 *            开头语，非必填
	 * @param remark
	 *            备注，非必填
	 * @return
	 */
	public static boolean sendBookingSuccess(String toUser, String handleTime, String content, String status,
			String link, String header, String remark) {
		TemplateMessage message = new TemplateMessage();
		message.setTouser(toUser);
		message.setTemplateId(WechatConstants.TEMPLATE_ID_BOOKING_SUCCESS);
		message.setUrl(link);
		List<String> messageList = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		messageList.add(header == null ? "尊敬的客户，您在" + format.format(new Date())
				+ "提交的预约订单已提交成功。收运单位会在24小时内与您联系并处理您的预约，3个工作日内完成收运(具体收运时间以收运单位确认为准)。请耐心等待，保持电话畅通。" : header);
		messageList.add(handleTime == null ? "" : handleTime);
		messageList.add(content == null ? "" : content);
		messageList.add(status == null ? "" : status);
		messageList.add(remark == null ? "" : remark);
		message.setMessageList(messageList);
		return sendMessagerRepetition(message);
	}

	/**
	 * 检测发送模板消息是否成功,若失败仅会重发一次
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static boolean sendMessagerRepetition(TemplateMessage data) {
		try {
			TemplateMessage resultData = sendMessage(data);
			if (resultData != null && resultData.getErrcode() != null
					&& WechatConstants.STATUS_SUCCESS_OK == resultData.getErrcode()) {
				return true;
			} else {
				if (WechatConstants.ERROR_CODE_ACCESS_TOKEN.indexOf(resultData.getErrcode() + "") > -1) {// ACCESS_TOKEN过期
					WechatUtlis.initAccessToken();// 刷新AccessToken；
					resultData = sendMessage(data);
					if (resultData != null && WechatConstants.STATUS_SUCCESS_OK == resultData.getErrcode()) {
						return true;
					}
				} else {
					LOGGER.error("发送微信公众号模板出现错误：" + resultData.getErrcode() + "," + resultData.getErrmsg());
					LOGGER.error("发送微信公众号模板出现错误,参数：" + JacksonUtils.getJsonCamelLower(data));
				}
			}
		} catch (Exception e) {
			LOGGER.error("发送微信公众号模板出现错误：" + e.getMessage());
		}
		return false;
	}

	/**
	 * 发送模板通知统一的方法
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static TemplateMessage sendMessage(TemplateMessage data) throws Exception {
		List<Element> templateList = WechatCacher.getTemplateElements(data.getTemplateId());
		if (templateList != null && templateList.size() > 0 && data.getMessageList() != null
				&& data.getMessageList().size() == templateList.size()) {
			Map<String, TemplateMessage.Message> messages = new HashMap<String, TemplateMessage.Message>();
			TemplateMessage.Message message = null;
			for (int i = 0; i < templateList.size(); i++) {
				message = new TemplateMessage.Message();
				message.setValue(data.getMessageList().get(i));
				Element ele = templateList.get(i);
				message.setColor(ele.getText());
				messages.put(ele.getName(), message);
			}
			data.setData(messages);
			data.setMessageList(null);// 自定义的参数不必再提交给微信
			if (WechatUtlis.checkAccessTokenValid()) {
				String url = WechatUtlis.buildTemplateSend(WechatCacher.getAccessToken().getAccessToken());
				String json = HttpUtils.httpPost(url, JacksonUtils.getJsonUnderLower(data));
				return JacksonUtils.getObjectCamelLower(TemplateMessage.class, json);
			}
		} else {
			throw new Exception("没有找到对应的模板,或参数不全");
		}
		return null;
	}
}