package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.Exam;
import com.jwxt.entity.quality.Student;
import com.jwxt.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamMapper extends BaseMapper<Exam> {


/*    @Select("<script> " +
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
    List<Exam> getExamListByStudentId(Integer page, Integer size, String userId);*/

/*    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM st_exam e,st_exam_classes ec,sys_classes c,sys_student_classes sc,sys_student s,sys_user u " +
            "WHERE s.`id` = sc.`student_id` " +
            "AND sc.`classes_id`=c.`id` " +
            "AND c.`id`=ec.`classes_id` " +
            "AND ec.`exam_id`=e.`id` " +
            "AND u.`id`=s.`id` " +
            "AND s.`id`= #{userId} " +
            " </script>")
    long getCountByStudentId(String userId);*/

    @Select("<script> " +
            "SELECT s.* " +
            "FROM st_exam e , st_exam_classes ec ,sys_classes c,sys_student_classes sc,sys_student s " +
            "WHERE e.`id` = ec.`exam_id` " +
            "AND ec.`classes_id` = c.`id` " +
            "AND c.`id` = sc.`classes_id` " +
            "AND sc.`student_id` = s.`id` " +
            "AND e.`id` = #{examId} " +
            " </script>")
    List<Student> getStudentByExamId(@Param("examId")String examId);

    @Select("<script>select  " +
            "       u.nick_name as nickName,  " +
            "       sc.status,sc.score,sc.single_succ as singleSucc, " +
            "       sc.single_score as singleScore, sc.single_succ_ids as singleSuccIds,sc.single_err_ids as singleErrIds, " +
            "       sc.multiple_succ_ids as multipleSuccIds, sc.multiple_err_ids as multipleErrIds," +
            "       sc.single_err as singleErr,sc.multiple_succ as multipleSucc, sc.multiple_err as multipleErr,sc.multiple_score as multipleScore, " +
            "       e.exam_name as examName  from sys_user u,sys_student s,st_score sc,st_exam e  " +
            "where e.id = sc.exam_id " +
            "    and sc.student_id = s.id " +
            "    and s.id = u.id " +
            "    and e.id = #{examId} " +
            "order by sc.score DESC</script>")
    List<UserVo> getStudentInfoByExamId(@Param("examId")String examId);

    @Select("SELECT e.* " +
            "FROM st_exam e " +
            "WHERE e.`class_id` = #{classId} " +
            "AND e.`exam_type` = #{examType} " +
            "AND e.`exam_time` = #{examTime} " +
            "AND e.question_type_ids != '4'" )
    Exam getExamByExamTypeAndClassIdAndExamTime(@Param("examType")String examType, @Param("classId")String classId, @Param("examTime")Date examTime);

    @Select("SELECT e.* " +
            "FROM st_exam e " +
            "WHERE e.`class_id` = #{classId} " +
            "AND e.`exam_type` = #{examType} " +
            "AND e.`exam_time` = #{examTime} " +
            "AND e.question_type_ids = '4'" )
    Exam getExamByUpperExamTypeAndClassIdAndExamTime(@Param("examType")String examType, @Param("classId")String classId, @Param("examTime")Date examTime);

    @Select("SELECT e.* " +
            "FROM st_exam e " +
            "WHERE e.`class_id` = #{classId} " +
            "AND e.`exam_type` = #{examType} " +
            "AND e.`exam_time` BETWEEN #{start} AND #{end} " +
            "AND e.question_type_ids != '4'" )
    List<Exam> getWeekExamByExamTypeAndClassIdAndStartAndEnd(
            @Param("examType")String examType,
            @Param("classId")String classId,
            @Param("start")Date start,
            @Param("end")Date end);

    @Select("SELECT e.* " +
            "FROM st_exam e " +
            "WHERE e.`class_id` = #{classId} " +
            "AND e.`exam_type` = #{examType} " +
            "AND e.`exam_time` BETWEEN #{start} AND #{end} " +
            "AND e.question_type_ids = '4'" )
    List<Exam> getWeekUpperExamByExamTypeAndClassIdAndStartAndEnd(
            @Param("examType")String examType,
            @Param("classId")String classId,
            @Param("start")Date start,
            @Param("end")Date end);
}