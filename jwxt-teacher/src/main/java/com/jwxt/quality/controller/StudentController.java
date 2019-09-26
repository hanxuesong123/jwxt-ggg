package com.jwxt.quality.controller;

import com.jwxt.base.BaseController;
import com.jwxt.exceptions.CommonException;
import com.jwxt.quality.service.StudentService;
import com.jwxt.response.Result;
import com.jwxt.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/list",method = RequestMethod.POST,name = "API-TEACHER-STUDENT-LIST")
    public Result list(@RequestBody Map<String,Object> map){
        return studentService.list(map);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST,name = "API-TEACHER-STUDENT-ADD")
    public Result save(@RequestBody StudentVo studentVo){
        return studentService.save(studentVo,super.nickName);
    }

    @RequestMapping(value = "/accessClasses",method = RequestMethod.POST,name="API-TEACHER-STUDENT-ACCESS-CLASSES")
    public Result accessClasses(@RequestBody Map<String,Object> map) throws CommonException {
        return studentService.accessClasses(map);
    }
}
