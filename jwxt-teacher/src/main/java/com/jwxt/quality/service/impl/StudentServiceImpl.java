package com.jwxt.quality.service.impl;

import com.jwxt.base.BaseService;
import com.jwxt.entity.permission.User;
import com.jwxt.entity.quality.Student;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.mapper.UserMapper;
import com.jwxt.quality.mapper.StudentClassesMapper;
import com.jwxt.quality.mapper.StudentMapper;
import com.jwxt.quality.service.StudentService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.PinYinUtil;
import com.jwxt.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class StudentServiceImpl extends BaseService<Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentClassesMapper studentClassesMapper;

    @Override
    public Result list(Map<String, Object> map) {

        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());

        long total = studentMapper.getTotal(map);
        List<StudentVo> list =  studentMapper.getStudentList((page -1 ) * size ,size,map);
        PageResult<StudentVo> pageResult = new PageResult<>(total,list);
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @Override
    public Result save(StudentVo vo,String nickName) {
        User user = User.builder()
                .birthday(vo.getBirthday())
                .education(vo.getEducation())
                .email(vo.getEmail())
                .emergencyContact(vo.getEmergencyContact())
                .graduationSchool(vo.getGraduationSchool())
                .idCard(vo.getIdCard())
                .major(vo.getMajor())
                .modifyName(nickName)
                .modifyTime(new Date())
                .nickName(vo.getNickName())
                .nowAddress(vo.getNowAddress())
                .password("123456")
                .relation(vo.getRelation())
                .residenceAddress(vo.getResidenceAddress())
                .sex(vo.getSex())
                .telephone(vo.getTelephone())
                .type("2")
                .status("1")
                .username(PinYinUtil.toPinyin(vo.getNickName()) + vo.getTelephone().substring(7))
                .build();

        userMapper.insert(user);

        Student student = Student.builder().id(user.getId()).studyType("1").build();

        studentMapper.insert(student);

        return Result.SUCCESS();
    }

    @Override
    public Result accessClasses(Map<String, Object> map) throws CommonException {

        String classesId = map.get("classesId").toString();
        String studentId = map.get("studentId").toString();

        studentClassesMapper.deleteData(studentId);

        studentClassesMapper.insertData(classesId,studentId);


        return Result.SUCCESS();
    }
}

