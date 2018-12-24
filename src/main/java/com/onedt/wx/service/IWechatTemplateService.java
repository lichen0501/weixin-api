package com.onedt.wx.service;

import java.util.List;

import com.onedt.wx.entity.WechatTemplate;

/**
 * 微信公众号模板业务类
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 下午4:33:50
 */
public interface IWechatTemplateService {
    /**
     * 获取模板列表
     * 
     * @return
     * @throws Exception
     */
    public List<WechatTemplate.Template> getTemplateList() throws Exception;

    /**
     * 创建微信通知模板xml
     * 
     * @param templates
     * @return
     * @throws Exception
     */
    public void updateTemplate(List<WechatTemplate.Template> templates) throws Exception;

}
