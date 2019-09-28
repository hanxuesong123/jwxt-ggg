package com.jwxt.exam.controller;

import com.jwxt.base.BaseController;
import com.jwxt.entity.academic.Exam;
import com.jwxt.exam.service.StudentExamService;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import com.jwxt.utils.JwtUtils;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/exam/")
public class StudentExamController extends BaseController {

    @Autowired
    private StudentExamService studentExamService;

    @RequestMapping(value = "/getList",method = RequestMethod.POST,name = "API-STUDENT-EXAM-LIST")
    public Result getList(@RequestBody Map<String,Object> map) throws CommonException {
        map.put("userId", JwtUtils.getClaims(request).getId());
        return studentExamService.getList(map);
    }

    //获取考试试卷试题列表
    @RequestMapping(value = "/getQuestionStudentExamList",method = RequestMethod.POST,name = "PROFILE")
    public Result getQuestionStudentExamList(@RequestBody Exam exam){
        return studentExamService.getQuestionStudentExamList(exam);
    }

    //当学生点击开始考试按钮时,查看学生的试卷是否是2,如果是2,则不让再次进入考试
    @RequestMapping(value = "/getScoreStatus/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result getScoreStatus(@PathVariable("id") String id) throws CommonException {
        return studentExamService.getScoreStatus(JwtUtils.getClaims(request).getId(),id);
    }

    //获取试卷的考试状态, 如果查询到当前试卷的状态为4,则停止考试
    @RequestMapping(value = "/getExamStatus/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result getExamStatus(@PathVariable("id") String id) throws CommonException {
        return studentExamService.getExamStatus(id);
    }

    @RequestMapping(value = "/saveExam",method = RequestMethod.POST,name = "PROFILE")
    public Result saveExam(@RequestBody Map<String,Object>map) throws Exception {
        map.put("userId",JwtUtils.getClaims(request).getId());
        return studentExamService.saveExam(map);
    }

    @RequestMapping(value = "/saveTempAnswer",method = RequestMethod.POST,name = "PROFILE")
    public Result saveTempAnswer(@RequestBody Map<String,Object>map) throws Exception {
        map.put("userId",JwtUtils.getClaims(request).getId());
        return studentExamService.saveTempAnswer(map);
    }

    //回显考试时的临时数据
    @RequestMapping(value = "/echoTempAnswer/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result echoTempAnswer(@PathVariable("id")String id){
        return studentExamService.echoTempAnswer(id,JwtUtils.getClaims(request).getId());
    }

    //回显考试最终数据
    @RequestMapping(value = "/goBackStudentExamData",method = RequestMethod.POST,name = "PROFILE")
    public Result goBackStudentExamData(@RequestBody Exam exam){
        return studentExamService.goBackStudentExamData(exam,JwtUtils.getClaims(request).getId());
    }

}
