package com.onedt.wx.entity.sys;

import java.io.Serializable;

/**
 * 预约成功通知模板消息实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-6-26 下午6:42:47
 */
public class CleanBookingSuccess implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String toUser;// 接收者openid
    private String handleTime;// 预约时间
    private String content;// 预约内容
    private String status;// 预约状态
    private String link;// 点击链接
    private String header;// 消息开头语
    private String remark;// 备注

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
