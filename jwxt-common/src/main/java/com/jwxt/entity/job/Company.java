package com.jwxt.entity.job;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("jy_company")
public class Company implements Serializable {


  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  private String name;
}

