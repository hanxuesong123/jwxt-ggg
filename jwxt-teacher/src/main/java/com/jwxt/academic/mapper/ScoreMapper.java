package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    @Select("select * from st_score where student_id = #{studentId} and exam_id = #{examId}")
    Score getScoreByStudentIdAndExamId(@Param("studentId") String studentId, @Param("examId") String examId);

   /* @Select("SELECT score,execute_time " +
            "FROM st_score " +
            "WHERE student_id = #{studentId} " +
            "AND execute_time BETWEEN #{startDate} AND #{endDate}")*/
   @Select("SELECT s.score,s.execute_time " +
           "FROM st_score s,st_exam e " +
           "WHERE s.student_id = #{studentId} " +
           "AND s.execute_time BETWEEN #{startDate} AND #{endDate} " +
           "AND s.`exam_id` = e.`id`" +
           "AND e.`exam_type` = #{examType} ")
    List<Score> findSingleStudentScores(@Param("studentId") String studentId,@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("examType") String examType);
}

