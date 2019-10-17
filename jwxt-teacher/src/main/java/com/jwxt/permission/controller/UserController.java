package com.jwxt.permission.controller;

import com.jwxt.base.BaseController;
import com.jwxt.permission.service.UserService;
import com.jwxt.response.Result;
import com.jwxt.utils.JwtUtils;
import com.jwxt.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getTeacherList",method = RequestMethod.POST,name = "API-USER-LIST")
    public Result getTeacherList(@RequestBody Map<String,Object> map){
        return userService.getTeacherList(map);
    }

    @RequestMapping(value = "/findTeachers")
    public Result findTeachers(){
        return userService.findTeachers();
    }

    @RequestMapping(value = "/saveTeacher",method = RequestMethod.POST,name = "API-USER-ADD")
    public Result saveTeacher(@RequestBody TeacherVo teacherVo){
        return userService.saveTeacher(teacherVo,super.nickName);
    }

    @RequestMapping(value = "/findOne/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result findOne(@PathVariable("id")String id){
        return userService.findOne(id);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT,name = "PROFILE")
    public Result updatePassword(@RequestBody Map<String,Object> map){
        return userService.updatePassword(map, JwtUtils.getClaims(request).getId());
    }

}