package com.luckycode.model;

/**
 * Created by Sean on 2018/08/23.
 * 邮箱配置
 */
public class MailConfig {

    private MailConfig(){}

    private int smtpPort;//SMTP协议端口

    private String smtpHost;//SMTP域名

    private int pop3Port;//pop3协议端口

    private String pop3Host;//pop3域名

    private int imapPotr;//imap端口

    private String imapHost;//imap域名

    private String userName;//认证邮件名

    private String password;//认证邮件密码

    private boolean debug = false;//是否开始调试模式


    private boolean isSmtp = false;//是否开启smtp服务

    private boolean isPop3 = false;//是否开启POP3服务

    private  boolean isImap=false;//是否开启imap服务

    private int cacheCount=20;//默认搜索最近的20个邮件





    private void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    private void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    private void setPop3Port(int pop3Port) {
        this.pop3Port = pop3Port;
    }

    private void setPop3Host(String pop3Host) {
        this.pop3Host = pop3Host;
    }

    public String  getImapHost() {
        return imapHost;
    }

    public void setImapHost(String imapHost) {
        this.imapHost = imapHost;
    }

    public int  getImapPotr() {
        return imapPotr;
    }

    public void setImapPotr(int imapPotr) {
        this.imapPotr = imapPotr;
    }

    public boolean isImap() {
        return isImap;
    }

    public void setImap(boolean imap) {
        isImap = imap;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setDebug(boolean debug) {
        this.debug = debug;
    }

    private void setSmtp(boolean smtp) {
        isSmtp = smtp;
    }

    private void setPop3(boolean pop3) {
        isPop3 = pop3;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public int getPop3Port() {
        return pop3Port;
    }

    public String getPop3Host() {
        return pop3Host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isSmtp() {
        return isSmtp;
    }

    public boolean isPop3() {
        return isPop3;
    }

    public int getCacheCount() {
        return cacheCount;
    }

    public void setCacheCount(int cacheCount) {
        this.cacheCount = cacheCount;
    }

    @Override
    public int hashCode() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(smtpHost).append(smtpPort).append(pop3Host).append(pop3Port).append(userName).append(password).hashCode();
    }

    //构建类
    public static class Builder {
        //防止非法初始化
        private Builder() {
        }

        private final MailConfig mailConfig = new MailConfig();

        public Builder(String username, String password) {
            mailConfig.setUserName(username);
            mailConfig.setPassword(password);
        }

        public Builder setSMTPInfo(int smtpPort, String smtpUrl) {
            mailConfig.setSmtpPort(smtpPort);
            mailConfig.setSmtpHost(smtpUrl);

            mailConfig.setSmtp(true);
            return this;
        }

        public Builder setPOP3Info(int pop3Port, String pop3Url) {
            mailConfig.setPop3Port(pop3Port);
            mailConfig.setPop3Host(pop3Url);
            mailConfig.setPop3(true);
            return this;
        }

        public Builder setIMAPInfo(int imapPort,String imapHost){
            mailConfig.setImap(true);
            mailConfig.setImapHost(imapHost);
            mailConfig.setImapPotr(imapPort);
            return this;
        }

        public Builder setDebug(boolean debug) {
            mailConfig.setDebug(debug);
            return this;
        }


        public Builder setCacheCount(int count) {
            mailConfig.setCacheCount(count);
            return this;
        }



        public MailConfig build() {
            return this.mailConfig;
        }
    }

}
