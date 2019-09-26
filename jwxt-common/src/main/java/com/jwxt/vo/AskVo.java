package com.jwxt.vo;

import com.jwxt.entity.quality.Student;
import com.jwxt.entity.academic.AskResult;
import com.jwxt.entity.academic.Score;
import com.jwxt.entity.permission.User;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AskVo implements Serializable {

    private Student student;
    private User user;
    private List<AskResult> list;
    private Score score;

}
