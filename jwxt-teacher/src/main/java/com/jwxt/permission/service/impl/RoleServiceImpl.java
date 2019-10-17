package com.jwxt.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwxt.entity.permission.Role;
import com.jwxt.permission.mapper.RoleMapper;
import com.jwxt.permission.mapper.RolePermissionMapper;
import com.jwxt.permission.mapper.UserRoleMapper;
import com.jwxt.permission.service.RoleService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Result list(Map<String,Object> map){
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        IPage<Role> iPage = new Page<>(page,size);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("id");

        IPage<Role> result = roleMapper.selectPage(iPage, null);
        PageResult<Role> pageResult = new PageResult<>(result.getTotal(),result.getRecords());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @Override
    public Result save(Role role) {
        roleMapper.insert(role);
        return Result.SUCCESS();
    }


    @Override
    public Result update(Role role) {
        roleMapper.updateById(role);
        return Result.SUCCESS();
    }

    @Override
    public Result accessPermission(Map<String, Object> map) {
        String roleId = map.get("roleId").toString();
        rolePermissionMapper.deleteRolePermissions(roleId);
        String [] permissionIdsArray = map.get("permissionIds").toString().split(",");
        if(roleId != null && permissionIdsArray != null && permissionIdsArray.length > 0){
            for (String permissionId : permissionIdsArray) {
                rolePermissionMapper.insertRolePermission(roleId,permissionId);
            }
        }
        return Result.SUCCESS();
    }


    @Override
    public Result getAssessPermission(String id) {

        List<String> permissionIds =  rolePermissionMapper.getAssessPermission(id);

        return new Result(ResultCode.SUCCESS,permissionIds);
    }

    @Override
    public Result assessRole(Map<String, Object> map) {
        String userId = map.get("userId").toString();
        userRoleMapper.deleteUserRole(userId);
        String[] roleIdsArray = map.get("roleIds").toString().split(",");
        if(userId != null && roleIdsArray != null && roleIdsArray.length > 0){
            for (String roleId : roleIdsArray) {
                userRoleMapper.insertUserRole(userId,roleId);
            }
        }
        return Result.SUCCESS();
    }


    @Override
    public Result findRoles(){
        List<Role> roles = roleMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,roles);
    }

    @Override
    public Result getAssessRoles(String id) {

        List<String> roles =  userRoleMapper.getAssessRoles(id);

        return new Result(ResultCode.SUCCESS,roles);
    }


}
