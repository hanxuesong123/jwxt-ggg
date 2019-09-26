package com.jwxt.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("sys_role")
public class Role implements Serializable {

  private String description;
  @TableId(type = IdType.ID_WORKER_STR)
  private String id;
  private String name;

}
