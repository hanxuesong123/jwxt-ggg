package com.jwxt.clients;

import com.jwxt.clients.fallback.UserClientFallback;
import com.jwxt.clients.fallback.UserClientFallbackFactory;
import com.jwxt.entity.academic.Exam;
import com.jwxt.response.Result;
import com.jwxt.vo.QuestionVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "JWXT-TEACHER",fallbackFactory = UserClientFallbackFactory.class)//,fallback = UserClientFallback.class)//,fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/user/findOne/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result findOne(@PathVariable("id")String id);

    @RequestMapping(value = "/general/findGenerals",method = RequestMethod.GET,name = "PROFILE")
    public Result findGenerals();

    @RequestMapping(value = "/user/findTeachers")
    public Result findTeachers();

    @RequestMapping(value = "/department/findDepartments",method = RequestMethod.GET)
    public Result findDepartments();

    @RequestMapping(value = "/exam/readStudentAsks/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result readStudentAsks(@PathVariable("id") String id);

    @RequestMapping(value = "/exam/getQuestionExamTeacherList",method = RequestMethod.POST,name = "API-EXAM-EXPLAIN")
    public Result getQuestionExamTeacherList(@RequestBody Exam exam);

    @RequestMapping(value = "/send/question/getSingleVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getSingleVOByIdAndType(@RequestParam("id")String id, @RequestParam("type") String type);

    @RequestMapping(value = "/send/question/getMutipleVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getMutipleVOByIdAndType(@RequestParam("id")String id, @RequestParam("type") String type);

    @RequestMapping(value = "/send/question/getAskVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getAskVOByIdAndType(@RequestParam("id")String id, @RequestParam("type") String type);

}
