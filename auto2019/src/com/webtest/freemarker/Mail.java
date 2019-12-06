package com.webtest.freemarker;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.webtest.utils.ReadProperties;

public class Mail {
	static String from = ReadProperties.getPropValue("from_mail");
	static String auth_code = ReadProperties.getPropValue("auth_code");
	static String to = ReadProperties.getPropValue("to_mail");

	public static void sendMail(String context) {
		Properties prop = new Properties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.host", "smtp.126.com");
		prop.put("mail.smtp.auth", true);

		// 2、Session对象，设置发件箱、授权码
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, auth_code);
			}
		});
		// 3、Message对象设置，发件人，收件人，主题，正文
		Message message = new MimeMessage(session);
		try {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setFrom(new InternetAddress(from));
			message.setSubject("Hello JavaMail11");
			message.setText(context);
			// 4、使用Transport发送
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
