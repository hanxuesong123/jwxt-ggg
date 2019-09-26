package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.AskResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AskResultMapper extends BaseMapper<AskResult> {

    @Select("select * from st_ask_result where ask_id = #{askId} and exam_id =#{examId} and student_id=#{studentId}")
    AskResult getAnswer(@Param("askId") String askId, @Param("examId")String examId, @Param("studentId")String studentId);
}
