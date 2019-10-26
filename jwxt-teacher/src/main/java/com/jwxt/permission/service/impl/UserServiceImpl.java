package com.jwxt.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.jwxt.base.BaseService;
import com.jwxt.entity.permission.Teacher;
import com.jwxt.entity.permission.User;
import com.jwxt.exceptions.CommonException;
import com.jwxt.permission.mapper.TeacherMapper;
import com.jwxt.permission.mapper.UserMapper;
import com.jwxt.permission.service.UserService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.PinYinUtil;
import com.jwxt.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    public String from;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


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

    @Override
    public Result checkTelephone(Map<String, Object> map) {
        Object telephone = map.get("telephone");
        if(telephone == null) return Result.PARAMETERS_IS_NULL();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone.toString());

        User user = userMapper.selectOne(queryWrapper);

        if(user == null) return Result.FAIL();

        String targetTelephone = user.getTelephone();

        if(targetTelephone == null){
            return Result.PARAMETERS_IS_NULL();
        }else{
            if(!targetTelephone.equals(telephone.toString())){
                return Result.PARAMETERS_IS_NULL();
            }else{
                return Result.SUCCESS();
            }
        }
    }

    @Override
    public Result sendCode(Map<String, Object> map) throws CommonException {

        Object telephone = map.get("telephone");
        if(telephone == null) return Result.PARAMETERS_IS_NULL();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone.toString());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) return Result.FAIL();
        String email = user.getEmail();

        if(email == null) return Result.FAIL();

        //现在没有用RabbitMQ来发送邮件, 实际生产环境是需要用到RabbitMQ的
        int random = (int) ((Math.random()*9+1)*1000);
        String subject = "石家庄北大青鸟";
        String content = "<html><head></head><body><h3>您的验证码是:"+random+"</h3></body></html>";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            //true 表⽰示需要创建⼀一个 multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            return new Result(ResultCode.EMAIL_IS_ERROR);
        }
        redisTemplate.opsForValue().set("code#"+user.getId(),random);
        redisTemplate.expire("code#"+user.getId(),5, TimeUnit.MINUTES);
        return Result.SUCCESS();
    }

    @Override
    public Result updatePasswordByCode(Map<String, Object> map) {
        Object code = map.get("code");
        Object telephone = map.get("telephone");
        Object password = map.get("password");
        if(code == null) return Result.PARAMETERS_IS_NULL();
        if(telephone == null) return Result.PARAMETERS_IS_NULL();
        if(password == null) return Result.PARAMETERS_IS_NULL();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone.toString());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) return Result.FAIL();

        Integer  random = (Integer) redisTemplate.opsForValue().get("code#" + user.getId());

        if(random == null) return new Result(false,9996,"验证码已过期");

        if(random != Integer.parseInt(code.toString())) return new Result(false,9995,"验证码输入错误");

        user.setPassword(password.toString());

        userMapper.updateById(user);

        return Result.SUCCESS();
    }

    protected void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        //true 表⽰示需要创建⼀一个 multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

}
