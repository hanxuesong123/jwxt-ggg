package com.jwxt.clients.fallback;

import com.jwxt.clients.UserClient;
import com.jwxt.entity.academic.Exam;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.vo.QuestionVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    Logger LOGGER = LoggerFactory.getLogger(UserClientFallbackFactory.class);

    @Override
    public UserClient create(Throwable e) {

        LOGGER.error("后备方法执行了,执行后备方法前的错误是:{}",e.getMessage());

        return  new UserClient() {
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
                return new Result(ResultCode.FAIL,"启动了回调工厂的getQuestionExamTeacherList的后备方法");
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
        };
    }
}
