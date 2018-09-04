package com.luckycode.interfaces;

import com.luckycode.model.InboxMail;
import com.luckycode.model.MailContent;
import com.luckycode.model.Result;

import java.util.List;

/**
 * Created by Sean on 2018/08/23.
 * 获取邮件事件处理接口
 */
public interface PostReceiveHandler {
    void process(Result result, List<InboxMail> mailList) ;


}
