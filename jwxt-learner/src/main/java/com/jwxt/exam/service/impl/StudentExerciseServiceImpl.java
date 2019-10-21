package com.jwxt.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwxt.base.BaseService;
import com.jwxt.entity.academic.ErrorQuestion;
import com.jwxt.entity.academic.Exam;
import com.jwxt.entity.academic.Single;
import com.jwxt.entity.other.General;
import com.jwxt.entity.quality.Classes;
import com.jwxt.exam.mapper.*;
import com.jwxt.exam.service.StudentExerciseService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.MyRedisTemplate;
import com.jwxt.utils.SerializeUtil;
import com.jwxt.vo.ChapterVo;
import com.jwxt.vo.ErrorQuestionVo;
import com.jwxt.vo.GeneralCardVo;
import com.jwxt.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
@Transactional
public class StudentExerciseServiceImpl extends BaseService implements StudentExerciseService {

    @Autowired
    private GeneralMapper generalMapper;

    @Autowired
    private QuestionMapper questionMapper;


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private ErrorQuestionMapper errorQuestionMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private ExamMapper examMapper;

    @Override
    public Result getStages() {

        String [] icons = {"md-locate","md-person-add","md-help-circle","md-share","md-chatbubbles","md-map"};
        String [] colors = {"#2d8cf0","#19be6b","#ff9900","#ed3f14","#E46CBB","#9A66E4"};

        QueryWrapper<General> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("code","STAGE");

        List<General> generals = generalMapper.selectList(queryWrapper);

        if(generals == null || generals.size() == 0) return Result.FAIL();

        List<GeneralCardVo> list = new ArrayList<>();

        for (General general : generals) {

            GeneralCardVo vo = new GeneralCardVo();

            vo.setId(general.getId());
            vo.setTitle(general.getName());
            vo.setIcon(icons[new Random().nextInt(6)]);
            vo.setColor(colors[new Random().nextInt(6)]);

            List<General> chapters = this.getChapters(general.getId());

            if(chapters != null && chapters.size() > 0){
                int count = 0;
                for (General chapter : chapters) {
                    int questionCount = questionMapper.getQuestionCountByLessionId(chapter.getId());
                    count += questionCount;
                }
                vo.setCount(count);
            }
            list.add(vo);
        }
        return new Result(ResultCode.SUCCESS,list);
    }


    @Override
    public Result getChaptersCount(String stageId,String studentId) {
        if(stageId == null) return Result.FAIL();
        List<General> chapters = getChapters(stageId);
        if(chapters == null) return Result.FAIL();

        List<ChapterVo> list = new ArrayList<>();
        for (General chapter : chapters) {
            int count = questionMapper.getQuestionCountByLessionId(chapter.getId());

            ChapterVo vo = new ChapterVo();
            vo.setId(chapter.getId());
            vo.setName(chapter.getName());
            Object o = redisTemplate.opsForHash().get("student#answer", studentId + "#" + chapter.getId());
            List<String> objects = JSONObject.parseArray((String) o, String.class);
            if (objects == null) vo.setOkCount(0);
            else vo.setOkCount(objects.size());
            int newCount = count == 0 ? 1 : count;
            vo.setTotalCount(newCount);
            list.add(vo);
        }

        return new Result(ResultCode.SUCCESS,list);
    }

    @Override
    public Result getRandomQuestionByChapter(Map<String, Object> map) {
        Object type = map.get("type");
        Object chapterId = map.get("chapterId");

        if(type == null) return Result.PARAMETERS_IS_NULL();
        if(chapterId == null) return Result.PARAMETERS_IS_NULL();

        switch (type.toString()){
            case "1":
                List<QuestionVo> singleList = questionMapper.getSingleListByLessionIdAndType(chapterId.toString());
                return new Result(ResultCode.SUCCESS,singleList);
            case "2":
                List<QuestionVo> mutipleList = questionMapper.getMutipleListByLessionIdAndType(chapterId.toString());
                return new Result(ResultCode.SUCCESS,mutipleList);
            case "3":
                List<QuestionVo> askList = questionMapper.getAskListByLessionIdAndType(chapterId.toString());
                return new Result(ResultCode.SUCCESS,askList);
            case "4":
                List<QuestionVo> upperList = questionMapper.getUpperListByLessionIdAndType(chapterId.toString());
                return new Result(ResultCode.SUCCESS,upperList);
            default:
                return Result.FAIL();
        }


    }

