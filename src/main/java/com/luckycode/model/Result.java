package com.luckycode.model;

/**
 * Created by Sean on 2018/08/23.
 * 邮件查找发送返回结构
 */
public class Result {

    private int status;

    private String msg;

    private Throwable e;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
}
