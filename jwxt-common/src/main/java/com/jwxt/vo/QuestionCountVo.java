package com.jwxt.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionCountVo implements Serializable {

  private Integer singleCount;
  private Integer mutipleCount;
  private Integer askCount;
  private Integer upperCount;
}
