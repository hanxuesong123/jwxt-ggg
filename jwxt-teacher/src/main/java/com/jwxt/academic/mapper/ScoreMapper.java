package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    @Select("select * from st_score where student_id = #{studentId} and exam_id = #{examId}")
    Score getScoreByStudentIdAndExamId(@Param("studentId") String studentId, @Param("examId") String examId);
}

