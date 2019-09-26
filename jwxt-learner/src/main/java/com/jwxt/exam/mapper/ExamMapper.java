package com.jwxt.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExamMapper extends BaseMapper<Exam> {

    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM st_exam e,st_exam_classes ec,sys_classes c,sys_student_classes sc,sys_student s,sys_user u " +
            "WHERE s.`id` = sc.`student_id` " +
            "AND sc.`classes_id`=c.`id` " +
            "AND c.`id`=ec.`classes_id` " +
            "AND ec.`exam_id`=e.`id` " +
            "AND u.`id`=s.`id` " +
            "AND s.`id`= #{userId} " +
            " </script>")
    long getCountByStudentId(@Param("userId") String userId);

    @Select("<script> " +
            "SELECT e.* " +
            "FROM st_exam e,st_exam_classes ec,sys_classes c,sys_student_classes sc,sys_student s,sys_user u " +
            "WHERE s.`id` = sc.`student_id` " +
            "AND sc.`classes_id`=c.`id` " +
            "AND c.`id`=ec.`classes_id` " +
            "AND ec.`exam_id`=e.`id` " +
            "AND u.`id`=s.`id` " +
            "AND s.`id`= #{userId} " +
            "ORDER BY e.`exam_time` DESC ,e.`exam_type`,e.`exam_status` DESC " +
            "LIMIT #{page},#{size}" +
            " </script>")
    List<Exam> getExamListByStudentId(@Param("page")Integer page, @Param("size")Integer size, @Param("userId")String userId);

}
