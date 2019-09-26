package com.jwxt.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.jwxt.clients.UserClient;
import com.jwxt.entity.academic.Exam;
import com.jwxt.entity.permission.User;
import com.jwxt.exam.mapper.ExamMapper;
import com.jwxt.exam.service.StudentExamService;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StudentExamServiceImpl implements StudentExamService {

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private UserClient userClient;

    @Override
    public Result getList(Map<String, Object> map) throws CommonException {
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        String userId = map.get("userId").toString();

        Result result = userClient.findOne(userId);
        Object data = result.getData();
        //User user = new User();
        //BeanUtils.copyProperties(data,user);

        User user = JSON.parseObject(JSON.toJSONString(data), User.class);

        if(!"2".equals(user.getType())){
            throw new CommonException(ResultCode.NO_STUDENT_IDENTITY);
        }


        List<Exam> list = examMapper.getExamListByStudentId((page - 1) * size, size , userId);
        long total = examMapper.getCountByStudentId(userId);

        PageResult<Exam> pageResult = new PageResult<>(total,list);

        return new Result(ResultCode.SUCCESS,pageResult);
    }
}
