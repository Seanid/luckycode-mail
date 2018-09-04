package com.luckycode.codding.test;

import com.luckycode.exception.UnknownProtocolException;
import com.luckycode.model.MailConfig;
import com.luckycode.model.MailContent;
import com.luckycode.model.Result;

import com.luckycode.services.SMTPService;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class EmailSenderTest {

    @Test
    public void sendTextTest() throws UnknownProtocolException {
        MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setDebug(true).build();
        SMTPService sender= SMTPService.getInstance(mailConfig);
        MailContent mailContent=new MailContent.Builder("demo@qq.com","来自hxy的邮件","你好，hello world~~").build();
        Result result=sender.sendEmail(mailContent);
        Assert.assertEquals(result.getStatus(),200);

    }


    @Test
    public void sendHtmlTest() throws UnknownProtocolException {
        MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setDebug(true).build();
        SMTPService sender= SMTPService.getInstance(mailConfig);
        MailContent mailContent=new MailContent.Builder("demo@qq.com","来自hxy的邮件","<s>你好，删除线</s>").build();
        Result result=sender.sendEmail(mailContent);
        Assert.assertEquals(result.getStatus(),200);
    }


    @Test
    public void sendFileTest() throws UnknownProtocolException {

        MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setDebug(true).build();
        SMTPService sender= SMTPService.getInstance(mailConfig);
        MailContent mailContent=new MailContent.Builder("demo@qq.com","来自hxy的邮件","<s>你好，删除线</s>").addFile(new File("demo.txt")).build();
        Result result=sender.sendEmail(mailContent);
        Assert.assertEquals(result.getStatus(),200);

    }

}
