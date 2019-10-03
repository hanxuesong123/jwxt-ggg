package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.UpperResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UpperResultMapper extends BaseMapper<UpperResult> {

    @Select("select * from st_upper_result where upper_id = #{upperId} and exam_id =#{examId} and student_id=#{studentId}")
    UpperResult getAnswer(@Param("upperId")String upperId, @Param("examId")String examId, @Param("studentId")String studentId);
}
