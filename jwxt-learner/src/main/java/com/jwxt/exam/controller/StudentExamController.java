package com.jwxt.exam.controller;

import com.jwxt.base.BaseController;
import com.jwxt.exam.service.StudentExamService;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/exam/")
public class StudentExamController extends BaseController {

    @Autowired
    private StudentExamService studentExamService;

    @RequestMapping(value = "/getList",method = RequestMethod.POST,name = "API-STUDENT-EXAM-LIST")
    public Result getList(@RequestBody Map<String,Object> map) throws CommonException {
        System.out.println("============================");
        map.put("userId","1165911343572766722");
        return studentExamService.getList(map);
    }

}
