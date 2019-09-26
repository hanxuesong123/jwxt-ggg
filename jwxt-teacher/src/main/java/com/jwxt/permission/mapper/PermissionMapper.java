package com.jwxt.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.permission.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT p.* " +
            "FROM sys_user u,sys_user_role ur,sys_role r,sys_role_permission rp,sys_permission p " +
            "WHERE u.`id` = ur.`user_id` " +
            "AND ur.`role_id` = r.`id` " +
            "AND r.`id` = rp.`role_id` " +
            "AND rp.`permission_id` = p.`id` " +
            "AND u.`id` = #{userId}")
    List<Permission> getPermissionListByUserId(@Param("userId") String userId);

    @Select("SELECT p.* " +
            "FROM sys_permission p , sys_role_permission rp, sys_role r " +
            "WHERE r.`id` = rp.`role_id` " +
            "AND rp.`permission_id` = p.`id` " +
            "AND r.`id` = #{id}")
    List<Permission> getPermissionsByRoleId(@Param("id")String id);

}
