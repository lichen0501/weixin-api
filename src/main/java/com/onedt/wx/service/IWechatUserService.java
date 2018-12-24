package com.onedt.wx.service;

import java.util.List;

import com.onedt.wx.entity.WechatUserInfo;

/**
 * 微信用户业务接口
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-13 下午4:08:09
 */
public interface IWechatUserService {
    /**
     * 批量查询公众号已关注的用户openid
     * 
     * @return
     * @throws Exception
     */
    public List<String> queryUsers() throws Exception;

    /**
     * 网页授权获取用户信息，去微信服务器查询此用户信息
     * 
     * @param openid
     * @param accessToken
     * @return
     * @throws Exception
     */
    public WechatUserInfo getOauthInfo(String openid, String accessToken) throws Exception;

    /**
     * 用户关注后去微信服务器获取用户信息
     * 
     * @param openid
     * @param accessToken
     * @return
     * @throws Exception
     */
    public WechatUserInfo getUser(String openid) throws Exception;

    /**
     * 网页授权，去微信服务器查询此用户的openid
     * 
     * @param code
     * @return
     * @throws Exception
     */
    public WechatUserInfo getOauthOpenid(String code) throws Exception;
}
