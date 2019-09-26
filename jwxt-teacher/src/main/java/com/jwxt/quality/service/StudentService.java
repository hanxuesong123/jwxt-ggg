package com.jwxt.quality.service;

import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import com.jwxt.vo.StudentVo;

import java.util.Map;

public interface StudentService {

    Result list(Map<String, Object> map);

    Result save(StudentVo studentVo, String nickName);

    Result accessClasses(Map<String, Object> map) throws CommonException;
}

