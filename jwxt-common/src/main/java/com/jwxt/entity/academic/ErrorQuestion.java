package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@TableName("st_error_question")
public class ErrorQuestion implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

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
}
