package com.onedt.wx.entity;

import java.io.Serializable;

import com.onedt.wx.constants.WechatConstants;

/**
 * 微信公众号所有对象实体共同属性
 * 
 * @author Tian Bing
 * @version 1.0
 * @date 2017-4-13 上午9:43:41
 */
public abstract class BaseWechatEntity implements Serializable {
    protected static final long serialVersionUID = 1L;
    /** 错误代码 */
    protected Integer errcode;
    /** 错误信息 */
    protected String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean isSuccess() {
        if (this.errcode == null)
            return false;
        return WechatConstants.STATUS_SUCCESS_OK == this.errcode;
    }

}
