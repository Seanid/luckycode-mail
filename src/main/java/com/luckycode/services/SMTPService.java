package com.luckycode.services;

import com.luckycode.exception.*;
import com.luckycode.model.MailConfig;
import com.luckycode.model.MailContent;
import com.luckycode.model.Result;
import com.luckycode.utils.RegUtil;
import com.luckycode.utils.StringUtil;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Sean on 2018/09/04.
 * SMTP 发送服务类
 * 单一职责化
 */
public class SMTPService extends EmailService{

    private SMTPService(){}

    private final static String SEND_PROTOCOL="smtp";

    public static SMTPService getInstance(MailConfig config) throws UnknownProtocolException {

        //配置SMTP协议
        if (config.isSmtp()) {
            props.setProperty("mail.transport.protocol", SEND_PROTOCOL);
            props.setProperty("mail.smtp.host", config.getSmtpHost());
            props.setProperty("mail.smtp.port", config.getSmtpPort() + "'");
        }else {
            throw new UnknownProtocolException();
        }
        //配置是否开启调试模式
        props.setProperty("mail.debug", Boolean.toString(config.isDebug()));
        //开启认证
        props.setProperty("mail.smtp.auth", "true");
        SMTPService emailSender = new SMTPService();
        emailSender.setProps(props);
        emailSender.setConfig(config);
        return emailSender;

    }

    /**
     * 发送邮件内容
     *
     * @param mailContent 邮件实体类
     */
    public Result sendEmail(MailContent mailContent) {

        Result result = new Result();


        try {
            result.setStatus(200);
            validContent(mailContent);
            buildAndSend(mailContent);
        } catch (UnExpectContentException | UnknownProtocolException | MessBulidingException | MailConnectException | MailSendedException | UnExpectMailException e) {
            result.setStatus(500);
            result.setE(e);
            result.setMsg(e.getMessage());
        } catch (MessagingException e) {
            result.setStatus(500);
            result.setE(e);
            result.setMsg(e.getMessage());
        }
        return result;


    }

    /**
     * 校验发送的邮件内容
     *
     * @param mailContent 邮件内容实体类
     * @throws UnExpectContentException 非法邮件内容异常
     * @throws UnExpectMailException    非法邮件地址异常
     */
    private void validContent(MailContent mailContent) throws UnExpectContentException, UnExpectMailException {

        if (mailContent == null) {
            throw new UnExpectContentException();
        }

        if (!validEmail(mailContent)) {
            throw new UnExpectMailException();
        }
    }

    /**
     * 判断邮件内容中邮件地址是否合格
     *
     * @param mailContent 邮件内容
     * @return 是否符合合格
     */
    private boolean validEmail(MailContent mailContent) {

        //收件人地址判断，不符合规格直接返回
        if (!validEmailList(mailContent.getTo())) {
            return false;
        }

        //抄送人邮件地址判断
        if (mailContent.getCcTo().size() != 0) {
            if (!validEmailList(mailContent.getCcTo())) {
                return false;
            }
        }

        //秘密抄送人邮件地址判断
        if (mailContent.getBccTo().size() != 0) {
            return validEmailList(mailContent.getBccTo());
        }

        return true;
    }

