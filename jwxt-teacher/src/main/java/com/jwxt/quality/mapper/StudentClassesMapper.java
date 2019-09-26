package com.jwxt.quality.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.quality.StudentClasses;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentClassesMapper extends BaseMapper<StudentClasses> {
    @Select("select count(0) from sys_student_classes where classes_id = #{classId}")
    Integer getStudentCountByClassesId(@Param("classId") String classId);

    @Select("select * from sys_student_classes where classes_id =#{classesId}  and student_id = #{studentId}")
    StudentClasses getData(@Param("classesId") String classesId, @Param("studentId") String studentId);

    @Insert("insert into sys_student_classes (classes_id,student_id) values(#{classesId},#{studentId})")
    void insertData(@Param("classesId") String classesId,@Param("studentId") String studentId);

    @Delete("delete from sys_student_classes where student_id = #{studentId}")
    void deleteData(@Param("studentId") String studentId);
}

