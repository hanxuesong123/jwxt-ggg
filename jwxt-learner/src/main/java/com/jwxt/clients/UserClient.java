package com.jwxt.clients;

import com.jwxt.response.Result;
import com.jwxt.vo.QuestionVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "JWXT-TEACHER")
public interface UserClient {

    @RequestMapping(value = "/user/findOne/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result findOne(@PathVariable("id")String id); //不行

    @RequestMapping(value = "/general/findGenerals",method = RequestMethod.GET,name = "PROFILE")
    public Result findGenerals(); //可以

    @RequestMapping(value = "/user/findTeachers")
    public Result findTeachers(); //不行

    @RequestMapping(value = "/department/findDepartments",method = RequestMethod.GET)
    public Result findDepartments(); //可以

    @RequestMapping(value = "/exam/readStudentAsks/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result readStudentAsks(@PathVariable("id") String id); //不行

    @RequestMapping(value = "/send/question/getSingleVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getSingleVOByIdAndType(@RequestParam("id")String id, @RequestParam("type") String type);

    @RequestMapping(value = "/send/question/getMutipleVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getMutipleVOByIdAndType(@RequestParam("id")String id, @RequestParam("type") String type);

    @RequestMapping(value = "/send/question/getAskVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getAskVOByIdAndType(@RequestParam("id")String id, @RequestParam("type") String type);

}
