package com.onedt.wx.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 微信公众号标签
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-17 上午9:36:29
 */
public class WechatTag extends BaseWechatEntity {
    private static final long serialVersionUID = 1L;
    private Tag tag;
    private List<Tag> tags;

    public static class Tag implements Serializable {

        private static final long serialVersionUID = 1L;
        /** 名称 */
        private String name;
        /** 标签id */
        private Integer id;
        /** 标签的粉丝数量 */
        private Integer count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
