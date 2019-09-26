package com.jwxt.permission.controller;


import com.jwxt.entity.permission.Role;
import com.jwxt.permission.service.RoleService;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.POST,name = "API-ROLE-LIST")
    public Result list(@RequestBody Map<String,Object> map){ return roleService.list(map); }

    @RequestMapping(value = "/save",method = RequestMethod.POST, name = "API-ROLE-ADD")
    public Result save(@RequestBody Role role){ return roleService.save(role); }

    @RequestMapping(value = "/update",method = RequestMethod.PUT, name = "API-ROLE-UPDATE")
    public Result update(@RequestBody Role role){
        return roleService.update(role);
    }

    @RequestMapping(value = "/accessPermission",method = RequestMethod.POST,name = "API-ACCESS-ROLE-PERMISSION")
    public Result accessPermission(@RequestBody Map<String,Object> map){
        return roleService.accessPermission(map);
    }

    @RequestMapping(value = "/getAssessPermissions/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result getAssessPermissions(@PathVariable("id")String id){
        return roleService.getAssessPermission(id);
    }

    @RequestMapping(value = "/findRoles",method = RequestMethod.GET,name = "PROFILE")
    public Result findRoles(){
        return roleService.findRoles();
    }

    @RequestMapping(value = "/assessRole",method = RequestMethod.POST, name = "API-ROLE-USER-ACCESS")
    public Result assessRole(@RequestBody Map<String,Object> map){
        return roleService.assessRole(map);
    }

    @RequestMapping(value = "/getAssessRoles/{id}",method = RequestMethod.GET, name = "PROFILE")
    public Result getAssessRoles(@PathVariable("id")String id){
        return roleService.getAssessRoles(id);
    }


}
