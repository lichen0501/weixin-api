package com.onedt.wx.service;

import java.util.List;

import com.onedt.wx.entity.WechatSysRole;
import com.onedt.wx.entity.WechatSysUser;
import com.onedt.wx.entity.WechatTag;
import com.onedt.wx.entity.WechatUserTag;

/**
 * 公众号标签业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 上午9:33:59
 */
public interface IWechatTagService {
    /**
     * 去微信服务器创建标签,要求30个字符以内，一个公众号，最多可以创建100个标签
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public WechatTag addTag(String name) throws Exception;

    /**
     * 给微信公众号添加角色(标签)
     * 
     * @param role
     * @return WechatSysRole
     */
    public WechatSysRole addRoleTag(WechatSysRole role) throws Exception;

    /**
     * 批量给微信公众号添加角色(标签)
     * 
     * @param roles
     * @return List<WechatSysRole>
     */
    public List<WechatSysRole> addRoleTags(List<WechatSysRole> roles) throws Exception;

    /**
     * 获取公众号所有的标签
     * 
     * @return
     * @throws Exception
     */
    public WechatTag tagsList() throws Exception;

    /**
     * 编辑标签
     * 
     * @param tag
     * @return
     * @throws Exception
     */
    public WechatTag updateTag(WechatTag tag) throws Exception;

    /**
     * 修改公众号的角色(标签)
     * 
     * @param role
     * @return
     * @throws Exception
     */
    public WechatTag updateRoleTag(WechatSysRole role) throws Exception;

    /**
     * 删除标签
     * 
     * @param tag
     * @return
     * @throws Exception
     */
    public WechatTag deleteTag(WechatTag tag) throws Exception;

    /**
     * 删除角色(标签)
     * 
     * @param role
     * @return
     * @throws Exception
     */
    public WechatTag deleteRoleTag(WechatSysRole role) throws Exception;

    /**
     * 给用户绑定微信公众号标签,如果WechatSysUser对象的tagId属性为空值,则会给此用户变更为普通默认角色标签
     * 
     * 某个用户只能有一种角色标签
     * 
     * 返回WechatUserTag对象errcode属性值为0表示添加角色成功
     * 
     * @param user
     * @throws Exception
     */
    public WechatUserTag bindUserTag(WechatSysUser user) throws Exception;

    /**
     * 给用户绑定标签
     * 
     * @param userTag
     * @return
     * @throws Exception
     */
    public WechatUserTag bindUser(WechatUserTag userTag) throws Exception;

    /**
     * 给用户取消绑定微信公众号标签
     * 
     * 返回WechatUserTag对象errcode属性值为0表示取消绑定的角色成功
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public WechatUserTag unbindUserTag(WechatSysUser user) throws Exception;

    /**
     * 给用户取消绑定的标签,单个标签取消
     * 
     * @param userTag
     * @return
     * @throws Exception
     */
    public WechatUserTag unbindUser(WechatUserTag userTag) throws Exception;

    /**
     * 给用户取消绑定的标签,用户所有的标签都取消
     * 
     * @param openid
     * @return
     * @throws Exception
     */
    public WechatUserTag unbindUser(String openid) throws Exception;

    /**
     * 查看用户已绑定的所有标签
     * 
     * @param userTag
     * @return
     * @throws Exception
     */
    public WechatUserTag queryUserTag(WechatUserTag userTag) throws Exception;
}
