package com.jwxt.base;

import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Claims claims;
    protected String userId;
    protected String nickName;

    @ModelAttribute  //在controller方法执行之前执行的方法
    public void setReqAndRes(HttpServletRequest request,HttpServletResponse response){
        this.response = response;
        this.request = request;
        Object obj = request.getAttribute("user_claims");
        if(obj != null){
            this.claims = (Claims) obj;
            this.userId = (String) claims.get("userId");
            this.nickName = (String) claims.get("nickName");
        }
    }

}
