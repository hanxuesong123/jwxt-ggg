package com.jwxt.exceptions;

import com.jwxt.response.ResultCode;
import lombok.Getter;

//自定义异常

@Getter
public class CommonException extends Exception {

  private ResultCode resultCode;

  public CommonException(ResultCode resultCode){
    this.resultCode = resultCode;
  }
}
