package com.jwxt.response;



public enum ResultCode {

  ERPI_TOKEN(false,9999,"令牌过期，请重新登陆"),
  SUCCESS(true,10000,"恭喜你,操作成功"),
  FAIL(false,10001,"操作失败,请检查你的网络"),
  LOGIN_USER_NOT_ENABLE_STATE(false,80088,"登录用户不存在或密码错误或被禁用"),
  NO_EXIST_PERMISSION(false,55555,"权限不足"),
  INTERCEPTOR_EXCEPTION(false,99999,"拦截异常"),


  EXIST_CLASSES(false,70000,"指定的班级已存在"),

  NO_STUDENT_IDENTITY(false,66666,"非学生身份无法查看"),
  NO_STUDENT_IN_CLASSES(false,66665,"所属班级没有学生"),

  EXAM_DAY_IS_ALWAYS(false,30100,"当前选择的日期已有日测试卷"),
  EXAM_WEEK_IS_ALWAYS(false,30103,"当前选择的日期已有周测试卷"),
  EXAM_MONTH_IS_ALWAYS(false,30104,"当前选择的日期已有月考试卷"),
  EXAM_IS_COMMIT(false,30101,"试卷已提交"),
  SCORE_IS_COMMIT(false,30102,"无法再次提交试卷"),

  SERVICE_IS_DOWN(false,10110,"服务已降级"),
  SERVICE_IS_RATE_LIMIT(false,10111,"服务已限制");


  int code;
  String message;
  boolean success;


  ResultCode(boolean success,int code,String message){
    this.success = success;
    this.code = code;
    this.message = message;
  }

  public int code(){
    return code;
  }

  public String message(){
    return message;
  }

  public boolean success(){
    return success;
  }
}
