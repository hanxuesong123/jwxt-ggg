package com.jwxt.entity.quality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_student_classes")
public class StudentClasses implements Serializable {

  @TableId(value = "student_id",type = IdType.ID_WORKER_STR)
  private String studentId;

  @TableId(value = "classes_id",type = IdType.ID_WORKER_STR)
  private String classesId;


}
