package bean;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bean.User;

public class SendMail {

	public static boolean SendMailTLS(User user,String to,String cc,String bcc,String subject,String content) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user.getUsername(), user.getPassword());
			}
		  });

		try {
			InternetAddress[] myToList = InternetAddress.parse(to);
			InternetAddress[] myBccList = InternetAddress.parse(bcc);
			InternetAddress[] myCcList = InternetAddress.parse(cc);			
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user.getUsername()));
			
			message.setRecipients(Message.RecipientType.TO,myToList);
			message.setRecipients(Message.RecipientType.BCC,myBccList);
			message.setRecipients(Message.RecipientType.CC,myCcList);
			
			
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
