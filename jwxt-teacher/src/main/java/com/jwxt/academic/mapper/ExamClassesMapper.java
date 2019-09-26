package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.ExamClasses;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamClassesMapper extends BaseMapper<ExamClasses> {

    @Insert("insert into st_exam_classes values(#{examId},#{classesId})")
    void insertExamClasses(@Param("examId")String examId,@Param("classesId") String classesId);

}
