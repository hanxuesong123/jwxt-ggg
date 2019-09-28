package com.jwxt.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExamStudentVo implements Serializable {

    List<QuestionStudentVo> singleList;
    List<QuestionStudentVo> mutipleList;
    List<QuestionStudentVo> askList;
    List<QuestionStudentVo> upperList;

}
