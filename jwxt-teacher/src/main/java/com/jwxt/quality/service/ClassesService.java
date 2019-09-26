package com.jwxt.quality.service;

import com.jwxt.entity.quality.Classes;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;

import java.text.ParseException;
import java.util.Map;

public interface ClassesService {

    Result getClassesList(Map<String, Object> map) throws ParseException;

    Result saveClasses(Classes classes, String nickName) throws CommonException;

    Result updateClasses(Classes classes);

    Result findClasses();
}
