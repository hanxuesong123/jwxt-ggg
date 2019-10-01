package com.jwxt.permission.controller;

import com.jwxt.entity.permission.User;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.service.LoginService;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST,name = "PROFILE")
    public Result login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws CommonException {
        return loginService.login(user,request,response);
    }

    @RequestMapping(value = "/profile",method = RequestMethod.GET,name = "PROFILE")
    public Result profile(HttpServletRequest request) throws CommonException {
        return loginService.profile(request);
    }
}
