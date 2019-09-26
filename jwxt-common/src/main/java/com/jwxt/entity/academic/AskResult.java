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
@TableName("st_ask_result")
public class AskResult implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("ask_answer")
    private String askAnswer;

    @TableField("ask_id")
    private String askId;

    @TableField("exam_id")
    private String examId;

    @TableField("student_id")
    private String studentId;

}
