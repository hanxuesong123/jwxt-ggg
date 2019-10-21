package com.jwxt.filter;

import com.alibaba.fastjson.JSON;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**安全验证*/
@Component
public class MyZuulFilter extends ZuulFilter {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();

        StringBuffer requestURL = request.getRequestURL();

        if (requestURL.toString().contains("teacher/login")) return null;
        if (requestURL.toString().contains("actuator/routes")) return null;
        if (requestURL.toString().contains("actuator/filters")) return null;

        String authorization = request.getHeader("token");
        //String authorization = request.getParameter("token");
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            Claims claims = JwtUtils.getClaims(request);

            if (claims == null) {
                access_denied();
                return null;
            }

            if(redisTemplate.opsForValue().get(claims.getId()) == null){
                access_denied();
                return null;
            }

            return null;

        } else {
            access_denied();
            return null;
        }
    }


//    @Override
//    public Object run() throws ZuulException {
//
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//        HttpServletResponse response = currentContext.getResponse();
//
//        StringBuffer requestURL = request.getRequestURL();
//
//        if (requestURL.toString().contains("teacher/login")) return null;
//        if (requestURL.toString().contains("actuator/routes")) return null;
//        if (requestURL.toString().contains("actuator/filters")) return null;
//
//        String authorization = request.getHeader("token");
//        //String authorization = request.getParameter("token");
//        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
//            Claims claims = JwtUtils.getClaims(request);
//
//            if (claims == null) {
//                access_denied();
//                return null;
//            }
//
//            if(redisTemplate.opsForValue().get(claims.getId()) == null){
//                access_denied();
//                return null;
//            }
//
//            return null;
//
//        } else {
//            access_denied();
//            return null;
//        }
//    }

    //拒绝访问
    protected void access_denied(){
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到response
        HttpServletResponse response = requestContext.getResponse();
        //拒绝访问
        requestContext.setSendZuulResponse(false);
        //设置响应代码
        requestContext.setResponseStatusCode(200);
        //构建响应的信息
        Result responseResult = new Result(ResultCode.SERVICE_DENIED);
        //转成json
        String jsonString = JSON.toJSONString(responseResult);
        requestContext.setResponseBody(jsonString);
        //转成json，设置contentType
        response.setContentType("application/json;charset=utf-8");
    }

}