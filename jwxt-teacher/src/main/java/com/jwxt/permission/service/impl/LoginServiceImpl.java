package com.jwxt.permission.service.impl;

import com.alibaba.fastjson.JSON;
import com.jwxt.entity.permission.Permission;
import com.jwxt.entity.permission.User;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.mapper.PermissionMapper;
import com.jwxt.permission.mapper.UserMapper;
import com.jwxt.permission.service.LoginService;
import com.jwxt.response.ProfileResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private MyRedisTemplate myRedisTemplate;

//    @Override
//    public Result login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        User target = userMapper.findUsernameAndPassword(user.getUsername(), user.getPassword());
//
//        if (target == null) return new Result(ResultCode.LOGIN_USER_NOT_ENABLE_STATE);
//
//        Boolean bool = redisTemplate.opsForHash().hasKey("login", target.getId());
//
//        if(bool){
//            //一台机器不允许多个账户登录
//            Object object = redisTemplate.opsForHash().get("login", target.getId());
//            if(object != null){
//                if(!object.toString().equals(target.getId())){
//                    return new Result(false,98765,"一台机器不能登录多个用户");
//                }
//            }
//        }
//
//
//        List<Permission> permissions = permissionMapper.getPermissionListByUserId(target.getId());
//
//        StringBuffer sb = new StringBuffer();
//
//        if(permissions != null && permissions.size() > 0){
//            for (Permission permission : permissions) {
//                if(permission != null &&  permission.getType() != null){
//                    if(permission.getType() == 3){
//                        sb.append(permission.getCode()).append(",");
//                    }
//                }
//            }
//        }
//
//        Map<String,Object> claims = new HashMap<>();
//
//        claims.put("apis",sb.toString());
//        claims.put("userId",target.getId());
//        claims.put("nickName",target.getNickName());
//
//        String token = JwtUtils.createJwt(target.getId(), target.getNickName(), claims);
//
//        redisTemplate.opsForHash().put("login",target.getId(),target.getId());
//
//        request.getSession().setAttribute("check",target.getId());
//
//        return new Result(ResultCode.SUCCESS,token);
//    }
//
//    @Override
//    public Result profile(HttpServletRequest request) throws Exception {
//
//        Claims claims = JwtUtils.getClaims(request);
//
//        String userId = claims.getId();
//
//        if(userId == null) throw new CommonException(ResultCode.FAIL);
//
//        User user = userMapper.selectById(claims.getId());
//
//        if(user == null) throw new CommonException(ResultCode.FAIL);
//
//        Boolean bool = redisTemplate.opsForHash().hasKey("login",  user.getId());
//
//        if(!bool) throw new CommonException(ResultCode.FAIL);
//
//        Map<String,Object> map = new HashMap<>();
//
//        //根据用户查询权限
//        List<Permission> permissions = permissionMapper.getPermissionListByUserId(user.getId());;
//
//        if(permissions != null){
//            ProfileResult profileResult = new ProfileResult(user,permissions);
//            return new Result(ResultCode.SUCCESS,profileResult);
//        }else{
//            throw new CommonException(ResultCode.FAIL);
//        }
//    }
//
//    @Override
//    public Result logout(HttpServletRequest request) throws Exception {
//
//        Claims claims = JwtUtils.getClaims(request);
//
//        String userId = claims.getId();
//
//        if(userId == null) throw new CommonException(ResultCode.FAIL);
//
//        User user = userMapper.selectById(claims.getId());
//
//        if(user == null) throw new CommonException(ResultCode.FAIL);
//
//        Boolean bool = redisTemplate.opsForHash().hasKey("login", user.getId());
//        if(bool){
//            redisTemplate.opsForHash().delete("login", user.getUsername());
//        }
//        return Result.SUCCESS();
//    }


    @Override
    public Result login(User user, HttpServletRequest request, HttpServletResponse response) throws CommonException {


        User target = userMapper.findUsernameAndPassword(user.getUsername(), user.getPassword());

        if (target == null) return new Result(ResultCode.LOGIN_USER_NOT_ENABLE_STATE);

        List<Permission> permissions = permissionMapper.getPermissionListByUserId(target.getId());

        StringBuffer sb = new StringBuffer();

        if(permissions != null && permissions.size() > 0){
            for (Permission permission : permissions) {
                if(permission != null &&  permission.getType() != null){
                    if(permission.getType() == 3){
                        sb.append(permission.getCode()).append(",");
                    }
                }
            }
        }

        Map<String,Object> claims = new HashMap<>();

        claims.put("apis",sb.toString());
        claims.put("userId",target.getId());
        claims.put("nickName",target.getNickName());

        String token = JwtUtils.createJwt(target.getId(), target.getNickName(), claims);

        redisTemplate.opsForValue().set(target.getId(),claims);

        return new Result(ResultCode.SUCCESS,token);
    }

    @Override
    public Result profile(HttpServletRequest request) throws CommonException {

        Claims claims = JwtUtils.getClaims(request);

        Object o = redisTemplate.opsForValue().get(claims.getId());

        if(o == null) throw new CommonException(ResultCode.FAIL);

        String userId = claims.getId();

        if(userId == null) throw new CommonException(ResultCode.FAIL);

        User user = userMapper.selectById(userId);

        if(user == null) throw new CommonException(ResultCode.FAIL);

        Map<String,Object> map = new HashMap<>();

        //根据用户查询权限
        List<Permission> permissions = permissionMapper.getPermissionListByUserId(user.getId());;

        if(permissions != null){
            ProfileResult profileResult = new ProfileResult(user,permissions);
            return new Result(ResultCode.SUCCESS,profileResult);
        }else{
            throw new CommonException(ResultCode.FAIL);
        }
    }

    @Override
    public Result logout(HttpServletRequest request) {
        String userId = JwtUtils.getClaims(request).getId();
        if(redisTemplate.hasKey(userId)){
            redisTemplate.delete(userId);
        }
        return Result.SUCCESS();
    }






}

