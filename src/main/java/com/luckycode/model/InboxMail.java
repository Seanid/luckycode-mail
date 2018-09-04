package com.luckycode.model;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sean on 2018/08/29.
 * 收件箱邮件
 */
public class InboxMail {

    private String subject;
    private String content;
    private boolean isSeen;

    private boolean isAttach;

    private String from;

    private String fromName;

    private Date receiveDate;


    private HashMap<String ,InputStream> fileMap=new HashMap<>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public HashMap<String, InputStream> getFileMap() {
        return fileMap;
    }

    public void setFileMap(HashMap<String, InputStream> fileMap) {
        this.fileMap = fileMap;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public boolean isAttach() {
        return isAttach;
    }

    public void setAttach(boolean attach) {
        isAttach = attach;
    }

    @Override
    public String toString() {
        return "InboxMail{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", isSeen=" + isSeen +
                ", isAttach=" + isAttach +
                ", from='" + from + '\'' +
                ", fromName='" + fromName + '\'' +
                ", receiveDate=" + receiveDate +
                ", fileMap=" + fileMap +
                '}';
    }
}
