package com.luckycode.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sean on 2018/08/23.
 * 发送邮件内容实体
 */
public class MailContent {

    //防止非法构造
    private MailContent(){}

    private String subject;
    private String content;
    private List<String> to=new ArrayList<String>();
    private List<String> ccTo=new ArrayList<String>();

    private List<String> bccTo=new ArrayList<String>();

    private Date sendDate=new Date();

//    private String htmlContent;

    private List<File> fileList=new ArrayList<File>();


    public String getSubject() {
        return subject;
    }

    private void setSubject(String subject) {
        this.subject = subject;
    }



    public List<String> getTo() {
        return to;
    }

    private void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCcTo() {
        return ccTo;
    }

    private void setCcTo(List<String> ccTo) {
        this.ccTo = ccTo;
    }

    public List<String> getBccTo() {
        return bccTo;
    }

    private void setBccTo(List<String> bccTo) {
        this.bccTo = bccTo;
    }

    public Date getSendDate() {
        return sendDate;
    }

    private void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }



    public List<File> getFileList() {
        return fileList;
    }

    private void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public static class Builder{

        private MailContent mailContent=new MailContent();

        private Builder(){}

        public Builder(String to,String subject,String content){
            this.mailContent.getTo().add(to);
            this.mailContent.setSubject(subject);
            this.mailContent.setContent(content);
        }

        public Builder addTo(String to){
            this.mailContent.getTo().add(to);
            return this;
        }

        public Builder addCcTo(String ccTo){
            this.mailContent.getCcTo().add(ccTo);
            return this;
        }

        public  Builder addBccTo(String bccTo){
            this.mailContent.getBccTo().add(bccTo);
            return this;
        }

        public Builder addFile(File file){
            this.mailContent.getFileList().add(file);
            return this;
        }

        public MailContent build(){
            return this.mailContent;
        }

    }

}
