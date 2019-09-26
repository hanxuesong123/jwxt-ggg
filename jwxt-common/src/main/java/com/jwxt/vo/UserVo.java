package com.jwxt.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserVo implements Serializable {


    @TableField("exam_name")
    private String examName;

    @TableField("single_succ")
    private Integer singleSucc;

    @TableField("single_err")
    private Integer singleErr;

    @TableField("multiple_succ")
    private Integer multipleSucc;

    @TableField("multiple_err")
    private Integer multipleErr;

    @TableField("multiple_score")
    private Integer multipleScore;

    private Integer score;

    private String status;

    @TableField("nick_name")
    private String nickName;




}
