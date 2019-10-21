package com.jwxt.exam.controller;

import com.jwxt.base.BaseController;
import com.jwxt.entity.academic.ErrorQuestion;
import com.jwxt.exam.service.StudentExerciseService;
import com.jwxt.response.Result;
import com.jwxt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/exercise")
public class StudentExerciseController extends BaseController {

    @Autowired
    private StudentExerciseService studentExerciseService;

    @RequestMapping(value = "/getStages",method = RequestMethod.GET, name = "PROFILE")
    public Result getStages(){
        return studentExerciseService.getStages();
    }

    @RequestMapping(value = "/getChapters/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result getChaptersCount(@PathVariable("id") String stageId){
        return studentExerciseService.getChaptersCount(stageId, JwtUtils.getClaims(request).getId());
    }

    @RequestMapping(value = "/getRandomQuestionByChapter",method = RequestMethod.POST,name = "PROFILE")
    public Result getRandomQuestionByChapter(@RequestBody Map<String,Object> map){
        return studentExerciseService.getRandomQuestionByChapter(map);
    }

    @RequestMapping(value = "/saveQuestionToCache",method = RequestMethod.POST,name = "PROFILE")
    public Result saveQuestionToCache(@RequestBody Map<String,Object> map){
        return studentExerciseService.saveQuestionToCache(map, JwtUtils.getClaims(request).getId());
    }

    @RequestMapping(value = "/saveErrorQuestion",method = RequestMethod.POST,name = "PROFILE")
    public Result saveErrorQuestion(@RequestBody ErrorQuestion errorQuestion){
        return studentExerciseService.saveErrorQuestion(errorQuestion,JwtUtils.getClaims(request).getId(),JwtUtils.getClaims(request).get("nickName").toString());
    }

    @RequestMapping(value = "/queryStudentExamStatus",method = RequestMethod.GET,name = "PROFILE")
    public Result queryStudentExamStatus(){
        return studentExerciseService.queryStudentExamStatus(JwtUtils.getClaims(request).getId());
    }

    @RequestMapping(value = "/getStudentErrorQuestionList",method = RequestMethod.POST,name = "PROFILE")
    public Result getStudentErrorQuestionList(@RequestBody Map<String,Object> map){
        map.put("studentId",JwtUtils.getClaims(request).getId());
        return studentExerciseService.getStudentErrorQuestionList(map);
    }


    @RequestMapping(value = "/removeErrorQuestion/{id}",method = RequestMethod.DELETE,name = "PROFILE")
    public Result removeErrorQuestion(@PathVariable("id")String id){
        return studentExerciseService.removeErrorQuestion(id,JwtUtils.getClaims(request).getId());
    }

}
