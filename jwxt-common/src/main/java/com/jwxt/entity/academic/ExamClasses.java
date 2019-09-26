package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("st_exam_classes")
public class ExamClasses implements Serializable {

  @TableId(value = "classes_id",type = IdType.ID_WORKER_STR)
  private String classesId;

  @TableId(value = "exam_id",type = IdType.ID_WORKER_STR)
  private String examId;
}

