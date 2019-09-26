package com.jwxt.other.service;

import com.jwxt.entity.other.General;
import com.jwxt.response.Result;

import java.util.Map;

public interface GeneralService {

    Result findGeneralByCode(Map<String,Object> map);

    Result list(Map<String, Object> map);

    Result opt(General general);

    Result save(General general);

    Result update(General general);

    Result findChapterByCodeAndStage(Map<String,Object> map);

    Result findGenerals();
}

