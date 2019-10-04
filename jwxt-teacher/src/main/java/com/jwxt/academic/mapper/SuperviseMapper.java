package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.Score;
import com.jwxt.vo.DayExam;
import com.jwxt.vo.DayExamVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SuperviseMapper extends BaseMapper<Score> {


    @Select("<script> " +
            "SELECT e.class_name,u.`nick_name`,s.score,s.`execute_time` " +
            "FROM st_exam e, st_score s,sys_student ss,sys_user u " +
            "WHERE s.`exam_id` = e.`id` " +
            "AND s.`student_id` = ss.`id` " +
            "AND ss.`id` =u.`id` " +
            "AND s.`execute_time` BETWEEN #{beforeDate} AND #{lastDate} " +
            "AND e.`question_type_ids` != '4' " +
            "AND ss.`id` = #{studentId} " +
            "ORDER BY s.`execute_time` " +
            "</script>")
    List<DayExam> getDayExamList(@Param("studentId") String studentId,@Param("beforeDate") Date beforeDate,@Param("lastDate") Date lastDate,@Param("map") Map<String, Object> map);
}
