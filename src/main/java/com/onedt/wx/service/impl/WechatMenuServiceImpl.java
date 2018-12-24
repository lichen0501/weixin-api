package com.onedt.wx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onedt.wx.cache.WechatCacher;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.WechatMenu;
import com.onedt.wx.entity.WechatMenu.Matchrule;
import com.onedt.wx.entity.WechatRoleTag;
import com.onedt.wx.service.IWechatMenuService;
import com.onedt.wx.utils.HttpUtils;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.WechatUtlis;

/**
 * 微信公众号个性化菜单。个性化菜单不存在修改菜单，重新新增即包括了修改。
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 下午2:11:20
 */
@Service
public class WechatMenuServiceImpl implements IWechatMenuService {
    private Logger logger = LoggerFactory.getLogger(WechatMenuServiceImpl.class);

    /**
     * 添加公众号角色标签对应的菜单,默认菜单（普通角色）WechatRoleTag对象的id属性值约定为1;
     * 
     * 返回WechatMenu结果列表，WechatMenu对象的errcode属性值为0表示添加某角色的菜单成功。
     * 
     * @param roleTags
     * @return
     * @throws Exception
     */
    public List<WechatMenu> addMenu(List<WechatRoleTag> roleTags) throws Exception {
        if (roleTags == null || roleTags.size() == 0) {
            return null;
        }
        logger.info("添加菜单数据=" + JacksonUtils.getJsonCamelLower(roleTags));
        List<WechatMenu> resultAdd = new ArrayList<WechatMenu>();
        for (WechatRoleTag roleTag : roleTags) {// 必须先创建默认菜单才能创建个性化菜单
            if (WechatConstants.SYS_ROLE_DEFAULT.equals(roleTag.getId()) && roleTag.getMenuList() != null && roleTag.getMenuList().size() > 0) {// 普通用户(角色)
                try {
                    WechatMenu wechatMenu = WechatUtlis.convertSysMenu(roleTag.getMenuList());
                    WechatMenu result = addDefaultMenu(wechatMenu);
                    resultAdd.add(result);
                    if (result != null && result.isSuccess()) {
                        logger.info("[addMenu]向微信公众号[" + roleTag.getTagName() + "]添加菜单成功。");
                    } else {
                        logger.error("[addMenu]向微信公众号[" + roleTag.getTagName() + "]添加菜单失败：" + result.getErrmsg());
                        logger.error(JacksonUtils.getJsonCamelLower(roleTag));
                    }
                } catch (Exception e) {
                    WechatMenu result = new WechatMenu();
                    result.setErrcode(WechatConstants.SYS_CODE_ERROR);
                    result.setErrmsg("向微信公众号添加普通默认菜单发生异常：" + e.getMessage());
                    resultAdd.add(result);
                    logger.error("[addMenu]向微信公众号添加默认菜单发生异常：" + e.getMessage());
                }
                break;
            }
        }
        for (WechatRoleTag roleTag : roleTags) {// 非普通用户(也就是个性化菜单)
            try {
                if (roleTag.getMenuList() != null && roleTag.getMenuList().size() > 0 && !WechatConstants.SYS_ROLE_DEFAULT.equals(roleTag.getId())) {
                    WechatMenu wechatMenu = WechatUtlis.convertSysMenu(roleTag.getMenuList());
                    WechatMenu.Matchrule matchrule = new WechatMenu.Matchrule();// 标签信息
                    matchrule.setTagId(String.valueOf(roleTag.getTagId()));
                    wechatMenu.setMatchrule(matchrule);
                    WechatMenu result = addMenu(wechatMenu);
                    resultAdd.add(result);
                    if (result != null && result.getMenuid() != null) {
                        logger.info("[addMenu]向微信公众号[" + roleTag.getTagName() + "]添加个性化菜单成功。菜单标识为：" + result.getMenuid() + "。");
                    } else {
                        logger.error("[addMenu]向微信公众号[" + roleTag.getTagName() + "]添加个性化菜单失败：" + result.getErrmsg());
                        logger.error(JacksonUtils.getJsonCamelLower(roleTag));
                    }

                }
            } catch (Exception e) {
                WechatMenu result = new WechatMenu();
                result.setErrcode(WechatConstants.SYS_CODE_ERROR);
                result.setErrmsg("向微信公众号添加个性化菜单发生异常：" + e.getMessage());
                resultAdd.add(result);
                logger.error("[addMenu]向微信公众号添加个性化菜单发生异常：" + e.getMessage());
            }
        }
        return resultAdd;
    }

    /**
     * 添加个性化菜单
     * 
     * @param menu
     * @return
     * @throws Exception
     */
    public WechatMenu addMenu(WechatMenu menu) throws Exception {
        WechatMenu result = null;
        if (WechatUtlis.checkAccessTokenValid() && menu != null) {
            String url = WechatUtlis.buildMenuAddUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonUnderLower(menu));
            result = JacksonUtils.getObjectUnderLower(WechatMenu.class, json);
        }
        return result;
    }

    /**
     * 添加默认的菜单
     * 
     * @param menu
     * @return
     * @throws Exception
     */
    public WechatMenu addDefaultMenu(WechatMenu menu) throws Exception {
        WechatMenu result = null;
        if (WechatUtlis.checkAccessTokenValid() && menu != null) {
            String url = WechatUtlis.buildMenuAddDefaultUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonUnderLower(menu));
            result = JacksonUtils.getObjectUnderLower(WechatMenu.class, json);
        }
        return result;
    }

    /**
     * 删除个性化菜单
     * 
     * @param menu
     * @return
     * @throws Exception
     */
    public WechatMenu deleteMenu(WechatMenu menu) throws Exception {
        WechatMenu result = null;
        if (WechatUtlis.checkAccessTokenValid() && menu != null) {
            String url = WechatUtlis.buildMenuDeleteUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonUnderLower(menu));
            result = JacksonUtils.getObjectUnderLower(WechatMenu.class, json);
        }
        return result;
    }

    /**
     * 删除所有菜单，包括普通和个性化菜单
     * 
     * @return
     * @throws Exception
     */
    public WechatMenu deleteMenuAll() throws Exception {
        WechatMenu result = null;
        if (WechatUtlis.checkAccessTokenValid()) {
            String url = WechatUtlis.buildMenuDeleteAllUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpGet(url);
            result = JacksonUtils.getObjectUnderLower(WechatMenu.class, json);
        }
        return result;
    }

    /**
     * 查询所有菜单，包括普通和个性化菜单
     * 
     * @param menu
     * @return
     * @throws Exception
     */
    public WechatMenu queryMenuAll() throws Exception {
        WechatMenu result = null;
        if (WechatUtlis.checkAccessTokenValid()) {
            String url = WechatUtlis.buildMenuQueryAllUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpGet(url);
            result = JacksonUtils.getObjectUnderLower(WechatMenu.class, json);
        }
        return result;
    }

    public static void main(String[] args) {
        WechatMenu wechatMenu = new WechatMenu();
        Matchrule rule = new Matchrule();
        wechatMenu.setMatchrule(rule);
    }
}
