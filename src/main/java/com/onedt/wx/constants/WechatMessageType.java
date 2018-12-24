package com.onedt.wx.constants;

/**
 * 定义微信公众号消息的类型，主要用于方便外部调用时知道回复给微信用户都有哪些类型的消息，本类中的枚举对象(也就是消息类型)与公众号官网提供的消息类型没有关系 。
 * */
public enum WechatMessageType {
    /** 文本消息 */
    TEXT(1),
    /** 图片 */
    IMAGE(2),
    /** 语音 */
    VOICE(3),
    /** 视频 */
    VIDEO(4),
    /** 音乐 */
    MUSIC(5),
    /** 图文链接 */
    ITEMS(6);
    private final int num;

    private WechatMessageType(int num) {
        this.num = num;
    }

    /** 获取枚举值 */
    public int getValue() {
        return this.num;
    }

    public String toString() {
        switch (num) {
        case 1:
            return "text type";
        case 2:
            return "image type";
        case 3:
            return "voice type";
        case 4:
            return "video type";
        case 5:
            return "music type";
        case 6:
            return "items type";
        default:
            return "unknown type ";
        }
    }
}
