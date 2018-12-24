package com.onedt.wx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onedt.wx.cache.WechatCacher;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.WechatUserInfo;
import com.onedt.wx.entity.UserOpenid;
import com.onedt.wx.service.IWechatUserService;
import com.onedt.wx.utils.HttpUtils;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.WechatUtlis;

/**
 * 微信用户业务接口
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-13 下午4:08:09
 */
@Service
public class WechatUserServiceImpl implements IWechatUserService {
    private static Logger logger = LoggerFactory.getLogger(WechatUserServiceImpl.class);

    /**
     * 批量查询公众号已关注的用户openid
     * 
     * @param openid
     * @return
     * @throws Exception
     */
    public List<String> queryUsers() throws Exception {
        List<String> result = null;
        if (WechatUtlis.checkAccessTokenValid()) {
            result = new ArrayList<String>();
            String nextOpenid = "";
            String url = WechatUtlis.buildGetUserOenidUrl(nextOpenid, WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpGet(url);
            if (StringUtils.isNotEmpty(json)) {
                UserOpenid resultData = JacksonUtils.getObjectUnderLower(UserOpenid.class, json);
                if (resultData != null && resultData.getData() != null && resultData.getCount() != null && resultData.getCount() > 0) {
                    int size = resultData.getCount() % WechatConstants.WECHAT_SUB_COUNT == 0 ? resultData.getCount() / WechatConstants.WECHAT_SUB_COUNT
                            : (resultData.getCount() / WechatConstants.WECHAT_SUB_COUNT + 1);
                    if (resultData.getData().getOpenid() != null && resultData.getData().getOpenid().size() > 0) {
                        for (String str : resultData.getData().getOpenid()) {
                            if (StringUtils.isNotEmpty(str)) {
                                result.add(str);
                            }
                        }
                    }
                    if (size > 1) {
                        for (int i = 0; i < size; i++) {
                            url = WechatUtlis.buildGetUserOenidUrl(resultData.getNextOpenid(), WechatCacher.getAccessToken().getAccessToken());
                            json = HttpUtils.httpGet(url);
                            if (StringUtils.isNotEmpty(json)) {
                                resultData = JacksonUtils.getObjectUnderLower(UserOpenid.class, json);
                                if (resultData.getData().getOpenid() != null && resultData.getData().getOpenid().size() > 0) {
                                    for (String str : resultData.getData().getOpenid()) {
                                        if (StringUtils.isNotEmpty(str)) {
                                            result.add(str);
                                        }
                                    }
                                } else {
                                    logger.error("[queryUsers]循环查询公众号已关注的用户openid失败,url=" + url);
                                    logger.error("[queryUsers]失败信息：" + (resultData == null ? json : (resultData.getErrmsg() + "," + resultData.getErrcode())));
                                }
                            } else {
                                logger.error("[queryUsers]循环查询公众号已关注的用户openid失败,json结果为空，url=" + url);
                                break;
                            }
                        }
                    }
                } else {
                    logger.error("[queryUsers]批量查询公众号已关注的用户openid失败,url=" + url);
                    logger.error("[queryUsers]失败信息：" + (resultData == null ? json : (resultData.getErrmsg() + "," + resultData.getErrcode())));
                }
            } else {
                logger.error("[queryUsers]批量查询公众号已关注的用户失败,json结果为空,url=" + url);
            }
        }
        logger.info("[queryUsers]总共获取公众号已关注用户的openid的个数为：" + result.size());
        return result;
    }

    /**
     * 网页授权获取用户信息，去微信服务器查询此用户信息
     * 
     * @param openid
     * @param accessToken
     * @return
     * @throws Exception
     */
    public WechatUserInfo getOauthInfo(String openid, String accessToken) throws Exception {
        String url = WechatUtlis.buildOauthUserinfoUrl(openid, accessToken);
        String json = HttpUtils.httpGet(url);
        WechatUserInfo result = null;
        if (StringUtils.isNotEmpty(json)) {
            result = JacksonUtils.getObjectUnderLower(WechatUserInfo.class, json);
        }
        return result;
    }

    /**
     * 用户关注后去微信服务器获取用户信息
     * 
     * @param openid
     * @param accessToken
     * @return
     * @throws Exception
     */
    public WechatUserInfo getUser(String openid) throws Exception {
        WechatUserInfo result = null;
        if (WechatUtlis.checkAccessTokenValid()) {
            String url = WechatUtlis.buildSubUserinfoUrl(openid, WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpGet(url);
            if (StringUtils.isNotEmpty(json)) {
                result = JacksonUtils.getObjectUnderLower(WechatUserInfo.class, json);
            }
        }
        return result;
    }

    /**
     * 网页授权，去微信服务器查询此用户的openid
     * 
     * @param code
     * @return
     * @throws Exception
     */
    public WechatUserInfo getOauthOpenid(String code) throws Exception {
        String json = HttpUtils.httpGet(WechatUtlis.buildOauthOpenidUrl(code));
        WechatUserInfo oauthInfo = null;
        if (StringUtils.isNotEmpty(json)) {
            oauthInfo = JacksonUtils.getObjectUnderLower(WechatUserInfo.class, json);
        }
        return oauthInfo;
    }

}
