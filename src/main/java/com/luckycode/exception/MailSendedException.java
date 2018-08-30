package com.luckycode.exception;

/**
 *  Created by Sean on 2018/08/28
 *  邮件发送失败异常
 */
public class MailSendedException extends  Throwable{
    public MailSendedException() {
        super("邮件发送失败异常");
    }
}
