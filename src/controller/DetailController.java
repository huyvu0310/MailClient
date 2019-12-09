package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MailService;
import bean.MessageView;
import bean.User;
import util.CreateFolder;

@WebServlet("/detail")
public class DetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DetailController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userlogin");
		String msg = (String) session.getAttribute("msg");
		int id = 0;
		String folder = "";
		boolean isSentMail = false;
		if (request.getParameter("idInbox") != null) {
			id = Integer.parseInt(request.getParameter("idInbox"));
			folder = "INBOX";
		}
		if (request.getParameter("idSent") != null) {
			id = Integer.parseInt(request.getParameter("idSent"));
			folder = "[Gmail]/Sent Mail";
			isSentMail = true;
		}
		if (msg != null) {
			request.setAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		try {
			MailService mailService = new MailService();
			mailService.login("imap.gmail.com", userLogin);
			mailService.setFolder(folder);
			request.setAttribute("mailService", mailService);

			Message message = mailService.getMessage(id);

			String subject = "";
			String from = "";
			String to = "";
			String cc = "";
			String bcc = "";
			String content = "";
			Date date = null;
			String attachFiles= "";
			List<String> listFile = new ArrayList<String>();
			
			if (message.getSubject() != null)
				subject = message.getSubject().toString();
			from = message.getFrom()[0].toString();
			InternetAddress[] toAddress = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
			for (InternetAddress internetAddress : toAddress) {
				to += internetAddress.toString() + ", ";
			}
			if (message.getRecipients(Message.RecipientType.CC) != null) {
				InternetAddress[] ccAddress = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
				for (InternetAddress i : ccAddress) {
					cc += i.toString() + ", ";
				}
			}
			if (message.getRecipients(Message.RecipientType.BCC) != null) {
				InternetAddress[] bccAddress = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);
				for (InternetAddress i : bccAddress) {
					bcc += i.toString() + ", ";
				}
			}
			
			 if (message.getContentType().contains("multipart")) {
                 // content may contain attachments
                 Multipart multiPart = (Multipart) message.getContent();
                 int numberOfParts = multiPart.getCount();
                 for (int partCount = 0; partCount < numberOfParts; partCount++) {
                     MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                     if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                         // this part is attachment
                         String fileName = part.getFileName();
                         attachFiles += fileName + ", ";
                         CreateFolder.createFolderIfNotExists(CreateFolder.saveDirectory + "/");
                         part.saveFile(CreateFolder.saveDirectory + File.separator + fileName);
                     } 
                 }

                 if (attachFiles.length() > 1) {
                     attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                 }
                 
                 listFile = Arrays.stream(attachFiles.split(",")).collect(Collectors.toList());
           
             }
			
			content = mailService.getText(message);
			date = message.getReceivedDate();
			MessageView obj = new MessageView(message.getMessageNumber(), subject, from, to, cc, bcc, content, date,
					true);
			request.setAttribute("msgRead", obj);
			request.setAttribute("content", message.getContentType());
			request.setAttribute("attachFile", listFile);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/inbox");
			e.printStackTrace();
			return;
		}
		
		request.setAttribute("isSentMail", isSentMail);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
