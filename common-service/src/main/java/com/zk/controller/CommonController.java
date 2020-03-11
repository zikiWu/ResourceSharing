package com.zk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zk.exception.BussinessException;
import com.zk.mapper.UserMapper;
import com.zk.po.enums.DateTimePatternEnum;
import com.zk.po.enums.OrderByEnum;
import com.zk.po.enums.ResponseCode;
import com.zk.po.model.*;
import com.zk.po.model.Collection;
import com.zk.po.query.CollectionQuery;
import com.zk.po.query.CommentQuery;
import com.zk.po.query.MessageQuery;
import com.zk.po.query.UserFriendQuery;
import com.zk.po.utils.*;
import com.zk.po.vo.AjaxResponse;
import com.zk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class CommonController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(CommonController.class);
	@Value(value = "${resources_path}")
	String resources_path;//资源文件绝对地址目录


	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private MessageService messageService;

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private UserFriendService userFriendService;


	@RequestMapping("login")
	public String login(){
		return "flogin";
	}
	@RequestMapping("register")
	public String register(){
		return "fregister";
	}

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index(){
		return "index";
	}

	@ResponseBody
	@RequestMapping("login.do")
	public AjaxResponse<Object> logindo(HttpServletRequest request, HttpServletResponse response,
										String account, String password, String rememberMe){
		final String REMEMBERME = "1";
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		HttpSession session = request.getSession();
		User user = null;
		try {
			user = userService.login(account, password);
			user.setLastLoginTime(new Date());
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserid(user.getUserid());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserIcon(user.getUserIcon());
			if(REMEMBERME.equals(rememberMe)){    // 清除之前的Cookie 信息
//				String infor = URLEncoder.encode(account.toString(), "utf-8") + "|" + user.getPassword();
				Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				// 建用户信息保存到Cookie中
				cookie = new Cookie(Constants.COOKIE_USER_INFO, JSON.toJSONString(sessionUser));
				cookie.setPath("/");
				// 设置最大生命周期为1年。
				cookie.setMaxAge(31536000);
				response.addCookie(cookie);
			}
			else {
				Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			String token = UUID.randomUUID().toString();
			ajaxResponse.setData(token);
			Cookie cookie = new Cookie(Constants.SESSION_USER_KEY, token);
			cookie.setValue(token);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			redisTemplate.opsForValue().set(token, JSON.toJSONString(sessionUser));
			redisTemplate.opsForValue().set(Constants.SESSION_USER_KEY, JSON.toJSONString(sessionUser));
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("用户登录失败,账号:{}",account);
		}
		catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("用户登录失败,账号:{}", account);
		}

		return ajaxResponse;
	}

	@ResponseBody
	@RequestMapping("register.do")
	public AjaxResponse<Object> registerdo(HttpSession session, User user){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			userService.register(user);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserid(user.getUserid());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserIcon(user.getUserIcon());
			session.setAttribute(Constants.SESSION_USER_KEY, sessionUser);


		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("用户注册失败,用户名:{}邮箱:{}", user.getUserName(), user.getEmail());
		}catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("用户注册失败,用户名:{}邮箱:{}", user.getUserName(), user.getEmail());
		}
		return ajaxResponse;
	}
	/**blur事件得到用户头像
	* @author wzk
	* @date 2019/3/6
	* @param
	*/
	@ResponseBody
	@RequestMapping("findHeadImage")
	public AjaxResponse<Object> findHeadImage(String account){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		String headIcon = null;
		try {
			headIcon = userService.findHeadIcon(account);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(headIcon);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
		}
		catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
		}
		return ajaxResponse;
	}

	@ResponseBody
	@RequestMapping("loadComment")
	public AjaxResponse<PageResult<Comment>> loadComment(HttpSession session, CommentQuery commentQuery){
		AjaxResponse<PageResult<Comment>> ajaxResponse = new AjaxResponse<PageResult<Comment>>();
		try {
			PageResult<Comment> pageResult = this.commentService.findCommentByPage(commentQuery);
			ajaxResponse.setData(pageResult);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("{}加载评论出错", e);
			ajaxResponse.setErrorMsg("加载评论出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	@ResponseBody
	@RequestMapping("getLoginUser")
	public String getLoginUser(HttpServletRequest request){
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			sessionUser = new SessionUser(10044,"123","default_usericon/8.jpg");
//			return "";
		}
		return JSON.toJSONString(sessionUser);
	}

	@ResponseBody
	@RequestMapping("getLoginUserForServer")
	public String getLoginUserForServer(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if (cookies == null){
			return redisTemplate.opsForValue().get(Constants.SESSION_USER_KEY).toString();
		}
		for (Cookie cookie1 : cookies){
			if(cookie1.getName().equals(Constants.SESSION_USER_KEY)){
				String token = cookie1.getValue();
				Object o =  redisTemplate.opsForValue().get(token);
				if(o != null){
					return (String)o;
				}
			}
		}
		Object o =  redisTemplate.opsForValue().get("1e5510ce-bbb9-4277-bc5e-daf4a55b2324");
		return String.valueOf((String)o);

	}


	@ResponseBody
	@RequestMapping("addComment")
	public AjaxResponse<Object> addComment(HttpSession session, Comment comment,HttpServletRequest request) throws BussinessException {
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		//添加积分
		this.setUserBaseInfo(Comment.class, comment, sessionUser);
		User user = userService.findUserByUserid(sessionUser.getUserid());
		user.setMark(user.getMark()+1);
		userService.updateUserInfo(user);
		try {
			this.commentService.addComment(comment);
			ajaxResponse.setData(comment);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", sessionUser.getUserName());
		}catch (Exception e) {
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			ajaxResponse.setErrorMsg("服务器出错");
			logger.error("{}评论出错", sessionUser.getUserName());
		}
		return ajaxResponse;
	}
	/** 进入消息中心
	* @author wzk
	* @date 2019/3/24
	* @param
	*/
	@RequestMapping("messageList")
	public ModelAndView messageList(HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/admin/message_list");
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取消息列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	/**個人消息
	* @author wzk
	* @date 2019/3/24
	* @param
	*/
	@ResponseBody
	@RequestMapping("load_user_message_list.action")
	public AjaxResponse<Object> load_user_message_list(HttpSession session, MessageQuery messageQuery,HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			messageQuery.setReceivedUserId(this.getUserid(request));
			messageQuery.setOrderBy(OrderByEnum.MESSAGE_STATUS_ASC_CREATE_TIME_DESC);
			PageResult<Message> result = this.messageService.findMessageByPage(messageQuery);
			ajaxResponse.setData(result);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		}catch (Exception e) {
			logger.error("获取消息列表出错{}", e);
			ajaxResponse.setErrorMsg("获取消息列表出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	/**收藏列表
	* @author wzk
	* @date 2019/3/24
	* @param
	*/

	@RequestMapping("collection_list.action")
	public ModelAndView collection_list(HttpSession session, CollectionQuery collectionQuery,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/admin/collection_list");
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.addObject("articleType", collectionQuery.getArticleType().getType());
		} catch (BussinessException e) {
			logger.error("获取收藏列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	/**收藏列表
	* @author wzk
	* @date 2019/4/16
	* @param
	*/

	@ResponseBody
	@RequestMapping("load_collection.action")
	public AjaxResponse<Object>load_collection(HttpSession session, CollectionQuery collectionQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			PageResult<Collection> result = this.collectionService.findCollectionByPage(collectionQuery);
			ajaxResponse.setData(result);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		}catch (Exception e) {
			logger.error("获取收藏列表出错{}", e);
			ajaxResponse.setErrorMsg("获取收藏列表出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	/**消息数
	* @author wzk
	* @date 2019/3/25
	* @param
	*/
	@ResponseBody
	@RequestMapping("load_user_message_count.action")
	public AjaxResponse<Object> load_user_message_count(HttpServletRequest request, MessageQuery messageQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
			if(sessionUser != null){
				messageQuery.setReceivedUserId(sessionUser.getUserid());
				messageQuery.setOrderBy(OrderByEnum.MESSAGE_STATUS_ASC_CREATE_TIME_DESC);
				Integer count = this.messageService.findMessageCount(messageQuery);
				ajaxResponse.setData(count);
				ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			}
		}catch (Exception e) {
			logger.error("获取消息列表出错{}", e);
			ajaxResponse.setErrorMsg("获取消息列表出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}



	public static String getSessionUser(HttpServletRequest request,RedisTemplate redisTemplate){
//		Cookie[] cookies = request.getCookies();
//		if (cookies == null){
//			return "";
//		}
//		for (Cookie cookie1 : cookies){
//			if(cookie1.getName().equals(Constants.SESSION_USER_KEY)){
//				String token = cookie1.getValue();
//				Object o =  redisTemplate.opsForValue().get(token);
//				if(o != null){
//					return (String)o;
//				}
//			}
//		}
		Object o =  redisTemplate.opsForValue().get(Constants.SESSION_USER_KEY);
		if(o != null){
			return (String)o;
		}
		return "";
	}

	@ResponseBody
	@RequestMapping("loadUserFriend")
	public AjaxResponse<Object> loadUserFriend(HttpSession session, int pageNum, HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		int userId = getUserid(request);
		UserFriendQuery userFriendQuery = new UserFriendQuery();
		userFriendQuery.setUserId(userId);
		userFriendQuery.setPageNum(pageNum);
		PageResult<UserFriend> pageResult = this.userFriendService.findFriendList(userFriendQuery);
		ajaxResponse.setData(pageResult);
		return ajaxResponse;
	}

	/**个人中心
	* @author wzk
	* @date 2019/3/25
	* @param
	*/

	@RequestMapping(value="/{userId}")
	public ModelAndView user(HttpServletRequest request, @PathVariable Integer userId){
		ModelAndView view = new ModelAndView("/user/home");
		try {
			User otherUser = this.userService.findUserInfo4UserHome(userId);
			view.addObject("otherUser", otherUser);

			String userStr = this.getSessionUser(request,redisTemplate);
			SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
			view.addObject("user", sessionUser);

			UserFriendQuery userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(this.getUserid(request));
			userFriendQuery.setFriendUserId(userId);
			view.addObject("focusType", this.userFriendService.findFocusType4UserHome(userFriendQuery));
			//获取粉丝和关注数量
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setFriendUserId(userId);
			view.addObject("fansCount", this.userFriendService.findCount(userFriendQuery));
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(userId);
			view.addObject("focusCount", this.userFriendService.findCount(userFriendQuery));

		} catch (BussinessException e) {
			logger.error("获取用户信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}



	/**其他用户个人中心
	 * @author wzk
	 * @date 2019/3/25
	 * @param
	 */

	@RequestMapping(value="/otherUser/{userId}")
	public ModelAndView otherUser(HttpServletRequest request, @PathVariable Integer userId){
		ModelAndView view = new ModelAndView("/user/other_home");
		try {
			User user = this.userService.findUserInfo4UserHome(userId);
			view.addObject("user", user);
			UserFriendQuery userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(this.getUserid(request));
			userFriendQuery.setFriendUserId(userId);
			view.addObject("focusType", this.userFriendService.findFocusType4UserHome(userFriendQuery));
			//获取粉丝和关注数量
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setFriendUserId(userId);
			view.addObject("fansCount", this.userFriendService.findCount(userFriendQuery));
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(userId);
			view.addObject("focusCount", this.userFriendService.findCount(userFriendQuery));

		} catch (BussinessException e) {
			logger.error("获取用户信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	/**关注的用户
	* @author wzk
	* @date 2019/3/25
	* @param
	*/
	@ResponseBody
	@RequestMapping("loadFocus")
	public AjaxResponse<Object> loadUserFriend(HttpSession session, UserFriendQuery userFriendQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			PageResult<UserFriend> pageResult = this.userFriendService.findFriendList(userFriendQuery);
			ajaxResponse.setData(pageResult);
		} catch (Exception e) {
			logger.error("加载关注用户异常", e);
			ajaxResponse.setErrorMsg("加载关注用户出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	/**加载粉丝
	* @author wzk
	* @date 2019/3/25
	* @param
	*/
	@ResponseBody
	@RequestMapping("loadFans")
	public AjaxResponse<Object> loadUserFans(HttpSession session, UserFriendQuery userFriendQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			PageResult<UserFriend> pageResult = this.userFriendService.findFansList(userFriendQuery);
			ajaxResponse.setData(pageResult);
		} catch (Exception e) {
			logger.error("加载用户粉丝异常", e);
			ajaxResponse.setErrorMsg("加载粉丝出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}


	@RequestMapping("preUpdatePassword")
	public ModelAndView preUpdatePassword(HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/admin/update_password");
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			view.setViewName("redirect:" +  "Constants.LOGINABSOLUTEPATH");
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}


	@ResponseBody
	@RequestMapping("modifyPassword")
	public AjaxResponse<Object> modifyPassword(HttpSession session,String oldPassword, String newPassword,HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		try {
			this.userService.updatePassword(sessionUser.getUserid(), oldPassword, newPassword);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			logger.error("修改出错", e);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
		}catch (Exception e) {
			logger.error("修改出错", e);
			ajaxResponse.setErrorMsg("修改出错,请重试{}");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	@RequestMapping("preUpdateUserIcon")
	public ModelAndView preUpdateUserIcon(HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/page/admin/update_usericon");
		SessionUser sessionUser = JSON.parseObject(getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			view.setViewName("redirect:" +  "Constants.LOGINABSOLUTEPATH");
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@ResponseBody
	@RequestMapping("saveSysUserIcon")
	public AjaxResponse<Object> saveSysUserIcon(HttpSession session, String userIcon,HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		Integer userId = this.getUserid(request);
		User user = new User();
		user.setUserid(userId);
		try {
			String dest = "user_icon/" + userId + ".jpg";
			this.userService.copyUserIcon(userIcon, dest);
			user.setUserIcon(dest);
			this.userService.updateUserWithoutValidate(user);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("修改出错{}", e);
			ajaxResponse.setErrorMsg("修改出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}





	public Integer getUserid(HttpServletRequest request) {
		SessionUser sessionUser = JSON.parseObject(this.getSessionUser(request,redisTemplate),SessionUser.class);
		return sessionUser.getUserid();
	}

	@RequestMapping(value = "UserService/findAllUser")
	@ResponseBody
	public List findAllUser(){
		List userList = userService.findAllUsers();
		return  userList;
	}


	@RequestMapping("/UserService/delUser")
	@ResponseBody
	public void getUsers(Integer userId)
	{
		try{
			this.userService.deleteUser(new Integer[]{userId});
		}catch (BussinessException b){

		}
	}

	/**标为已阅
	* @author wzk
	* @date 2019/4/14
	* @param
	*/

	@ResponseBody
	@RequestMapping("markMessageRead")
	public AjaxResponse<Object> markMessageRead(HttpServletRequest request, Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			this.messageService.update(ids, this.getUserid(request));
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		}catch (Exception e) {
			logger.error("消息标记已读出错{}", e);
			ajaxResponse.setErrorMsg("标记已读出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	/**删除
	* @author wzk
	* @date 2019/4/14
	* @param
	*/

	@ResponseBody
	@RequestMapping("delMessage")
	public AjaxResponse<Object> del_message(HttpServletRequest request, Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			this.messageService.delMessage( this.getUserid(request),ids);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		}catch (Exception e) {
			logger.error("消息删除出错{}", e);
			ajaxResponse.setErrorMsg("消息删除出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	@RequestMapping("readMessage.action")
	public ModelAndView readMessage(HttpServletRequest request, Integer id){
		ModelAndView view = new ModelAndView(Constants.ERROR_404);
		SessionUser sessionUser = JSON.parseObject(this.getSessionUser(request,redisTemplate),SessionUser.class);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.setViewName("redirect:" + this.messageService.getMessageById(id, sessionUser.getUserid()));
			this.messageService.update(new Integer[]{id}, sessionUser.getUserid());
		} catch (BussinessException e) {
			logger.error("获取消息列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@ResponseBody
	@RequestMapping("doCollection")
	public AjaxResponse<Object> doCollection(HttpServletRequest request, Collection collection){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		String userStr = this.getSessionUser(request,redisTemplate);
		if(userStr==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
		try {
			collection.setUserId(sessionUser.getUserid());
			this.collectionService.addCollection(collection);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("{}收藏出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
		}catch (Exception e) {
			logger.error("{}收藏出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
			ajaxResponse.setErrorMsg("服务器出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}


	@ResponseBody
	@RequestMapping("updateUserInfo")
	public AjaxResponse<Object> updateUserInfo(HttpServletRequest request,User user){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		String userStr = this.getSessionUser(request,redisTemplate);
		if(userStr==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
		user.setUserid(sessionUser.getUserid());
		try {
			this.userService.updateUserInfo(user);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			logger.error("修改出错", e);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
		}catch (Exception e) {
			logger.error("修改出错{}", e);
			ajaxResponse.setErrorMsg("修改出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	@ResponseBody
	@RequestMapping("focus.action")
	public AjaxResponse<Object> focus(HttpSession session, UserFriend userFriend,HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			String userStr = this.getSessionUser(request,redisTemplate);
			if(userStr==null){
				ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
				ajaxResponse.setErrorMsg("请先登录");
				return ajaxResponse;
			}
			SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
			userFriend.setUserId(sessionUser.getUserid());
			userFriend.setUserName(sessionUser.getUserName());
			userFriend.setUserIcon(sessionUser.getUserIcon());
			this.userFriendService.addFocus(userFriend);
		}catch (BussinessException e) {
			logger.error("关注异常", e);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		catch (Exception e) {
			logger.error("加载用户粉丝异常", e);
			ajaxResponse.setErrorMsg("关注出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	@ResponseBody
	@RequestMapping("cancel_focus.action")
	public AjaxResponse<Object> cancel_focus(HttpSession session, UserFriend userFriend,HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			String userStr = this.getSessionUser(request,redisTemplate);
			if(userStr==null){
				ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
				ajaxResponse.setErrorMsg("请先登录");
				return ajaxResponse;
			}
			SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
			userFriend.setUserId(sessionUser.getUserid());
			userFriend.setUserName(sessionUser.getUserName());
			userFriend.setUserIcon(sessionUser.getUserIcon());
			this.userFriendService.cancelFocus(userFriend);
		}catch (BussinessException e) {
			logger.error("关注异常", e);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		catch (Exception e) {
			logger.error("加载用户粉丝异常", e);
			ajaxResponse.setErrorMsg("关注出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	@RequestMapping("updateUser")
	public ModelAndView updateUser(HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/admin/update_user");
		String userStr = this.getSessionUser(request,redisTemplate);
		SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@RequestMapping("updateUserIcon")
	public ModelAndView updateUserIcon(HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/admin/update_userIcon");
		String userStr = this.getSessionUser(request,redisTemplate);
		SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session, HttpServletResponse response){
		ModelAndView view = new ModelAndView("/login");
		session.invalidate();
		Cookie cookie = new Cookie(Constants.SESSION_USER_KEY, null);
		redisTemplate.opsForValue().set(Constants.SESSION_USER_KEY, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return view;
	}

	@ResponseBody
	@RequestMapping("imageUpload2Temp.action")
	public Map<String, Object> imageUpload2Temp(HttpSession session, MultipartHttpServletRequest multirequest,
												HttpServletResponse response){
		String realPath = ServerUtils.getRealPath() + "/upload";
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> itr = multirequest.getFileNames();
		if(itr.hasNext()){
			MultipartFile multipartFile = multirequest.getFile(itr.next());
			long size = multipartFile.getSize();
			if(size > 30000){
				map.put("responseCode", ResponseCode.BUSSINESSERROR);
				map.put("msg", "文件不能超过3M");
				return map;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
			if(!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
					!"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)){
				map.put("responseCode", ResponseCode.BUSSINESSERROR);
				map.put("msg", "只能上传图片");
				return map;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
			String savePath = "http://localhost:8080/examples/"+saveDir + "/" + fileName;
			String fileDir = resources_path + "/" + saveDir;
			File dir = new File(fileDir);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			try {
				multipartFile.transferTo(file);
				map.put("responseCode", ResponseCode.SUCCESS);
				map.put("savePath", savePath);
				return map;
			} catch (Exception e) {
				map.put("responseCode", ResponseCode.SERVERERROR);
				map.put("msg", "服务器异常,上传失败");
				return map;
			}
		}
		return map;
	}

	@ResponseBody
	@RequestMapping("saveUserIcon")
	public AjaxResponse<Object> saveUserIcon( String img, Integer x1,
											 Integer y1, Integer width, Integer height,HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		if(StringUtils.isEmpty(img)){
			ajaxResponse.setErrorMsg("修改出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			return ajaxResponse;
		}
		String userStr = this.getSessionUser(request,redisTemplate);
		SessionUser sessionUser = JSON.parseObject(userStr,SessionUser.class);
		img="http:/"+img;
		try{
			User user1 = userService.findUserInfo4UserHome(sessionUser.getUserid());
			String[] icons = new String[]{"user_icon/10033.jpg","user_icon/10034.jpg","user_icon/10035.jpg"};
			user1.setUserIcon(icons[(int) ((Math.random()*10)%2)]);
			userService.updateUserInfo(user1);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		}catch(BussinessException e){
			logger.error("修改出错{}", e);
			ajaxResponse.setErrorMsg("修改出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}



}