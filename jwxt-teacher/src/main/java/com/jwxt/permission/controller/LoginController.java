package com.jwxt.permission.controller;

import com.jwxt.base.BaseController;
import com.jwxt.entity.permission.User;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.service.LoginService;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST,name = "PROFILE")
    public Result login(@RequestBody User user) throws Exception {
        return loginService.login(user,request,response);
    }

    @RequestMapping(value = "/profile",method = RequestMethod.GET,name = "PROFILE")
    public Result profile() throws Exception {
        return loginService.profile(request);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET, name = "PROFILE")
    public Result logout() throws Exception {
        return loginService.logout(request);
    }


}
