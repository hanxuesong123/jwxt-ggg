package com.jwxt.interceptor;

import com.jwxt.exceptions.CommonException;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@SuppressWarnings("all")
public class JwtInterceptor extends HandlerInterceptorAdapter {

  private JwtUtils jwtUtils;

  /**执行控制器方法之前执行的方法*/
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


   try {
     response.setHeader("Access-Control-Allow-Origin","*");
     response.setHeader("Access-Control-Allow-Headers","Content-Type,Content-Length,Authorization,Accept,X-Requested-With");
     response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
     String method = request.getMethod();

     if(method.equals("OPTIONS")){
       response.setStatus(200);
       return false;
     }

     //通过request域获取请求头中的token信息
     //String authorization = request.getHeader("token");

     String authorization = request.getParameter("token");

     if(!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")){
       //获取token数据
       String token = authorization.replace("Bearer ","");

       //解析token,获得token中的claims

       Claims claims = JwtUtils.parseJwt(token);

       if(claims != null){
         //通过claims获取到当前用户的可访问API权限字符串
         String apis = (String) claims.get("apis");

         //对方法处理的一个处理器
         HandlerMethod h = (HandlerMethod) handler;

         //获取接口上的reqeustmapping注解
         RequestMapping annotation = h.getMethodAnnotation(RequestMapping.class);
         String name = annotation.name();

         if(name.equals("PROFILE")){
           request.setAttribute("user_claims",claims);
           return true;
         }else if(apis.contains(name)){
           request.setAttribute("user_claims",claims);
           return true;
         }else{
           throw new CommonException(ResultCode.NO_EXIST_PERMISSION);
         }
       }
     }
   }catch (Exception e){
     throw new Exception(e.getMessage());
   }
    throw new CommonException(ResultCode.INTERCEPTOR_EXCEPTION);
  }


}




/*
package com.kytms.interceptor;

import com.kytms.exceptions.CommonException;
import com.kytms.response.ResultCode;
import com.kytms.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component  //标记该类为一个组件
@SuppressWarnings("all")
public class JwtInterceptor extends HandlerInterceptorAdapter{

  @Override //进入controller之前触发的方法
  public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws CommonException {

    String authorization = request.getParameter("token");

    if(!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")){
      //获取token的真实数据
      String token = authorization.replace("Bearer ","");

      //解析数据
      Claims claims = JwtUtils.parseJwt(token);

      if(claims != null){
        String apis = (String) claims.get("apis");

        //通过HandlerMethod来获得方法的注解
        HandlerMethod h = (HandlerMethod) handler;

        RequestMapping annotation = h.getMethodAnnotation(RequestMapping.class);

        //@RequetMapping注解的name属性值
        String name = annotation.name();

        if(name == null || name == ""){
          request.setAttribute("user_claims",claims);
          return true;
        }else if(name.equals("PROFILE")){
          //如果用户被放行,那么我们希望claims放到请求域中,后续可以通过request请求域来拿取用户的信息
          request.setAttribute("user_claims",claims);
          return true;
        }else if(apis.contains(name)){ //用户权限是否有可以访问当前方法的权利,如果包含则说明用户有访问这个方法的权限,如果不包含则没有
          request.setAttribute("user_claims",claims);
          return true;
        }else{
          throw new CommonException(ResultCode.FAIL);
        }
      }
    }
    throw new CommonException(ResultCode.FAIL);
  }

}
*/
