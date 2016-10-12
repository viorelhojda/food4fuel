package com.atraxo.f4f.service.email;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.atraxo.f4f.util.SettingsBundleUtils;

public class WsClientEmail {

	private static final Logger LOGGER = Logger.getLogger(WsClientEmail.class);
	
	/**
	 * @param to
	 * @param from
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param content
	 */
	public static void sendMail(String to, String from,String cc,String  bcc,String  subject,String  content){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendMail()");
		}
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("SENDING EMAIL at "+Calendar.getInstance().getTime().toLocaleString());
		System.out.println("to:"+to);
		System.out.println("from:"+from);
		System.out.println("cc:"+cc);
		System.out.println("bcc:"+bcc);
		System.out.println("subject:"+subject);
		System.out.println("------------------------------------------------------------------------");
		
//		final String username = "mailmerge";
//		final String password = "Tma1lMerge_1234!";
//		final String sender = "mailmerge@fuelplus.com";
//		
//		Properties props = new Properties();
//		props.put("mail.smtp.starttls.enable", "false");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.host", "mail.fuelplus.com");
//		props.put("mail.smtp.port", "2525");
		
		
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", SettingsBundleUtils.getSetting(SettingsBundleUtils.EMAIL_SERVER_SMTP_START_TLS));
		props.put("mail.smtp.auth", SettingsBundleUtils.getSetting(SettingsBundleUtils.EMAIL_SERVER_SMTP_AUTH));
		props.put("mail.smtp.host", SettingsBundleUtils.getSetting(SettingsBundleUtils.EMAIL_SERVER_SMTP_HOST));
		props.put("mail.smtp.port", SettingsBundleUtils.getSetting(SettingsBundleUtils.EMAIL_SERVER_SMTP_PORT));
		
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SettingsBundleUtils.getSetting(SettingsBundleUtils.EMAIL_SERVER_USER), 
						SettingsBundleUtils.getSetting(SettingsBundleUtils.EMAIL_SERVER_PASSWORD));
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SettingsBundleUtils.getSetting(SettingsBundleUtils.EMAIL_SERVER_SENDER)));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(bcc));
			message.setSubject(subject);
			message.setContent(content, "text/html; charset=utf-8");
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ERROR:could not send mail!",e);
		}
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendMail()");
		}
		
	}
	
}
