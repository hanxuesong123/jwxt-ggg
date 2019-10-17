package com.jwxt.quality.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwxt.base.BaseService;
import com.jwxt.entity.other.General;
import com.jwxt.entity.permission.User;
import com.jwxt.entity.quality.Classes;
import com.jwxt.entity.quality.TeacherClasses;
import com.jwxt.exceptions.CommonException;
import com.jwxt.other.mapper.GeneralMapper;
import com.jwxt.permission.mapper.UserMapper;
import com.jwxt.quality.mapper.ClassesMapper;
import com.jwxt.quality.service.ClassesService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class ClassesServiceImpl extends BaseService<Classes> implements ClassesService {


    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getClassesList(Map<String, Object> map) throws ParseException {

        IPage<Classes> iPage = super.iPage(map);

        QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(map.get("nickName"))){
            queryWrapper.like("teachers",map.get("nickName").toString());
        }

        if(!StringUtils.isEmpty(map.get("className"))){
            queryWrapper.like("class_name",map.get("className").toString());
        }

        if(!StringUtils.isEmpty(map.get("lessionId"))){
            queryWrapper.like("general_id",map.get("lessionId").toString());
        }

        if(!StringUtils.isEmpty(map.get("startTime"))){
            queryWrapper.between("start_time",DateUtils.beforeOneMonth(map.get("startTime").toString()),DateUtils.afterOneMonth(map.get("startTime").toString()));
        }

        if(!StringUtils.isEmpty(map.get("endTime"))){
            queryWrapper.between("end_time",DateUtils.beforeOneMonth(map.get("endTime").toString()),DateUtils.afterOneMonth(map.get("endTime").toString()));
        }

        queryWrapper.orderByDesc("modify_time");

        IPage<Classes> result = classesMapper.selectPage(iPage, queryWrapper);

        List<Classes> list = result.getRecords();

        if(list != null && list.size() > 0){
            for (Classes classes : list) {

                //当前班级的人数
                Integer count = classesMapper.getPersonNumber(classes.getId());

                classes.setPersonNumber(count);

                String teacherIds = "";
                List<TeacherClasses> teacherClasses =  classesMapper.getTeacherClassesList(classes.getId());
                if(teacherClasses != null && teacherClasses.size() > 0){
                    for (TeacherClasses teacherClass : teacherClasses) {
                        teacherIds += "," + teacherClass.getTeacherId();
                    }
                    if(teacherIds.startsWith(",")){
                        teacherIds = teacherIds.substring(1);
                    }
                }
                classes.setTeacherIds(teacherIds.split(","));
            }
        }


        PageResult<Classes> pageResult = new PageResult<>(result.getTotal(),list);

        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @Override
    public Result saveClasses(Classes classes,String nickName) throws CommonException {

        String[] teacherIds = classes.getTeacherIds();

        classes.setModifyName(nickName);
        classes.setModifyTime(new Date());

        General general = generalMapper.selectById(classes.getGeneralId());
        classes.setGeneralName(general.getName());


        String teachers = "";
        for (String teacherId : teacherIds) {
            User user = userMapper.selectById(teacherId);
            teachers += ","+user.getNickName();
        }

        if(teachers.startsWith(",")){
            teachers = teachers.substring(1);
        }

        classes.setTeachers(teachers);

        classesMapper.insert(classes);

        for (String teacherId : teacherIds) {

            List<TeacherClasses> teacherClasses =  classesMapper.teacherHasClasses(teacherId,classes.getId());

            if(teacherClasses != null && teacherClasses.size() > 0){ //存在同一个班,绑定同一个老师的情况,所以抛出异常禁止行为
                throw new CommonException(ResultCode.FAIL);
            }else{
                classesMapper.insertTeacherClasses(teacherId,classes.getId());
            }
        }

        return Result.SUCCESS();
    }


    @Override
    public Result updateClasses(Classes classes){
        Classes target = classesMapper.selectById(classes.getId());
        System.out.println(classes.toString());
        //把原来绑定的教师与班级的数据删除
        //String[] teacherIds = target.getTeacherIds();  //因为数据库表中没有teacherIds这个字段值,所以为空,我们需要查询中间表

        List<TeacherClasses> teacherClassesList = classesMapper.getTeacherClassesList(classes.getId());

        if(teacherClassesList != null && teacherClassesList.size() > 0){
            for (TeacherClasses teacherClasses : teacherClassesList) {
                classesMapper.deleteTeacherClasses(teacherClasses.getTeacherId(),classes.getId());
            }
        }
        //添加新的教师与班级的数据绑定
        String[] newTeacherIds = classes.getTeacherIds();
        for (String newTeacherId : newTeacherIds) {
            classesMapper.insertTeacherClasses(newTeacherId,target.getId());
        }

        String teachers = "";
        for (String teacherId : newTeacherIds) {
            User user = userMapper.selectById(teacherId);
            teachers += "," + user.getNickName();
        }

        if(teachers.startsWith(",")){
            teachers = teachers.substring(1);
        }

        BeanUtils.copyProperties(classes,target);

        target.setTeachers(teachers);

        classesMapper.updateById(target);
        return Result.SUCCESS();
    }


    public Result findClasses(){
        List<Classes> list = classesMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,list);
    }

}
