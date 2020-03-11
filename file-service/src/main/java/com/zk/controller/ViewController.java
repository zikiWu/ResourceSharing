package com.zk.controller;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.zk.po.enums.DateTimePatternEnum;
import com.zk.po.model.SessionUser;
import com.zk.po.utils.DateUtil;
import com.zk.po.utils.Page;
import com.zk.po.utils.PageResult;
import com.zk.po.vo.FileUserVO;
import com.zk.po.vo.UeditorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zk.bean.Attentions;
import com.zk.bean.Discussfiles;
import com.zk.bean.Downfiles;
import com.zk.bean.Files;
import com.zk.bean.Grades;
import com.zk.bean.Smallclassify;
import com.zk.bean.User;
import com.zk.service.AttentionsService;
import com.zk.service.ClassifyService;
import com.zk.service.DiscussfilesService;
import com.zk.service.DownfilesService;
import com.zk.service.FilesService;
import com.zk.service.GradesService;
import com.zk.service.LoginService;
import com.zk.service.UserService;


@Controller
public class ViewController{

	@Value(value = "${resources_path}")
	String resources_path;//资源文件绝对地址目录

	@Value(value = "${server_path}")
	String server_path;//文件服务器地址目录

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/test")
	public ModelAndView test(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test");
		return mav;
	}
	@RequestMapping("/test1")
	@ResponseBody
	public ArrayList<Files> test1(HttpServletRequest request){
		ArrayList<Files> download_al = FilesService.downloadRank();
		return download_al;
	}
	
	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		ArrayList<User> wealth_al = UserService.wealthRank();
		mav.addObject("wealth_al", wealth_al);
		ArrayList<FileUserVO> upload_al = UserService.uploadRank();
		mav.addObject("upload_al", upload_al);
		ArrayList<Files> download_al = FilesService.downloadRank();
		mav.addObject("download_al", download_al);
		mav.setViewName("index");
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		mav.addObject("user",  user);
		return mav;
	}
	
	@RequestMapping("/classifyData")
	@ResponseBody
	public ArrayList<ArrayList<ArrayList<Smallclassify>>> classifyData(HttpServletRequest request){
		ArrayList<ArrayList<ArrayList<Smallclassify>>> al = ClassifyService.all();
		return al;
	}

	
	@RequestMapping("/showfiles")
	@ResponseBody
	public ArrayList<Files> showfiles(HttpServletRequest request){
		String timeflag = request.getParameter("timeflag");
		String content = request.getParameter("content");
		String pagenumber = request.getParameter("pagenumber");
		if(pagenumber==null||pagenumber.equals("")||pagenumber.equals("null")){
			pagenumber = "1";
		}
		if(content==null||content.equals("null"))
			content = "";
		ArrayList<Files> al = FilesService.showAll(timeflag,content,Integer.parseInt(pagenumber));
		return al;
	}
	@RequestMapping("/showfiles/find1")
	@ResponseBody
	public ArrayList<Files> find1(HttpServletRequest request){
		String id = request.getParameter("id");
		String timeflag = request.getParameter("timeflag");
		String pagenum = request.getParameter("pagenum");
		if(id==null||id.equals("")||id.equals("null")){
			id = "1";
		}
		if(pagenum==null||pagenum.equals("")||pagenum.equals("null"))
			pagenum = "1";
		ArrayList<Files> al = FilesService.find1(Integer.parseInt(id),timeflag,Integer.parseInt(pagenum));
		return al;
	}
	@RequestMapping("/showfiles/find2")
	@ResponseBody
	public ArrayList<Files> find2(HttpServletRequest request){
		String id = request.getParameter("id");
		String timeflag = request.getParameter("timeflag");
		String pagenum = request.getParameter("pagenum");
		if(id==null||id.equals("")||id.equals("null")){
			id = "1";
		}
		if(pagenum==null||pagenum.equals("")||pagenum.equals("null"))
			pagenum = "1";
		ArrayList<Files> al = FilesService.find2(Integer.parseInt(id),timeflag,Integer.parseInt(pagenum));
		return al;
	}
	@RequestMapping("/showfiles/find3")
	@ResponseBody
	public ArrayList<Files> find3(HttpServletRequest request){
		String id = request.getParameter("id");
		String timeflag = request.getParameter("timeflag");
		String pagenum = request.getParameter("pagenum");
		if(id==null||id.equals("")||id.equals("null")){
			id = "1";
		}
		if(pagenum==null||pagenum.equals("")||pagenum.equals("null"))
			pagenum = "1";
		ArrayList<Files> al = FilesService.find3(Integer.parseInt(id),timeflag,Integer.parseInt(pagenum));
		return al;
	}
	
	@RequestMapping("/pageToShowFiles")
	@ResponseBody
	public ArrayList<Files> pageToShowFiles(HttpServletRequest request){
		String nosearch = request.getParameter("nosearch");
		String whichfind = request.getParameter("whichfind");
		String findid = request.getParameter("findid");
		String pagenum = request.getParameter("pagenum");
		String content_search = request.getParameter("content_search1");
		String timeflag = request.getParameter("timeflag");
		if(findid==null||findid.equals("")||findid.equals("null"))
			findid = "1";
		if(pagenum==null||pagenum.equals("")||pagenum.equals("null"))
			pagenum = "1";
		if(content_search==null||content_search.equals("")||content_search.equals("null"))
			content_search = "";
		ArrayList<Files> al = null;
		if(nosearch.equals("1"))
			al = FilesService.showAll(timeflag, content_search, Integer.parseInt(pagenum));
		else if(nosearch.equals("2")){
			if(whichfind.equals("1")){
				al = FilesService.find1(Integer.parseInt(findid),timeflag,Integer.parseInt(pagenum));
			}else if(whichfind.equals("2")){
				al = FilesService.find2(Integer.parseInt(findid),timeflag,Integer.parseInt(pagenum));
			}else if(whichfind.equals("3")){
				al = FilesService.find3(Integer.parseInt(findid),timeflag,Integer.parseInt(pagenum));
			}
		}
		return al;
	}
	
	@RequestMapping("/downloadfile")
	@ResponseBody
	public Map<String,String> downloadfile(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String contextPath = request.getContextPath();
		Map<String,String> m = new HashMap<String,String>();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		if(userJson.equals("")){
			m.put("flag", "1");
			m.put("fileurl", contextPath+"/common/login");
			return m;
		}
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		Map<String,String> map = FilesService.title(Integer.parseInt(id));
		boolean b = UserService.download(user.getUserid(), Integer.parseInt(map.get("money")));
		if(!b){
			m.put("flag", "2");
			m.put("message", "积分不足！");
			return m;
		}
		boolean bb = FilesService.updateDownload(Integer.parseInt(id));
		if(!bb){
			m.put("flag", "2");
			m.put("message", "操作失败！");
			return m;
		}
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		Downfiles downf = new Downfiles();
		downf.setUsername(user.getUserName());
		downf.setFileid(Integer.parseInt(id));
		downf.setTime(time);
		downf.setUserId(user.getUserid());
		DownfilesService.insertfile(downf);
		String fileUrl = server_path+map.get("title");

		//springMvc下载
		//设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + map.get("title"));
		try {
			//打开本地文件流
			InputStream inputStream = new FileInputStream(fileUrl);
			//激活下载操作
			OutputStream os = response.getOutputStream();

			//循环写入输出流
			byte[] bt = new byte[2048];
			int length;
			while ((length = inputStream.read(bt)) > 0) {
				os.write(bt, 0, length);
			}

			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (Exception e){

		}
		m.put("fileurl", server_path+map.get("title"));
		return m;
	}

	@RequestMapping("/httpMessageDown")
	@ResponseBody
	public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {

		String id = request.getParameter("id");
		String contextPath = request.getContextPath();
		Map<String,String> m = new HashMap<String,String>();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		Map<String,String> map = FilesService.title(Integer.parseInt(id));
		boolean b = UserService.download(user.getUserid(), Integer.parseInt(map.get("money")));
		if(!b){
			return null;
		}
		boolean bb = FilesService.updateDownload(Integer.parseInt(id));
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		Downfiles downf = new Downfiles();
		downf.setUsername(user.getUserName());
		downf.setFileid(Integer.parseInt(id));
		downf.setTime(time);
		downf.setUserId(user.getUserid());
		DownfilesService.insertfile(downf);
		String fileUrl = resources_path+map.get("title");

		File file = new File(fileUrl);
		byte[] body = null;
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename=" + file.getName());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return entity;
	}



	@RequestMapping("/cancel")
	public ModelAndView cancel(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies){
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/uploadfile")
	public ModelAndView uploadfile(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		mav.addObject("user",  user);
		
		ArrayList<String[]> al_big = ClassifyService.selectBig(1);
		mav.addObject("al_big", al_big);
		ArrayList<String[]> al_small = ClassifyService.selectSmall(Integer.parseInt(al_big.get(0)[0]));
		mav.addObject("al_small", al_small);
		
		mav.setViewName("uploadfile");
		return mav;
	}
	
	@RequestMapping("/classify/data1")
	@ResponseBody
	public Map<String,ArrayList<String[]>> data1(HttpServletRequest request){
		String val = request.getParameter("val");
		Map<String,ArrayList<String[]>> m = new HashMap<String,ArrayList<String[]>>();
		ArrayList<String[]> al_big = ClassifyService.selectBig(Integer.parseInt(val));
		ArrayList<String[]> al_small = ClassifyService.selectSmall(Integer.parseInt(al_big.get(0)[0]));
		m.put("a", al_big);
		m.put("b", al_small);
		return m;
	}
	@RequestMapping("/classify/data2")
	@ResponseBody
	public Map<String,ArrayList<String[]>> data2(HttpServletRequest request){
		String val = request.getParameter("val");
		Map<String,ArrayList<String[]>> m = new HashMap<String,ArrayList<String[]>>();
		ArrayList<String[]> al_small = ClassifyService.selectSmall(Integer.parseInt(val));
		m.put("b", al_small);
		return m;
	}
	
	@RequestMapping("/filecontent")
	public ModelAndView filecontent(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		if(id==null||id.equals(""))
			id = "1";
		Cookie[] cookies = request.getCookies();
		String name = "";
		if (cookies != null) {
			for (int c = 0; c < cookies.length; c++) {
				if (cookies[c].getName().equals("name")) {
					name = cookies[c].getValue();
					break;
				}
			}
		}
		mav.addObject("name",name);
		
		ArrayList<Files> download_al = FilesService.downloadRank();
		mav.addObject("download_al", download_al);
		
		Files filedetail =  FilesService.showfile(Integer.parseInt(id));
		if(filedetail.getId() ==0){
			mav.setViewName("/error/404.html");
			return mav;
		}
		DecimalFormat df=new DecimalFormat(".##");
		String st=df.format(filedetail.getScore());
		filedetail.setScore(Double.parseDouble(st));
		mav.addObject("filedetail", filedetail);
		
		ArrayList<Files> sameAuthor =  FilesService.sameAuthor(filedetail.getAuthor());
		mav.addObject("sameAuthor", sameAuthor);
		
		ArrayList<Files> fleshfile =  FilesService.fleshfile(filedetail.getBelong());
		mav.addObject("fleshfile", fleshfile);
		
		Attentions a = new Attentions();
		a.setFileid(Integer.parseInt(id));
		a.setUsername(name);
		boolean ab = AttentionsService.select(a);
		if(ab){
			mav.addObject("ab", "1");
		}else{
			mav.addObject("ab", "2");
		}
		
		int count = DiscussfilesService.total(Integer.parseInt(id));
		mav.addObject("count", count);
		ArrayList<Discussfiles> discussarr = DiscussfilesService.showAll(Integer.parseInt(id));
		for(Discussfiles d:discussarr){
			if(!d.getUsername().equals(name)){
				d.setId(-1);
			}
		}
		mav.addObject("discussarr", discussarr);
		
		mav.setViewName("filecontent");
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class);
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		mav.addObject("user",  user);
		return mav;
	}
	
	@RequestMapping(value="/uploadfile/all",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> all(@RequestParam("files") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException{
		String contextPath = request.getContextPath();
		String c1 = request.getParameter("c1");
		String c2 = request.getParameter("c2");
		String c3 = request.getParameter("c3");
		String money = request.getParameter("money");
		String shuoMing = request.getParameter("shuoming");
		Map<String,String> m = new HashMap<String, String>();

		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);

		if(userJson.equals("")){
			m.put("url", contextPath+"/common/login");
			m.put("result", "请先登录！");
			return m;
		}
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
//		System.out.println(time);
		
		double size = file.getSize()*1.0/1024;
		DecimalFormat df=new DecimalFormat(".##");
		String st=df.format(size);
		size = Double.parseDouble(st);
		String filename = new Date().getTime()+file.getOriginalFilename();
		int dotindex = filename.lastIndexOf(".");
		String type = filename.substring(dotindex+1);
		String path = resources_path+filename;
		//String path = "C:\\tomcat\\webapps\\informationSharing\\resources\\files\\"+filename;
		File newFile = new File(path);
		if(!newFile.exists()){
			newFile.mkdirs();
		}else {
			newFile.delete();
		}
		file.transferTo(newFile);
		if(money==null||money.equals(""))
			money = "0";
		int money1 = Integer.parseInt(money);
		Files fileobj = new Files();
		fileobj.setTitle(filename);
		fileobj.setSize(size);
		fileobj.setAuthor(user.getUserName());
		fileobj.setAttention(0);
		fileobj.setEvaluate(0);
		fileobj.setScore(5);
		fileobj.setType(type);
		fileobj.setBelong(Integer.parseInt(c3));
		fileobj.setTime(time);
		fileobj.setDownload(0);
		fileobj.setMoney(money1);
		fileobj.setUserId(user.getUserid());
		fileobj.setUserIcon(user.getUserIcon());
		fileobj.setShuoMing(shuoMing);
		boolean b = FilesService.insertfile(fileobj);
		if(b){
//			boolean uw = FilesService.updateWealth(name);
//			if(uw){
//				m.put("url", "1");
//				m.put("result", "上传文件成功！");
//			}else{
//				m.put("url", "1");
//				m.put("result", "上传文件失败！");
//			}
			m.put("url", "1");
			m.put("result", "上传文件成功！");
			
		}else{
			m.put("url", "1");
			m.put("result", "上传文件失败！");
		}
		
		return m;
	}
	
	@RequestMapping("/giveGrade")
	@ResponseBody
	public Map<String,String> giveGrade(HttpServletRequest request){
		String grade = request.getParameter("grade");
		String fileid = request.getParameter("fileid");
		Map<String,String> m = new HashMap<String,String>();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		if(userJson.equals("")){
			m.put("state", "1");
			m.put("message","请先登录" );
			return m;
		}
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		Grades g = new Grades();
		g.setUsername(user.getUserName());
		g.setFileid(Integer.parseInt(fileid));
		g.setTime(time);
		g.setScore(Integer.parseInt(grade));
		boolean selectb = GradesService.select(g);
		if(selectb){
			m.put("state", "2");
			m.put("message", "你已评价过，不能再评价");
			return m;
		}
		boolean insertb = GradesService.insert(g);
		if(insertb){
			m.put("state", "2");
			m.put("message", "评分成功");
		}else{
			m.put("state", "2");
			m.put("message", "评分失败");
		}
		return m;
	}
	@RequestMapping("/addview")
	@ResponseBody
	public Map<String,String> addview(HttpServletRequest request){
		String fileid = request.getParameter("fileid");
		Map<String,String> m = new HashMap<String,String>();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		if(userJson.equals("")){
			m.put("state", "1");
			m.put("message","请先登录" );
			return m;
		}
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		Attentions a = new Attentions();
		a.setFileid(Integer.parseInt(fileid));
		a.setTime(time);
		a.setUsername(user.getUserName());
		boolean selectb = AttentionsService.select(a);
		if(selectb){
			m.put("state", "2");
			m.put("message", "你已收藏过了");
			return m;
		}
		boolean insertb = AttentionsService.insert(a);
		if(insertb){
			m.put("state", "2");
			m.put("message", "收藏成功");
		}else{
			m.put("state", "2");
			m.put("message", "收藏失败");
		}
		return m;
	}
	@RequestMapping("/cancelview")
	@ResponseBody
	public Map<String,String> cancelview(HttpServletRequest request){
		String fileid = request.getParameter("fileid");
		Map<String,String> m = new HashMap<String,String>();
		String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,request);
		if(userJson.equals("")){
			m.put("state", "1");
			m.put("message","请先登录" );
			return m;
		}
		SessionUser user =	JSONObject.parseObject(userJson,SessionUser.class);
		Attentions a = new Attentions();
		a.setFileid(Integer.parseInt(fileid));
		a.setUsername(user.getUserName());
		boolean delectb = AttentionsService.delect(a);
		if(delectb){
			m.put("state", "2");
			m.put("message", "取消收藏成功");
		}else{
			m.put("state", "2");
			m.put("message", "取消收藏失败");
		}
		return m;
	}

	@RequestMapping("/funcResponse")
	@ResponseBody
	public ArrayList<Discussfiles> funcResponse(HttpServletRequest request){
		ArrayList<Discussfiles> al = new ArrayList<Discussfiles>();
		Cookie[] cookies = request.getCookies();
		String name = "";
		if (cookies != null) {
			for (int c = 0; c < cookies.length; c++) {
				if (cookies[c].getName().equals("name")) {
					name = cookies[c].getValue();
					break;
				}
			}
		}
		al = DiscussfilesService.showAllName(name);
		return al;
	}
	@RequestMapping("/funcResponse1")
	@ResponseBody
	public ArrayList<Discussfiles> funcResponse1(HttpServletRequest request){
		ArrayList<Discussfiles> al = new ArrayList<Discussfiles>();
		al = DiscussfilesService.showAllName1();
		return al;
	}
	@RequestMapping("/loadFile")
	@ResponseBody
	public PageResult<Files> funcUpload(HttpServletRequest request,Integer pageNum,String userName){
		ArrayList<Files> al = new ArrayList<Files>();
		al = FilesService.showAllName(userName);
		int count = al.size();
		int pageSize = 20;
		Page page = new Page(pageNum, count, pageSize);
		PageResult<Files> result = new PageResult<Files>(page,al);
		return result;
	}
	@RequestMapping("/funcUpload1")
	@ResponseBody
	public ArrayList<Files> funcUpload1(HttpServletRequest request){
		ArrayList<Files> al = new ArrayList<Files>();
		al = FilesService.showAllName1();
		return al;
	}
	@RequestMapping("/deleteUpload")
	@ResponseBody
	public Map<String,String> deleteUpload(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String,String> m = new HashMap<String,String>();
		String contextPath = request.getContextPath();
		Cookie[] cookies = request.getCookies();
		String name = "";
		if (cookies != null) {
			for (int c = 0; c < cookies.length; c++) {
				if (cookies[c].getName().equals("name")) {
					name = cookies[c].getValue();
					break;
				}
			}
		}
		if(name.equals("")){
			m.put("state", "1");
			m.put("url", contextPath+"/login");
			return m;
		}
		String fileName = FilesService.selectFileName(Integer.parseInt(id));
		boolean deleteb = FilesService.delete(name, Integer.parseInt(id));
		if(deleteb){
			boolean bdf = FilesService.deleteFile(fileName);
			m.put("state", "2");
			m.put("message", "删除文件成功");
		}else{
			m.put("state", "3");
			m.put("message", "删除文件失败");
		}
		return m;
	}
	@RequestMapping("/deleteUpload1")
	@ResponseBody
	public Map<String,String> deleteUpload1(String id){
		Map<String,String> m = new HashMap<String,String>();
		boolean deleteb = FilesService.delete1(Integer.parseInt(id));
		if(deleteb){
			m.put("state", "2");
			m.put("message", "删除文件成功");
		}else{
			m.put("state", "3");
			m.put("message", "删除文件失败");
		}
		return m;
	}
	//加载关注文件
	@RequestMapping("/loadFileColl")
	@ResponseBody
	public PageResult<Files> funcAttention(HttpServletRequest request,Integer pageNum,String userName){
		ArrayList<Attentions> al = new ArrayList<Attentions>();
		al = AttentionsService.showAllName(userName);
		List<Files> files = new ArrayList<>();
		for (Attentions a :al){
		 int fileId = a.getFileid();
		 Files f =	FilesService.showfile(fileId);
		 if(f.getId() != 0){

			 files.add(f);
		 }
		}
		int count = files.size();
		int pageSize = 20;
		Page page = new Page(pageNum, count, pageSize);
		PageResult<Files> result = new PageResult<Files>(page,files);
		return result;
	}
	@RequestMapping("/funcDownload")
	@ResponseBody
	public ArrayList<Downfiles> funcDownload(HttpServletRequest request){
		ArrayList<Downfiles> al = new ArrayList<Downfiles>();
		Cookie[] cookies = request.getCookies();
		String name = "";
		if (cookies != null) {
			for (int c = 0; c < cookies.length; c++) {
				if (cookies[c].getName().equals("name")) {
					name = cookies[c].getValue();
					break;
				}
			}
		}
		al = DownfilesService.showAllName(name);
		return al;
	}
	@RequestMapping("/funcGrade")
	@ResponseBody
	public ArrayList<Grades> funcGrade(HttpServletRequest request){
		ArrayList<Grades> al = new ArrayList<Grades>();
		Cookie[] cookies = request.getCookies();
		String name = "";
		if (cookies != null) {
			for (int c = 0; c < cookies.length; c++) {
				if (cookies[c].getName().equals("name")) {
					name = cookies[c].getValue();
					break;
				}
			}
		}
		al = GradesService.showAllName(name);
		return al;
	}

	@ResponseBody
	@RequestMapping("ueditorImageUpload.action")
	public UeditorResponse ueditorImageUpload(HttpSession session, MultipartHttpServletRequest multirequest,
											  HttpServletResponse response){
		String realPath = resources_path + "/upload";
		UeditorResponse ueditorResponse = new UeditorResponse();
		Iterator<String> itr = multirequest.getFileNames();
		if(itr.hasNext()){
			MultipartFile multipartFile = multirequest.getFile(itr.next());
			long size = multipartFile.getSize();
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
			if(!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
					!"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)){
				ueditorResponse.setState("只能是图片");
				return ueditorResponse;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
			String savePath = saveDir + "/" + fileName;
			String fileDir = realPath + "/" + saveDir;
			File dir = new File(fileDir);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			try {
				multipartFile.transferTo(file);
				ueditorResponse.setState("SUCCESS");
				ueditorResponse.setUrl(server_path + "upload/" + savePath);
				return ueditorResponse;
			} catch (Exception e) {
				ueditorResponse.setState("上传参数出错");
				return ueditorResponse;
			}
		}
		return ueditorResponse;
	}


}
