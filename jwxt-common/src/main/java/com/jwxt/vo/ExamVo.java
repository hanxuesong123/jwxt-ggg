package com.jwxt.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExamVo implements Serializable {

    List<QuestionVo> singleList;
    List<QuestionVo> mutipleList;
    List<QuestionVo> askList;
    List<QuestionVo> upperList;


}