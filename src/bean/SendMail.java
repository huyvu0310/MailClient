package bean;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import bean.User;

public class SendMail {

	public static boolean SendMailTLS(User user, String to, String cc, String bcc, String subject, String content, String attachFile) {
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user.getUsername(), user.getPassword());
			}
		});

		try {
//			InternetAddress[] myToList = InternetAddress.parse(to);
//			InternetAddress[] myBccList = InternetAddress.parse(bcc);
//			InternetAddress[] myCcList = InternetAddress.parse(cc);			
//			
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(user.getUsername()));
//			
//			message.setRecipients(Message.RecipientType.TO,myToList);
//			message.setRecipients(Message.RecipientType.BCC,myBccList);
//			message.setRecipients(Message.RecipientType.CC,myCcList);
//			
//			
//			message.setSubject(subject);
//			message.setText(content);
//
//			Transport.send(message);

			InternetAddress[] myToList = InternetAddress.parse(to);
			InternetAddress[] myBccList = InternetAddress.parse(bcc);
			InternetAddress[] myCcList = InternetAddress.parse(cc);

			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(user.getUsername()));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, myToList);
			message.setRecipients(Message.RecipientType.BCC, myBccList);
			message.setRecipients(Message.RecipientType.CC, myCcList);
			// Set Subject: header field
			message.setSubject(subject);

			Multipart multipart = new MimeMultipart();

			MimeBodyPart attachmentPart = new MimeBodyPart();

			MimeBodyPart textPart = new MimeBodyPart();

			try {

				File f = new File(attachFile);

				attachmentPart.attachFile(f);
				textPart.setText(content);
				multipart.addBodyPart(textPart);
				multipart.addBodyPart(attachmentPart);

				System.out.println("ATAATATA: " + f.getAbsolutePath());
			} catch (IOException e) {

				e.printStackTrace();

			}

			message.setContent(multipart);

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
