package com.onedt.wx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务系统的角色，相当于微信公众号的用户标签
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-21 下午5:53:21
 */
public class WechatSysRole implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String tagName;// 角色，即标签名
    private Integer tagId;// 标签标识
    private Long id;// 主键
    private Integer roleType;// 角色类型 0：WEB后台角色 1：微信公众号标签
    private String remark;// 备注
    private Date createTime;// 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

}
