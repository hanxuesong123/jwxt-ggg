package com.jwxt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVo {

  @TableId(type = IdType.INPUT)
  private String id;
  private String username;
  private String password;

  @TableField("nick_name")
  private String nickName;
  private String telephone;
  private String email;
  private String sex;

  @TableField("residence_address")
  private String residenceAddress; //户籍

  @TableField("now_address")
  private String nowAddress;

  private String type; //1 教师 2学生

  @TableField("modify_name")
  private String modifyName;

  @TableField("modify_time")
  private Date modifyTime;

  @TableField("id_card")
  private String idCard;

  private Date birthday;

  private String education; //学历

  @TableField("graduation_school")
  private String graduationSchool; //毕业院校

  private String major; //专业

  @TableField("emergency_contact")
  private String emergencyContact; //紧急联系人

  private String relation; //和紧急联系人的关系

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

  @TableField("status")
  private String status; //是否禁用该账号  1启用 2禁用

}
