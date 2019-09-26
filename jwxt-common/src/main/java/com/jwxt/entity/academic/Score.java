package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("st_score")
public class Score implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    @TableField("single_succ")
    private Integer singleSucc;
    @TableField("single_succ_ids")
    private String singleSuccIds;
    @TableField("single_err")
    private Integer singleErr;
    @TableField("single_err_ids")
    private String singleErrIds;

    @TableField("multiple_succ")
    private Integer multipleSucc;
    @TableField("multiple_succ_ids")
    private String multipleSuccIds;
    @TableField("multiple_err")
    private Integer multipleErr;

    @TableField("multiple_err_ids")
    private String multipleErrIds;
    @TableField("single_score")
    private Integer singleScore;

    @TableField("multiple_score")
    private Integer multipleScore;

    @TableField("ask_score")
    private Integer askScore;
    @TableField("upper_score")
    private Integer upperScore;

    private Integer score;

    private String status;
    @TableField("student_id")
    private String studentId;
    @TableField("exam_id")
    private String examId;

    @TableField("execute_time")
    private Date executeTime;

    @TableField(exist = false)
    private String studentName;

}
