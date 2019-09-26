package com.jwxt.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExamVo {

    List<QuestionVo> singleList;
    List<QuestionVo> mutipleList;
    List<QuestionVo> askList;
    List<QuestionVo> upperList;


}