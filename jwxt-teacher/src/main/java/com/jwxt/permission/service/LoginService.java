package com.jwxt.permission.service;

import com.jwxt.entity.permission.User;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    Result login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception;

    Result profile(HttpServletRequest request) throws Exception;

    Result logout(HttpServletRequest request) throws Exception;
}
