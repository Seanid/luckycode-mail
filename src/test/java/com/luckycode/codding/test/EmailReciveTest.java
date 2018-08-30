package com.luckycode.codding.test;

import com.luckycode.model.MailConfig;
import com.luckycode.search.AndSearchTermBulider;
import com.luckycode.search.OrSearchTermBulider;
import com.luckycode.search.SearchType;
import com.luckycode.services.EmailService;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.search.SearchTerm;

public class EmailReciveTest {

    @Test
    public void emtrySearch(){

        MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setSMTPInfo(25, "smtp.126.com").setPOP3Info(110, "pop3.126.com").setDebug(true).build();
        EmailService emailService = EmailService.getInstance(mailConfig);
        emailService.receiveEmail(null, (result, mailList) -> {
            Assert.assertEquals(result.getStatus(), 200);
            Assert.assertEquals(mailList.size(), 20);
        });

    }


    @Test
    public void andSearch(){

        MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setPOP3Info(110,"pop3.126.com").setDebug(true).build();
        EmailService emailService= EmailService.getInstance(mailConfig);
        SearchTerm searchTerm=new AndSearchTermBulider().add(SearchType.SUBJECT,"demo").add(SearchType.BODY,"demo").build();
        emailService.receiveEmail(searchTerm,(result,mailList)->{
            Assert.assertEquals(result.getStatus(),200);
            Assert.assertEquals(mailList.get(0).getSubject(),"demo");
        });

    }

    @Test
    public void orSearch(){

        MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setPOP3Info(110,"pop3.126.com").setDebug(true).build();
        EmailService emailService= EmailService.getInstance(mailConfig);
        SearchTerm searchTerm=new OrSearchTermBulider().add(SearchType.SUBJECT,"demo").add(SearchType.SUBJECT,"demo").build();
        emailService.receiveEmail(searchTerm,(result,mailList)->{
            Assert.assertEquals(result.getStatus(),200);
        });

    }


}
