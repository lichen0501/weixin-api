package com.onedt.wx.constants;

import com.onedt.wx.utils.PropertiesConfig;

/**
 * 微信公众号常量
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-10 上午9:32:32
 */
public class WechatConstants {
    /** get方式请求 */
    public static final String HTTP_METHOD_GET = "get";
    /** post方式请求 */
    public static final String HTTP_METHOD_POST = "post";
    /** 微信公众号消息回复最长时间,单位毫秒 */
    public static final long WECHAT_RESP_TIME = 4500L;
    /** 微信公众号每次获取已关注用户的数量限制 */
    public static final int WECHAT_SUB_COUNT = 10000;
    /** sha1方式加密 */
    public static final String DIGEST_SHA = "SHA-1";
    /** 符号-问号 */
    public static final String SYMBOL_QUESTION = "?";
    /** 符号-与 */
    public static final String SYMBOL_AND = "&";
    /** 符号-等号 */
    public static final String SYMBOL_EQUAL = "=";
    /** 编码类型 */
    public static final String ENCODE_TYPE = "UTF-8";
    /** http请求 */
    public static final String HTTP = "http://";
    /** https请求 */
    public static final String HTTPS = "https://";
    /** 微信公众号AppID **/
    public static final String APP_ID = PropertiesConfig.getValue("wechat.app_id");
    /** 微信公众号SECRET **/
    public static String SECRET = PropertiesConfig.getValue("wechat.secret");
    /** 微信公众号TOKEN **/
    public static String TOKEN = PropertiesConfig.getValue("wechat.token");
    /** 参数注释符号 */
    public static final String PARAM_NOTE = "#";
    /** URL参数名称之ACCESS_TOKEN */
    public static final String PARAM_ACCESS_TOKEN = "#ACCESS_TOKEN#";
    /** URL参数名称之MEDIA_ID */
    public static final String PARAM_MEDIA_ID = "#MEDIA_ID#";
    /** URL参数名称之APPID */
    public static final String PARAM_APPID = "#APPID#";
    /** URL参数名称之REDIRECT_URI */
    public static final String PARAM_REDIRECT_URI = "#REDIRECT_URI#";
    /** URL参数名称之SCOPE */
    public static final String PARAM_SCOPE = "#SCOPE#";
    /** URL参数名称之STATE */
    public static final String PARAM_STATE = "#STATE#";
    /** URL参数名称之CODE */
    public static final String PARAM_CODE = "#CODE#";
    /** URL参数名称之SECRET */
    public static final String PARAM_SECRET = "#SECRET#";
    /** URL参数名称之OPENID */
    public static final String PARAM_OPENID = "#OPENID#";
    /** URL参数名称之NEXT_OPENID */
    public static final String PARAM_NEXT_OPENID = "#NEXT_OPENID#";
    /** 请求参数名称之code */
    public static final String REQUEST_PARAM_CODE = "code";
    /** 请求参数名称之openid */
    public static final String REQUEST_OPENID = "openid";
    /** 请求参数名称之signature */
    public static final String REQUEST_SIGNATURE = "signature";
    /** 请求参数名称之timestamp */
    public static final String REQUEST_TIMESTAMP = "timestamp";
    /** 请求参数名称之nonce */
    public static final String REQUEST_NONCE = "nonce";
    /** 请求参数名称之echostr */
    public static final String REQUEST_ECHOSTR = "echostr";
    /** 微信网页授权：用户信息详情授权作用域 ,需要用户同意 */
    public static final String SCOPE_USERINFO = "snsapi_userinfo";
    /** 微信网页授权：用户基本信息授权作用域 ,用户无感知 */
    public static final String SCOPE_BASE = "snsapi_base";
    /** 素材下载到本地的临时目录 */
    public static final String MEDIA_TEMP_PATH = PropertiesConfig.getValue("file_temp_path");
    /** ngix服务器地址 */
    public static final String NGINX_HOST = PropertiesConfig.getValue("nginx_host");
    /** 微信网页授权获取openid后放入cookie中的有效期 */
    public static final int OPENID_COOKIE_DATE = 604800;

