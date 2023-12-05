package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 用于处理发生数据库重复输入异常，为前端返回一个正确的错误提示信息
     *
     * @param exception
     * @return com.sky.result.Result
     * @author TZzzQAQ
     * @create 2023/12/5
     **/

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.error("出现数据库重复输入异常");
        //使用contains函数对于是否存在冗余输入的问题
        if (exception.getMessage().contains("Duplicate entry")) {
            //使用split分割以空格为分割的字符串数组
            String[] s = exception.getMessage().split(" ");
            return Result.error(s[2] + MessageConstant.ACCOUNT_ALREADY_EXIST);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
