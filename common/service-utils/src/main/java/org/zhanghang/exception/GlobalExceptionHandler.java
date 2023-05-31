package org.zhanghang.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zhanghang.Result.Result;

//异常处理类,全局advise
@ControllerAdvice
public class GlobalExceptionHandler {
    //全局异常处理
    @ExceptionHandler(Exception.class)
    //使得输出的结构为json
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();//打印输出结果
        return Result.fail(null).message("执行了全局异常处理");
    }

    //特定异常处理,当有特定异常处理类时，返回给前端会优先返回特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    //使得输出的结构为json
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();//打印输出结果
        return Result.fail(null).message("执行了特定异常处理");
    }
    //自定义异常处理
    @ExceptionHandler(DiyException.class)
    //使得输出的结构为json
    @ResponseBody
    public Result error(DiyException e) {
        e.printStackTrace();//打印输出结果
        return Result.fail(null).message("执行了自定义异常处理");
    }

}
