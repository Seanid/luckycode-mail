package com.luckycode.services;

import com.luckycode.exception.MailConnectException;
import com.luckycode.exception.UnknownMailResourceException;
import com.luckycode.exception.UnknownProtocolException;
import com.luckycode.interfaces.PostReceiveHandler;
import com.luckycode.model.InboxMail;
import com.luckycode.model.MailConfig;
import com.luckycode.model.Result;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Sean on 2018/09/04.
 */
public class POP3Service extends ReciveEmailService {


    public static POP3Service getInstance(MailConfig config) throws UnknownProtocolException {

        setReciveProtocol("pop3");
        Properties props = new Properties();

        //配置POP3协议
        if (config.isPop3()) {
            props.setProperty("mail.store.protocol", RECIVE_PROTOCOL);        // 协议
            props.setProperty("mail.pop3.port", config.getPop3Port() + "");                // 端口
            props.setProperty("mail.pop3.host", config.getPop3Host());    // pop3服务器
        } else {
            throw new UnknownProtocolException();
        }
        //配置是否开启调试模式
        props.setProperty("mail.debug", Boolean.toString(config.isDebug()));
        POP3Service emailService = new POP3Service();
        emailService.setProps(props);
        emailService.setConfig(config);
        return emailService;
    }


}
