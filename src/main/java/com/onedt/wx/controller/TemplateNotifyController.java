package com.onedt.wx.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onedt.wx.entity.WechatSysUser;
import com.onedt.wx.entity.sys.CleanBookingSuccess;
import com.onedt.wx.entity.sys.CleanNewOrder;
import com.onedt.wx.entity.sys.CleanOrderChange;
import com.onedt.wx.entity.sys.CleanOrderFinish;
import com.onedt.wx.entity.sys.ReqBodyObj;
import com.onedt.wx.entity.sys.RespBodyObj;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.TemplateUtils;

/**
 * 微信模板通知发送接口
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-12-10 上午10:07:06
 */
@RestController
@RequestMapping("/wx/temp")
public class TemplateNotifyController {
    private Logger logger = LoggerFactory.getLogger(TemplateNotifyController.class);

    /**
     * 预约成功通知
     * 
     * @return
     */
    @PostMapping("/bookingOk")
    public RespBodyObj<?> bookingSuccess(@RequestBody ReqBodyObj<CleanBookingSuccess> pojo) {
        try {
            if (pojo != null && pojo.getData() != null) {
                CleanBookingSuccess goodsObj = pojo.getData();
                String handleTime = goodsObj.getHandleTime();
                String content = goodsObj.getContent();
                String toUser = goodsObj.getToUser();
                if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(handleTime) && StringUtils.isNotEmpty(toUser)) {
                    if (TemplateUtils.sendBookingSuccess(toUser, handleTime, content, "预约成功", goodsObj.getLink(), goodsObj.getHeader(), goodsObj.getRemark())) {
                        return RespBodyObj.ok();
                    }
                } else {
                    logger.error("[bookingSuccess]预约成功通知模板发生异常,参数为空。handleTime=" + handleTime + ",content=" + content + ",toUser=" + toUser);
                }
            } else {
                logger.error("[bookingSuccess]预约成功通知模板发生异常,参数为空");
            }
        } catch (Exception e) {
            logger.error("[bookingSuccess]预约成功通知模板发生异常");
            logger.error("[bookingSuccess]异常信息：" + e.getMessage());
        }
        return RespBodyObj.error();
    }

    /**
     * 新的订单通知
     * 
     * @throws JsonProcessingException
     */
    @PostMapping("/sendNotifyNewOrder")
    public RespBodyObj<?> sendNotifyNewOrder(@RequestBody ReqBodyObj<CleanNewOrder> pojo) throws JsonProcessingException {
        CleanNewOrder cleanNewOrder = pojo.getData();
        if (cleanNewOrder != null) {
            if (TemplateUtils.sendNewOrder(cleanNewOrder.getToUser(), cleanNewOrder.getTradeDateTime(), cleanNewOrder.getOrderType(), cleanNewOrder.getCustomer(), cleanNewOrder.getOrderInfo(),
                    cleanNewOrder.getLink(), cleanNewOrder.getHeader(), cleanNewOrder.getRemark())) {
                return RespBodyObj.ok();
            }
        }
        return RespBodyObj.error("获取新通知失败,参数" + JacksonUtils.getJsonCamelLower(cleanNewOrder));
    }

    /**
     * 
     * @throws JsonProcessingException
     * @Title: sendNotifyOrderChange @Description: TODO(订单状态变更时候的消息通知) @param @param pojo @param @return 设定文件 @return ResponseData<?> 返回类型 @throws
     */
    @PostMapping("/sendNotifyOrderChange")
    public RespBodyObj<?> sendNotifyOrderChange(@RequestBody ReqBodyObj<CleanOrderChange> pojo) throws JsonProcessingException {
        CleanOrderChange cleanOrderChange = pojo.getData();
        if (cleanOrderChange != null) {
            if (TemplateUtils.sendOrderStatus(cleanOrderChange.getToUser(), cleanOrderChange.getOrderNo(), cleanOrderChange.getStatus(), cleanOrderChange.getLink(), cleanOrderChange.getHeader(),
                    cleanOrderChange.getRemark())) {
                return RespBodyObj.ok();
            }
        }
        return RespBodyObj.error("获取订单状态变更失败,参数：" + JacksonUtils.getJsonCamelLower(cleanOrderChange));
    }

    /**
     * 订单完成通知
     * 
     * @throws JsonProcessingException
     */
    @PostMapping("/sendNotifyOrderFinish")
    public RespBodyObj<?> sendNotifyOrderFinish(@RequestBody ReqBodyObj<CleanOrderFinish> pojo) throws JsonProcessingException {
        CleanOrderFinish cleanOrderFinish = pojo.getData();
        if (cleanOrderFinish != null) {
            if (TemplateUtils.sendOrderFinish(cleanOrderFinish.getToUser(), cleanOrderFinish.getOrderNo(), cleanOrderFinish.getOrderTime(), cleanOrderFinish.getLink(), cleanOrderFinish.getHeader(),
                    cleanOrderFinish.getRemark())) {
                return RespBodyObj.ok();
            }
        }
        return RespBodyObj.error("订单完成通知失败,参数：" + JacksonUtils.getJsonCamelLower(cleanOrderFinish));
    }

    /**
     * 业务系统通知微信平台下发验证码
     * 
     * @param pojo
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/sendCode")
    public RespBodyObj<?> sendCode(@RequestBody ReqBodyObj<WechatSysUser> pojo) throws JsonProcessingException {
        String toUser = pojo.getData().getOpenId();
        String code = pojo.getData().getSmsCode();
        if (StringUtils.isNoneEmpty(toUser) && StringUtils.isNotEmpty(code)) {
            if (TemplateUtils.sendCodeMessage(toUser, code, null, null, null)) {
                return RespBodyObj.ok();
            }
        }
        return RespBodyObj.error("发送验证码失败,参数:" + JacksonUtils.getJsonCamelLower(pojo));
    }

    /**
     * 微信页面端请求后台服务器下发验证码
     * 
     * @param pojo
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/getCode")
    public RespBodyObj<?> getCode(@RequestBody RespBodyObj<WechatSysUser> pojo) throws JsonProcessingException {
        WechatSysUser data = pojo.getData();
        if (data != null && StringUtils.isNotEmpty(data.getOpenId())) {

            return RespBodyObj.ok();
        }
        return RespBodyObj.error("微信页面端请求后台服务器下发验证码失败,参数:" + JacksonUtils.getJsonCamelLower(pojo));

    }
}
