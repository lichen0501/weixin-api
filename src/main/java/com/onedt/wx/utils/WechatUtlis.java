package com.onedt.wx.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onedt.wx.cache.WechatCacher;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.AccessToken;
import com.onedt.wx.entity.JsTicket;
import com.onedt.wx.entity.Signature;
import com.onedt.wx.entity.WechatMenu;
import com.onedt.wx.entity.WechatMenu.Menu;
import com.onedt.wx.entity.WechatRoleTag;
import com.onedt.wx.entity.WechatSysMenu;

/**
 * 微信公众号的工具类(获取token,ticket,sigature)
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-13 上午9:26:38
 */
public class WechatUtlis {
    public static final Logger logger = LoggerFactory.getLogger(WechatUtlis.class);

    /**
     * 初始化创建（刷新）AccessToken
     * 
     * @return
     */
    public static boolean initAccessToken() {
        String appId = WechatConstants.APP_ID;
        String secret = WechatConstants.SECRET;
        try {
            AccessToken accessToken = buildAccessToken(appId, secret);
            if (accessToken != null && accessToken.getAccessToken() != null && accessToken.getExpiresIn() > 0) {
                long time = accessToken.getExpiresIn();
                time = (long) (time * 0.9);
                accessToken.setValidity(time);
                accessToken.setLastRequest(System.currentTimeMillis());
                WechatCacher.setAccessToken(accessToken);
                return true;
            } else {
                logger.error("[initAccessToken]初始化AccessToken失败，原因：" + JacksonUtils.getJsonCamelLower(accessToken));
            }
        } catch (Exception e) {
            logger.error("[initAccessToken]初始化AccessToken发生异常");
        }
        return false;
    }

    /**
     * 创建生成AccessToken
     * 
     * @param appId
     * @param secret
     * @return
     * @throws Exception
     */
    public static AccessToken buildAccessToken(String appId, String secret) throws Exception {
        AccessToken accessToken = null;
        try {
            String json = HttpUtils.httpGet(buildAccessTokenUrl(appId, secret));// 返回json数据
            accessToken = JacksonUtils.getObjectUnderLower(AccessToken.class, json);
        } catch (Exception e) {
            logger.error("[buildAccessToken]获取AccessToken发生异常：" + e.getMessage());
            throw e;
        }
        return accessToken;
    }

    /**
     * 初始化创建（刷新）公众号页面调用jssdk的凭证
     * 
     * @return
     */
    public static boolean initJsTicket() {
        try {
            JsTicket ticket = buildJsTicket();
            if (ticket != null && ticket.getTicket() != null && ticket.getExpiresIn() > 0) {
                long time = ticket.getExpiresIn();
                time = (long) (time * 0.9);
                ticket.setValidity(time);
                ticket.setLastRequest(System.currentTimeMillis());
                WechatCacher.setJsTicket(ticket);
                return true;
            }
        } catch (Exception e) {
            logger.error("[initJsTicket]获取JsTicket发生异常：" + e.getMessage());
        }
        return false;
    }

    /**
     * 创建公众号页面调用jssdk的凭证
     * 
     * @return
     * @throws Exception
     */
    public static JsTicket buildJsTicket() throws Exception {
        JsTicket ticket = null;
        if (checkAccessTokenValid()) {
            String url = buildJsTicketUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpGet(url);// 返回json数据
            ticket = JacksonUtils.getObjectUnderLower(JsTicket.class, json);
        }
        return ticket;
    }

    /**
     * 封装接口调用凭据请求地址
     * 
     * @param appId
     * @param secret
     * @return
     */
    public static String buildAccessTokenUrl(String appId, String secret) {
        String urlWecaht = WechatConstants.URL_GET_ACCESS_TOKEN;
        if (urlWecaht != null) {
            if (urlWecaht.indexOf(WechatConstants.PARAM_APPID) > -1) {
                urlWecaht = urlWecaht.replace(WechatConstants.PARAM_APPID, appId);
            }
            if (urlWecaht.indexOf(WechatConstants.PARAM_SECRET) > -1) {
                urlWecaht = urlWecaht.replace(WechatConstants.PARAM_SECRET, secret);
            }
        }
        return urlWecaht;
    }

