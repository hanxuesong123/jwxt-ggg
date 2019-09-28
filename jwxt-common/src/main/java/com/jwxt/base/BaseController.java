package com.jwxt.base;

import com.jwxt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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
        this.claims = null;
        this.userId = "1";
        this.nickName = "";
        /*Claims claims = JwtUtils.getClaims(request);
        if(claims != null){
            this.claims = claims;
            this.userId = (String) this.claims.get("userId");
            this.nickName = (String) this.claims.get("nickName");
        }*/
    }

}
