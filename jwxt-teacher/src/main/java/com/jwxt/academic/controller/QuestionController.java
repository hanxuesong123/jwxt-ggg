package com.jwxt.academic.controller;

import com.jwxt.academic.service.QuestionService;
import com.jwxt.base.BaseController;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import com.jwxt.utils.JwtUtils;
import com.jwxt.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "list", method = RequestMethod.POST,name = "API-QUESTION-LIST")
    public Result list(@RequestBody Map<String,Object> map){
        return questionService.list(map);
    }

    @RequestMapping(value = "/upperList",method = RequestMethod.POST,name = "PROFILE")
    public Result upperList(@RequestBody Map<String,Object> map){
        return questionService.upperList(map);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST,name = "API-QUESTION-ADD")
    public Result save(@RequestBody QuestionVo questionVo){
        return questionService.save(questionVo, JwtUtils.getClaims(request).get("nickName").toString());
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT,name = "API-QUESTION-UPDATE")
    public Result update(@RequestBody QuestionVo questionVo){
        return questionService.update(questionVo,super.nickName);
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST,name = "API-QUESTION-UPLOAD")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
        return questionService.upload(file.getOriginalFilename(),file.getInputStream(),super.nickName);
    }

    @RequestMapping(value = "/opt/{id}",method = RequestMethod.GET,name = "API-QUESTION-OPT")
    public Result opt(@PathVariable("id") String id){
        return  questionService.opt(id);
    }

    @RequestMapping(value = "/count/{ids}" ,method = RequestMethod.GET,name = "PROFILE")
    public Result count(@PathVariable("ids") String ids) throws CommonException {
        return questionService.count(ids);
    }
}
