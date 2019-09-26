package com.jwxt.exam.service;

import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;

import java.util.Map;

public interface StudentExamService {
    Result getList(Map<String, Object> map) throws CommonException;
}
