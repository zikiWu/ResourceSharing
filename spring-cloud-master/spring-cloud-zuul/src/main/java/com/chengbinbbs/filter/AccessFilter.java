package com.chengbinbbs.filter;

import com.chengbinbbs.service.CommonService;
import com.chengbinbbs.service.UserService;
import com.chengbinbbs.service.UserServiceImpl;
import com.chengbinbbs.util.Constants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

/**
 * 服务过滤
 *
 * @author zhangcb
 * @created 2017-05-24 16:56.
 */
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

//    @Autowired
//    private CommonService commonService;
    /**
     * pre：可以在请求被路由之前调用
      routing：在路由请求时候被调用
      post：在routing和error过滤器之后被调用
      error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 通过int值来定义过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否要执行，
     * 所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑。需要注意，这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，
     * 不对其进行路由，然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，
     * 当然我们也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等
     * @return
     */
    @Override
    public Object run() {

        System.out.println("zuul过滤器");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
//        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
//        Object accessToken = request.getParameter("accessToken");
//        if(accessToken == null) {
//            log.warn("access token is empty");
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            return null;
//        }
        //跨域访问
//        log.debug("*****************FirstFilter run start*****************");
//        HttpServletResponse response = ctx.getResponse();
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Credentials","true");
//        response.setHeader("Access-Control-Allow-Headers","authorization, content-type");
//        response.setHeader("Access-Control-Allow-Methods","POST,GET");
//        response.setHeader("Access-Control-Expose-Headers","X-forwared-port, X-forwarded-host");
//        response.setHeader("Vary","Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
//        //不再路由
//        ctx.setSendZuulResponse(false);
//        ctx.setResponseStatusCode(200);
//        log.debug("*****************FirstFilter run end*****************");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) ctx.getResponse();
        ServletContext servletContext = req.getSession().getServletContext();
        String req_uri = req.getRequestURI();
        String type = req_uri.substring(req_uri.lastIndexOf(".") + 1);
        HttpSession session = req.getSession();
        Object sessionUserObj = session
                .getAttribute(Constants.SESSION_USER_KEY);
        // 自动登陆
        if (null == sessionUserObj) {
            autoLogin(req, resp);
            sessionUserObj = session.getAttribute(Constants.SESSION_USER_KEY);
        }
        return null;
    }
    private String getRealPath(HttpServletRequest request){
        String port = request.getServerPort() == 80 ? "": ":" + request.getServerPort();
        String realpath = "http://" + request.getServerName() + port;
        return realpath;
    }

    private void autoLogin(HttpServletRequest req, HttpServletResponse response) {
        try {
            Cookie cookieInfo = getCookieByName(req, Constants.COOKIE_USER_INFO);
            if (cookieInfo != null) {
                String info = URLDecoder.decode(cookieInfo.getValue(), "utf-8");
                if (info != null && !"".equals(info)) {
                    String infos[] = info.split("\\|");
//                    commonService.logindo(req,response,infos[0], infos[1],"1");
                }
            }
        } catch (Exception e) {
            // 清楚cookie信息
            Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

    }
    private Cookie  getCookieByName(HttpServletRequest req, String cookieName){
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(int i =0; i < cookies.length; i++){
                if(cookieName.equals(cookies[i].getName())){
                    return new Cookie(cookies[i].getName(), cookies[i].getValue());
                }
            }
        }
        return null;
    }
}
