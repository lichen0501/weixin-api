package com.onedt.wx.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onedt.wx.entity.sys.HttpCode;
import com.onedt.wx.entity.sys.ReqBodyObj;
import com.onedt.wx.entity.sys.RespBodyObj;
import com.onedt.wx.service.IWechatMediaService;

/**
 * 微信前端上传图片控制器
 * 
 * @author nemo
 * @version 1.0
 * @date 2017年12月11日 下午6:51:36
 */
@RestController
@RequestMapping("/wx/upload")
public class UploadImageController {
    private Logger logger = LoggerFactory.getLogger(UploadImageController.class);
    @Autowired
    private IWechatMediaService mediaService;

    /**
     * 上传图片
     * 
     * @param param微信图片素材id由页面jssdk提供给后台
     * @return
     */
    @PostMapping("/img")
    public RespBodyObj<String> image(@RequestBody ReqBodyObj<String> param) {
        String mediaId = param.getData().toString();
        try {
            String imageUrl = mediaService.getTempMedisFromWechat(mediaId);
            // 要注意，当用微信web开发工具上传图片时，上传不了，但又能获取到素材mediaId=1237378768e7q8e7r8qwesafdasdfasdfaxss111
            if (StringUtils.isNotEmpty(imageUrl)) {
                return new RespBodyObj<String>(HttpCode.OK.value(), "上传图片成功mediaId=" + mediaId, imageUrl);
            } else {
                logger.error("[image]获取微信图片素材并上传失败:素材id=" + mediaId);
            }
        } catch (Exception e) {
            logger.error("[image]获取微信图片素材并上传出现异常:" + e.getMessage());
        }
        return RespBodyObj.error();
    }

    public void setMediaService(IWechatMediaService mediaService) {
        this.mediaService = mediaService;
    }

}
