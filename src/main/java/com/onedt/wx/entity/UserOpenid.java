package com.onedt.wx.entity;

import java.util.List;

/**
 * 获取已关注的用户openid实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-6-13 下午5:27:43
 */
public class UserOpenid extends BaseWechatEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer total;// 关注该公众账号的总用户数
    private Integer count;// 拉取的OPENID个数，最大值为10000
    private Openid data;// 列表数据，OPENID的列表
    private String nextOpenid;// 拉取列表的最后一个用户的OPENID

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Openid getData() {
        return data;
    }

    public void setData(Openid data) {
        this.data = data;
    }

    public String getNextOpenid() {
        return nextOpenid;
    }

    public void setNextOpenid(String nextOpenid) {
        this.nextOpenid = nextOpenid;
    }

    /**
     * openid集合
     * 
     * @author nemo
     * @version 1.0
     * @date 2017-6-13 下午5:32:58
     */
    public static class Openid {
        private List<String> openid;

        public List<String> getOpenid() {
            return openid;
        }

        public void setOpenid(List<String> openid) {
            this.openid = openid;
        }
    }

}
