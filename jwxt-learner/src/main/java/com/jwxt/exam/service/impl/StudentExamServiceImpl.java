package com.jwxt.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwxt.clients.UserClient;
import com.jwxt.entity.academic.*;
import com.jwxt.entity.permission.User;
import com.jwxt.exam.mapper.*;
import com.jwxt.exam.service.StudentExamService;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.MyRedisTemplate;
import com.jwxt.vo.ExamStudentVo;
import com.jwxt.vo.GoBackVo;
import com.jwxt.vo.QuestionStudentVo;
import com.jwxt.vo.QuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StudentExamServiceImpl implements StudentExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private UserClient userClient;

    @Autowired
    private SingleMapper singleMapper;

    @Autowired
    private MutipleMapper mutipleMapper;


    @Autowired
    private SingleResultMapper singleResultMapper;

    @Autowired
    private MutipleResultMapper mutipleResultMapper;

    @Autowired
    private AskResultMapper askResultMapper;

    @Autowired
    private MyRedisTemplate myRedisTemplate;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public Result getList(Map<String, Object> map) throws CommonException {

        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        String userId = map.get("userId").toString();


        Result result = userClient.findOne(userId);

        Object data = result.getData();

        User user = JSON.parseObject(JSON.toJSONString(data), User.class);

        if(!"2".equals(user.getType())){
            throw new CommonException(ResultCode.NO_STUDENT_IDENTITY);
        }


        List<Exam> list = examMapper.getExamListByStudentId((page - 1) * size, size , userId);

        long total = examMapper.getCountByStudentId(userId);

        PageResult<Exam> pageResult = new PageResult<>(total,list);

        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @Override
    public Result getQuestionStudentExamList(Exam exam) {
        String questionTypeIds = exam.getQuestionTypeIds();

        List<QuestionStudentVo> singleList = new ArrayList<>();
        List<QuestionStudentVo> mutipleList = new ArrayList<>();
        List<QuestionStudentVo> askList = new ArrayList<>();

        if(questionTypeIds.contains("1")){
            String singleJoins = exam.getSingleJoins();
            for (String singleId : singleJoins.split(",")) {
                QuestionVo vo = userClient.getSingleVOByIdAndType(singleId,"1");

                QuestionStudentVo single = new QuestionStudentVo();

                BeanUtils.copyProperties(vo,single);

                singleList.add(single);
            }
        }

        if(questionTypeIds.contains("2")){
            String mutipleJoins = exam.getMutipleJoins();
            for (String mutipleId : mutipleJoins.split(",")) {
                QuestionVo vo = userClient.getMutipleVOByIdAndType(mutipleId, "2");

                QuestionStudentVo mutiple = new QuestionStudentVo();

                BeanUtils.copyProperties(vo,mutiple);

                mutipleList.add(mutiple);
            }
        }

        if(questionTypeIds.contains("3")){
            String askJoins = exam.getAskJoins();
            for (String askId : askJoins.split(",")) {
                QuestionVo vo = userClient.getAskVOByIdAndType(askId, "3");

                QuestionStudentVo ask = new QuestionStudentVo();

                BeanUtils.copyProperties(vo,ask);

                askList.add(ask);
            }
        }


        ExamStudentVo examVo = new ExamStudentVo();
        examVo.setAskList(askList);
        examVo.setMutipleList(mutipleList);
        examVo.setSingleList(singleList);

        return new Result(ResultCode.SUCCESS,examVo);


    }

    @Override
    public Result getScoreStatus(String userId, String examId) throws CommonException {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",userId);
        queryWrapper.eq("exam_id",examId);

        Score score = scoreMapper.selectOne(queryWrapper);

        String status = score.getStatus();

        if("0".equals(status)){
            score.setStatus("1");
            scoreMapper.updateById(score);
        }


        if("2".equals(status)) throw new CommonException(ResultCode.EXAM_IS_COMMIT);

        return Result.SUCCESS();
    }


    @Override
    public Result getExamStatus(String id) {
        return new Result(ResultCode.SUCCESS,examMapper.selectById(id));
    }

    @Override
    public Result saveExam(Map<String, Object> map) {
        String[] singles = map.get("single").toString().split(",");
        String[] mutiples = map.get("mutiple").toString().split("@");
        String[] asks = map.get("ask").toString().split(",");

        Exam exam = examMapper.selectById(map.get("id").toString());  //获得当前试卷实例

        String questionTypeIds = exam.getQuestionTypeIds(); //获取试卷中试题类型

        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",map.get("userId").toString());
        queryWrapper.eq("exam_id",exam.getId());

        Score score = scoreMapper.selectOne(queryWrapper); //通过学生id和当前试卷的id来查询当前学生的成绩实例

        if("1".equals(score.getStatus())){  //屏蔽为0的学生
            score.setStatus("2");
            score.setExecuteTime(new Date());
        }


        Integer upperScore = 0;

        Integer totalScore = 0;


        if(questionTypeIds.contains("1")){

            String singleSuccIds = "";
            String singleErrIds = "";
            Integer singleSucc = 0;
            Integer singleErr = 0;

            Integer singleScore = 0;

            //处理st_score表
            String singleJoins = exam.getSingleJoins();
            String[] singleIdArray = singleJoins.split(",");
            Integer scoreSingle = exam.getSingleScore(); //单选题每题分数

            Single single = null;

            for(int i = 0 ;i < singleIdArray.length ; i ++){

                if(myRedisTemplate.hasHashKey("single",singleIdArray[i])){
                    Object object1 = myRedisTemplate.hGetObject("single", singleIdArray[i], Single.class);
                    if(object1 == null){
                        single = singleMapper.selectById(singleIdArray[i]);
                        myRedisTemplate.hSetObject("single",singleIdArray[i],single);
                    }else{
                        single = (Single) object1;
                    }
                }else{
                    single = singleMapper.selectById(singleIdArray[i]);
                    myRedisTemplate.hSetObject("single",singleIdArray[i],single);
                }
                String singleAsk = single.getSingleAsk();      //单选题答案

                if(singleAsk.equals((singles[i]))){ //singles[i]学生答案
                    singleSuccIds += "," + singleIdArray[i] ;
                    singleSucc += 1;
                    singleScore += scoreSingle; //单选总分=  单选题每题分数 + 单选题总分
                    totalScore += scoreSingle;  //总成绩 = 总成绩 + 单选题每题分数
                }else{
                    singleErrIds += "," + singleIdArray[i];
                    singleErr += 1;
                }

                //处理 st_single_result表
                SingleResult singleResult = SingleResult.builder()
                        .singleId(singleIdArray[i])
                        .examId(exam.getId())
                        .optionIds(singles[i])
                        .studentId(map.get("userId").toString())
                        .build();

                singleResultMapper.insert(singleResult);

            }

            score.setSingleErr(singleErr);
            score.setSingleErrIds(singleErrIds);
            score.setSingleSucc(singleSucc);
            score.setSingleSuccIds(singleSuccIds);
            score.setSingleScore(singleScore);
        }

        if(questionTypeIds.contains("2")){

            String multipleSuccIds = "";
            String multipleErrIds = "";
            Integer multipleSucc = 0;
            Integer multipleErr = 0;

            Integer multipleScore = 0;

            String mutipleJoins = exam.getMutipleJoins();
            String[] mutipleIdArray = mutipleJoins.split(",");
            Integer mutipleScore = exam.getMutipleScore();//多选题分数

            Mutiple mutiple = null;

            for (int i = 0; i < mutipleIdArray.length; i++) {

                if(myRedisTemplate.hasHashKey("mutiple",mutipleIdArray[i])){
                    Object object = myRedisTemplate.hGetObject("mutiple", mutipleIdArray[i], Mutiple.class);
                    if(object == null){
                        mutiple = mutipleMapper.selectById(mutipleIdArray[i]);
                        myRedisTemplate.hSetObject("mutiple",mutipleIdArray[i],mutiple);
                    }else{
                        mutiple = (Mutiple) object;
                    }
                }else{
                    mutiple = mutipleMapper.selectById(mutipleIdArray[i]);
                    myRedisTemplate.hSetObject("mutiple",mutipleIdArray[i],mutiple);
                }

                String mutipleAsk = mutiple.getMutipleAsk();
                mutipleAsk = this.sort(mutipleAsk);  //处理答案  有可能 1,3,2,4 处理成 1,2,3,4
                mutiples[i] = this.sort(mutiples[i]);//处理学生答案
                if(mutipleAsk.equals(mutiples[i])){
                    multipleSuccIds += "," + mutipleIdArray[i];
                    multipleSucc += 1;
                    multipleScore += mutipleScore;
                    totalScore += mutipleScore;
                }else{
                    multipleErrIds += "," + mutipleIdArray[i];
                    multipleErr += 1;
                }

                //处理 st_mutiple_result表
                MutipleResult mutipleResult = MutipleResult.builder()
                        .mutipleId(mutipleIdArray[i])
                        .examId(exam.getId())
                        .optionIds(mutiples[i]) //学生答案
                        .studentId(map.get("userId").toString())
                        .build();
                mutipleResultMapper.insert(mutipleResult);
            }

            score.setMultipleErr(multipleErr);
            score.setMultipleErrIds(multipleErrIds);
            score.setMultipleSucc(multipleSucc);
            score.setMultipleSuccIds(multipleSuccIds);
            score.setMultipleScore(multipleScore);
        }

        if (questionTypeIds.contains("3")){

            Integer askScore = 0;

            String askJoins = exam.getAskJoins();
            String[] askIdArray = askJoins.split(",");
            for (int i = 0; i < askIdArray.length; i++) {
                //因为简单题无法自动判分,所以只能保存学生答案到数据库st_ask_result
                //等待老师结束试卷的考试时,再把当前班级的所有学生的简答题都发送给前台
                AskResult askResult = AskResult.builder()
                        .studentId(map.get("userId").toString())
                        .examId(exam.getId())
                        .askId(askIdArray[i])
                        .askAnswer(asks[i]) //学生答案
                        .build();
                askResultMapper.insert(askResult);
            }

            score.setAskScore(askScore);

        }

        score.setScore(totalScore);

        scoreMapper.updateById(score);


        //TODO: 删除redis中的临时数据
        redisTemplate.opsForHash().delete(exam.getId(),map.get("userId").toString());

        return Result.SUCCESS();
    }

    @Override
    public Result saveTempAnswer(Map<String, Object> map) {
        String singles = map.get("single").toString();
        String mutiples = map.get("mutiple").toString();
        String asks = map.get("ask").toString();
        String examId = map.get("id").toString();
        String studentId = map.get("userId").toString();

        StringBuffer sb = new StringBuffer();

        sb.append(singles).append("#").append(mutiples).append("#").append(asks);

        redisTemplate.opsForHash().put(examId,studentId,sb.toString().trim());

        return Result.SUCCESS();
    }

    @Override
    public Result echoTempAnswer(String examId, String userId) {
        String answer = redisTemplate.opsForHash().get(examId, userId).toString();
        return new Result(ResultCode.SUCCESS,answer);
    }



    @Override
    public Result goBackStudentExamData(Exam exam, String userId) {

        String questionTypeIds = exam.getQuestionTypeIds();

        GoBackVo goBackVo = new GoBackVo();

        if(questionTypeIds.contains("1")){
            String singleJoins = exam.getSingleJoins();
            String[] singleIdArray = singleJoins.split(",");

            QueryWrapper<SingleResult> queryWrapper = new QueryWrapper();

            List<SingleResult> list = new ArrayList<>();

            for (String singleId : singleIdArray) {
                queryWrapper.eq("single_id",singleId);
                queryWrapper.eq("exam_id",exam.getId());
                queryWrapper.eq("student_id",userId);
                SingleResult singleResult = singleResultMapper.selectOne(queryWrapper);
                list.add(singleResult);
            }

            goBackVo.setSingleResults(list);
        }

        if(questionTypeIds.contains("2")){
            String mutipleJoins = exam.getMutipleJoins();
            String[] mutipleIdArray = mutipleJoins.split(",");

            QueryWrapper<MutipleResult> queryWrapper = new QueryWrapper<>();

            List<MutipleResult> list = new ArrayList<>();

            for (String mutipleId : mutipleIdArray) {

                queryWrapper.eq("exam_id",exam.getId());
                queryWrapper.eq("student_id",userId);
                queryWrapper.eq("mutiple_id",mutipleId);

                MutipleResult mutipleResult = mutipleResultMapper.selectOne(queryWrapper);

                list.add(mutipleResult);

            }

            goBackVo.setMutipleResults(list);

        }

        if(questionTypeIds.contains("3")){
            String askJoins = exam.getAskJoins();

            String[] askIdArray = askJoins.split(",");

            List<AskResult> list = new ArrayList<>();

            QueryWrapper<AskResult> queryWrapper = new QueryWrapper<>();

            for (String askId : askIdArray) {
                queryWrapper.eq("ask_id",askId);
                queryWrapper.eq("exam_id",exam.getId());
                queryWrapper.eq("student_id",userId);

                AskResult askResult = askResultMapper.selectOne(queryWrapper);
                list.add(askResult);
            }

            goBackVo.setAskResults(list);
        }

        Score score = scoreMapper.getScoreByStudentIdAndExamId(userId, exam.getId());

        goBackVo.setScore(score);

        return new Result(ResultCode.SUCCESS,goBackVo);
    }


    protected String sort(String options){

        String[] split = options.split(",");

        Integer [] target = new Integer[split.length];

        for (int i = 0; i < split.length; i++) {
            target[i] = Integer.parseInt(split[i]);
        }

        Arrays.sort(target);

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < target.length; i++) {
            sb.append(",").append(target[i]);
        }

        String substring = sb.toString().substring(1);

        return substring;
    }

}
