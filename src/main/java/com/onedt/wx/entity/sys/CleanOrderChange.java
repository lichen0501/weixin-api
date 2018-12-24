package com.onedt.wx.entity.sys;

import java.io.Serializable;

/**
 * 垃圾预约订单状态改变的通知
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月18日 下午4:43:02
 */
public class CleanOrderChange implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String toUser;
    private String orderNo;
    private String status;
    private String link;
    private String header;
    private String remark;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
