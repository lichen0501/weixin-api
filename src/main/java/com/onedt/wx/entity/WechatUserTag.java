package com.onedt.wx.entity;

import java.util.List;

/**
 * 微信用户标签实体类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 上午11:58:05
 */
public class WechatUserTag extends BaseWechatEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 用户Openid集合 */
    private List<String> openidList;
    /** 标签id */
    private Integer tagid;
    /** 用户唯一标识 */
    private String openid;
    /** 用户标签id集合 */
    private List<Integer> tagidList;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public List<Integer> getTagidList() {
        return tagidList;
    }

    public void setTagidList(List<Integer> tagidList) {
        this.tagidList = tagidList;
    }

    public List<String> getOpenidList() {
        return openidList;
    }

    public void setOpenidList(List<String> openidList) {
        this.openidList = openidList;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

}
