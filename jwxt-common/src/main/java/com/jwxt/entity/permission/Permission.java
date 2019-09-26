package com.jwxt.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@TableName("sys_permission")
public class Permission implements Serializable {

  @TableId(value = "id",type = IdType.ID_WORKER_STR)
  private String id;
  private String code;
  private String name;
  private Integer type;
  private String description;
  private String pid;

  @TableField(exist = false)
  List<Permission> children;

}
