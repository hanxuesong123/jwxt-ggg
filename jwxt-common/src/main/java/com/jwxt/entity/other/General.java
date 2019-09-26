package com.jwxt.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@TableName("di_general")
public class General implements Serializable {

  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  private String name;

  private String code;

  private String status;

  @TableField("modify_name")
  private String modifyName;

  private String pid;

  @TableField("modify_time")
  private Date modifyTime;

  private String description;

}
