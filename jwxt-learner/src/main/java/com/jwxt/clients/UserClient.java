package com.jwxt.clients;

import com.jwxt.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "JWXT-TEACHER")
public interface UserClient {

    @RequestMapping(value = "/user/findOne/{id}",method = RequestMethod.GET,name = "PROFILE")
    public Result findOne(@PathVariable("id")String id);

}
