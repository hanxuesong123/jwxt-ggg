package com.jwxt.job.service.impl;

import com.jwxt.entity.job.Company;
import com.jwxt.job.mapper.CompanyMapper;
import com.jwxt.job.service.CompanyService;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Result findCompanys() {
        List<Company> companies = companyMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,companies);
    }
}

