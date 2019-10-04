package com.jwxt.academic.controller;

import com.jwxt.academic.service.SuperviseService;
import com.jwxt.base.BaseController;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/supervise")
public class SuperviseController extends BaseController {

    @Autowired
    private SuperviseService superviseService;

    @RequestMapping(value = "/getDayExamList",method = RequestMethod.POST, name = "PROFILE")
    public Result getDayExamList(@RequestBody Map<String,Object> map) throws ParseException {
        return superviseService.getDayExamList(map);
    }
}
