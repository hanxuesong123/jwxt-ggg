package com.jwxt.clients.fallback;

import com.jwxt.clients.UserClient;
import com.jwxt.entity.academic.Exam;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.vo.QuestionVo;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public Result findOne(String id) {
        return null;
    }

    @Override
    public Result findGenerals() {
        return null;
    }

    @Override
    public Result findTeachers() {
        return null;
    }

    @Override
    public Result findDepartments() {
        return null;
    }

    @Override
    public Result readStudentAsks(String id) {
        return null;
    }

    @Override
    public Result getQuestionExamTeacherList(Exam exam) {
        return new Result(ResultCode.FAIL,"启动了userClient实现类的后备方法");
    }

    @Override
    public QuestionVo getSingleVOByIdAndType(String id, String type) {
        return null;
    }

    @Override
    public QuestionVo getMutipleVOByIdAndType(String id, String type) {
        return null;
    }

    @Override
    public QuestionVo getAskVOByIdAndType(String id, String type) {
        return null;
    }
}
