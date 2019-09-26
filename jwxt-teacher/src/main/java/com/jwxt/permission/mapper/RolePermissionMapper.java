package com.jwxt.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.permission.RolePermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    void deleteRolePermissions(@Param("roleId")String roleId);

    @Select("select permission_id from sys_role_permission where role_id = #{id}")
    List<String> getAssessPermission(@Param("id")String id);

    @Insert("insert into sys_role_permission values(#{roleId},#{permissionId})")
    void insertRolePermission(@Param("roleId")String roleId,@Param("permissionId")String permissionId);
}

