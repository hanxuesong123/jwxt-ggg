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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserClientFallbackFactory.class);

    @Override
    public UserClient create(Throwable e) {

        LOGGER.warn("错误服务回调:{}",e.getMessage());

        return new UserClient() {
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

                return new Result(ResultCode.FAIL,"启动了回调工厂");
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
