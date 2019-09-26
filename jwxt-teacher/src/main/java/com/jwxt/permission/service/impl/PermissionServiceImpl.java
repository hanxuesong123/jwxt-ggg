package com.jwxt.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwxt.entity.permission.Permission;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.mapper.PermissionMapper;
import com.jwxt.permission.service.PermissionService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result findAll() {
        List<Permission> permissions = permissionMapper.selectList(null);
        return new Result(ResultCode.SUCCESS, permissions);
    }


    @Override
    public Result list(Map<String, Object> map) {
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());

        IPage<Permission> iPage = new Page<>(page, size);

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", "0");
        queryWrapper.eq("type", "1");


        IPage<Permission> result = permissionMapper.selectPage(iPage, queryWrapper);

        PageResult<Permission> pageResult = new PageResult<>(result.getTotal(), result.getRecords());

        return new Result(ResultCode.SUCCESS, pageResult);
    }


    @Override
    public Result load(Permission permission) {
        List<Permission> permissions = this.getPermissions(permission.getId());
        return new Result(ResultCode.SUCCESS, permissions);
    }


    protected List<Permission> getPermissions(String pid) {
        List<Permission> list = new ArrayList<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        List<Permission> permissions = permissionMapper.selectList(queryWrapper);
        if (permissions != null && permissions.size() > 0) {
            for (Permission permission : permissions) {
                //permission现在已经拿到了,我们就等同于拿到了id, 即下一层的pid
                permission.setChildren(this.getPermissions(permission.getId()));
                list.add(permission);
            }
        }

        return list;
    }


    @Override
    public Result save(Permission permission) throws CommonException {

        String pid = permission.getPid();

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();

        queryWrapper.eq("pid", pid);
        queryWrapper.eq("type", "3");

        List<Permission> permissions = permissionMapper.selectList(queryWrapper);

        if (permissions != null && permissions.size() == 1) throw new CommonException(ResultCode.FAIL);

        permissionMapper.insert(permission);

        return Result.SUCCESS();
    }


    @Override
    public Result update(Permission permission) {
        Permission target = permissionMapper.selectById(permission.getId());
        BeanUtils.copyProperties(permission, target);
        permissionMapper.updateById(target);
        return Result.SUCCESS();
    }

    @Override
    public Result getPermissionsByRoleId(String id) {
        List<Permission> list = permissionMapper.getPermissionsByRoleId(id);
        return new Result(ResultCode.SUCCESS, list);
    }
}