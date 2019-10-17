package com.jwxt.academic.service.impl;

import com.jwxt.academic.mapper.SuperviseMapper;
import com.jwxt.academic.service.SuperviseService;
import com.jwxt.entity.quality.Student;
import com.jwxt.quality.mapper.ClassesMapper;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.DateUtils;
import com.jwxt.vo.DayExam;
import com.jwxt.vo.DayExamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SuperviseServiceImpl implements SuperviseService {

    @Autowired
    private SuperviseMapper superviseMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Result getDayExamList(Map<String, Object> map) throws ParseException {

        String date = map.get("date").toString();

        Date beforeDate = DateUtils.getFirstDayDateOfMonth(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        Date lastDate = DateUtils.getLastDayOfMonth(new SimpleDateFormat("yyyy-MM-dd").parse(date));

        String classesId = map.get("classesId").toString();

        List<Student> students = classesMapper.getStudentList(classesId);

        List<DayExamVo> list = new ArrayList<>();

        for (Student student : students) {
            //一个学生一个DayExamVo    DayExamVo获得当前学生当前月份的所有成绩
            List<DayExam> dayExams = superviseMapper.getDayExamList(student.getId(),beforeDate,lastDate,map);

            if(dayExams != null && dayExams.size() > 0){
                DayExamVo day = new DayExamVo();
                day.setClassName(dayExams.get(0).getClassName());
                day.setNickName(dayExams.get(0).getNickName());

                Integer count = superviseMapper.getCountByCurrentMonth(student.getId(),beforeDate,lastDate);

                day.setCount(count);

                Integer newCount = count == 0 ? 1 : count;

                for (DayExam dayExam : dayExams) {  //dayExam是单个学生的每天成绩
                    int d = DateUtils.getDay(dayExam.getExecuteTime());
                    if( d == 1 ) day.setCol1(dayExam.getScore());
                    if( d == 2 ) day.setCol2(dayExam.getScore());
                    if( d == 3 ) day.setCol3(dayExam.getScore());
                    if( d == 4 ) day.setCol4(dayExam.getScore());
                    if( d == 5 ) day.setCol5(dayExam.getScore());
                    if( d == 6 ) day.setCol6(dayExam.getScore());
                    if( d == 7 ) day.setCol7(dayExam.getScore());
                    if( d == 8 ) day.setCol8(dayExam.getScore());
                    if( d == 9 ) day.setCol9(dayExam.getScore());
                    if( d == 10 ) day.setCol10(dayExam.getScore());
                    if( d == 11 ) day.setCol11(dayExam.getScore());
                    if( d == 12 ) day.setCol12(dayExam.getScore());
                    if( d == 13 ) day.setCol13(dayExam.getScore());
                    if( d == 14 ) day.setCol14(dayExam.getScore());
                    if( d == 15 ) day.setCol15(dayExam.getScore());
                    if( d == 16 ) day.setCol16(dayExam.getScore());
                    if( d == 17 ) day.setCol17(dayExam.getScore());
                    if( d == 18 ) day.setCol18(dayExam.getScore());
                    if( d == 19 ) day.setCol19(dayExam.getScore());
                    if( d == 20 ) day.setCol20(dayExam.getScore());
                    if( d == 21 ) day.setCol21(dayExam.getScore());
                    if( d == 22 ) day.setCol22(dayExam.getScore());
                    if( d == 23 ) day.setCol23(dayExam.getScore());
                    if( d == 24 ) day.setCol24(dayExam.getScore());
                    if( d == 25 ) day.setCol25(dayExam.getScore());
                    if( d == 26 ) day.setCol26(dayExam.getScore());
                    if( d == 27 ) day.setCol27(dayExam.getScore());
                    if( d == 28 ) day.setCol28(dayExam.getScore());
                    if( d == 29 ) day.setCol29(dayExam.getScore());
                    if( d == 30 ) day.setCol30(dayExam.getScore());
                    if( d == 31 ) day.setCol31(dayExam.getScore());
                }

                Integer total = day.getCol1() + day.getCol2() + day.getCol3()
                        + day.getCol4() + day.getCol5() + day.getCol6() + day.getCol7() + day.getCol8()
                        + day.getCol9() + day.getCol10() + day.getCol11() + day.getCol12() + day.getCol13()
                        + day.getCol14() + day.getCol15() + day.getCol16() + day.getCol17() + day.getCol18()
                        + day.getCol19() + day.getCol20() + day.getCol21() + day.getCol22() + day.getCol23()
                        + day.getCol24() + day.getCol25() + day.getCol26() + day.getCol27() + day.getCol28()
                        + day.getCol29() + day.getCol30() + day.getCol31();


                day.setSvg(total / newCount);

                day.setTotal(total);

                list.add(day);
            }

        }

        return new Result(ResultCode.SUCCESS,list);
    }

}
