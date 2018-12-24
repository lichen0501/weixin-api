package com.onedt.wx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onedt.wx.entity.WechatSysRole;
import com.onedt.wx.entity.WechatSysUser;
import com.onedt.wx.entity.WechatTag;
import com.onedt.wx.entity.WechatUserTag;
import com.onedt.wx.entity.sys.ReqBodyObj;
import com.onedt.wx.entity.sys.RespBodyObj;
import com.onedt.wx.service.IWechatTagService;

/**
 * 微信标签接口 Controller
 * 
 * 业务系统的用户角色与微信平台的用户标签处理控制器.标签名不能重复，单个标签字符不能超过30个，总标签不能超过100个
 * 
 * @author nemo
 * @date 2017-12-01 09:35:17
 */
@RestController
@RequestMapping("/wx/tag")
public class WechatTagController {
    @Autowired
    private IWechatTagService tagService;

    /**
     * 添加单个标签
     * 
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping("/addTag")
    public RespBodyObj<WechatSysRole> addRoleTag(@RequestBody ReqBodyObj<WechatSysRole> pojo) throws Exception {
        return RespBodyObj.ok(tagService.addRoleTag(pojo.getData()));
    }

    /**
     * 修改单个标签
     * 
     * 返回WechatTag对象errcode属性值为0表示修改成功
     * 
     * @param pojo
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateTag")
    public RespBodyObj<WechatTag> updateRoleTag(@RequestBody ReqBodyObj<WechatSysRole> pojo) throws Exception {
        return RespBodyObj.ok(tagService.updateRoleTag(pojo.getData()));
    }

    /**
     * 删除单个标签
     * 
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteTag")
    public RespBodyObj<WechatTag> deleteTag(@RequestBody ReqBodyObj<WechatSysRole> pojo) throws Exception {
        return RespBodyObj.ok(tagService.deleteRoleTag(pojo.getData()));
    }

    /**
     * 添加多个标签
     * 
     * 返回WechatTag对象errcode属性值为0表示删除成功
     * 
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping("/addTags")
    public RespBodyObj<List<WechatSysRole>> addRoleTags(@RequestBody ReqBodyObj<List<WechatSysRole>> pojo) throws Exception {
        return RespBodyObj.ok(tagService.addRoleTags(pojo.getData()));
    }

    /**
     * 给用户绑定微信公众号标签,如果WechatSysUser对象的tagId属性为空值,则会给此用户变更为普通默认角色标签
     * 
     * 某个用户只能有一种角色标签
     * 
     * 返回WechatUserTag对象errcode属性值为0表示添加角色成功
     * 
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping("/bindTag")
    public RespBodyObj<WechatUserTag> bindUserTag(@RequestBody ReqBodyObj<WechatSysUser> pojo) throws Exception {
        return RespBodyObj.ok(tagService.bindUserTag(pojo.getData()));
    }

    /**
     * 给用户取消绑定微信公众号标签
     * 
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping("/unbindTag")
    public RespBodyObj<WechatUserTag> unbindUserTag(@RequestBody ReqBodyObj<WechatSysUser> pojo) throws Exception {
        return RespBodyObj.ok(tagService.unbindUserTag(pojo.getData()));
    }

}
