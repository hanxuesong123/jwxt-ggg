package com.jwxt.entity.quality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
@TableName("sys_classes")
public class Classes implements Serializable {


  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  @TableField("class_name")
  private String className;

  @TableField("start_time")
  private Date startTime;

  @TableField("end_time")
  private Date endTime;

  @TableField("general_id")
  private String generalId;

  @TableField("general_name")
  private String generalName;

  @TableField("person_number")
  private Integer personNumber;

  @TableField("teachers")  //用于查询时,显示在表格上的教师数据
  private String teachers;

  @TableField(exist = false)  //用于保存,修改时能够把数据插入中间表进行班级和教师的关联
  private String [] teacherIds;

  @TableField("modify_name")
  private String modifyName;

  @TableField("modify_time")
  private Date modifyTime;
}
