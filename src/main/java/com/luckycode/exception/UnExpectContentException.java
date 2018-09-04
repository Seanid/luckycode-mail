package com.luckycode.exception;

/**
 * Created by Sean on 2018/08/28.
 * 非法邮件内容异常
 */
public class UnExpectContentException extends Throwable {
    public UnExpectContentException() {
        super("非法邮件内容异常");
    }

}
