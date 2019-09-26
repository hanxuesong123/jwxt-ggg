package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@TableName("st_question")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Question implements Serializable {


  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  @TableField(value = "discipline")
  private String discipline; //学科编号  1软件 2网络

  @TableField(value = "lession_id")
  private String lessionId; //课程id

  @TableField(value = "lession_name")
  private String lessionName;//课程名称

  @TableField(value = "sourced")
  private String sourced;  //来源: 1.课程试题 2.面试宝典 3.企业真题

  @TableField(value = "company_id")
  private String companyId; //公司id

  @TableField("company_name")
  private String companyName;

  @TableField(value = "type")
  private String type;  //题型: 1.单选题 2.多选题 3.问答题 4.上机题

}
