package com.jwxt.entity.quality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_teacher_classes")
public class TeacherClasses implements Serializable {

  @TableId(value = "classes_id",type = IdType.INPUT)
  private String classesId;

  @TableId(value = "teacher_id",type = IdType.INPUT)
  private String teacherId;

}
