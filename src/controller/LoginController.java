package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MailService;
import bean.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userlogin");
		if (userLogin != null) {
			response.sendRedirect(request.getContextPath() + "/inbox");
			return;
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if ("".equals(username) && "".equals(password)) {
			request.setAttribute("msgErr", "Can not be blank!!");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
			rd.forward(request, response);
			return;
		}
		userLogin = new User(username, password);
		
		try {
			MailService x =new MailService();
			x.login("imap.gmail.com", userLogin);
			x.setFolder("[Gmail]/Drafts");
			int drafts = x.getMessageCount();
			session.setAttribute("numDrafts", drafts);
			x.setFolder("INBOX");
			session.setAttribute("mailService", x);
			session.setAttribute("userlogin", userLogin);
			response.sendRedirect(request.getContextPath()+"/inbox");
			return;
			
		} catch (Exception e) {
			session.setAttribute("userlogin", userLogin);
			request.setAttribute("msgErr", "Sai username,password!!");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
			rd.forward(request, response);
			return;
		}
		
	}

}
