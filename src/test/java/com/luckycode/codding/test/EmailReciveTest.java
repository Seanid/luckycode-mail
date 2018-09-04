package com.luckycode.codding.test;

import com.luckycode.exception.UnknownProtocolException;
import com.luckycode.model.MailConfig;
import com.luckycode.search.AndSearchTermBulider;
import com.luckycode.search.OrSearchTermBulider;
import com.luckycode.search.SearchType;
import com.luckycode.services.IMAPService;
import com.luckycode.services.POP3Service;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.search.SearchTerm;

public class EmailReciveTest {

    @Test
    public void emtrySearchPOP3() throws UnknownProtocolException {

        MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setPOP3Info(110, "pop3.126.com").setDebug(true).build();
        POP3Service emailService = POP3Service.getInstance(mailConfig);
        emailService.receiveEmail(null, null, (result, mailList) -> {
            Assert.assertEquals(result.getStatus(), 200);

        });

    }


    @Test
    public void andSearchPOP3() throws UnknownProtocolException {

        MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setPOP3Info(110, "pop3.126.com").setCacheCount(60).setDebug(true).build();
        POP3Service emailService = POP3Service.getInstance(mailConfig);
        SearchTerm searchTerm = new AndSearchTermBulider().add(SearchType.SUBJECT, "demo").add(SearchType.BODY, "demo").build();
        emailService.receiveEmail(searchTerm, null, (result, mailList) -> {
            Assert.assertEquals(result.getStatus(), 200);
            Assert.assertEquals(mailList.get(0).getSubject(), "demo");
        });

    }

    @Test
    public void orSearchPOP3() throws UnknownProtocolException {

        MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setPOP3Info(110, "pop3.126.com").setCacheCount(60).setDebug(true).build();
        POP3Service emailService = POP3Service.getInstance(mailConfig);
        SearchTerm searchTerm = new OrSearchTermBulider().add(SearchType.SUBJECT, "demo").add(SearchType.SUBJECT, "demo").build();
        emailService.receiveEmail(searchTerm, null, (result, mailList) -> {
            Assert.assertEquals(result.getStatus(), 200);
            System.out.println(mailList.size());
        });

    }


    @Test
    public void emtrySearchIMAP() throws UnknownProtocolException {

        MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setIMAPInfo(143, "imap.126.com").setDebug(false).build();
        IMAPService emailService = IMAPService.getInstance(mailConfig);
        emailService.receiveEmail(null, "demo", (result, mailList) -> {
            Assert.assertEquals(result.getStatus(), 200);

        });

    }


    @Test
    public void andSearchIMAP() throws UnknownProtocolException {

        MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setIMAPInfo(143, "imap.126.com").setDebug(true).build();
        IMAPService emailService = IMAPService.getInstance(mailConfig);
        SearchTerm searchTerm = new AndSearchTermBulider().add(SearchType.SUBJECT, "demo").add(SearchType.BODY, "demo").build();
        emailService.receiveEmail(searchTerm, "demo", (result, mailList) -> {
            Assert.assertEquals(result.getStatus(), 200);
            System.out.println(mailList.size());
            Assert.assertEquals(mailList.get(0).getSubject(), "demo");
        });

    }

    @Test
    public void orSearchIMAP() throws UnknownProtocolException {

         MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setIMAPInfo(143, "imap.126.com").setDebug(false).build();
        IMAPService emailService = IMAPService.getInstance(mailConfig);
        SearchTerm searchTerm = new OrSearchTermBulider().add(SearchType.SUBJECT, "demo").add(SearchType.SUBJECT, "demo").build();
        emailService.receiveEmail(searchTerm, "demo", (result, mailList) -> {
            Assert.assertEquals(result.getStatus(), 200);
            System.out.println(mailList.size());
        });

    }


}
