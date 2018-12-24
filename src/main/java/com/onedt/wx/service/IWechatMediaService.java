package com.onedt.wx.service;

/**
 * 微信素材业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-10 上午9:26:28
 */
public interface IWechatMediaService {
    /**
     * 通过素材id获取微信服务器上面的临时素材
     * 
     * @param mediaId
     * @return String
     * @throws Exception
     */
    public String getTempMedisFromWechat(String mediaId) throws Exception;

}
