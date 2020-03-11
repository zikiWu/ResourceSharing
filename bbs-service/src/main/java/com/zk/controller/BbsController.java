package com.zk.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zk.cache.CategoryCache;
import com.zk.exception.BussinessException;
import com.zk.mapper.TopicMapper;
import com.zk.po.enums.*;
import com.zk.po.model.*;
import com.zk.po.model.Collection;
import com.zk.po.query.CategoryQuery;
import com.zk.po.query.CollectionQuery;
import com.zk.po.query.TopicQuery;
import com.zk.po.query.UpdateQuery4ArticleCount;
import com.zk.po.utils.Constants;
import com.zk.po.utils.DateUtil;
import com.zk.po.utils.PageResult;
import com.zk.service.CategoryService;
import com.zk.service.CollectionService;
import com.zk.service.LikeService;
import com.zk.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("")
public class BbsController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(BbsController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TopicService topicService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CategoryCache categoryCache;

    @Autowired
	private TopicMapper topicMapper;

    @Autowired
	private LikeService likeService;

	@Autowired
	private CollectionService collectionService;

	/** bbs主页面
	* @author wzk
	* @date 2019/3/3
	* @param
	*/
	@RequestMapping
	public ModelAndView bbs(HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView("bbs");
		//查询条件
		CategoryQuery categoryQuery = new CategoryQuery();
		Date date = new Date();
		String curDate = DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern());
		categoryQuery.setStartDate(curDate);
		categoryQuery.setEndDate(curDate);
		//根据发帖量查询分类
		view.addObject("categories", categoryService.findCategory4TopicCount(categoryQuery));
		//查询发帖数最高的用户
		view.addObject("activeUser", this.topicService.findActiveUsers());
		//获取总贴子数
		view.addObject("count", this.topicService.findCount(null));
		//获取今日贴子
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setStartDate(curDate);
		topicQuery.setEndDate(curDate);
		view.addObject("today", this.topicService.findCount(topicQuery));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		topicQuery.setStartDate(DateUtil.format(calendar.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(calendar.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		Integer yesterdayCount = this.topicService.findCount(topicQuery);
		view.addObject("yesterdayCount",  yesterdayCount);
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		if(user != null){
			view.addObject("user",  user);
		}

		return view;
	}
	/** bbs帖子列表
	 * @author wzk
	 * @date 2019/3/3
	 * @param
	 */
	@RequestMapping(value = "/board/{pCategoryId}", method=RequestMethod.GET)
	public ModelAndView board(@PathVariable Integer pCategoryId, TopicQuery topicQuery){
		ModelAndView view = new ModelAndView("bbs_list");
		Category pCategory = this.categoryService.findCategoryBypCategoryId(pCategoryId);
		view.addObject("pCategory", pCategory);

		PageResult<Topic> result = this.topicService.findTopicByPage(topicQuery);
		view.addObject("result", result);

		//获取分类总贴子数
		TopicQuery query = new TopicQuery();
		query.setpCategoryId(pCategory.getCategoryId());
		view.addObject("count", this.topicService.findCount(query));
		//获取今日贴子数
		Date date = new Date();
		query.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		query.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", this.topicService.findCount(query));
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		view.addObject("user",  user);
		return view;
	}
	@RequestMapping(value = "/sub_board/{categoryId}", method=RequestMethod.GET)
	public ModelAndView sub_board(@PathVariable Integer categoryId, TopicQuery topicQuery){
		ModelAndView view = new ModelAndView("/bbs_list");
		Category pCategory = this.categoryService.findCategoryByCategoryId(categoryId);
		view.addObject("pCategory", pCategory);
		view.addObject("category", CategoryCache.getCategoryById(categoryId));
		PageResult<Topic> result = this.topicService.findTopicByPage(topicQuery);
		view.addObject("result", result);
		//获取分类总贴子数
		TopicQuery query = new TopicQuery();
		query.setpCategoryId(pCategory.getCategoryId());
		view.addObject("count", this.topicService.findCount(query));
		//获取今日贴子数
		Date date = new Date();
		query.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		query.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", this.topicService.findCount(query));
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		view.addObject("user",  user);
		return view;
	}
	/** 帖子详情
	* @author wzk
	* @date 2019/3/3
	* @param
	*/
	@RequestMapping("/{topicId}")
	public ModelAndView bbsDetail(@PathVariable Integer topicId,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/bbs_detail");
		try {
			Topic topic = this.topicService.showTopic(topicId);
			view.addObject("topic", topic);
		} catch (Exception e) {
			logger.error("{}贴子加载出错", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
		}
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		view.addObject("user",  user);
		return view;
	}
	/** 发帖页面
	* @author wzk
	* @date 2019/3/3
	* @param
	*/
	@RequestMapping("prePublicTopic")
	public ModelAndView prePublicTopic(HttpServletRequest request){

		String sessionUserStr = restTemplate.getForEntity("http://COMMON-SERVICE/getLoginUserForServer",String.class,request).getBody();
		SessionUser sessionUser = JSONObject.parseObject(sessionUserStr,SessionUser.class);

		if(sessionUser==null){
			ModelAndView view = new ModelAndView("redirect:http://7777/common/login");
			return view;
		}

		ModelAndView view = new ModelAndView("/publicTopic");
		view.addObject("user",sessionUser);
		CategoryQuery categoryQuery = new CategoryQuery();
		categoryQuery.setShowInBbs(Constants.Y);
		view.addObject("topicType", TopicType.values());
		view.addObject("voteType", VoteType.values());
		return view;
	}

	@RequestMapping("/index")
	public String index(HttpSession session){
		return "bbs";
	}

	/*加载主分类
	* @author wzk
	* @date 2019/3/17
	* @param
	*/

    @ResponseBody
    @RequestMapping("/loadCategories")
    public AjaxResponse<List<Category>> loadCategories(){
        AjaxResponse<List<Category>> ajaxResponse = new AjaxResponse<List<Category>>();
        try {
            ajaxResponse.setData(CategoryCache.getBbsCategories());
            ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
            return ajaxResponse;
        } catch (Exception e) {
            ajaxResponse.setErrorMsg("加载分类出错");
            ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
            logger.error("{}加载分类出错",e);
        }
        return ajaxResponse;
    }

    /** 发布帖子
    * @author wzk
    * @date 2019/3/18
    * @param
    */
	@ResponseBody
	@RequestMapping("/publicTopic")
	public AjaxResponse<Integer> publicTopic(HttpSession session, Topic topic, TopicVote topicVote,
											 String[] voteContent, Attachment attachment,HttpServletRequest request){
		AjaxResponse<Integer> ajaxResponse = new AjaxResponse<Integer>();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUser",String.class,request);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);

		Map<String, Object> params = new HashMap<>();
		params.put("userid", user.getUserid());
		params.put("mark", MarkEnum.MARK_TOPIC.getMark());
		try {
			restTemplate.getForObject("http://COMMON-SERVICE/UserService/changeMark?userid={userid}&mark={mark}",
					JSONObject.class, params);
		}catch (Exception e){
			logger.error("更新积分失败", e.getMessage());
		}
		try {
			this.setUserBaseInfo(Topic.class, topic, user);
			this.topicService.addTopic(topic, topicVote, voteContent, attachment,user);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(topic.getTopicId());
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("{}发表帖子失败", user.getUserName()+" "+e.getMessage());
		} catch (Exception e) {
			ajaxResponse.setErrorMsg("服务器出错,帖子发表失败");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("{}发表帖子失败", user.getUserName());
		}
		return ajaxResponse;
	}


	/**添加收藏，给收藏远程调用
	* @author wzk
	* @date 2019/3/24
	* @param
	*/

	@ResponseBody
	@RequestMapping("/updateInfoCount")
	public void publicTopic(Integer topicId){
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddReadCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(topicId);
		topicMapper.updateInfoCount(updateQuery4ArticleCount);
	}
	/**添加收藏，给收藏远程调用
	 * @author wzk
	 * @date 2019/3/24
	 * @param
	 */

	@ResponseBody
	@RequestMapping("/updateInfoReadCount")
	public void updateInfoReadCount(Integer topicId){
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddReadCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(topicId);
		topicMapper.updateInfoCount(updateQuery4ArticleCount);
	}
	/**添加收藏，给收藏远程调用
	 * @author wzk
	 * @date 2019/3/24
	 * @param
	 */

	@ResponseBody
	@RequestMapping("/updateInfoCommentCount")
	public void updateInfoCommentCount(Integer topicId){
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddCommentCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(topicId);
		topicMapper.updateInfoCount(updateQuery4ArticleCount);
	}
	/**添加收藏，给收藏远程调用
	 * @author wzk
	 * @date 2019/3/24
	 * @param
	 */

	@ResponseBody
	@RequestMapping("/updateInfoCollCount")
	public void updateInfoCollCount(Integer topicId){
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddCollectionCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(topicId);
		topicMapper.updateInfoCount(updateQuery4ArticleCount);
	}

	/*
        获取浏览器的cookie，将其塞入header中
     */
	public static HttpHeaders getHeader(Cookie[] cs){
		HttpHeaders headers = new HttpHeaders();
		Set<Cookie> cookies = new HashSet<Cookie>(Arrays.asList(cs));
		List<String> cookieList = new ArrayList<String>();
		for(Cookie cookie:cookies){ //将浏览器cookies放入list中
			//System.out.println("当前cookies为:" +  cookie.getDomain() + " " + cookie.getName() + ":" + cookie.getValue());
			cookieList.add(cookie.getName() + "=" + cookie.getValue());
		}
		//System.out.println("cookie为：" + cookieList.toString());
		headers.put(HttpHeaders.COOKIE,cookieList); //将cookie放入header
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //post表单 ，如果是个json则设置为MediaType.APPLICATION_JSON
		return headers;
	}


	/**個人中心加載帖子
	 * @author wzk
	 * @date 2019/3/25
	 * @param
	 */
	@ResponseBody
	@RequestMapping("loadTopic")
	public AjaxResponse<Object> loadTopic(HttpSession session, TopicQuery topicQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			PageResult<Topic> pageResult = this.topicService.findTopicByPage(topicQuery);
			ajaxResponse.setData(pageResult);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载帖子异常", e);
			ajaxResponse.setErrorMsg("加载帖子出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	@ResponseBody
	@RequestMapping("doLike")
	public AjaxResponse<Object> doLike(HttpSession session, Like like,HttpServletRequest request){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		SessionUser sessionUser =	JSONObject.parseObject(userJson,SessionUser.class);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		like.setUserId(sessionUser.getUserid());
		try {
			this.likeService.addLike(like);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("{}赞出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
		}catch (Exception e) {
			logger.error("{}赞出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
			ajaxResponse.setErrorMsg("服务器出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	@ResponseBody
	@RequestMapping("loadTopicColl")
	public AjaxResponse<Object> loadTopicColl(CollectionQuery collectionQuery){
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
	/**删除收藏
	* @author wzk
	* @param
	*/
	@ResponseBody
	@RequestMapping("delTopicColl")
	public void delTopicColl(Integer articleId,Integer userId){
		Collection collection = new Collection();
		collection.setArticleId(articleId);
		collection.setUserId(userId);
		collectionService.deleteCollection(collection);
	}

	@RequestMapping("search")
	public ModelAndView search( String keyWord,TopicQuery topicQuery){

		ModelAndView view = new ModelAndView("/bbs_search");
		topicQuery.setTitle("%"+keyWord+"%");
		PageResult<Topic> result = this.topicService.findTopicByPage(topicQuery);
		view.addObject("result", result);
		view.addObject("keyWord",keyWord);
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		view.addObject("user",  user);
		return view;
	}

}
