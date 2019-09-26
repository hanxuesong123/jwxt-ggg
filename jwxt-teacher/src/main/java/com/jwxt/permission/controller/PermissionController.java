package com.jwxt.permission.controller;

import com.jwxt.entity.permission.Permission;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.service.PermissionService;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/list",method = RequestMethod.POST,name = "API-PERMISSION-LIST")
    public Result list(@RequestBody Map<String,Object> map){
        return permissionService.list(map);
    }

    @RequestMapping(value = "/getPermissions",method = RequestMethod.GET,name = "PROFILE")
    public Result getPermissions(){
        return permissionService.findAll();
    }

    @RequestMapping(value = "/load",method = RequestMethod.POST, name = "API-PERMISSION-ALL")
    public Result load(@RequestBody Permission permission){
        return permissionService.load(permission);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST,name = "API-PERMISSION-ADD")
    public Result save(@RequestBody Permission permission) throws CommonException {
        return permissionService.save(permission);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT,name = "API-PERMISSION-UPDATE")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @RequestMapping(value = "/getPermissionsByRoleId/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result getPermissionsByRoleId(@PathVariable("id")String id){
        return permissionService.getPermissionsByRoleId(id);
    }

}
