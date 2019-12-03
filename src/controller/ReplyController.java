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

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReplyController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userlogin");
		MessageView obj = null;
		if (request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			try {
				MailService mailService = new MailService();
				mailService.login("imap.gmail.com", userLogin);
				mailService.setFolder("INBOX");
				Message message = mailService.getMessage(id);

				String subject = "RE: ";
				String from = "";
				if (message.getSubject() != null)
					subject += message.getSubject().toString();
				from = message.getFrom()[0].toString();
				obj = new MessageView(message.getMessageNumber(), subject, from, "", "", "", "", null, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("msgSend", obj);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/reply.jsp");
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
			session.setAttribute("msg", "Gửi thành công!");
			response.sendRedirect(request.getContextPath() + "/sent");
			return;
		} else {
			request.setAttribute("msgErr", "Gửi thất bại!");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/compose.jsp");
			rd.forward(request, response);
		}

	}
}
