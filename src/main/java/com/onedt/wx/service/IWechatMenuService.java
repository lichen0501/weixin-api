package com.onedt.wx.service;

import java.util.List;

import com.onedt.wx.entity.WechatMenu;
import com.onedt.wx.entity.WechatRoleTag;

/**
 * 微信公众号个性化菜单
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 下午2:11:20
 */
public interface IWechatMenuService {
    /**
     * 添加个性化菜单
     * 
     * @param menu
     * @return
     * @throws Exception
     */
    public WechatMenu addMenu(WechatMenu menu) throws Exception;

    /**
     * 添加默认的菜单
     * 
     * @param menu
     * @return
     * @throws Exception
     */
    public WechatMenu addDefaultMenu(WechatMenu menu) throws Exception;

    /**
     * 添加公众号角色标签对应的菜单
     * 
     * @param roleTags
     * @return
     * @throws Exception
     */
    public List<WechatMenu> addMenu(List<WechatRoleTag> roleTags) throws Exception;

    /**
     * 删除个性化菜单
     * 
     * @param menu
     * @return
     * @throws Exception
     */
    public WechatMenu deleteMenu(WechatMenu menu) throws Exception;

    /**
     * 删除所有菜单，包括普通和个性化菜单
     * 
     * @return
     * @throws Exception
     */
    public WechatMenu deleteMenuAll() throws Exception;

    /**
     * 查询所有菜单，包括普通和个性化菜单
     * 
     * @return
     * @throws Exception
     */
    public WechatMenu queryMenuAll() throws Exception;
}
