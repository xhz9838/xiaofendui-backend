package cn.edu.sdbi.controller;

import cn.edu.sdbi.utils.Result;
import cn.edu.sdbi.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author suiyue
 * @version 1.0
 * @date 2020/10/13
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handler(Exception e){
        // 获取异常信息,获取异常堆栈的完整异常信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        log.error(sw.toString());

        return Result.error(ResultCode.ERROR,"服务器异常，请稍后再试");
    }



}
