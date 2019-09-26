package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@TableName("st_mutiple_result")
public class MutipleResult implements Serializable {


    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("option_ids")
    private String optionIds;

    @TableField("mutiple_id")
    private String mutipleId;

    @TableField("exam_id")
    private String examId;

    @TableField("student_id")
    private String studentId;

}
