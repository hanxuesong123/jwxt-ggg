package com.jwxt.other.controller;

import com.jwxt.entity.other.General;
import com.jwxt.other.service.GeneralService;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/general")
public class GeneralController {


    @Autowired
    private GeneralService generalService;

    @RequestMapping(value = "/findGeneralByCode",method = RequestMethod.POST, name = "PROFILE")
    public Result findGeneralByCode(@RequestBody Map<String,Object> map){
        return generalService.findGeneralByCode(map);
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST,name = "API-GENERAL-LIST")
    public Result list(@RequestBody Map<String,Object> map){
        return generalService.list(map);
    }

    @RequestMapping(value = "/opt", method = RequestMethod.POST,name = "API-GENERAL-OPT")
    public Result opt(@RequestBody General general){
        return generalService.opt(general);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST,name = "API-GENERAL-ADD")
    public Result save(@RequestBody General general){
        return generalService.save(general);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT,name = "API-GENERAL-UPDATE")
    public Result update(@RequestBody General general){
        return generalService.update(general);
    }

    @RequestMapping(value = "/findChapterByCodeAndStage",method = RequestMethod.POST,name = "PROFILE")
    public Result findChapterByCodeAndStage(@RequestBody Map<String,Object> map){
        return generalService.findChapterByCodeAndStage(map);
    }

    @RequestMapping(value = "/findGenerals",method = RequestMethod.GET,name = "PROFILE")
    public Result findGenerals(){
        return generalService.findGenerals();
    }
}
