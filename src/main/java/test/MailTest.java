package test;

import com.lsy.jstorm.utils.MailUtils;

import javax.mail.MessagingException;

/**
 * Created by lisiyu on 2016/11/11.
 */
public class MailTest {
    public static void main(String[] args) throws MessagingException {
//        MailUtils.sendMail("18310051576@163.com","测试邮件");
//        MailUtils.sendMail("13810584823@163.com","说明");
        MailUtils.sendMail("13810584823@163.com","test");
    }
}
