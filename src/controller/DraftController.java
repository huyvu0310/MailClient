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

@WebServlet("/draft")
public class DraftController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DraftController() {
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
			mailService.setFolder("[Gmail]/Drafts");
			int draftCount = mailService.getMessageCount();

			int sumPage = (int) Math.ceil((float) draftCount / Define.numRecord);

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
			System.out.println((draftCount - offset) + " -" + (draftCount - offset - Define.numRecord));
			int start = draftCount - offset - 1;
			int end = draftCount - offset - Define.numRecord;

			if (end < 0)
				end = 0;

			for (int i = start; i >= end; i--) {
				String subject = "";
				String content = "";
				Date date = null;
				if (messages[i].getSubject() != null)
					subject = messages[i].getSubject().toString();
				content = mailService.getText(messages[i]);
				date = messages[i].getReceivedDate();
				MessageView obj = new MessageView(messages[i].getMessageNumber(), subject, "", "", "", "", content,
						date, true);
				al.add(obj);
			}
			request.setAttribute("alMsg", al);
			session.setAttribute("numberDraft", draftCount);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("start", offset + 1);
			if (offset + Define.numRecord <= draftCount) {
				request.setAttribute("end", offset + Define.numRecord);
			} else {
				request.setAttribute("end", draftCount);
			}
			request.setAttribute("sumPage", sumPage);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/login");
			System.out.println("EEE");
			e.printStackTrace();
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/draft.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