    /**
     * 封装网页授权的获取code请求地址
     * 
     * @param url 需要utf-8编码过
     * @return
     */
    public static String buildOauthUrl(String url) {
        String urlWecaht = WechatConstants.URL_AUTH_GET_CODE;
        if (urlWecaht.indexOf(WechatConstants.PARAM_APPID) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_APPID, WechatConstants.APP_ID);
        }
        if (urlWecaht.indexOf(WechatConstants.PARAM_REDIRECT_URI) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_REDIRECT_URI, url);
        }
        if (urlWecaht.indexOf(WechatConstants.PARAM_SCOPE) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_SCOPE, WechatConstants.SCOPE_BASE);
        }
        if (urlWecaht.indexOf(WechatConstants.PARAM_STATE) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_STATE, "");
        }
        return urlWecaht;
    }

    /**
     * 封装网页授权的获取openid请求地址
     * 
     * @param code
     * @return
     */
    public static String buildOauthOpenidUrl(String code) {
        String urlWecaht = WechatConstants.URL_AUTH_GET_OPENID;
        if (urlWecaht.indexOf(WechatConstants.PARAM_APPID) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_APPID, WechatConstants.APP_ID);
        }
        if (urlWecaht.indexOf(WechatConstants.PARAM_SECRET) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_SECRET, WechatConstants.SECRET);
        }
        if (urlWecaht.indexOf(WechatConstants.PARAM_CODE) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_CODE, code);
        }
        return urlWecaht;
    }

    /**
     * 封装网页授权的获取用户信息请求地址
     * 
     * @param openid
     * @param accessToken
     * @return
     */
    public static String buildOauthUserinfoUrl(String openid, String accessToken) {
        String urlWecaht = WechatConstants.URL_GET_USER_INFO_OAUTH;
        urlWecaht = replaceAccessTokenUrl(urlWecaht, accessToken);
        urlWecaht = replaceOpenidUrl(urlWecaht, openid);
        return urlWecaht;
    }

    /**
     * 封装用户关注公众号后的获取用户信息请求地址
     * 
     * @param openid
     * @param accessToken
     * @return
     */
    public static String buildSubUserinfoUrl(String openid, String accessToken) {
        String urlWecaht = WechatConstants.URL_GET_USER_INFO;
        urlWecaht = replaceAccessTokenUrl(urlWecaht, accessToken);
        urlWecaht = replaceOpenidUrl(urlWecaht, openid);
        return urlWecaht;
    }

    /**
     * 封装查询已注公众号后的用户openid集合请求地址
     * 
     * @param nextOpenid
     * @param accessToken
     * @return
     */
    public static String buildGetUserOenidUrl(String nextOpenid, String accessToken) {
        String urlWecaht = WechatConstants.URL_USERS_GET;
        urlWecaht = replaceAccessTokenUrl(urlWecaht, accessToken);
        if (urlWecaht.indexOf(WechatConstants.PARAM_NEXT_OPENID) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_NEXT_OPENID, nextOpenid);
        }
        return urlWecaht;
    }

    /**
     * 封装公众号页面调用jssdk的凭证请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildJsTicketUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_GET_JS_TICKET;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装创建微信公众号标签的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildTagCreateUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_TAG_CREATE;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装查询微信公众号所有标签的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildTagListUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_TAG_LIST;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装修改标签的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildTagUpateUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_TAG_UPDATE;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装删除标签的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildTagDeleteUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_TAG_DELETE;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装用户绑定标签的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildTagBindUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_TAG_USER_BIND;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装用户绑定标签的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildTagUnbindUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_TAG_USER_UNBIND;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装公众号添加个性化菜单的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildMenuAddUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_MENU_ADD;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装公众号添加默认菜单的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildMenuAddDefaultUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_MENU_ADD_DEFAULT;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装公众号删除个性化菜单的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildMenuDeleteUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_MENU_DELETE;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装公众号删除所有菜单，包括默认和个性化菜单的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildMenuDeleteAllUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_MENU_DELETE_ALL;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装公众号查询所有菜单，包括默认和个性化菜单的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildMenuQueryAllUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_MENU_QUERY_ALL;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装公众号查询所有模板的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildTemplateListUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_TEMPLATE_LIST;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装查询某用户所有标签的请求地址
     * 
     * @param accessToken
     * @return
     */
    public static String buildUserTagsUrl(String accessToken) {
        String urlWecaht = WechatConstants.URL_USER_TAG_LIST;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装公众号发送模板消息接口
     * 
     * @param accessToken
     * @return
     */
    public static String buildTemplateSend(String accessToken) {
        String urlWecaht = WechatConstants.URL_TEMPLATE_SEND;
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装获取微信临时素材请求地址
     * 
     * @param mediaId
     * @param accessToken
     * @return
     */
    public static String buildMediaGetUrl(String mediaId, String accessToken) {
        String urlWecaht = WechatConstants.URL_MEDIA_GET;
        if (urlWecaht.indexOf(WechatConstants.PARAM_MEDIA_ID) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_MEDIA_ID, mediaId);
        }
        return replaceAccessTokenUrl(urlWecaht, accessToken);
    }

    /**
     * 封装accessToken请求参数
     * 
     * @param urlWecaht
     * @param accessToken
     * @return
     */
    public static String replaceAccessTokenUrl(String urlWecaht, String accessToken) {
        if (urlWecaht.indexOf(WechatConstants.PARAM_ACCESS_TOKEN) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_ACCESS_TOKEN, accessToken);
        }
        return urlWecaht;
    }

    /**
     * 封装openid请求参数
     * 
     * @param urlWecaht
     * @param openid
     * @return
     */
    public static String replaceOpenidUrl(String urlWecaht, String openid) {
        if (urlWecaht.indexOf(WechatConstants.PARAM_OPENID) > -1) {
            urlWecaht = urlWecaht.replace(WechatConstants.PARAM_OPENID, openid);
        }
        return urlWecaht;
    }

    /**
     * 将web请求的封装参数成map集合
     * 
     * @param url
     * @return
     */
    public static Map<String, String> buildParamMap(String url) {
        Map<String, String> result = new HashMap<String, String>();
        if (url != null && url.indexOf(WechatConstants.SYMBOL_QUESTION) > -1) {
            String[] paramArr = url.split("\\" + WechatConstants.SYMBOL_QUESTION);
            if (paramArr.length == 2) {
                String[] params = paramArr[1].split("\\" + WechatConstants.SYMBOL_AND);
                for (String param : params) {
                    String[] keyValue = param.split("\\" + WechatConstants.SYMBOL_EQUAL);
                    if (keyValue.length == 2) {
                        result.put(keyValue[0], keyValue[1]);
                    }
                }
            }
        }
        result.put("url", url);
        return result;
    }

    /**
     * 获取jssdk签名
     * 
     * @param url
     * @return
     */
    public static Signature getSignature(String url) {
        Signature result = null;
        if (checkTicketValid()) {
            result = getSignature(WechatCacher.getJsTicket().getTicket(), url);
        }
        return result;
    }

    /**
     * 获取jssdk签名
     * 
     * @param jsapiTicket
     * @param url 必须是调用微信JS接口页面的完整URL
     * @return Signature
     */
    public static Signature getSignature(String jsapiTicket, String url) {
        Signature result = new Signature();
        String nonceStr = createNoncestr();
        String timestamp = createTimestamp();
        String str = "", signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        str = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        try {
            signature = toHexStr(str);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        result.setTicket(jsapiTicket);
        result.setNonceStr(nonceStr);
        result.setTimestamp(timestamp);
        result.setSignature(signature);
        result.setAppId(WechatConstants.APP_ID);
        return result;
    }

    /**
     * 工具方法：将字符串参数进行sha1加密，得到密钥并转为十六进制的字符串
     * 
     * @param str字符串
     * @return String
     */
    private static String toHexStr(String str) throws NoSuchAlgorithmException {
        StringBuffer hexStr = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance(WechatConstants.DIGEST_SHA);
        byte[] digest = md.digest(str.getBytes());
        for (int i = 0; i < digest.length; i++) {
            String hStr = Integer.toHexString(digest[i] & 0xff);
            if (hStr != null && hStr.length() == 1) {
                hexStr.append("0");// 不够两位的数用0来占位
            }
            hexStr.append(hStr);
        }
        return hexStr.toString();
    }

    /**
     * 返回随机字符串
     * 
     * @return String
     */
    public static String createNoncestr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 返回随机字符串,指定长度，只有数字
     * 
     * @param length
     * @return String
     */
    public static String createNoncestr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                sb.append(random.nextInt(10));
            }
        }
        return sb.toString();
    }

    /**
     * 返回时间戳
     * 
     * @return String
     */
    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 检查tiket是否有效
     * 
     * @return
     */
    public static boolean checkTicketValid() {
        JsTicket ticket = WechatCacher.getJsTicket();
        if (ticket == null || ((System.currentTimeMillis() - ticket.getLastRequest()) > ticket.getValidity())) {
            return initJsTicket();
        }
        return true;
    }

    /**
     * 检查AccessToken是否有效
     * 
     * @return
     */
    public static boolean checkAccessTokenValid() {
        AccessToken token = WechatCacher.getAccessToken();
        if (token == null || ((System.currentTimeMillis() - token.getLastRequest()) > token.getValidity())) {
            return initAccessToken();
        }
        return true;
    }

    /**
     * 验证消息的确来自微信服务器自己的公众号
     * 
     * @param request
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String checkWechatRequest(HttpServletRequest request) throws NoSuchAlgorithmException {
        String signature = request.getParameter(WechatConstants.REQUEST_SIGNATURE);
        String timestamp = request.getParameter(WechatConstants.REQUEST_TIMESTAMP);
        String nonce = request.getParameter(WechatConstants.REQUEST_NONCE);
        String echostr = request.getParameter(WechatConstants.REQUEST_ECHOSTR);
        String token = WechatConstants.TOKEN;
        String[] parameters = new String[] { nonce, timestamp, token };
        Arrays.sort(parameters);
        StringBuffer contents = new StringBuffer();
        for (int i = 0; i < parameters.length; i++) {
            contents.append(parameters[i]);
        }
        String signatureNew = toHexStr(contents.toString());
        if (signatureNew != null && signatureNew.equals(signature)) {
            logger.info("微信服务器消息验证通过");
            return echostr;
        } else {
            logger.error("微信服务器消息验证没有通过,nonce=" + nonce + ",timestamp=" + timestamp + ",token=" + token);
            logger.error("微信服务器消息验证签名：signatureNew=" + signatureNew + ",signature=" + signature);
        }
        return "";
    }

    /**
     * 将业务系统的角色和菜单转换成公众号要求的格式
     * 
     * @param roles
     * @return
     */
    public static List<WechatMenu> convertSysMenuList(List<WechatRoleTag> roles) {
        if (roles == null || roles.size() == 0)
            return null;
        List<WechatMenu> result = new ArrayList<WechatMenu>();
        for (WechatRoleTag roleTag : roles) {
            if (roleTag.getMenuList() != null && roleTag.getMenuList().size() > 0) {
                List<WechatSysMenu> sysMenus = roleTag.getMenuList();// 原始菜单集合
                WechatMenu wechatMenu = convertSysMenu(sysMenus);
                if (roleTag.getId() != null && roleTag.getId() != WechatConstants.SYS_ROLE_DEFAULT) {// 非普通用户(角色)
                    WechatMenu.Matchrule matchrule = new WechatMenu.Matchrule();// 标签信息
                    matchrule.setTagId(roleTag.getTagId() + "");
                    wechatMenu.setMatchrule(matchrule);
                } // 除了普通用户之外的用户，带有某种角色即标签
                result.add(wechatMenu);
            }
        }
        return result;
    }

    /**
     * 将业务系统的角色和菜单转换成公众号要求的格式
     * 
     * @param roles
     * @return
     */
    public static WechatMenu convertSysMenu(List<WechatSysMenu> menus) {
        WechatMenu wechatMenu = null;
        if (menus == null || menus.size() == 0) {
            return wechatMenu;
        }

        wechatMenu = new WechatMenu();
        List<WechatMenu.Menu> buttonList = new ArrayList<WechatMenu.Menu>();
        List<WechatSysMenu> levenMenu = makeSysMenuLevel(menus);// 转成带有一二级层级关系的菜单
        Collections.sort(levenMenu, createComparatorDesc());// 排序，升序
        Menu menu = null;
        for (WechatSysMenu sm : levenMenu) {
            menu = new Menu();
            List<WechatSysMenu> childs = sm.getChilds();
            menu.setName("\u2795" + sm.getName());
            if (childs == null || childs.size() == 0) {// 只有一个一级的菜单
                convertMenuType(sm, menu);
            } else {// 此一级菜单有二级菜单
                Collections.sort(childs, createComparatorDesc());
                List<Menu> menuChildList = new ArrayList<Menu>();
                Menu childMenu = null;
                for (WechatSysMenu smChild : childs) {
                    childMenu = new Menu();
                    childMenu.setName("\u267b" + smChild.getName());
                    convertMenuType(smChild, childMenu);
                    menuChildList.add(childMenu);
                }
                menu.setSubButton(menuChildList);
            }
            buttonList.add(menu);
        }
        wechatMenu.setButton(buttonList);
        return wechatMenu;
    }

    /**
     * 微信公众号菜单类型与业务系统的菜单类型转换
     * 
     * @param param
     * @param menu
     */
    public static void convertMenuType(WechatSysMenu param, Menu menu) {
        if (param != null && param.getMenuType() != null) {
            if (WechatConstants.SYS_MENU_TYPE_VIEW == param.getMenuType()) {// 链接类型菜单
                menu.setType(WechatConstants.WECHAT_MENU_TYPE_VIEW);
                menu.setUrl(param.getUrl());
            } else if (WechatConstants.SYS_MENU_TYPE_CLICK == param.getMenuType()) {// 点击菜单
                menu.setType(WechatConstants.WECHAT_MENU_TYPE_CLICK);
                menu.setKey(param.getId() + "");
            }
        }
    }

    /**
     * 将无一二级层级关系的菜单集合手动整理有一二级关系的菜单集合
     * 
     * @param menus
     * @return List<SysMenu>
     * @return
     */
    public static List<WechatSysMenu> makeSysMenuLevel(List<WechatSysMenu> menus) {
        if (menus == null || menus.size() == 0) {
            return menus;
        }
        Map<Long, WechatSysMenu> mapOld = new HashMap<Long, WechatSysMenu>();
        for (WechatSysMenu sm : menus) {
            mapOld.put(sm.getId(), sm);
        }
        Map<Long, WechatSysMenu> mapNew = new HashMap<Long, WechatSysMenu>();// 整理一二级菜单后的一级菜单集合
        for (WechatSysMenu sm : menus) {
            Long id = sm.getParentId();
            WechatSysMenu condition = mapOld.get(id);
            if (condition == null || id == null) {// 是一级菜单
                mapNew.put(sm.getId(), sm);
            } else {// 是二级菜单
                WechatSysMenu first = mapNew.get(id);// 一级菜单
                if (first == null) {
                    first = condition;
                    mapNew.put(id, first);
                }
                List<WechatSysMenu> childs = first.getChilds();
                if (childs == null) {
                    childs = new ArrayList<WechatSysMenu>();
                }
                childs.add(sm);
                first.setChilds(childs);
            }
        }
        List<WechatSysMenu> allList = new ArrayList<WechatSysMenu>(mapNew.values());
        return allList;
    }

    /**
     * 创建一个升序的菜单排序工具
     * 
     * @return
     */
    public static Comparator<WechatSysMenu> createComparatorDesc() {
        return new Comparator<WechatSysMenu>() {
            public int compare(WechatSysMenu s1, WechatSysMenu s2) {
                if (s1.getOrderNum() == null || s2.getOrderNum() == null) {
                    return 0;
                }
                return s1.getOrderNum() - s2.getOrderNum();
            }
        };
    }
}
