package com.jwxt.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@TableName("sys_role_permission")
public class RolePermission implements Serializable {

  @TableId(value = "role_id",type = IdType.ID_WORKER_STR)
  private String roleId;

  @TableId(value = "permission_id",type = IdType.ID_WORKER_STR)
  private String permissionId;
}
