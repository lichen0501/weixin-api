package com.onedt.wx.service;

import java.util.Map;

import com.onedt.wx.entity.BaseMessage;

/**
 * 后台处理来自微信公众号消息的业务接口。根据不同消息类型，此接口衍生了不同的子接口。
 * */
public interface IMessageHandleService {
    /**
     * 处理来自公众号用户消息的业务接口
     * 
     * */
    public BaseMessage invoke(Map<String, String> params) throws Exception;

    /**
     * 处理来自公众号文本消息的业务接口。 接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Content文本消息内容、MsgId消息编号。
     * */
    public interface ITextHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号图片类型消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、PicUrl图片链接（由系统生成）、MsgId消息编号、MediaId媒体id。
     * */
    public interface IImageHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号语音消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Format语音格式如amr、MsgId消息编号、MediaId媒体id、 Recognition语音识别结果（
     * 公众号需开通语音识别功能才有此值）。
     * */
    public interface IVoiceHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号视频消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、ThumbMediaId缩略图的媒体id、MsgId消息编号、MediaId媒体id。
     * */
    public interface IVideoHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号小视频消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、ThumbMediaId缩略图的媒体id、MsgId消息编号、MediaId媒体id。
     * 
     * */
    public interface IShortvideoHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号地理位置消息的业务接口 。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Location_X纬度、Location_Y经度、MsgId消息编号、 Scale地图缩放大小、Label地理位置信息。
     * 
     * */
    public interface ILocationHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号链接消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Title标题、MsgId消息编号、Description描述、Url链接。
     */
    public interface ILinkHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号关注公众号事件(不带参数)消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Event事件类型、MsgId消息编号。
     * 
     * */
    public interface ISubHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号扫描关注公众号事件(带有参数)消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Event事件类型、MsgId消息编号、EventKey参数、 Ticket二维码的ticket。
     * 
     * */
    public interface ISubParamHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号取消关注公众号事件(不带参数)消息的业务接口。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Event事件类型、MsgId消息编号。
     * 
     * */
    public interface IUnSubHandleService extends IMessageHandleService {
    }

    /**
     * 处理来自公众号上报地理位置事件消息的业务接口 。接口中的 service(message)方法里的message对象属性中有值的如下：ToUserName开发者微信号、FromUserName发送方帐号 、CreateTime消息创建时间、MsgType消息类型、Event事件类型、MsgId消息编号、Latitude纬度、Longitude经度、 Precision地理位置精度。
     * 
     * */
    public interface ILocationEventService extends IMessageHandleService {
    }

    /** 处理来自公众号点击自定义菜单事件消息的业务接口 */
    public interface IMenuEventService extends IMessageHandleService {
    }

    /** 发送模板通知成功后的事件接口 */
    public interface ITemplateSendFinishService extends IMessageHandleService {

    }
}
