package com.onedt.wx.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.onedt.wx.cache.WechatCacher;
import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.service.IWechatMediaService;
import com.onedt.wx.utils.HttpUtils;
import com.onedt.wx.utils.WechatUtlis;

/**
 * 微信素材业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-10 上午9:26:28
 */
@Service
public class WechatMediaServiceImpl implements IWechatMediaService {

    /**
     * 通过素材id获取微信服务器上面的临时素材
     * 
     * @param mediaId
     * @return String
     * @throws Exception
     */
    public String getTempMedisFromWechat(String mediaId) throws Exception {
        if (StringUtils.isEmpty(mediaId))
            return null;
        String path = null;
        if (WechatUtlis.checkAccessTokenValid()) {
            String url = WechatUtlis.buildMediaGetUrl(mediaId, WechatCacher.getAccessToken().getAccessToken());
            url = url.replace(WechatConstants.PARAM_ACCESS_TOKEN, WechatCacher.getAccessToken().getAccessToken());
            path = HttpUtils.downLoad(url);
        }

        return path;
    }
}
