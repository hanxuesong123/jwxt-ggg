package com.jwxt.permission.service;

import com.jwxt.entity.permission.Permission;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;

import java.util.Map;

public interface PermissionService {

    Result findAll();

    Result getPermissionsByRoleId(String id);

    Result list(Map<String, Object> map);

    Result load(Permission permission);

    Result save(Permission permission) throws CommonException;

    Result update(Permission permission);

}
