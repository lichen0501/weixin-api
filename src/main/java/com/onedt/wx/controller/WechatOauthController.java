package com.onedt.wx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 微信网页授权业务
 * @author nemo
 * @version 1.0
 * @date 2017年12月10日 下午4:03:59
 */

import com.onedt.wx.entity.Signature;
import com.onedt.wx.entity.WechatOauthInfo;
import com.onedt.wx.entity.sys.RespBodyObj;
import com.onedt.wx.service.IWechatOauthService;

@RestController
@RequestMapping("/wx/oauth")
public class WechatOauthController {
    @Autowired
    IWechatOauthService service;

    /**
     * 先获取授权的code值
     * 
     * @param info
     * @return
     * @throws Exception
     */
    @PostMapping("/getOpenid")
    public RespBodyObj<WechatOauthInfo> getOpenid(@RequestBody WechatOauthInfo info) throws Exception {
        return RespBodyObj.ok(service.getOpenid(info));
    }

    /**
     * 获取网页jssdk签名
     * 
     * @param info
     * @return
     * @throws Exception
     */
    @PostMapping("/sign")
    public RespBodyObj<Signature> getSignature(@RequestBody WechatOauthInfo info) throws Exception {
        return RespBodyObj.ok(service.getSignature(info));
    }

    /**
     * 同步微信公众号用户数据,保持微信已关注用户与后台数据库一致
     * 
     * @return
     */
    @RequestMapping("/syncUsers")
    public RespBodyObj<List<String>> syncUsers() throws Exception {
        return RespBodyObj.ok(service.syncUsers());
    }

    public void setService(IWechatOauthService service) {
        this.service = service;
    }

}
