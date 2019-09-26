package com.jwxt.entity.permission;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@TableName("sys_teacher")
public class Teacher implements Serializable {


  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  @TableField("dept_id")
  private String deptId; //部门id

  @TableField("job_title")
  private String jobTitle; //职位名称

  @TableField("entry_time")
  private Date entryTime; //入职时间

  @TableField("has_member")
  private String hasMember; //是否转正  1已转正 2未转正

  @TableField("job_number")
  private Integer jobNumber; //工龄

  @TableField("labor_contract")
  private String laborContract; //是否办理劳动合同

  @TableField("contract_time")
  private Date contractTime; //签订劳动合同的日期

  @TableField("contract_number")
  private Integer contractNumber; //续签天数

  @TableField("has_social_security")
  private String hasSocialSecurity; //是否有社保 1有 2无

  @TableField("has_leave")
  private String hasLeave; //是否离职 1在职 2离职


}
