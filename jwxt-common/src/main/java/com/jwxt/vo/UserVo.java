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

    @TableField("single_score")
    private Integer singleScore;

    @TableField("single_succ_ids")
    private String singleSuccIds;

    @TableField("single_err_ids")
    private String singleErrIds;

    @TableField("multiple_succ")
    private Integer multipleSucc;

    @TableField("multiple_err")
    private Integer multipleErr;

    @TableField("multiple_score")
    private Integer multipleScore;

    @TableField("multiple_succ_ids")
    private String multipleSuccIds;

    @TableField("multiple_err_ids")
    private String multipleErrIds;


    private Integer score;

    private String status;  //score

    @TableField("nick_name")
    private String nickName; //user




}
