package com.jwxt.job.controller;

import com.jwxt.job.service.CompanyService;
import com.jwxt.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")

public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/findCompanys" , method = RequestMethod.GET,name= "PROFILE")
    public Result findCompanys(){
        return companyService.findCompanys();
    }
}
