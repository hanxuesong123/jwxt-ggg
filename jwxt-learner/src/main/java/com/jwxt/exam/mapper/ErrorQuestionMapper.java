package com.jwxt.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.ErrorQuestion;
import com.jwxt.vo.ErrorQuestionVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ErrorQuestionMapper extends BaseMapper<ErrorQuestion> {


    @Select("<script> " +
            "SELECT eq.*, g.name AS stageName,s.*, s.`single_content` AS content, " +
            "(SELECT NAME FROM di_general WHERE id = eq.`lession_id`) AS chapterName " +
            "FROM st_question q, st_error_question eq,di_general g, st_single s ,sys_student ss " +
            "WHERE eq.`question_id` = q.`id` " +
            "AND eq.`stage_id` = g.`id` " +
            "AND q.`id` = s.`id` " +
            "AND eq.`student_id` = ss.`id` " +
            "AND ss.`id` = #{map.studentId} " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if> " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND s.`single_content` LIKE CONCAT('%',#{map.content},'%')  </if> " +
            "ORDER BY eq.modify_time " +
            "LIMIT #{map.page},#{map.size}" +
            "</script>")
    List<ErrorQuestionVo> getSingleErrorList(@Param("map") Map<String, Object> map);

    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM st_question q, st_error_question eq,di_general g, st_single s ,sys_student ss " +
            "WHERE eq.`question_id` = q.`id` " +
            "AND eq.`stage_id` = g.`id` " +
            "AND q.`id` = s.`id` " +
            "AND eq.`student_id` = ss.`id` " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if> " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND s.`single_content` LIKE CONCAT('%',#{map.content},'%')  </if> " +
            "AND ss.`id` = #{map.studentId} </script>")
    int getSingleErrorsCount(@Param("map") Map<String, Object> map);



    @Select("<script> " +
            " SELECT eq.*, g.name AS stageName,m.*, m.mutiple_content AS content, " +
            " (SELECT NAME FROM di_general WHERE id = eq.`lession_id`) AS chapterName  " +
            " FROM st_question q, st_error_question eq,di_general g, st_mutiple m,sys_student ss  " +
            " WHERE eq.`question_id` = q.`id` " +
            " AND eq.`stage_id` = g.`id` " +
            " AND q.`id` = m.id " +
            " AND eq.`student_id` = ss.`id` " +
            " AND ss.`id` = #{map.studentId} " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if>  " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND m.mutiple_content LIKE CONCAT('%',#{map.content},'%') </if> " +
            " ORDER BY eq.modify_time " +
            " LIMIT #{map.page},#{map.size} " +
            " </script> ")
    List<ErrorQuestionVo> getMutipleErrorList(@Param("map") Map<String, Object> map);

    @Select("<script>" +
            " SELECT COUNT(0)" +
            " FROM st_question q, st_error_question eq,di_general g, st_mutiple m,sys_student ss  " +
            " WHERE eq.`question_id` = q.`id` " +
            " AND eq.`stage_id` = g.`id` " +
            " AND q.`id` = m.id " +
            " AND eq.`student_id` = ss.`id` " +
            " AND ss.`id` = #{map.studentId} " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if>  " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND m.mutiple_content LIKE CONCAT('%',#{map.content},'%') </if> " +
            "</script>")
    int getMutipleErrorsCount(@Param("map") Map<String, Object> map);


    @Select(" <script> " +
            " SELECT eq.*, g.name AS stageName,a.ask_content AS content, " +
            " (SELECT NAME FROM di_general WHERE id = eq.`lession_id`) AS chapterName " +
            " FROM st_question q, st_error_question eq,di_general g, st_ask a,sys_student ss " +
            " WHERE eq.`question_id` = q.`id` " +
            " AND eq.`stage_id` = g.`id` " +
            " AND q.`id` = a.id " +
            " AND eq.`student_id` = ss.`id` " +
            " AND ss.`id` = #{map.studentId} " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if> " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND a.ask_content LIKE CONCAT('%',#{map.content},'%')  </if> " +
            " ORDER BY eq.modify_time " +
            " LIMIT #{map.page},#{map.size} " +
            "</script>")
    List<ErrorQuestionVo> getAskErrorList(@Param("map") Map<String, Object> map);

    @Select("<script> " +
            " SELECT COUNT(0)" +
            " FROM st_question q, st_error_question eq,di_general g, st_ask a,sys_student ss " +
            " WHERE eq.`question_id` = q.`id` " +
            " AND eq.`stage_id` = g.`id` " +
            " AND q.`id` = a.id " +
            " AND eq.`student_id` = ss.`id` " +
            " AND ss.`id` = #{map.studentId} " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if> " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND a.ask_content LIKE CONCAT('%',#{map.content},'%')  </if> " +
            " </script>")
    int getAskErrorsCount(@Param("map") Map<String, Object> map);



    @Select(" <script> " +
            " SELECT eq.*, g.name AS stageName,u.upper_url as upperUrl,u.upper_content AS content, " +
            " (SELECT NAME FROM di_general WHERE id = eq.`lession_id`) AS chapterName " +
            " FROM st_question q, st_error_question eq,di_general g, st_upper u,sys_student ss " +
            " WHERE eq.`question_id` = q.`id` " +
            " AND eq.`stage_id` = g.`id` " +
            " AND q.`id` = u.id " +
            " AND eq.`student_id` = ss.`id` " +
            " AND ss.`id` = #{map.studentId} " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if> " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND u.upper_content LIKE CONCAT('%',#{map.content},'%') </if> " +
            " ORDER BY eq.modify_time " +
            " LIMIT #{map.page},#{map.size} " +
            " </script> ")
    List<ErrorQuestionVo> getUpperErrorList(@Param("map") Map<String, Object> map);


    @Select("<script>" +
            "SELECT COUNT(0)" +
            " FROM st_question q, st_error_question eq,di_general g, st_upper u,sys_student ss " +
            " WHERE eq.`question_id` = q.`id` " +
            " AND eq.`stage_id` = g.`id` " +
            " AND q.`id` = u.id " +
            " AND eq.`student_id` = ss.`id` " +
            " AND ss.`id` = #{map.studentId} " +
            " <if test='map.stageId != null'> AND eq.`stage_id` = #{map.stageId} </if> " +
            " <if test='map.chapterId != null' >AND eq.`lession_id` = #{map.chapterId} </if> " +
            " <if test='map.content != null'> AND u.upper_content LIKE CONCAT('%',#{map.content},'%') </if> " +
            "</script>")
    int getUpperErrorsCount(@Param("map") Map<String, Object> map);


    @Delete("delete from st_error_question where id = #{id} and student_id = #{studentId}")
    void removeErrorQuestion(@Param("id") String id,@Param("studentId") String studentId);
}
