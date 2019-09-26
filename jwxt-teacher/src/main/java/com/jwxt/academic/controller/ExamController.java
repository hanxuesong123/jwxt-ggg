package com.jwxt.academic.controller;

import com.jwxt.academic.service.ExamService;
import com.jwxt.base.BaseController;
import com.jwxt.entity.academic.Exam;
import com.jwxt.entity.academic.Score;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController {

    @Autowired
    private ExamService examService;

    //试卷列表
    @RequestMapping(value = "/list",method = RequestMethod.POST,name = "API-EXAM-LIST")
    public Result list(@RequestBody Map<String,Object> map){
        return examService.list(map);
    }

    //保存试卷
    @RequestMapping(value = "/save",method = RequestMethod.POST,name = "API-EXAM-RELEASE")
    public Result save(@RequestBody Exam exam)throws CommonException {
        return examService.save(exam,super.nickName);
    }

    //开始考试
    @RequestMapping(value = "/startExam/{id}",method = RequestMethod.GET,name = "API-EXAM-START")
    public Result startExam(@PathVariable("id") String id) throws CommonException {
        return examService.startExam(id);
    }

    //已交/未交
    @RequestMapping(value = "/showExam",method = RequestMethod.POST,name = "API-EXAM-SHOW")
    public Result showExam(@RequestBody Exam exam){
        return examService.showExam(exam);
    }

    //停止考试
    @RequestMapping(value = "/stopExam",method = RequestMethod.POST,name = "API-EXAM-STOP")
    public Result stopExam(@RequestBody Exam exam){
        return examService.stopExam(exam,super.nickName);
    }

    @RequestMapping(value = "/readStudentAsks/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result readStudentAsks(@PathVariable("id") String id){
        return examService.readStudentAsks(id);
    }

    //问答评分
    @RequestMapping(value = "/lastExam", method = RequestMethod.PUT,name = "PROFILE")
    public Result lastExam(@RequestBody Score score){
        return examService.lastExam(score);
    }

    //结束考试
    @RequestMapping(value = "/endExam",method = RequestMethod.POST,name = "API-EXAM-END")
    public Result endExam(@RequestBody Exam exam){
        return examService.endExam(exam,super.nickName);
    }

    //试卷分析
    @RequestMapping(value = "/analysisExam",method = RequestMethod.POST,name = "API-EXAM-ANALYSIS")
    public Result analysisExam(@RequestBody Exam exam){
        return examService.analysisExam(exam);
    }

    //试卷讲解
  /*  @RequestMapping(value = "/showObjectExam",method = RequestMethod.POST,name = "API-EXAM-EXPLAIN")
    public Result showObjectExam(@RequestBody Exam exam){
        return examService.showObjectExam(exam);
    }
*/
    //试卷讲解时的数据集合
    @RequestMapping(value = "/getQuestionExamTeacherList",method = RequestMethod.POST,name = "API-EXAM-EXPLAIN")
    public Result getQuestionExamTeacherList(@RequestBody Exam exam){
        return examService.getQuestionExamList(exam);
    }

}

