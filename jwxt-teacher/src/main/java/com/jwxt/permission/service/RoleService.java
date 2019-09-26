package com.jwxt.permission.service;

import com.jwxt.entity.permission.Role;
import com.jwxt.response.Result;

import java.util.Map;

public interface RoleService {

    Result accessPermission(Map<String, Object> map);

    Result assessRole(Map<String, Object> map);

    Result findRoles();

    Result getAssessPermission(String id);

    Result getAssessRoles(String id);

    Result list(Map<String, Object> map);

    Result save(Role role);

    Result update(Role role);
}
