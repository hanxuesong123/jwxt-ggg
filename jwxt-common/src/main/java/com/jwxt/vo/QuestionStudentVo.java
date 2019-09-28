package com.jwxt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuestionStudentVo implements Serializable {


    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String discipline; //学科编号  1软件 2网络

    private String lessionId; //课程id

    private String lessionName; //课程名称

    private String sourced;  //来源: 1.课程试题 2.面试宝典 3.企业真题

    private String companyId; //公司id

    private String companyName;

    private String type;  //题型: 1.单选题 2.多选题 3.问答题 4.上机题


    private String singleContent; //题干
    private String singleOptionA;
    private String singleOptionB;
    private String singleOptionC;
    private String singleOptionD;



    private String mutipleContent; //题干
    private String mutipleOptionA;
    private String mutipleOptionB;
    private String mutipleOptionC;
    private String mutipleOptionD;



    private String askContent; //题干



    private String upperContent; //题干
    private String upperUrl; //素材地址


}
