package com.onedt.wx.entity;

import java.io.Serializable;

/**
 * 微信公众号消息实体
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-14 下午2:09:07
 */
public abstract class BaseMessage implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 消息接收者（开发者微信号）
     */
    protected String ToUserName;

    /**
     * 消息发送者（发送方帐号[一个OpenID]）
     */
    protected String FromUserName;
    /** 消息类型 */
    protected String MsgType;
    /** 消息创建时间 */
    protected String CreateTime;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public static class Property {
        public static final String toUserName = "ToUserName";// 接收者
        public static final String fromUserName = "FromUserName";// 发送者
        public static final String createTime = "CreateTime";// 消息创建时间，单位是秒
        public static final String msgType = "MsgType";// 消息类型
        public static final String msgId = "MsgId";// 消息编号,64位整型
        public static final String event = "Event";// 事件
        public static final String eventKey = "EventKey";// 事件标识
        public static final String ticket = "Ticket";// 凭证
        public static final String latitude = "Latitude";// 纬度（事件类型消息上报地理事件）
        public static final String longitude = "Longitude";// 经度（事件类型消息上报地理事件）
        public static final String precision = "Precision";// 精确度
        public static final String picUrl = "PicUrl";// 图片地址
        public static final String mediaId = "MediaId";// 素材编号
        public static final String title = "Title";// 消息标题
        public static final String description = "Description";// 消息描述
        public static final String url = "Url";// 消息链接
        public static final String location_X = "Location_X";// 纬度（地理类型消息）
        public static final String location_Y = "Location_Y";// 经度（地理类型消息）
        public static final String scale = "Scale";// 地图缩放大小
        public static final String label = "Label";// 地理位置信息
        public static final String content = "Content";// 内容
        public static final String format = "Format";// 语音格式，如amr，speex等
        public static final String recognition = "Recognition";// 语音识别
        public static final String status = "Status";// 状态
        public static final String menuId = "MenuId";// 菜单编号
        public static final String musicUrl = "MusicUrl";// 音乐地址
        public static final String HQMusicUrl = "HQMusicUrl";// 高质音乐地址
        public static final String thumbMediaId = "ThumbMediaId";// 缩略图素材编号
    }
}
