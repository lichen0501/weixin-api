package com.onedt.wx.service;

import java.util.List;

import com.onedt.wx.entity.Signature;
import com.onedt.wx.entity.WechatOauthInfo;

/**
 * 微信公众页面授权业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月8日 下午5:32:19
 */
public interface IWechatOauthService {
    /**
     * 先获取授权的code值
     * 
     * @param info
     * @return WechatOauthInfo
     * @throws Exception
     */
    public WechatOauthInfo getOpenid(WechatOauthInfo info) throws Exception;

    /**
     * 获取网页jssdk签名
     * 
     * @param info
     * @return Signature
     */
    public Signature getSignature(WechatOauthInfo info) throws Exception;

    /**
     * 同步微信公众号用户数据,保持微信已关注用户与后台数据库一致
     * 
     * @return
     */
    public List<String> syncUsers() throws Exception;
}
