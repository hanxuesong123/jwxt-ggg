package com.jwxt.quality.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.quality.Classes;
import com.jwxt.entity.quality.Student;
import com.jwxt.entity.quality.TeacherClasses;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClassesMapper extends BaseMapper<Classes> {


    @Select("SELECT COUNT(0) " +
            "FROM `sys_classes` c,`sys_student` s,`sys_student_classes` sc " +
            "WHERE c.`id`=sc.`classes_id` " +
            "AND sc.`student_id` =s.`id` " +
            "AND c.`id`= #{classesId} ")
    Integer getPersonNumber(@Param("classesId")String classesId);

    @Select("<script>select count(0) from sys_classes</script>")
    long getTotal(@Param("map")Map<String,Object> map);

    @Select("<script> " +
            "SELECT " +
            "GROUP_CONCAT(u.`nick_name`) AS teachers, " +
            "c.*, " +
            "(SELECT g.name FROM di_general g WHERE g.id = c.`general_id`) AS generalName " +
            "FROM sys_teacher t,sys_classes c,sys_teacher_classes tc,sys_user u " +
            "WHERE t.`id` = tc.`teacher_id` AND tc.`classes_id` = c.`id` AND t.`id` = u.`id` " +
            "ORDER BY c.`modify_time` DESC " +
            "LIMIT #{page},#{size}" +
            "</script>")
    List<Classes> getClassesList(@Param("page")Integer page, @Param("size")Integer size,@Param("map") Map<String,Object> map);


    @Insert("insert into sys_teacher_classes values(#{teacherId},#{classesId})")
    void insertTeacherClasses(@Param("teacherId")String teacherId,@Param("classesId")String classesId);

    @Select("select * from sys_teacher_classes where teacher_id = #{teacherId} and classes_id = #{classesId}")
    List<TeacherClasses> teacherHasClasses(@Param("teacherId")String teacherId,@Param("classesId") String classesId);

    @Select("select * from sys_teacher_classes where classes_id = #{classesId}")
    List<TeacherClasses> getTeacherClassesList(@Param("classesId")String classesId);

    @Delete("delete from sys_teacher_classes where teacher_id = #{teacherId} and classes_id = #{classesId}")
    void deleteTeacherClasses(@Param("teacherId") String teacherId,@Param("classesId") String classesId);

    @Select("<script> " +
            "SELECT s.* " +
            "FROM sys_student s,sys_student_classes sc,sys_classes c " +
            "WHERE c.`id` = sc.`classes_id` " +
            "AND sc.`student_id` = s.`id` " +
            "AND c.`id` = #{classesId} " +
            " </script>")
    List<Student> getStudentList(@Param("classesId") String classesId);
}
