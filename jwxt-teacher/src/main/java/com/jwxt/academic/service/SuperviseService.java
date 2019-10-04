package com.jwxt.academic.service;

import com.jwxt.response.Result;

import java.text.ParseException;
import java.util.Map;

public interface SuperviseService {
    Result getDayExamList(Map<String, Object> map) throws ParseException;
}
