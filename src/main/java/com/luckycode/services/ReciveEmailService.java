package com.luckycode.services;

import com.luckycode.exception.MailConnectException;
import com.luckycode.exception.UnknownMailResourceException;
import com.luckycode.exception.UnknownProtocolException;
import com.luckycode.interfaces.PostReceiveHandler;
import com.luckycode.model.InboxMail;
import com.luckycode.model.Result;
import com.luckycode.utils.MessageUtil;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 2018/09/04.
 */
public abstract class ReciveEmailService extends EmailService {

    protected static String RECIVE_PROTOCOL = "";


    protected static void setReciveProtocol(String protocol) {
        RECIVE_PROTOCOL = protocol;
    }


    /**
     * @param mailFilter 邮件搜索条件
     * @param folderName 文件名，POP3没法选择
     * @param handler    处理器
     */
    public void receiveEmail(SearchTerm mailFilter, String folderName, PostReceiveHandler handler) {
        Result result = new Result();

        Message[] messages = new Message[0];
        try {
            messages = reciveEmail(mailFilter, folderName);
        } catch (UnknownProtocolException | MailConnectException | UnknownMailResourceException | SearchException e) {
            result.setStatus(500);
            result.setE(e);
            result.setMsg(e.getMessage());
            return;
        }

        List<InboxMail> maillist = null;
        try {
            maillist = parseMessage(messages);
        } catch (IOException e) {
            result.setStatus(500);
            result.setE(e);
            result.setMsg(e.getMessage());
            return;
        } catch (MessagingException e) {
            result.setStatus(500);
            result.setE(e);
            result.setMsg(e.getMessage());
            return;
        }

        result.setStatus(200);
//        handler.process(result,maillist);
        final List<InboxMail> resultMailList = maillist;
        new Thread(() -> {
            handler.process(result, resultMailList);
        }).start();

    }


    /**
     * 收取邮件
     *
     * @param mailFilter 邮件搜索条件
     * @param folderName 收取文件夹
     * @return 获取到的信息数组
     * @throws UnknownProtocolException
     * @throws MailConnectException
     * @throws UnknownMailResourceException
     * @throws SearchException
     */
    protected Message[] reciveEmail(SearchTerm mailFilter, String folderName) throws UnknownProtocolException, MailConnectException, UnknownMailResourceException, SearchException {
        // 创建Session实例对象
        Session session = Session.getInstance(props);

        Store store = null;
        try {
            store = session.getStore(RECIVE_PROTOCOL);
        } catch (NoSuchProviderException e) {
            throw new UnknownProtocolException();
        }

        try {
            store.connect(config.getUserName(), config.getPassword());
        } catch (MessagingException e) {
            throw new MailConnectException();
        }
        Folder folder = null;

        try {
            // 获得收件箱
            if (RECIVE_PROTOCOL.equals("pop3")) {
                folder = (POP3Folder) store.getFolder("INBOX");
            } else {
                folder = (IMAPFolder) store.getFolder(folderName);
            }
            /* Folder.READ_ONLY：只读权限
             * Folder.READ_WRITE：可读可写（可以修改邮件的状态）
             */
            folder.open(Folder.READ_WRITE);    //打开收件箱
        } catch (MessagingException e) {
            throw new UnknownMailResourceException();
        }

        Message[] messages = new Message[0];

        try {
            if (null == mailFilter) {
//                messages=folder.getMessages();
                if (folder.getMessageCount() > config.getCacheCount()) {
                    messages = folder.getMessages(folder.getMessageCount() - config.getCacheCount() + 1, folder.getMessageCount());
                } else {
                    messages = folder.getMessages();
                }
            } else {
                if (folder.getMessageCount() > config.getCacheCount()) {
                    messages = folder.search(mailFilter, folder.getMessages(folder.getMessageCount() - config.getCacheCount() + 1, folder.getMessageCount()));
                } else {
                    messages = folder.search(mailFilter, folder.getMessages());

                }

            }
        } catch (MessagingException e) {
            throw new SearchException();
        }

        return messages;
    }


    /**
     * 解析邮件
     *
     * @param messages 要解析的邮件列表
     */
    protected List<InboxMail> parseMessage(Message[] messages) throws IOException, MessagingException {
        List<InboxMail> maillist = new ArrayList<>();
        // 解析所有邮件
        for (int i = 0, count = messages.length; i < count; i++) {
            MimeMessage msg = (MimeMessage) messages[i];
            InboxMail mail = new InboxMail();
            mail.setSubject(MessageUtil.getSubject(msg));
            mail.setFrom(MessageUtil.getFromAddress(msg));
            mail.setFromName(MessageUtil.getFromName(msg));
            mail.setReceiveDate(msg.getReceivedDate());
            mail.setSeen(msg.getFlags().contains(Flags.Flag.SEEN));
            boolean isContainerAttachment = MessageUtil.isContainAttachment(msg);
            mail.setAttach(isContainerAttachment);
            StringBuffer content = new StringBuffer();
            MessageUtil.getMailTextContent(msg, content);
            mail.setContent(content.toString());
            if (isContainerAttachment) {
                mail.setFileMap(MessageUtil.saveAttachment(msg)); //保存附件
            }
            maillist.add(mail);
        }
        return maillist;
    }


}
