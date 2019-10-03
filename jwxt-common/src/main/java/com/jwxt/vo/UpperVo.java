package com.jwxt.vo;


import com.jwxt.entity.academic.Score;
import com.jwxt.entity.academic.UpperResult;
import com.jwxt.entity.permission.User;
import com.jwxt.entity.quality.Student;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpperVo implements Serializable {

    private Student student;
    private User user;
    private List<UpperResult> list;
    private Score score;

}
