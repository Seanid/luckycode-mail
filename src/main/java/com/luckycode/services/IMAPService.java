package com.luckycode.services;

import com.luckycode.exception.UnknownProtocolException;
import com.luckycode.interfaces.PostReceiveHandler;
import com.luckycode.model.MailConfig;

import javax.mail.search.SearchTerm;
import java.security.Security;
import java.util.Properties;

/**
 * Created by Sean on 2018/09/04.
 * IMAP收取协议
 */
public class IMAPService extends ReciveEmailService {
    private static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";


    public static IMAPService getInstance(MailConfig config) throws UnknownProtocolException {
        setReciveProtocol("imap");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        Properties props = new Properties();
        //配置POP3协议
        if (config.isImap()) {
            props.setProperty("mail.store.protocol", RECIVE_PROTOCOL);        // 协议
            props.setProperty("mail.imap.port", config.getImapPotr() + "");                // 端口
            props.setProperty("mail.imap.host", config.getImapHost());    // imap服务器
//            props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
//            props.setProperty("mail.imap.socketFactory.fallback", "false");
//            props.setProperty("mail.imap.auth.login.disable", "true");
        } else {
            throw new UnknownProtocolException();
        }
        //配置是否开启调试模式
        props.setProperty("mail.debug", Boolean.toString(config.isDebug()));
        IMAPService emailService = new IMAPService();
        emailService.setProps(props);
        emailService.setConfig(config);
        return emailService;
    }
}
