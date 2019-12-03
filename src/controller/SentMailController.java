package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.Message;
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
import util.Define;

@WebServlet("/sent")
public class SentMailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SentMailController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userlogin");
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			request.setAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		try {
			MailService mailService = new MailService();
			mailService.login("imap.gmail.com", userLogin);
			mailService.setFolder("[Gmail]/Sent Mail");
			int messageCount = mailService.getMessageCount();

			int sumPage = (int) Math.ceil((float) messageCount / Define.numRecord);

			int currentPage = 1;

			String pages = request.getParameter("page");
			if (pages != null) {
				try {
					currentPage = Integer.parseInt(pages);
				} catch (NumberFormatException e) {
					response.sendRedirect(request.getContextPath() + "/error");
					return;
				}
			}
			int offset = (currentPage - 1) * Define.numRecord;

			Message[] messages = mailService.getMessages();
			ArrayList<MessageView> al = new ArrayList<>();
			System.out.println((messageCount - offset) + " -" + (messageCount - offset - Define.numRecord));
			int start = messageCount - offset - 1;
			int end = messageCount - offset - Define.numRecord;

			if (end < 0)
				end = 0;

			for (int i = start; i >= end; i--) {
				String subject = "";
				String from = "";
				String to = "";
				Date date = null;
				if (messages[i].getSubject() != null)
					subject = messages[i].getSubject().toString();
				from = messages[i].getFrom()[0].toString();
				to = messages[i].getRecipients(Message.RecipientType.TO)[0].toString();
				date = messages[i].getSentDate();
				MessageView obj = new MessageView(messages[i].getMessageNumber(), subject, from, to, "", "", "", date,
						true);
				al.add(obj);
			}
			request.setAttribute("alMsg", al);
			request.setAttribute("numberMsg", messageCount);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("start", offset + 1);
			if (offset + Define.numRecord <= messageCount) {
				request.setAttribute("end", offset + Define.numRecord);
			} else {
				request.setAttribute("end", messageCount);
			}
			request.setAttribute("sumPage", sumPage);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/login");
			System.out.println("EEE");
			e.printStackTrace();
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sentmail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
