package com.jwxt.other.service.impl;

import com.jwxt.entity.other.Department;
import com.jwxt.other.mapper.DepartmentMapper;
import com.jwxt.other.service.DepartmentService;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Result findDepartments() {
        List<Department> departments = departmentMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,departments);
    }
}
