package com.jwxt.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_department")
public class Department implements Serializable {

  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  private String name;

  @TableField("manager_id")
  private String managerId;

  @TableField("manager_name")
  private String  managerName;

  @TableField("person_number")
  private String personNumber;

  @TableField("modify_time")
  private Date modifyTime;

  @TableField("modify_name")
  private String modifyName;


}
