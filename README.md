# 对Java Mail二次封装的工具类
基于Javamail上进行了封装，提供了收取邮件和发送邮件更为简单的API。

## V0.2
[*] 支持IMAP/SMTP模式

[*] 修复搜索时分页问题

[*] 服务类拆分

## V0.1

[*] 支持POP3/SMTP模式


# 使用

## 下载

```
    git clone https://github.com/Seanid/luckycode-mail.git
```

## 编译到版本库

```
    mvn install -Dmaven.test.skip=true
```


## 项目导入

```
    <dependency>
        <groupId>com.luckycode</groupId>
        <artifactId>mail</artifactId>
        <version>${version}</version>
    </dependency>
```


## Example

发送邮件
```
    MailConfig mailConfig=new MailConfig.Builder("demo@126.com","demo").setSMTPInfo(25,"smtp.126.com").setDebug(true).build();
    SMTPService sender= SMTPService.getInstance(mailConfig);
    MailContent mailContent=new MailContent.Builder("demo@qq.com","来自sean的问候","你好，hello world~~").build();
    Result result=sender.sendEmail(mailContent);
```

POP3接收邮件

```
    MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setPOP3Info(110, "pop3.126.com").setDebug(true).build();
    POP3Service emailService = POP3Service.getInstance(mailConfig);
    emailService.receiveEmail(null, null, (result, mailList) -> {
     ……
    });
```

POP3搜索邮件

```

    MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setPOP3Info(110, "pop3.126.com").setCacheCount(60).setDebug(true).build();
    POP3Service emailService = POP3Service.getInstance(mailConfig);
    SearchTerm searchTerm = new AndSearchTermBulider().add(SearchType.SUBJECT, "demo1").add(SearchType.BODY, "demo2").build();
    emailService.receiveEmail(searchTerm, null, (result, mailList) -> {
       ……
    });

```

IMAP接收邮件

```
    MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setIMAPInfo(143, "imap.126.com").setDebug(false).build();
    IMAPService emailService = IMAPService.getInstance(mailConfig);
    emailService.receiveEmail(null, "demo", (result, mailList) -> {
        ……
    });
```

IMAP搜索邮件

```
    MailConfig mailConfig = new MailConfig.Builder("demo@126.com", "demo").setIMAPInfo(143, "imap.126.com").setDebug(false).build();
    IMAPService emailService = IMAPService.getInstance(mailConfig);
    SearchTerm searchTerm = new OrSearchTermBulider().add(SearchType.SUBJECT, "demo").add(SearchType.SUBJECT, "demo").build();
    emailService.receiveEmail(searchTerm, "demo", (result, mailList) -> {
        ……
    });
```


## TIPS

国内常用邮件服务商对IMAP的支持
* 126/163：允许通过IMAP获取自定义的文件夹，但是需要开通授权
* QQ ：只允许获取QQ定义的文件夹，自定义无法获取
* coremail ： 支持获取自定义文件夹，无需授权


---

维护邮箱:hxy9104@gmail.com