    /** 消息参数：消息类型，文本 **/
    public static final String MESSAGE_TYPE_TEXT = "text";
    /** 消息参数：消息类型，图片 **/
    public static final String MESSAGE_TYPE_IMAGE = "image";
    /** 消息参数：消息类型，语音 **/
    public static final String MESSAGE_TYPE_VOICE = "voice";
    /** 消息参数：消息类型，视频 **/
    public static final String MESSAGE_TYPE_VIDEO = "video";
    /** 消息参数：消息类型，小视频 **/
    public static final String MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    /** 消息参数：消息类型，地理位置 **/
    public static final String MESSAGE_TYPE_LOCATION = "location";
    /** 消息参数：消息类型，链接 **/
    public static final String MESSAGE_TYPE_LINK = "link";
    /** 消息参数：音乐类型，主要用于回复 **/
    public static final String MESSAGE_TYPE_MUSIC = "music";
    /** 消息参数：图文类型，主要用于回复 **/
    public static final String MESSAGE_TYPE_NEWS = "news";
    /** 消息参数：事件类型，subscribe(订阅)、unsubscribe(取消订阅) **/
    public static final String MESSAGE_TYPE_EVENT = "event";
    /** 消息参数：事件类型,关注公众号事件 **/
    public static final String MESSAGE_TYPE_EVENT_SUBSCRIBE = "subscribe";
    /** 消息参数：事件类型,取消关注 事件 **/
    public static final String MESSAGE_TYPE_EVENT_UNSUBSCRIBE = "unsubscribe";
    /** 消息参数：事件类型,已关注公众号，扫描公众号事件 **/
    public static final String MESSAGE_TYPE_EVENT_SCAN = "SCAN";
    /** 消息参数：事件类型,上报地理位置事件 **/
    public static final String MESSAGE_TYPE_EVENT_LOCATION = "LOCATION";
    /** 消息参数：事件类型,点击自定义菜单事件 **/
    public static final String MESSAGE_TYPE_EVENT_CLICK = "CLICK";
    /** 消息参数：事件类型,发送模板事件 */
    public static final String MESSAGE_TYPE_TEMPLATE_SEND = "TEMPLATESENDJOBFINISH";
    /** 微信网页授权，获取code */
    public static final String URL_AUTH_GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=#APPID#&redirect_uri=#REDIRECT_URI#&response_type=code&scope=#SCOPE#&state=#STATE##wechat_redirect";
    /** 微信网页授权获取openid */
    public static final String URL_AUTH_GET_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=#APPID#&secret=#SECRET#&code=#CODE#&grant_type=authorization_code";
    /** 微信获取accesstoken接口 */
    public static final String URL_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=#APPID#&secret=#SECRET#";
    /** 微信网页授权:获取微信公众号用户信息 ,注意与用户关注后获取用户信息不同 */
    public static final String URL_GET_USER_INFO_OAUTH = "https://api.weixin.qq.com/sns/userinfo?access_token=#ACCESS_TOKEN#&openid=#OPENID#&lang=zh_CN";
    /** 用户关注后获取用户信息，注意与授权获取用户信息不同接口 */
    public static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=#ACCESS_TOKEN#&openid=#OPENID#&lang=zh_CN";
    /** 获取公众号页面调用jssdk的凭证 */
    public static final String URL_GET_JS_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=#ACCESS_TOKEN#&type=jsapi";
    /** 创建公众号的标签 */
    public static final String URL_TAG_CREATE = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=#ACCESS_TOKEN#";
    /** 查询公众号所有的标签 */
    public static final String URL_TAG_LIST = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=#ACCESS_TOKEN#";
    /** 查询某用户所有的标签 */
    public static final String URL_USER_TAG_LIST = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=#ACCESS_TOKEN#";
    /** 编辑公众号标签 */
    public static final String URL_TAG_UPDATE = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=#ACCESS_TOKEN#";
    /** 删除公众号标签 */
    public static final String URL_TAG_DELETE = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=#ACCESS_TOKEN#";
    /** 给用户绑定标签 */
    public static final String URL_TAG_USER_BIND = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=#ACCESS_TOKEN#";
    /** 给用户取消标签 */
    public static final String URL_TAG_USER_UNBIND = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=#ACCESS_TOKEN#";
    /** 创建个性化菜单 */
    public static final String URL_MENU_ADD = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=#ACCESS_TOKEN#";
    /** 删除个性化菜单 */
    public static final String URL_MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=#ACCESS_TOKEN#";
    /** 删除所有菜单，包括默认和个性化的菜单 */
    public static final String URL_MENU_DELETE_ALL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=#ACCESS_TOKEN#";
    /** 添加微信公众号默认菜单 */
    public static final String URL_MENU_ADD_DEFAULT = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=#ACCESS_TOKEN#";
    /** 获取临时素材 */
    public static final String URL_MEDIA_GET = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=#ACCESS_TOKEN#&media_id=#MEDIA_ID#";
    /** 获取公众号已关注的用户openid集合 */
    public static final String URL_USERS_GET = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=#ACCESS_TOKEN#&next_openid=#NEXT_OPENID#";
    /** 微信查询所有模板 */
    public static final String URL_TEMPLATE_LIST = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=#ACCESS_TOKEN#";
    /** 微信发送模板接口地址 */
    public static final String URL_TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=#ACCESS_TOKEN#";
    /** 查询所有菜单，包括默认和个性化的菜单 */
    public static final String URL_MENU_QUERY_ALL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=#ACCESS_TOKEN#";
    /** 微信菜单类型之按钮点击类型 */
    public static final String WECHAT_MENU_TYPE_CLICK = "click";
    /** 微信菜单类型之链接类型 */
    public static final String WECHAT_MENU_TYPE_VIEW = "view";
    /** 业务系统微信菜单类型-链接 */
    public static final int SYS_MENU_TYPE_VIEW = 1;
    /** 业务系统微信菜单类型-按钮 */
    public static final int SYS_MENU_TYPE_CLICK = 2;
    /** 微信菜单类型之小程序类型 */
    public static final String WECHAT_MENU_TYPE_MINIPROGRAM = "miniprogram";
    /** 微信公众号接口调用成功状态 */
    public static final Integer STATUS_SUCCESS_OK = 0;
    /** 业务系统默认菜单标签类型 */
    public static final Integer SYS_ROLE_TAG_DEFAULT = 0;
    /** 业务系统操作成功 */
    public static final Integer SYS_CODE_SUCCESS = 1;
    /** 业务系统普通用户角色主键值 */
    public static final Long SYS_ROLE_DEFAULT = 1L;
    /** 业务系统操作发生异常 */
    public static final Integer SYS_CODE_ERROR = 500;
    /** 业务系统操作发生参数异常 */
    public static final Integer SYS_CODE_ERROR_PARAM = 1003;
    /** 模板路径 */
    public static final String TEMPLATE_PATH = PropertiesConfig.getValue("file_template_path");
    /** 模板节点名称 */
    public static final String WECHAT_TEMPLATE_TEMPLATE = "template";// 模板父节点
    public static final String WECHAT_TEMPLATE_REFRESH = "refresh";// 刷新
    public static final String WECHAT_TEMPLATE_TEMPLATEID = "templateId";// 模板ID
    public static final String WECHAT_TEMPLATE_TITLE = "title";// 模板标题
    public static final String WECHAT_TEMPLATE_PRIMARYINDUSTRY = "primaryIndustry";// 模板所属行业的一级行业
    public static final String WECHAT_TEMPLATE_DEPUTYINDUSTRY = "deputyIndustry";// 模板所属行业的二级行业
    public static final String WECHAT_TEMPLATE_CONTENT = "content";// 模板内容
    public static final String WECHAT_TEMPLATE_EXAMPLE = "example";// 模板示例
    public static final String WECHAT_TEMPLATE_TYPE = "type";// 自定义类型
    public static final String WECHAT_TEMPLATE_DESCRIPTION = "description";// 描述
    public static final String WECHAT_TEMPLATE_MESSAGE_LIST = "messageList";// 通知模板可填参数集合
    public static final String WECHAT_TEMPLATE_COLOR = "#000000";// 模板字体颜色
    /** 微信公众号的access_token超时的错误代码 */
    public static final String ERROR_CODE_ACCESS_TOKEN = "40001,42001,40014";
    /** 发送验证码模板标识 */
    public static String TEMPLATE_ID_CODE;
    /** 发送新订单模板标识 */
    public static String TEMPLATE_ID_ORDER_NEW;
    /** 发送订单完成模板标识 */
    public static String TEMPLATE_ID_ORDER_FINISH;
    /** 发送订单状态模板标识 */
    public static String TEMPLATE_ID_ORDER_STATUS;
    /** 发送预约成功模板标识 */
    public static String TEMPLATE_ID_BOOKING_SUCCESS;
    /** 发送验证码模板标识名称关键字 */
    public static final String TEMPLATE_KEY_CODE = "验证码";
    /** 发送预约成功模板标识名称关键字 */
    public static final String TEMPLATE_KEY_BOOKING = "预约成功";
    /** 发送新订单模板标识名称关键字 */
    public static final String TEMPLATE_KEY_ORDER_NEW = "新订单";
    /** 发送订单完成模板标识名称关键字 */
    public static final String TEMPLATE_KEY_ORDER_FINISH = "订单完成";
    /** 发送订单状态模板标识名称关键字 */
    public static final String TEMPLATE_KEY_ORDER_STATUS = "订单状态";
}
