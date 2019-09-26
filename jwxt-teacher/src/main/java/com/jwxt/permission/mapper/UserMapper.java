package com.jwxt.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.permission.User;
import com.jwxt.vo.TeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from sys_user where username = #{username} and password = #{password} and status = '1'")
    User findUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    @Select("<script> " +
            "SELECT su.*,st.* " +
            "FROM sys_user su,sys_teacher st " +
            "WHERE su.`id` = st.`id` " +
            " <if test='map.nickName  != null'> and su.nick_name like CONCAT('%',#{map.nickName},'%') </if> " +
            " <if test='map.telephone  != null'> and su.telephone like CONCAT('%',#{map.telephone},'%') </if> " +
            " <if test='map.email  != null'> and su.email like CONCAT('%',#{map.email},'%') </if> " +
            " <if test='map.residenceAddress  != null'> and su.residence_address like CONCAT('%',#{map.residenceAddress},'%') </if> " +
            " <if test='map.nowAddress  != null'> and su.now_address like CONCAT('%',#{map.nowAddress},'%') </if> " +
            " <if test='map.idCard  != null'> and su.id_card like CONCAT('%',#{map.idCard},'%') </if> " +
            " <if test='map.graduationSchool  != null'> and su.graduation_school like CONCAT('%',#{map.graduationSchool},'%') </if> " +
            " <if test='map.emergencyContact  != null'> and su.emergency_contact like CONCAT('%',#{map.emergencyContact},'%') </if> " +
            " <if test='map.jobTitle  != null'> and st.job_title like CONCAT('%',#{map.jobTitle},'%') </if> " +
            " <if test='map.jobNumber  != null'> and st.job_number = #{map.jobNumber} </if> " +
            " <if test='map.education  != null'> and su.education = #{map.education} </if> " +
            " <if test='map.major  != null'> and su.major = #{map.major} </if> " +
            " <if test='map.deptId  != null'> and st.dept_id = #{map.deptId} </if> " +
            " <if test='map.laborContract  != null'> and st.labor_contract = #{map.laborContract} </if> " +
            " <if test='map.hasSocialSecurity  != null'> and st.has_social_security = #{map.hasSocialSecurity} </if> " +
            " <if test='map.sex  != null'> and su.sex = #{map.sex} </if> " +
            " <if test='map.hasMember  != null'> and st.has_member = #{map.hasMember} </if> " +
            " <if test='map.hasLeave  != null'> and st.has_leave = #{map.hasLeave} </if> " +
            " <if test='map.status  != null'> and st.status = #{map.status} </if> " +
            "AND su.`type` = '1' " +
            "ORDER BY st.`entry_time` DESC " +
            "LIMIT #{page},#{size}" +
            " </script>")
    List<TeacherVo> getTeacherList(@Param("page")Integer page,@Param("size") Integer size, @Param("map")Map<String, Object> map);

    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM sys_user su,sys_teacher st " +
            "WHERE su.`id` = st.`id` " +
            " <if test='map.nickName  != null'> and su.nick_name like CONCAT('%',#{map.nickName},'%') </if> " +
            " <if test='map.telephone  != null'> and su.telephone like CONCAT('%',#{map.telephone},'%') </if> " +
            " <if test='map.email  != null'> and su.email like CONCAT('%',#{map.email},'%') </if> " +
            " <if test='map.residenceAddress  != null'> and su.residence_address like CONCAT('%',#{map.residenceAddress},'%') </if> " +
            " <if test='map.nowAddress  != null'> and su.now_address like CONCAT('%',#{map.nowAddress},'%') </if> " +
            " <if test='map.idCard  != null'> and su.id_card like CONCAT('%',#{map.idCard},'%') </if> " +
            " <if test='map.graduationSchool  != null'> and su.graduation_school like CONCAT('%',#{map.graduationSchool},'%') </if> " +
            " <if test='map.emergencyContact  != null'> and su.emergency_contact like CONCAT('%',#{map.emergencyContact},'%') </if> " +
            " <if test='map.jobTitle  != null'> and st.job_title like CONCAT('%',#{map.jobTitle},'%') </if> " +
            " <if test='map.jobNumber  != null'> and st.job_number = #{map.jobNumber} </if> " +
            " <if test='map.education  != null'> and su.education = #{map.education} </if> " +
            " <if test='map.major  != null'> and su.major = #{map.major} </if> " +
            " <if test='map.deptId  != null'> and st.dept_id = #{map.deptId} </if> " +
            " <if test='map.laborContract  != null'> and st.labor_contract = #{map.laborContract} </if> " +
            " <if test='map.hasSocialSecurity  != null'> and st.has_social_security = #{map.hasSocialSecurity} </if> " +
            " <if test='map.sex  != null'> and su.sex = #{map.sex} </if> " +
            " <if test='map.hasMember  != null'> and st.has_member = #{map.hasMember} </if> " +
            " <if test='map.hasLeave  != null'> and st.has_leave = #{map.hasLeave} </if> " +
            " <if test='map.status  != null'> and st.status = #{map.status} </if> " +
            "AND su.`type` = '1'" +
            " </script>")
    long getTotal( @Param("map") Map<String,Object> map);


    @Select("<script> " +
            "SELECT su.*,st.* " +
            "FROM sys_user su,sys_teacher st " +
            "WHERE su.`id` = st.`id` " +
            "AND su.`type` = '1' " +
            "AND st.has_leave = '1' " +
            "ORDER BY st.`entry_time` DESC " +
            " </script>")
    List<TeacherVo> findTeachers();

}
