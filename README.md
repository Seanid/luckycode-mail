# 对Java Mail二次封装的工具类

---
项目在Javamail的基础上进行了封装，提供了收取邮件和发送邮件更为简单的API。不需要关注Javamail内部内容的构建，只需要像普通对象一样构建一个邮件内容发送即可。

## Example

发送邮件
```
MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setDebug(true).build();
EmailService sender=EmailService.getInstance(mailConfig);
MailContent mailContent=new MailContent.Builder("demo@qq.com","来自hxy的邮件","你好，hello world").build();
Result result=sender.sendEmail(mailContent);
Assert.assertEquals(result.getStatus(),200);

```

接收邮件

```
MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setPOP3Info(110,"pop3.126.com").setDebug(true).build();
EmailService emailService= EmailService.getInstance(mailConfig);
emailService.receiveEmail(null,(result,mailList)->{
    Assert.assertEquals(result.getStatus(),200);
    Assert.assertEquals(mailList.size(),20);
});
```

搜索邮件

```

MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setPOP3Info(110,"pop3.126.com").setDebug(true).build();
EmailService emailService= EmailService.getInstance(mailConfig);
SearchTerm searchTerm=new AndSearchTermBulider().add(SearchType.SUBJECT,"demo").add(SearchType.BODY,"demo").build();
emailService.receiveEmail(searchTerm,(result,mailList)->{
    Assert.assertEquals(result.getStatus(),200);
    Assert.assertEquals(mailList.get(0).getSubject(),"demo");
});

```

维护邮箱:hxy9104@gmail.com