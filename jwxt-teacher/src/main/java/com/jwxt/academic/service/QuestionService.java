package com.jwxt.academic.service;

import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import com.jwxt.vo.QuestionVo;

import java.io.InputStream;
import java.util.Map;

public interface QuestionService {
    Result list(Map<String, Object> map);

    Result upperList(Map<String, Object> map);

    Result save(QuestionVo questionVo,String nickName);

    Result update(QuestionVo questionVo,String nickName);

    Result upload(String originalFilename, InputStream inputStream,String nickName);

    Result opt(String id);

    Result count(String ids) throws CommonException;

}
