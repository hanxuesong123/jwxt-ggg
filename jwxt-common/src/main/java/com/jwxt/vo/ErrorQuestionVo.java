package com.jwxt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ErrorQuestionVo implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;


    @TableField("student_answer")
    private String studentAnswer;

    @TableField("question_id")
    private String questionId;

    @TableField("question_type")
    private String questionType;

    @TableField("stage_id")
    private String stageId;

    @TableField("lession_id")
    private String lessionId;

    @TableField("student_id")
    private String studentId;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("modify_name")
    private String modifyName;

    private String stageName;

    private String chapterName;

    private String content;

    @TableField("single_option_a")
    private String singleOptionA;

    @TableField("single_option_b")
    private String singleOptionB;

    @TableField("single_option_c")
    private String singleOptionC;

    @TableField("single_option_d")
    private String singleOptionD;

    @TableField("single_ask")
    private String singleAsk;



    @TableField("mutiple_content")
    private String mutipleContent; //题干

    @TableField("mutiple_option_a")
    private String mutipleOptionA;

    @TableField("mutiple_option_b")
    private String mutipleOptionB;

    @TableField("mutiple_option_c")
    private String mutipleOptionC;

    @TableField("mutiple_option_d")
    private String mutipleOptionD;

    @TableField("mutiple_ask")
    private String mutipleAsk;



    @TableField("ask_content")
    private String askContent; //题干


    @TableField("upper_content")
    private String upperContent; //题干

    @TableField("upper_url")
    private String upperUrl; //素材地址

}
