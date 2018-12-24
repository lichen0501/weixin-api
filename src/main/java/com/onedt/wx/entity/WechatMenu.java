package com.onedt.wx.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 微信公众号菜单实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 下午2:12:40
 */
public class WechatMenu extends BaseWechatEntity {
    private static final long serialVersionUID = 1L;

    private List<Menu> button;// 菜单集合

    private Matchrule matchrule;// 菜单与标签等条件的对应关系

    private String menuid;// 菜单标识

    private WechatMenu menu;// 查询所有菜单时用,普通菜单用

    private List<WechatMenu> conditionalmenu;//// 查询所有菜单时用,个性化菜单用

    /**
     * 菜单与标签等条件的对应绑定关系
     * 
     * @author
     * @version 1.0
     * @date 2017-4-17 下午5:18:12
     */
    public static class Matchrule implements Serializable {
        private static final long serialVersionUID = 1L;
        private String tagId;// 标签id
        private String sex;// 性别
        private String country;// 国家
        private String province;// 省份
        private String city;// 城市
        private String clientPlatformType;// 客户端平台类型
        private String language;// 语言

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getClientPlatformType() {
            return clientPlatformType;
        }

        public void setClientPlatformType(String clientPlatformType) {
            this.clientPlatformType = clientPlatformType;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    /**
     * 微信菜单实体
     */
    public static class Menu implements Serializable {
        private static final long serialVersionUID = 1L;

        private String type;// 类型：click，view，miniprogram
        private String name;// 名称
        private String key;// 菜单标识
        private String url;// 链接地址
        private String appid;// 小程序appid
        private String pagepath;// 小程序页面
        private List<Menu> subButton;// 子菜单

        public List<Menu> getSubButton() {
            return subButton;
        }

        public void setSubButton(List<Menu> subButton) {
            this.subButton = subButton;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPagepath() {
            return pagepath;
        }

        public void setPagepath(String pagepath) {
            this.pagepath = pagepath;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public List<Menu> getButton() {
        return button;
    }

    public void setButton(List<Menu> button) {
        this.button = button;
    }

    public Matchrule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(Matchrule matchrule) {
        this.matchrule = matchrule;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public WechatMenu getMenu() {
        return menu;
    }

    public void setMenu(WechatMenu menu) {
        this.menu = menu;
    }

    public List<WechatMenu> getConditionalmenu() {
        return conditionalmenu;
    }

    public void setConditionalmenu(List<WechatMenu> conditionalmenu) {
        this.conditionalmenu = conditionalmenu;
    }

}
