package com.onedt.wx.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 微信公众号模板实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 下午4:36:01
 */
public class WechatTemplate implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }

    private List<Template> templateList;

    /**
     * 模板实体
     */
    public static class Template implements Serializable {
        private static final long serialVersionUID = 1L;
        private String templateId;// 模板ID
        private String title;// 模板标题
        private String primaryIndustry;// 模板所属行业的一级行业
        private String deputyIndustry;// 模板所属行业的二级行业
        private String content;// 模板内容
        private String example;// 模板示例

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrimaryIndustry() {
            return primaryIndustry;
        }

        public void setPrimaryIndustry(String primaryIndustry) {
            this.primaryIndustry = primaryIndustry;
        }

        public String getDeputyIndustry() {
            return deputyIndustry;
        }

        public void setDeputyIndustry(String deputyIndustry) {
            this.deputyIndustry = deputyIndustry;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }
    }

}
