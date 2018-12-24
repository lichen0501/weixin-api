package com.onedt.wx.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onedt.wx.cache.WechatCacher;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.WechatSysRole;
import com.onedt.wx.entity.WechatSysUser;
import com.onedt.wx.entity.WechatTag;
import com.onedt.wx.entity.WechatUserTag;
import com.onedt.wx.service.IWechatTagService;
import com.onedt.wx.utils.HttpUtils;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.WechatUtlis;

/**
 * 公众号标签业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 上午9:33:59
 */
@Service
public class WechatTagServiceImpl implements IWechatTagService {
    private Logger logger = LoggerFactory.getLogger(WechatTagServiceImpl.class);

    /**
     * 去微信服务器创建标签,要求30个字符以内，一个公众号，最多可以创建100个标签
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public WechatTag addTag(String name) throws Exception {
        WechatTag result = null;
        if (StringUtils.isNotEmpty(name)) {
            if (WechatUtlis.checkAccessTokenValid()) {
                String url = WechatUtlis.buildTagCreateUrl(WechatCacher.getAccessToken().getAccessToken());
                WechatTag.Tag tag = new WechatTag.Tag();
                tag.setName(name);
                result = new WechatTag();
                result.setTag(tag);
                String json = HttpUtils.httpPost(url, JacksonUtils.getJsonCamelLower(result));
                result = JacksonUtils.getObjectCamelLower(WechatTag.class, json);
            }
        }
        return result;
    }

    /**
     * 给微信公众号添加角色(标签)
     * 
     * @param role
     * @return WechatSysRole
     * @throws Exception
     */
    public WechatSysRole addRoleTag(WechatSysRole role) throws Exception {
        String name = role.getTagName();
        WechatTag tagsOld = tagsList();
        List<WechatTag.Tag> tagsOldList = null;
        if (tagsOld != null && tagsOld.getTags() != null && tagsOld.getTags().size() > 0) {// 已经创建有的标签
            tagsOldList = tagsOld.getTags();
            if (tagsOldList != null && tagsOldList.size() > 0) {
                for (WechatTag.Tag tag : tagsOldList) {
                    if (name.equals(tag.getName())) {
                        role.setTagId(tag.getId());
                        return role;
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(name) && role.getTagId() == null && role.getId() != WechatConstants.SYS_ROLE_DEFAULT) {// 非普通用户
            WechatTag tag = addTag(name);
            if (tag != null && tag.getTag() != null && tag.getTag().getId() != null) {
                role.setTagId(tag.getTag().getId());
            }
        }
        return role;
    }

    /**
     * 批量给微信公众号添加角色(标签)
     * 
     * @param roles
     * @return WechatTag
     */
    public List<WechatSysRole> addRoleTags(List<WechatSysRole> roles) throws Exception {
        logger.info("标签角色数据=" + JacksonUtils.getJsonCamelLower(roles));
        WechatTag tagsOld = tagsList();
        List<WechatTag.Tag> tagsOldList = null;
        if (tagsOld != null && tagsOld.getTags() != null && tagsOld.getTags().size() > 0) {// 查询已经创建有的标签
            tagsOldList = tagsOld.getTags();
        }
        for (WechatSysRole role : roles) {
            String name = role.getTagName();
            if (tagsOldList != null && tagsOldList.size() > 0) {
                for (WechatTag.Tag tag : tagsOldList) {
                    if (tag != null && name.equals(tag.getName())) {
                        role.setTagId(tag.getId());
                        break;
                    }
                }
            }
            if (StringUtils.isNotEmpty(name) && role.getTagId() == null && role.getId() != WechatConstants.SYS_ROLE_DEFAULT) {
                WechatTag result = addTag(name);
                if (result != null) {
                    if (result.getTag() != null && result.getTag().getId() != null) {
                        role.setTagId(result.getTag().getId());
                        logger.info("[addRoleTags]创建标签成功name=" + name + ",tag_id=" + result.getTag().getId());
                    } else {
                        logger.error("[addRoleTags]创建标签失败name=" + name);
                        role.setRemark(result.getErrmsg());
                    }
                }
            }
        }
        return roles;
    }

    /**
     * 获取公众号所有的标签
     * 
     * @return
     * @throws Exception
     */
    public WechatTag tagsList() throws Exception {
        WechatTag result = null;
        if (WechatUtlis.checkAccessTokenValid()) {
            String url = WechatUtlis.buildTagListUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpGet(url);
            result = JacksonUtils.getObjectCamelLower(WechatTag.class, json);
        }
        return result;
    }

    /**
     * 修改公众号的角色(标签),返回WechatTag对象errcode属性值为0表示修改成功
     * 
     * @param role
     * @return
     * @throws Exception
     */
    public WechatTag updateRoleTag(WechatSysRole role) throws Exception {
        String name = role.getTagName();
        Integer tagId = role.getTagId();
        if (StringUtils.isNotEmpty(name) && tagId != null) {
            WechatTag condition = new WechatTag();
            WechatTag.Tag param = new WechatTag.Tag();
            param.setName(name);
            param.setId(tagId);
            condition.setTag(param);
            return updateTag(condition);
        } else {
            logger.error("[updateRoleTag]修改公众号的角色(标签)失败，参数为空");
        }
        return null;
    }

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
    public WechatUserTag bindUserTag(WechatSysUser user) throws Exception {
        String openid = user.getOpenId();
        Integer tagId = user.getTagId();
        logger.info("[bindUserTag]开始给用户绑定标签,参数openid=" + openid + ",tagId=" + tagId);
        if (StringUtils.isEmpty(openid)) {
            logger.error("[bindUserTag]给用户绑定标签失败，参数为空openid=" + openid + ",tagId=" + tagId);
            return null;
        }
        WechatUserTag result = null;
        if (tagId != null) {
            // 先检查用户已绑定的标签，有的取消
            unbindUser(openid);
            WechatUserTag userTag = new WechatUserTag();
            List<String> list = new ArrayList<String>();
            list.add(openid);
            userTag.setOpenidList(list);
            userTag.setTagid(tagId);
            result = bindUser(userTag);
        } else {// 变为普通用户
            result = unbindUser(openid);
        }
        return result;
    }

    /**
     * 给用户取消绑定微信公众号标签
     * 
     * 返回WechatUserTag对象errcode属性值为0表示取消绑定的角色成功
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public WechatUserTag unbindUserTag(WechatSysUser user) throws Exception {
        String openid = user.getOpenId();
        Integer tagId = user.getTagId();
        if (StringUtils.isEmpty(openid) || tagId == null) {
            logger.error("[bindUserTag]开始给用户取消绑定标签失败,参数为空openid=" + openid + ",tagId=" + tagId);
            return null;
        }
        WechatUserTag userTag = new WechatUserTag();
        List<String> list = new ArrayList<String>();
        list.add(openid);
        userTag.setOpenidList(list);
        userTag.setTagid(tagId);
        return unbindUser(userTag);
    }

    /**
     * 编辑标签
     * 
     * @return
     * @throws Exception
     */
    public WechatTag updateTag(WechatTag tagJson) throws Exception {
        WechatTag result = null;
        if (WechatUtlis.checkAccessTokenValid() && tagJson != null) {
            String url = WechatUtlis.buildTagUpateUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonCamelLower(tagJson));
            result = JacksonUtils.getObjectCamelLower(WechatTag.class, json);
        }
        return result;
    }

    /**
     * 删除角色(标签),返回WechatTag对象errcode属性值为0表示删除成功
     * 
     * @param role
     * @return
     * @throws Exception
     */
    public WechatTag deleteRoleTag(WechatSysRole role) throws Exception {
        Integer tagId = role.getTagId();
        if (tagId != null) {
            WechatTag condition = new WechatTag();
            WechatTag.Tag param = new WechatTag.Tag();
            param.setId(tagId);
            condition.setTag(param);
            return deleteTag(condition);
        } else {
            logger.error("[deleteRoleTag]删除公众号的角色(标签)失败，参数为空");
        }
        return null;
    }

    /**
     * 删除标签
     * 
     * @param tagJson
     * @return
     * @throws Exception
     */
    public WechatTag deleteTag(WechatTag tagJson) throws Exception {
        WechatTag result = null;
        if (WechatUtlis.checkAccessTokenValid() && tagJson != null) {
            String url = WechatUtlis.buildTagDeleteUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonCamelLower(tagJson));
            result = JacksonUtils.getObjectCamelLower(WechatTag.class, json);
        }
        return result;
    }

    /**
     * 给用户绑定标签
     * 
     * @param userTag
     * @return
     * @throws Exception
     */
    public WechatUserTag bindUser(WechatUserTag userTag) throws Exception {
        WechatUserTag result = null;
        if (WechatUtlis.checkAccessTokenValid() && userTag != null) {
            String url = WechatUtlis.buildTagBindUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonUnderLower(userTag));
            result = JacksonUtils.getObjectCamelLower(WechatUserTag.class, json);
        }
        logger.info("[bindUser]给用户绑定标签操作，结果:" + JacksonUtils.getJsonCamelLower(result));
        return result;
    }

