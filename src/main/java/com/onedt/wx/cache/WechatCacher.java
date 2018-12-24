package com.onedt.wx.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.onedt.wx.entity.AccessToken;
import com.onedt.wx.entity.JsTicket;

/**
 * 微信数据缓存数据工具类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-12 下午8:53:47
 */
public class WechatCacher {
    /** 微信公众号的Access_Token */
    private static AccessToken accessToken = new AccessToken();
    /** 微信公众号的JsTicket */
    private static JsTicket jsTicket = new JsTicket();
    /** 模板消息可填参数集合 */
    private static final Map<String, List<Element>> templateEles = new HashMap<String, List<Element>>();

    /**
     * 获取缓存中的模板参数
     * 
     * @param templateId
     * @return
     */
    public static List<Element> getTemplateElements(String templateId) {
        return templateEles.get(templateId);
    }

    /**
     * 往缓存添加模板参数
     * 
     * @param templateId
     * @param elements
     */
    public static void putTemplateElements(String templateId, List<Element> elements) {
        templateEles.put(templateId, elements);
    }

    /**
     * 获取缓存Access_Token
     * 
     * @return
     */
    public static AccessToken getAccessToken() {
        return accessToken;
    }

    /**
     * 添加缓存Access_Token
     * 
     * @param accessToken
     */
    public static void setAccessToken(AccessToken accessToken) {
        WechatCacher.accessToken = accessToken;
    }

    /**
     * 获取缓存JsTicket
     * 
     * @return
     */
    public static JsTicket getJsTicket() {
        return jsTicket;
    }

    /**
     * 添加缓存JsTicket
     * 
     * @param accessToken
     */
    public static void setJsTicket(JsTicket jsTicket) {
        WechatCacher.jsTicket = jsTicket;
    }
}
