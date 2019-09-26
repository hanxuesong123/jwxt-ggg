package com.jwxt.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.permission.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Delete("delete from sys_user_role where user_id = #{userId}")
    void deleteUserRole(@Param("userId")String userId);

    @Select("select role_id from sys_user_role where user_id = #{id}")
    List<String> getAssessRoles(@Param("id")String id);

    @Insert("insert into sys_user_role values(#{userId},#{roleId})")
    void insertUserRole(@Param("userId")String userId,@Param("roleId") String roleId);

}