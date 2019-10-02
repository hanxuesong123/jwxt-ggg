package com.jwxt.academic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwxt.academic.mapper.*;
import com.jwxt.academic.service.ExamService;
import com.jwxt.base.BaseService;
import com.jwxt.entity.academic.*;
import com.jwxt.entity.permission.User;
import com.jwxt.entity.quality.Classes;
import com.jwxt.entity.quality.Student;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.mapper.UserMapper;
import com.jwxt.quality.mapper.ClassesMapper;
import com.jwxt.quality.mapper.StudentClassesMapper;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.MyRedisTemplate;
import com.jwxt.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@SuppressWarnings("all")
public class ExamServiceImpl extends BaseService<Exam> implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private StudentClassesMapper studentClassesMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ExamClassesMapper examClassesMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AskResultMapper askResultMapper;

    @Autowired
    private AskMapper askMapper;

    @Autowired
    private MyRedisTemplate myRedisTemplate;

    @Override
    public Result list(Map<String, Object> map) {
        IPage<Exam> iPage = super.iPage(map);

        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        if(map.get("classesId") != null) queryWrapper.eq("class_id",map.get("classesId").toString());
        if(map.get("examName") != null) queryWrapper.like("exam_name",map.get("examName").toString());
        if(map.get("examType") != null) queryWrapper.eq("exam_type",map.get("examType").toString());
        if(map.get("examStatus") != null) queryWrapper.eq("exam_status",map.get("examStatus").toString());

        if(map.get("questionType") != null && map.get("questionType").toString().equals("1")){
            queryWrapper.ne("question_type_ids","4");
        }else if (map.get("questionType") != null && map.get("questionType").toString().equals("2")){
            queryWrapper.eq("question_type_ids","4");
        }
        IPage<Exam> result = examMapper.selectPage(iPage, queryWrapper);
        PageResult<Exam> pageResult = new PageResult<>(result.getTotal(),result.getRecords());
        return new Result(ResultCode.SUCCESS,pageResult);
    }



    @Override
    public Result save(Exam exam,String nickName) throws CommonException {

        try {
            exam.setModifyName(nickName);
            exam.setModifyTime(new Date());
            exam.setExamStatus("1");

            //处理章节id
            String[] chapterIds = exam.getChapterIdsArray();
            String chapterIdsJoin = StringUtils.join(chapterIds, ",");
            exam.setChapterIds(chapterIdsJoin);

            //处理试题类型
            String[] questionTypeIdsArray = exam.getQuestionTypeIdsArray();
            String questionTypeIdsJoin = StringUtils.join(questionTypeIdsArray, ",");
            exam.setQuestionTypeIds(questionTypeIdsJoin);


            //处理所考班级
            String classId = exam.getClassId();
            Classes classes = classesMapper.selectById(classId);
            exam.setClassName(classes.getClassName());

            //处理班级人数
            Integer count = studentClassesMapper.getStudentCountByClassesId(classId);

            exam.setPersonNumber(count);

            //处理试题
            if (questionTypeIdsJoin.contains("1")) {
                String singleJoins = this.handleQuestions(exam.getSingleCount(), chapterIds,"1");
                exam.setSingleJoins(singleJoins); //保存试卷中单选题的id组合 以，进行拼接后保存到singleJoins字段中
            }
            if (questionTypeIdsJoin.contains("2")) {
                String mutipleJoins = this.handleQuestions(exam.getMutipleCount(), chapterIds, "2");
                exam.setMutipleJoins(mutipleJoins);
            }
            if (questionTypeIdsJoin.contains("3")) {
                String askJoins = this.handleQuestions(exam.getAskCount(), chapterIds, "3");
                exam.setAskJoins(askJoins);
            }

            if(questionTypeIdsJoin.contains("4")){
                String upperJoins = this.handleQuestions(exam.getUpperCount(), chapterIds, "4");
                exam.setUpperJoins(upperJoins);
            }

            examMapper.insert(exam);

            examClassesMapper.insertExamClasses(exam.getId(),classId);

            myRedisTemplate.hSetObject(exam.getId(),classId,exam);

            return Result.SUCCESS();
        } catch (Exception e) {
            return Result.FAIL();
        }
    }



    protected String handleQuestions(Integer count,String[] chapterIds,String type)throws CommonException{
        List<String> list = new ArrayList<>();
        for (String chapterId : chapterIds) {
            List<Question> questionList = questionMapper.getQuestionListByLessionIdAndType(chapterId, type);
            if (questionList != null && questionList.size() > 0) {
                for (Question question : questionList) {
                    list.add(question.getId());
                }
            }
        }

        Collections.shuffle(list,new Random(5)); //获取数据后进行打乱  打乱5次
        if(list.size() < count){ //数据库试题数量小于用户输入数量
            throw new CommonException(ResultCode.FAIL);
        }else{
            List<String> resultString = list.subList(0, count);  //从数据库中抽取的数据进行截取
            String joins = StringUtils.join(resultString, ",");
            return joins;
        }
    }

    @Override
    public Result startExam(String id) throws CommonException {
        Exam exam = examMapper.selectById(id);
        exam.setExamStatus("2"); //=========================> 关键所在
        exam.setModifyTime(new Date());
        exam.setModifyName("张三");
        examMapper.updateById(exam);

        //当老师点击开始考试按钮时,我们要初始化当前试卷的当前班级的所有学生的试卷成绩
        List<Student> students = examMapper.getStudentByExamId(exam.getId()); //找到当前试卷下的所在班级的所有学生
        if(students == null){
            throw new CommonException(ResultCode.NO_STUDENT_IN_CLASSES);
        }else if(students != null && students.size() == 0){
            throw new CommonException(ResultCode.NO_STUDENT_IN_CLASSES);
        }else{
            for (Student student : students) {  //为每一个学生初始化试卷成绩
                Score score = Score.builder()
                        .executeTime(new Date())
                        .studentId(student.getId())
                        .score(0)
                        .examId(exam.getId())
                        .askScore(0)
                        .multipleScore(0)
                        .singleScore(0)
                        .upperScore(0)
                        .multipleErr(0)
                        .multipleErrIds("")
                        .multipleSucc(0)
                        .multipleSuccIds("")
                        .singleErr(0)
                        .singleErrIds("")
                        .singleSucc(0)
                        .singleSuccIds("")
                        .status("0")  // 初始化0  未交卷1 已交卷为2
                        .build();
                scoreMapper.insert(score);
            }
        }


        return Result.SUCCESS();
    }

    @Override
    public Result showExam(Exam exam) {

        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("exam_id",exam.getId());

        List<Score> scores = scoreMapper.selectList(queryWrapper);

        if(scores != null && scores.size() > 0){
            for (Score score : scores) {
                User user = userMapper.selectById(score.getStudentId());
                score.setStudentName(user.getNickName());
            }
        }

        return new Result(ResultCode.SUCCESS,scores);
    }

    @Override
    public Result stopExam(Exam exam, String nickName) {

        String questionTypeIds = exam.getQuestionTypeIds(); //获得当前试卷的题型  1,2,3,4

        //判断当前试卷是否有问题和上机题 如果没有 则试卷直接进入结束
        if(!questionTypeIds.contains("3") && !questionTypeIds.contains("4")){
            exam.setExamStatus("4"); //试卷修改为结束状态
        }

        //判断当前试卷是否有问题或上机题 如果没有 则试卷直接进入结束
        if(questionTypeIds.contains("3") || questionTypeIds.contains("4")){
            exam.setExamStatus("3"); //试卷修改为批阅中
        }

        exam.setModifyName(nickName);
        exam.setModifyTime(new Date());

        examMapper.updateById(exam);

        return Result.SUCCESS();
    }

    @Override
    public Result readStudentAsks(String id) {
        List<Student> students = examMapper.getStudentByExamId(id); //通过试卷id查询当前试卷的当前班级的所有学生
        Exam exam = examMapper.selectById(id); //查询试卷
        String askJoins = exam.getAskJoins(); //获得当前试卷的问答题ids

        List<AskVo> result = new ArrayList<>();

        for (Student student : students) {
            AskVo askVo = new AskVo(); //每一个AskVo相当于一个学生的简答题信息

            User user = userMapper.selectById(student.getId());

            askVo.setStudent(student);
            askVo.setUser(user);

            Score score = scoreMapper.getScoreByStudentIdAndExamId(student.getId(),id); //通过学生id和试卷id查找当前学生的成绩表
            askVo.setScore(score);

            List<AskResult> list = new ArrayList<>(); //装载单个学生多个简答题答案
            for (String askId : askJoins.split(",")) {
                AskResult askResult = askResultMapper.getAnswer(askId,id,student.getId()); //通过问答题id，试卷id，学生id查询对应的askResult
                list.add(askResult);
            }

            askVo.setList(list); //封装单个学生多个简答题答案

            result.add(askVo); //封装所有学生
        }

        List<Ask> asks = new ArrayList<>();

        for (String askId : askJoins.split(",")) {
            Ask ask = askMapper.selectById(askId);
            asks.add(ask);
        }

        AskVoResult askVoResult = new AskVoResult();

        askVoResult.setAsks(asks);
        askVoResult.setList(result);

        return new Result(ResultCode.SUCCESS,askVoResult);
    }

    @Override
    public Result lastExam(Score score) {

        Integer askScore = score.getAskScore();

        Integer totalScore = score.getScore();

        score.setScore(askScore + totalScore);

        scoreMapper.updateById(score);
        return Result.SUCCESS();
    }

    @Override
    public Result endExam(Exam exam,String nickName) {

        exam.setExamStatus("4");
        exam.setModifyTime(new Date());
        exam.setModifyName(nickName);

        examMapper.updateById(exam);


/*    List<Student> students = examMapper.getStudentByExamId(exam.getId());

    if(students != null && students.size() > 0){
      for (Student student : students) {
        Score score = scoreMapper.getScoreByStudentIdAndExamId(student.getId(), exam.getId());
        String status = score.getStatus();
        if(!"0".equals(status)){
          score.setStatus("2");
          scoreMapper.updateById(score);
        }
      }
    }*/

        return Result.SUCCESS();
    }

    @Override
    public Result analysisExam(Exam exam) {
        List<UserVo> userVos = examMapper.getStudentInfoByExamId(exam.getId());
        return new Result(ResultCode.SUCCESS,userVos);
    }

 /*   @Override
    public Result showObjectExam(Exam exam) {
        return null;
    }*/

    @Override
    public Result getQuestionExamList(Exam exam) {
        String questionTypeIds = exam.getQuestionTypeIds(); //试题类型  1,2,3,4

        List<QuestionVo> singleList = new ArrayList<>();
        List<QuestionVo> mutipleList = new ArrayList<>();
        List<QuestionVo> askList = new ArrayList<>();

        if(questionTypeIds.contains("1")){
            String singleJoins = exam.getSingleJoins();
            for (String singleId : singleJoins.split(",")) {
                QuestionVo single = questionMapper.getSingleVOByIdAndType(singleId,"1");
                singleList.add(single);
            }
        }

        if(questionTypeIds.contains("2")){
            String mutipleJoins = exam.getMutipleJoins();
            for (String mutipleId : mutipleJoins.split(",")) {
                QuestionVo mutiple = questionMapper.getMutipleVOByIdAndType(mutipleId, "2");
                mutipleList.add(mutiple);
            }
        }

        if(questionTypeIds.contains("3")){
            String askJoins = exam.getAskJoins();
            for (String askId : askJoins.split(",")) {
                QuestionVo ask = questionMapper.getAskVOByIdAndType(askId, "3");
                askList.add(ask);
            }
        }

        ExamVo examVo = new ExamVo();
        examVo.setAskList(askList);
        examVo.setMutipleList(mutipleList);
        examVo.setSingleList(singleList);

        return new Result(ResultCode.SUCCESS,examVo);
    }

}
