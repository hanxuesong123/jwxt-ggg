package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@TableName("st_single")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Single implements Serializable {

  @TableId(type = IdType.INPUT)
  private String id;

  @TableField("single_content")
  private String singleContent; //题干

  @TableField("single_option_a")
  private String singleOptionA;

  @TableField("single_option_b")
  private String singleOptionB;

  @TableField("single_option_c")
  private String singleOptionC;

  @TableField("single_option_d")
  private String singleOptionD;

  @TableField("single_ask")
  private String singleAsk;

  @TableField("modify_time")
  private Date modifyTime;

  @TableField("modify_name")
  private String modifyName;

  @TableField("single_status")
  private String singleStatus; //启禁 1 启用 2禁用


}