    /**
     * 判断邮件是否合格
     * @param mailList
     * @return
     */
    private boolean validEmailList(List<String> mailList) {
        if (mailList.size() > 0) {
            for (String mail : mailList) {
                if (!RegUtil.vaildEmail(mail)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 构造发送内容并发送
     * 邮件发送主要业务逻辑类
     *
     * @param mailContent 邮件内容实体
     */
    private void buildAndSend(MailContent mailContent) throws MessBulidingException, UnknownProtocolException, MailConnectException, MailSendedException, MessagingException {

        // 创建Session实例对象
        Session session = Session.getInstance(props);

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);

        try {
            message = buildMailBody(message, mailContent);
        } catch (MessagingException e) {
            throw new MessBulidingException();
        }

        // 获得Transport实例对象
        Transport transport = null;
        try {
            transport = session.getTransport();
        } catch (NoSuchProviderException e) {
            //协议异常
            throw new UnknownProtocolException();
        }

        // 打开连接
        try {
            transport.connect(this.config.getUserName(), this.config.getPassword());
        } catch (MessagingException e) {
            //连接异常
            throw new MailConnectException();
        }

        // 将message对象传递给transport对象，将邮件发送出去
        try {
            transport.sendMessage(message, message.getAllRecipients());
        } catch (SendFailedException sfe) {
            //发送失败
            throw new MailSendedException();
        } catch (MessagingException e) {
            //发送失败
            throw e;
        }

        // 关闭连接
        try {
            transport.close();
        } catch (MessagingException e) {
            //关闭连接失败
            throw e;
        }

    }

    /**
     * 构建邮件内容体报文
     * @param message   信息体
     * @param mailContent   封装邮件内容
     * @return  返回内容体
     * @throws MessagingException
     */
    private MimeMessage buildMailBody(MimeMessage message, MailContent mailContent) throws MessagingException {
        try {

            String charset = "utf-8";    // 指定中文编码格式

            // 设置主题
            message.setSubject(mailContent.getSubject());
            // 设置发送人
            message.setFrom(new InternetAddress(this.config.getUserName(), this.config.getUserName(), charset));

            // InternetAddress参数：参数1：邮箱地址，参数2：姓名（在客户端收件只显示姓名，而不显示邮件地址），参数3：姓名中文字符串编码
            InternetAddress[] toEmailAddress = getMailAddress(mailContent.getTo());

            // 设置收件人
            message.setRecipients(MimeMessage.RecipientType.TO, toEmailAddress);

            // 设置抄送
            if (mailContent.getCcTo().size() > 0) {
                InternetAddress[] ccToEmailAddress = getMailAddress(mailContent.getCcTo());
                message.setRecipients(MimeMessage.RecipientType.CC, ccToEmailAddress);
            }

            // 设置密送
            if (mailContent.getBccTo().size() > 0) {
                InternetAddress[] bccToEmailAddress = getMailAddress(mailContent.getBccTo());
                message.setRecipients(MimeMessage.RecipientType.BCC, bccToEmailAddress);

            }

            // 设置发送时间
            message.setSentDate(mailContent.getSendDate());

            // 设置回复人(收件人回复此邮件时,默认收件人)
//        message.setReplyTo(InternetAddress.parse("\"" + MimeUtility.encodeText("田七") + "\" <417067629@qq.com>"));

            // 设置优先级(1:紧急	3:普通	5:低)
            message.setHeader("X-Priority", "1");

            // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)
            //message.setHeader("Disposition-Notification-To", from);

            // 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件
            MimeMultipart mail = new MimeMultipart("mixed");
            message.setContent(mail);

            // 内容
            MimeBodyPart mailBody = new MimeBodyPart();

            // 邮件正文组合体
            MimeMultipart bodyContent = new MimeMultipart("related");    //邮件正文也是一个组合体,需要指明组合关系

            //邮件正文部分，支持非html和html两种
            if (!StringUtil.isNullOrEmpty(mailContent.getContent())) {
                MimeBodyPart htmlPart = new MimeBodyPart();
                //html邮件内容
                MimeMultipart htmlMultipart = new MimeMultipart("alternative");
                MimeBodyPart htmlContent = new MimeBodyPart();
                htmlContent.setContent(mailContent.getContent(), "text/html;charset=" + charset);
                htmlMultipart.addBodyPart(htmlContent);
                htmlPart.setContent(htmlMultipart);
                bodyContent.addBodyPart(htmlPart);
            }

            //附件部分
            if (mailContent.getFileList().size() > 0) {
                for (File attachFile : mailContent.getFileList()) {
                    MimeBodyPart attach = new MimeBodyPart();
                    DataSource attachDataSource = new FileDataSource(attachFile);
                    DataHandler attachDataHandler = new DataHandler(attachDataSource);
                    attach.setFileName(MimeUtility.encodeText(attachFile.getName()));
                    attach.setDataHandler(attachDataHandler);
                    bodyContent.addBodyPart(attach);
                }
            }
            mailBody.setContent(bodyContent);
            mail.addBodyPart(mailBody);
            message.setContent(mail);
            // 保存邮件内容修改
            message.saveChanges();
            return message;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取邮件地址数组
     * @param mailList
     * @return
     * @throws AddressException
     */
    private InternetAddress[] getMailAddress(List<String> mailList) throws AddressException {
        InternetAddress[] addresses = new InternetAddress[mailList.size()];
        for (int i = 0; i < mailList.size(); i++) {
            addresses[i] = new InternetAddress(mailList.get(i));
        }
        return addresses;
    }



}
