package com.jwxt.entity.academic;

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
@TableName("st_mutiple")
public class Mutiple implements Serializable {

  @TableId(type = IdType.INPUT)
  private String id;

  @TableField("mutiple_content")
  private String mutipleContent; //题干

  @TableField("mutiple_option_a")
  private String mutipleOptionA;

  @TableField("mutiple_option_b")
  private String mutipleOptionB;

  @TableField("mutiple_option_c")
  private String mutipleOptionC;

  @TableField("mutiple_option_d")
  private String mutipleOptionD;

  @TableField("mutiple_ask")
  private String mutipleAsk;

  @TableField("modify_time")
  private Date modifyTime;

  @TableField("modify_name")
  private String modifyName;

  @TableField("mutiple_status")
  private String mutipleStatus; //启禁 1 启用 2禁用

}

