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
@TableName("st_upper")
public class Upper implements Serializable {

  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  @TableField("upper_content")
  private String upperContent; //题干

  @TableField("upper_url")
  private String upperUrl; //素材地址

  @TableField("modify_time")
  private Date modifyTime;

  @TableField("modify_name")
  private String modifyName;

  @TableField("upper_status")
  private String upperStatus; //启禁 1 启用 2禁用
}
