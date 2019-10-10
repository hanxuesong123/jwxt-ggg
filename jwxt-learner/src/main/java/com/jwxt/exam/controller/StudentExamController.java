package com.jwxt.exam.controller;

import com.jwxt.base.BaseController;
import com.jwxt.entity.academic.Exam;
import com.jwxt.exam.service.StudentExamService;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.JwtUtils;
import com.jwxt.vo.ExamVo;
import com.jwxt.vo.QuestionVo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import io.lettuce.core.dynamic.annotation.Command;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam")
@Api(tags = {"学生考试接口"})//(value = "学生考试接口")
public class StudentExamController extends BaseController {

    @Autowired
    private StudentExamService studentExamService;

    @RequestMapping(value = "/getList",method = RequestMethod.POST,name = "API-STUDENT-EXAM-LIST")
    @ApiOperation(value = "试卷列表",notes = "获取学生试卷列表")
    public Result getList(@ApiParam(value = "查询试卷列表参数") @RequestBody Map<String,Object> map) throws CommonException {
        map.put("userId", JwtUtils.getClaims(request).getId());
        return studentExamService.getList(map);
    }

    //获取考试试卷试题列表(用于学生考试,不带答案)
    @RequestMapping(value = "/getQuestionStudentExamList",method = RequestMethod.POST,name = "PROFILE")
    public Result getQuestionStudentExamList(@RequestBody Exam exam){
        return studentExamService.getQuestionStudentExamList(exam);
    }

    //获取考试试卷试题列表(用于学生讲解,带答案)
    @RequestMapping(value = "/getAnswerQuestionStudentList",method = RequestMethod.POST,name = "PROFILE")
/*    @HystrixCommand(fallbackMethod = "getAnswerQuestionStudentListFallBack",commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED,value = "true"), //开启熔断
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE"), //隔离策略: 线程模式,信号量模式
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,value="1"),//请求数量达到10个后才计算
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,value = "1"),//错误率
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "1000")//当满足请求数量和  错误率后,会在这个时间内随机拿取一个请求查看是否成功,成功则正常运行,否则继续运行该后备方法
    })*/
    public Result getAnswerQuestionStudentList(@RequestBody Exam exam){
        return studentExamService.getAnswerQuestionStudentList(exam);
    }

/*    public Result getAnswerQuestionStudentListFallBack(Exam exam){
        ExamVo vo = new ExamVo();
        List<QuestionVo> singleList = new ArrayList<>();
        List<QuestionVo> mutipleList = new ArrayList<>();
        List<QuestionVo> askList = new ArrayList<>();
        List<QuestionVo> upperList = new ArrayList<>();
        vo.setAskList(askList);
        vo.setMutipleList(mutipleList);
        vo.setSingleList(singleList);
        vo.setUpperList(upperList);
        return new Result(ResultCode.FAIL,vo);
    }*/

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

    //提交试卷
    @RequestMapping(value = "/saveExam",method = RequestMethod.POST,name = "PROFILE")
    public Result saveExam(@RequestBody Map<String,Object>map) throws Exception {
        map.put("userId",JwtUtils.getClaims(request).getId());
        return studentExamService.saveExam(map);
    }

    //保存临时试卷答案到缓存
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

    //获取上机题内容
    @RequestMapping(value = "/getUpper",method = RequestMethod.POST,name = "PROFILE")
    public Result getUpper(@RequestBody Exam exam){
        return studentExamService.getUpper(exam);
    }

    //上传上机题学生答案视频
    @RequestMapping(value = "/upload/{examId}/{upperIds}",method = RequestMethod.POST,name = "PROFILE")
    public Result upload(@RequestParam("file") MultipartFile file,@PathVariable("examId")String examId,@PathVariable("upperIds")String upperIds) throws IOException {
        return studentExamService.upload(file.getOriginalFilename(),file.getInputStream(),examId,upperIds,JwtUtils.getClaims(request).getId());
    }

}
