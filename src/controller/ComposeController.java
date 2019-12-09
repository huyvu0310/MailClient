package controller;

import java.io.IOException;
import javax.mail.Message;
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
import bean.SendMail;
import bean.User;

@WebServlet("/compose")
public class ComposeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ComposeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userlogin");
		MessageView obj = null;
		if (request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				MailService mailService = new MailService();
				mailService.login("imap.gmail.com", userLogin);
				mailService.setFolder("[Gmail]/Drafts");
				Message message = mailService.getMessage(id);

				String subject = "";
				String to = "";
				String cc = "";
				String bcc = "";
				String content = "";
				if (message.getSubject() != null)
					subject = message.getSubject().toString();
				if (message.getRecipients(Message.RecipientType.TO) != null) {
					InternetAddress[] toAddress = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
					for (InternetAddress internetAddress : toAddress) {
						to += internetAddress.toString() + ", ";
					}
				}
				if (message.getRecipients(Message.RecipientType.CC) != null) {
					InternetAddress[] ccAddress = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
					for (InternetAddress internetAddress : ccAddress) {
						cc += internetAddress.toString() + ",";
					}
				}
				if (message.getRecipients(Message.RecipientType.BCC) != null) {
					InternetAddress[] bccAddress = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);
					for (InternetAddress internetAddress : bccAddress) {
						bcc += internetAddress.toString() + ",";
					}
				}
				content = mailService.getText(message);
				obj = new MessageView(message.getMessageNumber(), subject, "", to, cc, bcc, content, null, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("msgSend", obj);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/compose.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userlogin");

		String to = request.getParameter("to");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String cc = request.getParameter("cc");
		String bcc = request.getParameter("bcc");

		if (SendMail.SendMailTLS(userLogin, to, cc, bcc, subject, content)) {
			session.setAttribute("msg", "Sent email successfully!");
			response.sendRedirect(request.getContextPath() + "/sent");
			return;
		} else {
			request.setAttribute("msgErr", "Send email failed!");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/compose.jsp");
			rd.forward(request, response);
		}

	}
}
