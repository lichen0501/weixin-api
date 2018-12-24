package com.onedt.wx.entity.sys;

import java.io.Serializable;

/**
 * 垃圾预约新增订单通知
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月18日 下午4:26:49
 */
public class CleanNewOrder implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String toUser;
    private String tradeDateTime;
    private String orderType;
    private String customer;
    private String orderInfo;
    private String link;
    private String header;
    private String remark;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTradeDateTime() {
        return tradeDateTime;
    }

    public void setTradeDateTime(String tradeDateTime) {
        this.tradeDateTime = tradeDateTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
