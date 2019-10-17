package com.jwxt.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwxt.base.BaseService;
import com.jwxt.entity.permission.Teacher;
import com.jwxt.entity.permission.User;
import com.jwxt.permission.mapper.TeacherMapper;
import com.jwxt.permission.mapper.UserMapper;
import com.jwxt.permission.service.UserService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.PinYinUtil;
import com.jwxt.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Result getTeacherList(Map<String, Object> map) {
        long total = userMapper.getTotal(map);
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        List<TeacherVo> list = userMapper.getTeacherList((page - 1) * size,size,map);
        PageResult<TeacherVo> pageResult = new PageResult<>(total,list);
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @Override
    public Result findTeachers(){
        List<TeacherVo> teachers = userMapper.findTeachers();
        return new Result(ResultCode.SUCCESS,teachers);
    }

    @Override
    public Result saveTeacher(TeacherVo vo,String nickName) {

        try {
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
                    .password("123")
                    .relation(vo.getRelation())
                    .residenceAddress(vo.getResidenceAddress())
                    .sex(vo.getSex())
                    .telephone(vo.getTelephone())
                    .type("1")
                    .status("1")
                    .username(PinYinUtil.toPinyin(vo.getNickName()) + vo.getTelephone().substring(7))
                    .build();

            userMapper.insert(user);


            Teacher teacher = Teacher.builder()
                    .contractNumber(vo.getContractNumber())
                    .contractTime(vo.getContractTime())
                    .deptId(vo.getDeptId())
                    .entryTime(vo.getEntryTime())
                    .hasLeave(vo.getHasLeave())
                    .hasMember(vo.getHasMember())
                    .hasSocialSecurity(vo.getHasSocialSecurity())
                    .id(user.getId())
                    .jobNumber(vo.getJobNumber())
                    .jobTitle(vo.getJobTitle())
                    .laborContract(vo.getLaborContract())
                    .build();

            teacherMapper.insert(teacher);

            return Result.SUCCESS();
        } catch (Exception e) {
            return Result.FAIL();
        }
    }

    @Override
    public Result findOne(String id) {
        User user = userMapper.selectById(id);
        return new Result(ResultCode.SUCCESS,user);
    }

    @Override
    public Result updatePassword(Map<String, Object> map, String id) {

        User target = userMapper.selectById(id);

        if(!target.getPassword().equals(map.get("oldPassword").toString())){
            return Result.FAIL();
        }

        target.setPassword(map.get("newPassword").toString());

        userMapper.updateById(target);

        return Result.SUCCESS();
    }
}
