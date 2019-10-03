package com.jwxt.exceptions;


import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice //controller层的增强器: 捕获controller层抛出的异常
public class BaseExceptionHandler {


  @ExceptionHandler(value = Exception.class) //处理Exception异常
  @ResponseBody
  public Result error(HttpServletRequest request, HttpServletResponse response,Exception e){
    Result result = null;
    if(e.getClass()  == CommonException.class){
      CommonException ce = (CommonException) e;
      result = new Result(((CommonException) e).getResultCode());
    }else{
      result = new Result(ResultCode.FAIL);
    }
    //e.printStackTrace();
    return result;
  }

}