    @Override //每次答题触发保存到redis
    public Result saveQuestionToCache(Map<String, Object> map,String studentId) {
        Object id = map.get("id");
        Object lessionId = map.get("lessionId");

        if(id == null) return Result.PARAMETERS_IS_NULL();
        if(lessionId == null) return Result.PARAMETERS_IS_NULL();

        boolean bool = redisTemplate.opsForHash().hasKey("student#answer", studentId+"#"+lessionId.toString());
        if(!bool){ //student#answer为key,学生id+课程id为field,试题id为value
            List<String> list = new ArrayList<>();
            list.add(id.toString());
            redisTemplate.opsForHash().put("student#answer",studentId+"#"+lessionId.toString(),JSON.toJSONString(list));
        }else{ //存在则追加
            Object o = redisTemplate.opsForHash().get("student#answer", studentId + "#" + lessionId.toString());
            List<String> list = JSONObject.parseArray((String) o, String.class);
            if(list != null){
                boolean contains = list.contains(id.toString());
                if(!contains){
                    list.add(id.toString());
                    redisTemplate.opsForHash().put("student#answer",studentId+"#"+lessionId.toString(),JSON.toJSONString(list));
                }
            }
        }

        return Result.SUCCESS();
    }

    @Override
    public Result saveErrorQuestion(ErrorQuestion errorQuestion,String studentId,String nickName) {
        QueryWrapper<ErrorQuestion> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("question_id",errorQuestion.getQuestionId());
        questionQueryWrapper.eq("lession_id",errorQuestion.getLessionId());
        questionQueryWrapper.eq("question_type",errorQuestion.getQuestionType());
        ErrorQuestion target = errorQuestionMapper.selectOne(questionQueryWrapper);

        if(target == null){

            General general = generalMapper.selectById(errorQuestion.getLessionId());

            General stage = generalMapper.selectById(general.getPid());

            errorQuestion.setModifyName(nickName);
            errorQuestion.setModifyTime(new Date());
            errorQuestion.setStudentId(studentId);
            errorQuestion.setStageId(stage.getId());
            errorQuestionMapper.insert(errorQuestion);
        }

        return Result.SUCCESS();
    }

    @Override
    public Result queryStudentExamStatus(String studentId) {
        Classes classes = classesMapper.getClassesByStudentId(studentId);
        if(classes == null) return Result.FAIL();
        String classesId = classes.getId();

        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",classesId);

        List<Exam> exams = examMapper.selectList(queryWrapper);

        int count = 0;

        if(exams == null) return Result.SUCCESS();

        if (exams.size() == 0) return Result.SUCCESS();

        for (Exam exam : exams) {
            if(!"4".equals(exam.getExamStatus())){
                count ++;
            }else{
                continue;
            }
        }

        if(count == 0){
            return Result.SUCCESS();
        }else{
            return new Result(ResultCode.EXAM_IS_STARTING);
        }

    }

    @Override
    public Result getStudentErrorQuestionList(Map<String, Object> map) {

        Object type = map.get("type");

        type = type == null ? "1" : type.toString();

        Object page = map.get("page");
        Object size = map.get("size");

        if(page == null) return Result.PARAMETERS_IS_NULL();
        if(size == null) return Result.PARAMETERS_IS_NULL();

        Integer newPage = (Integer.parseInt(page.toString() ) - 1 ) * Integer.parseInt(size.toString());

        map.put("page",newPage);

        List<ErrorQuestionVo> errorList = null;
        int total = 0;

        switch (type.toString()){
            case "1":
                total = errorQuestionMapper.getSingleErrorsCount(map);
                errorList = errorQuestionMapper.getSingleErrorList(map);
                break;
            case "2":
                total = errorQuestionMapper.getMutipleErrorsCount(map);
                errorList = errorQuestionMapper.getMutipleErrorList(map);
                break;
            case "3":
                total = errorQuestionMapper.getAskErrorsCount(map);
                errorList = errorQuestionMapper.getAskErrorList(map);
                break;
            case "4":
                total = errorQuestionMapper.getUpperErrorsCount(map);
                errorList = errorQuestionMapper.getUpperErrorList(map);
                break;
        }

        PageResult<ErrorQuestionVo> pageResult = new PageResult<>(total,errorList);

        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @Override
    public Result removeErrorQuestion(String id, String studentId) {
        errorQuestionMapper.removeErrorQuestion(id,studentId);
        return Result.SUCCESS();
    }

    protected List<General> getChapters(String pid){
        QueryWrapper<General> query = new QueryWrapper<>();
        query.eq("pid",pid);
        query.eq("code","CHAPTER");
        List<General> chapters = generalMapper.selectList(query);
        return chapters;
    }
}
