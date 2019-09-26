package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("st_ask")
public class Ask implements Serializable {

  @TableId(type = IdType.INPUT)
  private String id;

  @TableField("ask_content")
  private String askContent; //题干

  @TableField("modify_time")
  private Date modifyTime;

  @TableField("modify_name")
  private String modifyName;

  @TableField("ask_status")
  private String askStatus; //启禁 1 启用 2禁用

}

