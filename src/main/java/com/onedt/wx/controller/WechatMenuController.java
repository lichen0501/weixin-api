package com.onedt.wx.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onedt.wx.entity.WechatMenu;
import com.onedt.wx.entity.WechatMenu.Menu;
import com.onedt.wx.entity.WechatRoleTag;
import com.onedt.wx.entity.WechatTag;
import com.onedt.wx.entity.sys.ReqBodyObj;
import com.onedt.wx.entity.sys.RespBodyObj;
import com.onedt.wx.service.IWechatMenuService;
import com.onedt.wx.service.impl.WechatTagServiceImpl;
import com.onedt.wx.utils.JacksonUtils;
import com.onedt.wx.utils.PropertiesConfig;

/**
 * 微信公众号菜单接口 Controller
 * 
 * @author nemo
 * @date 2017-12-01 09:35:17
 */
@RestController
@RequestMapping("/wx/menu")
public class WechatMenuController {
	private Logger logger = LoggerFactory.getLogger(WechatMenuController.class);
	@Autowired
	private IWechatMenuService menuService;

	/**
	 * 新增微信公众号的菜单，重复新增菜单会覆盖等价于修改
	 * 
	 * 添加公众号角色标签对应的菜单,默认菜单（普通角色）WechatRoleTag对象的id属性值约定为1;
	 * 
	 * 返回WechatMenu结果列表，WechatMenu对象的errcode属性值为0表示添加某角色的菜单成功。
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addMenuList")
	public RespBodyObj<List<WechatMenu>> addMenuList(@RequestBody ReqBodyObj<List<WechatRoleTag>> roleTags)
			throws Exception {
		logger.info("[addMenuList]添加菜单，数据" + JacksonUtils.getJsonCamelLower(roleTags));
		return RespBodyObj.ok(menuService.addMenu(roleTags.getData()));
	}

	/**
	 * 新增微信公众号的菜单，重复新增菜单会覆盖等价于修改
	 * 
	 * 添加公众号角色标签对应的菜单,默认菜单（普通角色）WechatRoleTag对象的id属性值约定为1;
	 * 
	 * 返回WechatMenu结果列表，WechatMenu对象的errcode属性值为0表示添加某角色的菜单成功。
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addMenu")
	public RespBodyObj<List<WechatMenu>> addMenuByStr(@RequestBody ReqBodyObj<String> roleTagsStr) throws Exception {
		logger.info("[addMenuByStr]添加菜单，数据" + JacksonUtils.getJsonCamelLower(roleTagsStr));
		if (roleTagsStr != null && StringUtils.isNotEmpty(roleTagsStr.getData())) {
			return RespBodyObj
					.ok(menuService.addMenu(JacksonUtils.getObjectCamelLower(new TypeReference<List<WechatRoleTag>>() {
					}, roleTagsStr.getData())));
		} else {
			return RespBodyObj.error("菜单数据不能为空");
		}
	}

	/**
	 * 查询所有菜单，包括默认和个性化菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/queryMenuAll")
	public RespBodyObj<WechatMenu> queryMenuAll() throws Exception {
		return RespBodyObj.ok(menuService.queryMenuAll());
	}

	/**
	 * 删除所有菜单，包括默认和个性化菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deleteMenuAll")
	public RespBodyObj<WechatMenu> deleteMenuAll() throws Exception {
		return RespBodyObj.ok(menuService.deleteMenuAll());
	}

	/**
	 * ET健康信息服务管理站微信公众号菜单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/menuMedial")
	public RespBodyObj<WechatMenu> menuTestMedial(@RequestParam Map<String, Object> param) throws Exception {
		String randomStr = createRandomStr();
		List<Menu> menuList = new ArrayList<Menu>();
		Menu m1 = new Menu();
		m1.setName("进入首页");
		m1.setType("view");
		m1.setUrl(randomStr);
		menuList.add(m1);
		WechatMenu wechatMenu = new WechatMenu();
		wechatMenu.setButton(menuList);
		WechatMenu result = menuService.addDefaultMenu(wechatMenu);
		if (result != null) {
			logger.info("[addMenu]向微信公众号菜单结果：" + JacksonUtils.getJsonCamelLower(result) + "。");
		} else {
			logger.info("[addMenu]向微信公众号添加菜单失败：" + result);
		}
		return RespBodyObj.ok(result);
	}

	/**
	 * 测试例子,普通用户
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/menuTest")
	public RespBodyObj<Map<String, Object>> menuTest(@RequestParam Map<String, Object> param) throws Exception {
		String randomStr = createRandomStr();
		List<Menu> menuList = new ArrayList<Menu>();
		Menu m1 = new Menu();
		m1.setName("\u2795科普");
		Menu m2 = new Menu();
		m2.setName("\u267b速动态");
		m2.setType("view");
		m2.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=543");
		Menu m3 = new Menu();
		m3.setName("\u267b涨知识");
		m3.setType("view");
		m3.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=544");
		Menu m4 = new Menu();
		m4.setName("\u267b新奇事");
		m4.setType("view");
		m4.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=519");
		Menu m5 = new Menu();
		m5.setName("\u267b趣视频");
		m5.setType("view");
		m5.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=545");
		Menu m56 = new Menu();
		m56.setName("\u267b分类投放指引");
		m56.setType("view");
		m56.setUrl(randomStr + "#/other/one");
		List<Menu> child = new ArrayList<Menu>();
		child.add(m2);
		child.add(m3);
		child.add(m4);
		child.add(m5);
		child.add(m56);
		m1.setSubButton(child);

		Menu m22 = new Menu();
		m22.setName("\u2795活动");
		Menu m6 = new Menu();
		m6.setName("\u267b晒光盘 赢奖品");
		m6.setType("view");
		m6.setUrl("https://mp.weixin.qq.com/s/sP8QKYFgsFZ6nBF-B37qHw");
		Menu m7 = new Menu();
		m7.setName("\u267b扫光盘 赢红包");
		m7.setType("view");
		m7.setUrl("https://mp.weixin.qq.com/s/mfkl3CiyUaJgeXG8rW-gPA");
		Menu m71 = new Menu();
		m71.setName("\u267b餐饮商家优惠");
		m71.setType("view");
		m71.setUrl("https://mp.weixin.qq.com/s/G6znDfTPIasFnsIB3yN_Vg");
		Menu m72 = new Menu();
		m72.setName("\u267b光盘行动介绍");
		m72.setType("view");
		m72.setUrl("https://mp.weixin.qq.com/s/Za9KJWycny573iyaNSMp-g");
		Menu m73 = new Menu();
		m73.setName("\u267b创建文明城市");
		m73.setType("view");
		m73.setUrl(randomStr + "#/other/two");
		List<Menu> child2 = new ArrayList<Menu>();
		child2.add(m6);
		child2.add(m7);
		child2.add(m71);
		child2.add(m72);
		child2.add(m73);
		m22.setSubButton(child2);
		Menu m33 = new Menu();
		m33.setName("\u2795服务");
		// Menu m9 = new Menu();
		// m9.setName("\u267b");
		// m9.setType("view");
		// m9.setUrl("http://wx.etidg.cn/wechat/html/PropertyBooking.html");
//		Menu m10 = new Menu();
//		m10.setName("\u267b信息查询");
//		m10.setType("view");
//		m10.setUrl(randomStr + "#/station/stationInfo");
		Menu m11 = new Menu();
		m11.setName("\u267b个人中心");
		m11.setType("view");
		m11.setUrl(randomStr + "#/user-info/person");
		List<Menu> child3 = new ArrayList<Menu>();
		// child3.add(m9);
//		child3.add(m10);
		child3.add(m11);
		m33.setSubButton(child3);
		menuList.add(m1);
		menuList.add(m22);
		menuList.add(m33);
		WechatMenu wechatMenu = new WechatMenu();
		wechatMenu.setButton(menuList);
		WechatMenu result = menuService.addDefaultMenu(wechatMenu);
		if (result != null && result.getMenuid() != null) {
			logger.info("[addMenu]向微信公众号[普通居民 ]添加自定义菜单成功。菜单标识为：" + result.getMenuid() + "。");
		} else {
			logger.info("[addMenu]向微信公众号[ 普通居民 ]添加自定义菜单失败：" + JacksonUtils.getJsonCamelLower(result));
		}

		WechatTagServiceImpl impl = new WechatTagServiceImpl();
		WechatTag tags = impl.tagsList();
		if (tags != null) {
			logger.info(JacksonUtils.getJsonCamelLower(tags));
		}
		return RespBodyObj.ok(param);
	}

	/**
	 * 测试例子,管理员
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/menuTest1")
	public RespBodyObj<Map<String, Object>> menuTest1(@RequestParam Map<String, Object> param) throws Exception {
		String tagId = (String) param.get("tagId");
		if (StringUtils.isEmpty(tagId)) {
			if ("wx2edc8548d26a0f77".equals(PropertiesConfig.getValue("wechat.app_id"))) {// 一体车联
				tagId = "104";
			} else if ("wx678d98d1c7c1111f".equals(PropertiesConfig.getValue("wechat.app_id"))) {// 深圳垃圾分类
				tagId = "121";
			} else {
				return RespBodyObj.error("缺少tagId参数");
			}
			logger.info("缺少tagId参数,将采用默认tagid=" + tagId);
		} else {
			logger.info("[管理员menuTest1]tagid=" + tagId);
		}
		String randomStr = createRandomStr();
		List<Menu> menuList = new ArrayList<Menu>();
		Menu m1 = new Menu();
		m1.setName("\u2795科普");
		// m1.setType("click");
		// m1.setKey("7");
		Menu m2 = new Menu();
		m2.setName("\u267b速动态");
		m2.setType("view");
		m2.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=543");
		Menu m3 = new Menu();
		m3.setName("\u267b涨知识");
		m3.setType("view");
		m3.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=544");
		Menu m4 = new Menu();
		m4.setName("\u267b新奇事");
		m4.setType("view");
		m4.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=519");
		Menu m5 = new Menu();
		m5.setName("\u267b趣视频");
		m5.setType("view");
		m5.setUrl("https://mp.weixin.qq.com/s/Za9KJWycny573iyaNSMp-g");
		Menu m56 = new Menu();
		m56.setName("\u267b分类投放指引");
		m56.setType("view");
		m56.setUrl(randomStr + "#/other/one");
		List<Menu> child = new ArrayList<Menu>();
		child.add(m2);
		child.add(m3);
		child.add(m4);
		child.add(m5);
		child.add(m56);
		m1.setSubButton(child);

		Menu m22 = new Menu();
		m22.setName("\u2795活动");
		// m22.setType("click");
		// m22.setKey("6");
		Menu m6 = new Menu();
		m6.setName("\u267b晒光盘 赢奖品");
		m6.setType("view");
		m6.setUrl("https://mp.weixin.qq.com/s/sP8QKYFgsFZ6nBF-B37qHw");
		Menu m7 = new Menu();
		m7.setName("\u267b扫光盘 赢红包");
		m7.setType("view");
		m7.setUrl("https://mp.weixin.qq.com/s/mfkl3CiyUaJgeXG8rW-gPA");
		Menu m71 = new Menu();
		m71.setName("\u267b餐饮商家优惠");
		m71.setType("view");
		m71.setUrl("https://mp.weixin.qq.com/s/G6znDfTPIasFnsIB3yN_Vg");
		Menu m72 = new Menu();
		m72.setName("\u267b光盘行动介绍");
		m72.setType("view");
		m72.setUrl("https://mp.weixin.qq.com/s/Za9KJWycny573iyaNSMp-g");
		Menu m73 = new Menu();
		m73.setName("\u267b创建文明城市");
		m73.setType("view");
		m73.setUrl(randomStr + "#/other/two");
		List<Menu> child2 = new ArrayList<Menu>();
		child2.add(m6);
		child2.add(m7);
		child2.add(m71);
		child2.add(m72);
		child2.add(m73);
		m22.setSubButton(child2);
		Menu m33 = new Menu();
		m33.setName("\u2795服务");
		// m33.setType("click");
		// m33.setKey("1");
		Menu m9 = new Menu();
		m9.setName("\u267b数据报表");
		m9.setType("view");
		m9.setUrl(randomStr + "#/manager/excel");
//		Menu m10 = new Menu();
//		m10.setName("\u267b信息查询");
//		m10.setType("view");
//		m10.setUrl(randomStr + "#/station/stationInfo");
		Menu m11 = new Menu();
		m11.setName("\u267b个人中心");
		m11.setType("view");
		m11.setUrl(randomStr + "#/user-info/manager");
		List<Menu> child3 = new ArrayList<Menu>();
		child3.add(m9);
//		child3.add(m10);
		child3.add(m11);
		m33.setSubButton(child3);
		menuList.add(m1);
		menuList.add(m22);
		menuList.add(m33);
		WechatMenu wechatMenu = new WechatMenu();
		wechatMenu.setButton(menuList);
		WechatMenu.Matchrule matchrule = new WechatMenu.Matchrule();// 标签信息
		matchrule.setTagId(tagId);// 管理员：一体车联104，深圳环卫121
		wechatMenu.setMatchrule(matchrule);
		WechatMenu result = menuService.addMenu(wechatMenu);
		if (result != null && result.getMenuid() != null) {
			logger.info("[addMenu]向微信公众号[管理员 ]添加个性化菜单成功。菜单标识为：" + result.getMenuid() + "。");
		} else {
			logger.info("[addMenu]向微信公众号[ 管理员 ]添加个性化菜单失败：" + JacksonUtils.getJsonCamelLower(result));
		}
		return RespBodyObj.ok(param);
	}

	/**
	 * 测试例子,清运
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping("/menuTest2")
	public RespBodyObj<Map<String, Object>> menuTest2(@RequestParam Map<String, Object> param) throws Exception {
		String tagId = (String) param.get("tagId");
		if (StringUtils.isEmpty(tagId)) {
			if ("wx2edc8548d26a0f77".equals(PropertiesConfig.getValue("wechat.app_id"))) {// 一体车联
				tagId = "106";
			} else if ("wx678d98d1c7c1111f".equals(PropertiesConfig.getValue("wechat.app_id"))) {// 深圳垃圾分类
				tagId = "123";
			} else {
				return RespBodyObj.error("缺少tagId参数");
			}
			logger.info("缺少tagId参数,将采用默认tagid=" + tagId);
		} else {
			logger.info("[清运menuTest2]tagid=" + tagId);
		}
		String randomStr = createRandomStr();
		List<Menu> menuList = new ArrayList<Menu>();
		Menu m1 = new Menu();
		m1.setName("\u2795科普");
		// m1.setType("click");
		// m1.setKey("97");
		Menu m2 = new Menu();
		m2.setName("\u267b速动态");
		m2.setType("view");
		m2.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=543");
		Menu m3 = new Menu();
		m3.setName("\u267b涨知识");
		m3.setType("view");
		m3.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=544");
		Menu m4 = new Menu();
		m4.setName("\u267b新奇事");
		m4.setType("view");
		m4.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=519");
		Menu m5 = new Menu();
		m5.setName("\u267b趣视频");
		m5.setType("view");
		m5.setUrl("https://mp.weixin.qq.com/s/Za9KJWycny573iyaNSMp-g");
		Menu m56 = new Menu();
		m56.setName("\u267b分类投放指引");
		m56.setType("view");
		m56.setUrl(randomStr + "#/other/one");
		List<Menu> child = new ArrayList<Menu>();
		child.add(m2);
		child.add(m3);
		child.add(m4);
		child.add(m5);
		child.add(m56);
		m1.setSubButton(child);

		Menu m22 = new Menu();
		m22.setName("\u2795活动");
		// m22.setType("click");
		// m22.setKey("96");
		Menu m6 = new Menu();
		m6.setName("\u267b晒光盘 赢奖品");
		m6.setType("view");
		m6.setUrl("https://mp.weixin.qq.com/s/sP8QKYFgsFZ6nBF-B37qHw");
		Menu m7 = new Menu();
		m7.setName("\u267b扫光盘 赢红包");
		m7.setType("view");
		m7.setUrl("https://mp.weixin.qq.com/s/mfkl3CiyUaJgeXG8rW-gPA");
		Menu m71 = new Menu();
		m71.setName("\u267b餐饮商家优惠");
		m71.setType("view");
		m71.setUrl("https://mp.weixin.qq.com/s/G6znDfTPIasFnsIB3yN_Vg");
		Menu m72 = new Menu();
		m72.setName("\u267b光盘行动介绍");
		m72.setType("view");
		m72.setUrl("https://mp.weixin.qq.com/s/Za9KJWycny573iyaNSMp-g");
		Menu m73 = new Menu();
		m73.setName("\u267b创建文明城市");
		m73.setType("view");
		m73.setUrl(randomStr + "#/other/two");
		List<Menu> child2 = new ArrayList<Menu>();
		child2.add(m6);
		child2.add(m7);
		child2.add(m71);
		child2.add(m72);
		child2.add(m73);
		m22.setSubButton(child2);
		Menu m33 = new Menu();
		m33.setName("\u2795服务");
		Menu m9 = new Menu();
		m9.setName("\u267b订单处理");
		m9.setType("view");
		m9.setUrl(randomStr + "#/transport/transportHandle/addAppoint");
//		Menu m10 = new Menu();
//		m10.setName("\u267b信息查询");
//		m10.setType("view");
//		m10.setUrl(randomStr + "#/station/stationInfo");
		Menu m11 = new Menu();
		m11.setName("\u267b个人中心");
		m11.setType("view");
		m11.setUrl(randomStr + "#/user-info/manager");
		List<Menu> child3 = new ArrayList<Menu>();
		child3.add(m9);
//		child3.add(m10);
		child3.add(m11);
		m33.setSubButton(child3);
		menuList.add(m1);
		menuList.add(m22);
		menuList.add(m33);
		WechatMenu wechatMenu = new WechatMenu();
		wechatMenu.setButton(menuList);
		WechatMenu.Matchrule matchrule = new WechatMenu.Matchrule();// 标签信息
		matchrule.setTagId(tagId);// 清运：一体车联106，深圳环卫123
		wechatMenu.setMatchrule(matchrule);
		WechatMenu result = menuService.addMenu(wechatMenu);
		if (result != null && result.getMenuid() != null) {
			logger.info("[addMenu]向微信公众号[清运 ]添加个性化菜单成功。菜单标识为：" + result.getMenuid() + "。");
		} else {
			logger.info("[addMenu]向微信公众号[ 清运]添加个性化菜单失败：" + JacksonUtils.getJsonCamelLower(result));
		}
		return RespBodyObj.ok(param);
	}

	/**
	 * 测试例子,物业
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping("/menuTest3")
	public RespBodyObj<Map<String, Object>> menuTest3(@RequestParam Map<String, Object> param) throws Exception {
		String tagId = (String) param.get("tagId");
		if (StringUtils.isEmpty(tagId)) {
			if ("wx2edc8548d26a0f77".equals(PropertiesConfig.getValue("wechat.app_id"))) {// 一体车联
				tagId = "105";
			} else if ("wx678d98d1c7c1111f".equals(PropertiesConfig.getValue("wechat.app_id"))) {// 深圳垃圾分类
				tagId = "122";
			} else {
				return RespBodyObj.error("缺少tagId参数");
			}
			logger.info("缺少tagId参数,将采用默认tagid=" + tagId);
		} else {
			logger.info("[物业menuTest3]tagid=" + tagId);
		}
		String randomStr = createRandomStr();
		List<Menu> menuList = new ArrayList<Menu>();
		Menu m1 = new Menu();
		m1.setName("\u2795科普");
		// m1.setType("click");
		// m1.setKey("106");
		Menu m2 = new Menu();
		m2.setName("\u267b速动态");
		m2.setType("view");
		m2.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=543");
		Menu m3 = new Menu();
		m3.setName("\u267b涨知识");
		m3.setType("view");
		m3.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=544");
		Menu m4 = new Menu();
		m4.setName("\u267b新奇事");
		m4.setType("view");
		m4.setUrl("http://wesalt582.ffdf750515414932.iwesalt.com/ElementsPage/index.aspx?pageId=519");
		Menu m5 = new Menu();
		m5.setName("\u267b趣视频");
		m5.setType("view");
		m5.setUrl("https://mp.weixin.qq.com/s/Za9KJWycny573iyaNSMp-g");
		Menu m56 = new Menu();
		m56.setName("\u267b分类投放指引");
		m56.setType("view");
		m56.setUrl(randomStr + "#/other/one");
		List<Menu> child = new ArrayList<Menu>();
		child.add(m2);
		child.add(m3);
		child.add(m4);
		child.add(m5);
		child.add(m56);
		m1.setSubButton(child);

		Menu m22 = new Menu();
		m22.setName("\u2795活动");
		// m22.setType("click");
		// m22.setKey("106");
		Menu m6 = new Menu();
		m6.setName("\u267b晒光盘 赢奖品");
		m6.setType("view");
		m6.setUrl("https://mp.weixin.qq.com/s/sP8QKYFgsFZ6nBF-B37qHw");
		Menu m7 = new Menu();
		m7.setName("\u267b扫光盘 赢红包");
		m7.setType("view");
		m7.setUrl("https://mp.weixin.qq.com/s/mfkl3CiyUaJgeXG8rW-gPA");
		Menu m71 = new Menu();
		m71.setName("\u267b餐饮商家优惠");
		m71.setType("view");
		m71.setUrl("https://mp.weixin.qq.com/s/G6znDfTPIasFnsIB3yN_Vg");
		Menu m72 = new Menu();
		m72.setName("\u267b光盘行动介绍");
		m72.setType("view");
		m72.setUrl("https://mp.weixin.qq.com/s/Za9KJWycny573iyaNSMp-g");
		Menu m73 = new Menu();
		m73.setName("\u267b创建文明城市");
		m73.setType("view");
		m73.setUrl(randomStr + "#/other/two");
		List<Menu> child2 = new ArrayList<Menu>();
		child2.add(m6);
		child2.add(m7);
		child2.add(m71);
		child2.add(m72);
		child2.add(m73);
		m22.setSubButton(child2);
		Menu m33 = new Menu();
		m33.setName("\u2795服务");
		Menu m90 = new Menu();
		m90.setName("\u267b年花年桔回收");
		m90.setType("view");
		m90.setUrl(randomStr + "#/reservation-recovery/juhua");
		Menu m9 = new Menu();
		m9.setName("\u267b大件垃圾回收");
		m9.setType("view");
		m9.setUrl(randomStr + "#/reservation-recovery/make-appointment");
//		Menu m10 = new Menu();
//		m10.setName("\u267b信息查询");
//		m10.setType("view");
//		m10.setUrl(randomStr + "#/station/stationInfo");
		Menu m11 = new Menu();
		m11.setName("\u267b个人中心");
		m11.setType("view");
		m11.setUrl(randomStr + "#/reservation-recovery/person-center/1");
		List<Menu> child3 = new ArrayList<Menu>();
		child3.add(m90);
		child3.add(m9);
//		child3.add(m10);
		child3.add(m11);
		m33.setSubButton(child3);
		menuList.add(m1);
		menuList.add(m22);
		menuList.add(m33);
		WechatMenu wechatMenu = new WechatMenu();
		wechatMenu.setButton(menuList);
		WechatMenu.Matchrule matchrule = new WechatMenu.Matchrule();// 标签信息
		matchrule.setTagId(tagId);// 物业：一体车联105,深圳环卫122
		wechatMenu.setMatchrule(matchrule);
		WechatMenu result = menuService.addMenu(wechatMenu);
		if (result != null && result.getMenuid() != null) {
			logger.info("[addMenu]向微信公众号[物业]添加个性化菜单成功。菜单标识为：" + result.getMenuid() + "。");
		} else {
			logger.info("[addMenu]向微信公众号[ 物业 ]添加个性化菜单失败：" + JacksonUtils.getJsonCamelLower(result));
		}
		return RespBodyObj.ok(param);
	}

	public void setMenuService(IWechatMenuService menuService) {
		this.menuService = menuService;
	}

	private String createRandomStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = PropertiesConfig.getValue("web.domain") + "?v=" + format.format(date);
		logger.info("[创建微信公众号菜单]地址前缀：" + str);
		return str;
	}
}
