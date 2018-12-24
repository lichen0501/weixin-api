package com.onedt.wx.entity.sys;

import java.io.Serializable;

/**
 * 响应报文体
 * 
 * @author hy
 */
public class RespBodyObj<T> implements Serializable {
    private static final long serialVersionUID = -6280679303183251413L;

    private Integer code;
    private String msg;
    private T data;

    public RespBodyObj() {

    }

    public RespBodyObj(HttpCode code) {
        this.code = code.value();
        this.msg = code.msg();
    }

    public RespBodyObj(HttpCode code, T data) {
        this.code = code.value();
        this.msg = code.msg();
        this.data = data;
    }

    public RespBodyObj(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespBodyObj(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> RespBodyObj<T> error() {
        return error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> RespBodyObj<T> error(String msg) {
        return error(HttpCode.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static <T> RespBodyObj<T> error(HttpCode code) {
        return new RespBodyObj<>(code);
    }

    public static <T> RespBodyObj<T> error(int code, String msg) {
        return new RespBodyObj<T>(code, msg);
    }

    public static <T> RespBodyObj<T> ok() {
        return new RespBodyObj<T>(HttpCode.OK);
    }

    public static <T> RespBodyObj<T> ok(String msg) {
        RespBodyObj<T> respBody = new RespBodyObj<T>(HttpCode.OK.value(), msg);
        return respBody;
    }

    public static <T> RespBodyObj<T> ok(T data) {
        RespBodyObj<T> respBody = new RespBodyObj<T>(HttpCode.OK, data);
        return respBody;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
