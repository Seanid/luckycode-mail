package com.luckycode.services;

import com.luckycode.model.MailConfig;

import java.util.Properties;

/**
 * Created by Sean on 2018/09/04.
 */
public abstract class EmailService {

    protected static Properties props = new Properties();//邮件配置

    protected MailConfig config;//自定义邮件配置信息


    protected void setProps(Properties props) {
        this.props = props;
    }

    protected void setConfig(MailConfig config) {
        this.config = config;
    }


}
