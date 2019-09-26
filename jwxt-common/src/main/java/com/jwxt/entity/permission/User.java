package com.jwxt.entity.permission;

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
@TableName("sys_user")
public class User implements Serializable {


  @TableId(type = IdType.ID_WORKER_STR)
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

  private String status; // 1启用 2禁用
}
