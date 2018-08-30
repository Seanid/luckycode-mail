package com.luckycode.exception;

/**
 * Created by Sean on 2018/08/28.
 * 非法邮件地址异常
 */
public class UnExpectMailException extends Throwable {
    public UnExpectMailException() {
        super("非法邮件地址异常");
    }

}
