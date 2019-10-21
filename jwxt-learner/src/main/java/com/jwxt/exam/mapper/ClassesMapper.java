package com.jwxt.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.quality.Classes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassesMapper extends BaseMapper<Classes> {
    @Select("SELECT c.* " +
            "FROM sys_classes c, sys_student_classes sc ,sys_student s " +
            "WHERE s.`id` = sc.`student_id` " +
            "AND sc.`classes_id` = c.`id` " +
            "AND s.`id` = #{studentId} ")
    Classes getClassesByStudentId(@Param("studentId") String studentId);
}
