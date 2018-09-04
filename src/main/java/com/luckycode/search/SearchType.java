package com.luckycode.search;

/**
 * Created by Sean on 2018/08/29.
 * 搜索类型
 */
public enum SearchType {
    SUBJECT("subject"), BODY("body"), SENDDATE("send_date"), FROM("from"), RECEIVEDDATE("received_date"), SIZE("size");
    private String value;

    SearchType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
