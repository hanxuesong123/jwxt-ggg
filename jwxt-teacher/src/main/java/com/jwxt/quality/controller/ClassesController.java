package com.jwxt.quality.controller;

import com.jwxt.base.BaseController;
import com.jwxt.entity.quality.Classes;
import com.jwxt.exceptions.CommonException;
import com.jwxt.quality.service.ClassesService;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/classes")
public class ClassesController extends BaseController {


    @Autowired
    private ClassesService classesService;

    @RequestMapping(value = "/getClassesList",method = RequestMethod.POST,name = "API-CLASSES-API")
    public Result getClassesList(@RequestBody Map<String,Object> map) throws ParseException {
        return classesService.getClassesList(map);
    }

    @RequestMapping(value = "/saveClasses", method = RequestMethod.POST,name = "API-CLASSES-ADD")
    public Result saveClasses(@RequestBody Classes classes) throws CommonException {
        return classesService.saveClasses(classes,super.nickName);
    }

    @RequestMapping(value = "/updateClasses",method = RequestMethod.PUT,name = "API-CLASSES-UPDATE")
    public Result updateClasses(@RequestBody Classes classes){
        return classesService.updateClasses(classes);
    }

    @RequestMapping(value = "/findClasses",method = RequestMethod.GET,name = "PROFILE")
    public Result findClasses(){
        return classesService.findClasses();
    }
}

