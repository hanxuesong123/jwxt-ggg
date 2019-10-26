package com.jwxt.permission.service;

import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import com.jwxt.vo.TeacherVo;

import java.util.Map;

public interface UserService {

    Result getTeacherList(Map<String, Object> map);

    Result findTeachers();

    Result saveTeacher(TeacherVo teacherVo,String nickName);

    Result findOne(String id);

    Result updatePassword(Map<String, Object> map, String id);

    Result checkTelephone(Map<String, Object> map);

    Result sendCode(Map<String, Object> map) throws CommonException;

    Result updatePasswordByCode(Map<String, Object> map);
}
