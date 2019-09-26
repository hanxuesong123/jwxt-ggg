package com.jwxt.quality.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.quality.Student;
import com.jwxt.vo.StudentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {


    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM sys_user u, sys_student ss  " +
            "WHERE u.`id` = ss.`id` " +
            " <if test='map.nickName != null'> and u.nick_name like CONCAT('%',#{map.nickName},'%') </if> " +
            " <if test='map.telephone != null'> and u.telephone like CONCAT('%',#{map.telephone},'%') </if> " +
            " <if test='map.idCard != null'> and u.id_card like CONCAT('%',#{map.idCard},'%') </if> " +
            " <if test='map.email != null'> and u.email like CONCAT('%',#{map.email},'%') </if> " +
            " <if test='map.residenceAddress != null'> and u.residence_address like CONCAT('%',#{map.residenceAddress},'%') </if> " +
            " <if test='map.nowAddress != null'> and u.now_address like CONCAT('%',#{map.nowAddress},'%') </if> " +
            " <if test='map.education != null'> and u.education = #{map.education} </if> " +
            " <if test='map.graduationSchool != null'> and u.graduation_school like CONCAT('%',#{map.graduationSchool},'%') </if> " +
            " <if test='map.emergencyContact != null'> and u.emergency_contact like CONCAT('%',#{map.emergencyContact},'%') </if> " +
            " <if test='map.major != null'> and u.major = #{map.major} </if> " +
            " <if test='map.sex != null'> and u.sex = #{map.sex} </if> " +
            " <if test='map.status != null'> and u.status = #{map.status} </if> " +
            " <if test='map.studyType != null'> and ss.study_type = #{map.studyType} </if> " +
            "AND u.`type` = '2' " +
            "</script>")
    long getTotal(@Param("map") Map<String, Object> map);

    @Select("<script> " +
            "SELECT u.*,ss.*, " +
            " (SELECT c.class_name FROM sys_classes c, sys_student s,sys_student_classes sc WHERE s.`id`=sc.student_id AND sc.classes_id = c.id AND s.id = ss.`id` ) AS classesName " +
            "FROM sys_user u, sys_student ss " +
            "WHERE u.`id` = ss.`id` " +
            " <if test='map.nickName != null'> and u.nick_name like CONCAT('%',#{map.nickName},'%') </if> " +
            " <if test='map.telephone != null'> and u.telephone like CONCAT('%',#{map.telephone},'%') </if> " +
            " <if test='map.idCard != null'> and u.id_card like CONCAT('%',#{map.idCard},'%') </if> " +
            " <if test='map.email != null'> and u.email like CONCAT('%',#{map.email},'%') </if> " +
            " <if test='map.residenceAddress != null'> and u.residence_address like CONCAT('%',#{map.residenceAddress},'%') </if> " +
            " <if test='map.nowAddress != null'> and u.now_address like CONCAT('%',#{map.nowAddress},'%') </if> " +
            " <if test='map.education != null'> and u.education = #{map.education} </if> " +
            " <if test='map.graduationSchool != null'> and u.graduation_school like CONCAT('%',#{map.graduationSchool},'%') </if> " +
            " <if test='map.emergencyContact != null'> and u.emergency_contact like CONCAT('%',#{map.emergencyContact},'%') </if> " +
            " <if test='map.major != null'> and u.major = #{map.major} </if> " +
            " <if test='map.sex != null'> and u.sex = #{map.sex} </if> " +
            " <if test='map.status != null'> and u.status = #{map.status} </if> " +
            " <if test='map.studyType != null'> and ss.study_type = #{map.studyType} </if> " +
            "AND u.`type` = '2' " +
            "ORDER BY classesName " +
            " LIMIT #{page},#{size} " +
            "</script>")
    List<StudentVo> getStudentList(@Param("page") int page, @Param("size") Integer size,@Param("map") Map<String, Object> map);
}

