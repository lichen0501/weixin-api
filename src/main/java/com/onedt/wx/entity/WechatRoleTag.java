package com.onedt.wx.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 业务系统角色与公众号标签的对应关系
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-22 下午3:12:04
 */
public class WechatRoleTag implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tagName;// 角色即标签名字
    private Integer tagId;// 微信公众号标签ID
    private Integer roleType;// //角色类型 0：WEB后台角色 1：微信公众号标签
    private String remark;  // 备注
    private Date createTime;// 创建时间
    private List<WechatSysMenu> menuList;// 菜单集合

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<WechatSysMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<WechatSysMenu> menuList) {
        this.menuList = menuList;
    }

}
