package com.jwxt.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DayExam implements Serializable {

    private String className;

    private String nickName;

    private Integer score;

    private Date executeTime;
}