    /**
     * 给用户取消绑定的标签,单个标签取消
     * 
     * @param userTag
     * @return
     * @throws Exception
     */
    public WechatUserTag unbindUser(WechatUserTag userTag) throws Exception {
        WechatUserTag result = null;
        if (WechatUtlis.checkAccessTokenValid() && userTag != null) {
            String url = WechatUtlis.buildTagUnbindUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonUnderLower(userTag));
            result = JacksonUtils.getObjectCamelLower(WechatUserTag.class, json);
        }
        return result;
    }

    /**
     * 给用户取消绑定的标签,用户所有的标签都取消
     * 
     * @param openid
     * @return
     * @throws Exception
     */
    public WechatUserTag unbindUser(String openid) throws Exception {
        logger.info("[unbindUser]开始给用户取消绑定的标签操作...");
        WechatUserTag userTag = new WechatUserTag();
        userTag.setOpenid(openid);
        WechatUserTag result = queryUserTag(userTag);
        if (result != null && result.getTagidList() != null && result.getTagidList().size() > 0) {
            for (Integer userTagid : result.getTagidList()) {
                WechatUserTag condition = new WechatUserTag();
                List<String> openids = new ArrayList<String>();
                openids.add(openid);
                condition.setOpenidList(openids);
                condition.setTagid(userTagid);
                result = unbindUser(condition);// 取消用户已有标签
                if (result != null) {
                    if (result.isSuccess()) {
                        logger.info("[unbindUser]给用户(" + openid + ")取消已绑定的标签成功，标签ID=" + userTagid);
                    } else {
                        logger.error("[unbindUser]给用户(" + openid + ")取消已绑定的标签失败：" + result.getErrmsg() + ",标签id=" + userTagid);
                        return result;
                    }
                }
            }
        } else {
            logger.error("[unbindUser]给用户(" + openid + ")取消已绑定的标签失败.result=" + result);
            if (result != null) {
                logger.error("[unbindUser]给用户(" + openid + ")取消已绑定的标签失败." + JacksonUtils.getJsonCamelLower(result));
            }
        }
        return result;
    }

    /**
     * 查看用户已绑定的所有标签
     * 
     * @param userTag
     * @return
     * @throws Exception
     */
    public WechatUserTag queryUserTag(WechatUserTag userTag) throws Exception {
        WechatUserTag result = null;
        if (WechatUtlis.checkAccessTokenValid() && userTag != null) {
            String url = WechatUtlis.buildUserTagsUrl(WechatCacher.getAccessToken().getAccessToken());
            String json = HttpUtils.httpPost(url, JacksonUtils.getJsonCamelLower(userTag));
            result = JacksonUtils.getObjectUnderLower(WechatUserTag.class, json);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
    }

}
