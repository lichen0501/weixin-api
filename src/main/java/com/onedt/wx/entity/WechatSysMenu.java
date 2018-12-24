package com.onedt.wx.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.WechatUtlis;

/**
 * 业务系统菜单实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-21 下午5:51:32
 */
public class WechatSysMenu implements Serializable {

    private Long id;
    // 父菜单ID，一级菜单为0
    private Long parentId;
    // 菜单名称
    private String name;
    // 菜单URL
    private String url;
    // 菜单类型 0：目录 1：菜单 2：按钮
    private Integer menuType;
    // 排序
    private Integer orderNum;

    private List<WechatSysMenu> childs;// 子菜单

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<WechatSysMenu> getChilds() {
        return childs;
    }

    public void setChilds(List<WechatSysMenu> childs) {
        this.childs = childs;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws JsonProcessingException {
        // List<RoleTag> list = new ArrayList<RoleTag>();
        WechatRoleTag r1 = new WechatRoleTag();
        r1.setTagName("普通用户");
        r1.setId(1L);
        List<WechatSysMenu> menus = new ArrayList<WechatSysMenu>();
        WechatSysMenu s1 = new WechatSysMenu();
        s1.setOrderNum(4);
        s1.setId(1L);
        s1.setName("菜单一");
        s1.setMenuType(2);
        WechatSysMenu s2 = new WechatSysMenu();
        s2.setOrderNum(1);
        s2.setId(2L);
        s2.setName("菜单二");
        s2.setMenuType(1);
        WechatSysMenu s3 = new WechatSysMenu();
        s3.setId(3L);
        s3.setName("菜单三");
        s3.setMenuType(2);
        s3.setParentId(2L);
        s3.setOrderNum(4);
        WechatSysMenu s4 = new WechatSysMenu();
        s4.setId(4L);
        s4.setName("菜单四");
        s4.setMenuType(1);
        s4.setUrl("baidu.com");
        s4.setParentId(2L);
        s4.setOrderNum(2);
        menus.add(s4);
        menus.add(s1);
        menus.add(s2);
        menus.add(s3);
        r1.setMenuList(menus);
        WechatMenu wechatMenu = WechatUtlis.convertSysMenu(r1.getMenuList());
        System.out.println("-----------------");
        System.out.println(JacksonUtils.getJsonUnderLower(wechatMenu));

    }
}
