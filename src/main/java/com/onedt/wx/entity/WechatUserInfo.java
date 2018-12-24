package com.onedt.wx.entity;

import java.util.List;

/**
 * 网页授权时用户信息实体和用户关注后用户信息实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-15 下午6:07:14
 */
public class WechatUserInfo extends BaseWechatEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String accessToken;// accessToken
    private String scope;// 用户授权的作用域
    private String code;// 网页授权code,用来换取用户openid
    private String expiresIn;// accessToken的有效时间，单位是秒
    private String refreshToken;// 用户刷新access_token
    private String unionid;// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String openid;// 用户唯一标识
    private String nickname;// 用户昵称
    private String sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String language;// 用户个人资料填写的省份
    private String country;// 国家，如中国为CN
    private String province;// 用户个人资料填写的省份
    private String city;// 普通用户个人资料填写的城市
    private String headimgurl;// 用户头像
    private String redirectUri;// 网页授权时获取code后重定向的路径;
    private String[] privilege;// 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    private Long subscribeTime;// 关注时间
    private String subscribe;// 是否关注了公众号
    private String remark;// 备注
    private Integer groupid;// 用户所在的分组ID(旧版接口)
    private List<Integer> tagidList;// 用户被打上的标签ID列表

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public List<Integer> getTagidList() {
        return tagidList;
    }

    public void setTagidList(List<Integer> tagidList) {
        this.tagidList = tagidList;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

}
