package com.lsy.jstorm.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

/**
 * 邮件的工具类
 * @author Administrator
 */
public class MailUtils {
	
	/**
	 * 发送邮件
	 * @param email
	 * @param emailMsg
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session
		
		Properties props = new Properties();
		// 发送邮件的协议
		props.setProperty("mail.transport.protocol", "SMTP");
		// 发送邮件服务器主机地址
		props.setProperty("mail.host", "smtp.163.com");
		// 指定验证为true
		props.setProperty("mail.smtp.auth", "true");
		
		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// 修改账号和密码
				return new PasswordAuthentication("lhccc", "ddd");
			}
		};
		
		// 代表连接
		Session session = Session.getInstance(props, auth);
		
		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		// 设置发件人（系统的账号）
		message.setFrom(new InternetAddress("lhccc@163.com"));
		
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); 
		
		// 设置邮件的主题
		message.setSubject("IDS告警邮件");
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");
		
		// 设置邮件的正文
		message.setContent(emailMsg, "text/html;charset=utf-8");
		
		// 3.创建 Transport用于将邮件发送
		Transport.send(message, message.getAllRecipients());
	}
}
