package controller;

import java.io.File;
import java.io.IOException;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.MailService;
import bean.MessageView;
import bean.SendMail;
import bean.User;

@WebServlet("/compose")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ComposeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String SAVE_DIRECTORY = "uploadDir";

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
		String attachFile ="";
		
		try {
			// Đường dẫn tuyệt đối tới thư mục gốc của web app.
			String appPath = request.getServletContext().getRealPath("");
			appPath = appPath.replace('\\', '/');

			// Thư mục để save file tải lên.
			String fullSavePath = null;
			if (appPath.endsWith("/")) {
				fullSavePath = appPath + SAVE_DIRECTORY;
			} else {
				fullSavePath = appPath + "/" + SAVE_DIRECTORY;
			}

			System.out.println("AA: " + fullSavePath);
			// Tạo thư mục nếu nó không tồn tại.
			File fileSaveDir = new File(fullSavePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}

			// Danh mục các phần đã upload lên (Có thể là nhiều file).
			for (Part part : request.getParts()) {
				String fileName = extractFileName(part);
				if (fileName != null && fileName.length() > 0) {
					String filePath = fullSavePath + File.separator + fileName;
					System.out.println("Write attachment to file: " + filePath);

					// Ghi vào file.
					part.write(filePath);
					attachFile = filePath;
				}
			}
			// Upload thành công.
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msgErr", "Error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/compose.jsp");
			rd.forward(request, response);
		}

		System.out.println("ATACH: " + attachFile);
		
		if (SendMail.SendMailTLS(userLogin, to, cc, bcc, subject, content, "A")) {
			session.setAttribute("msg", "Sent email successfully!");
			response.sendRedirect(request.getContextPath() + "/sent");
			return;
		} else {
			request.setAttribute("msgErr", "Send email failed!");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/compose.jsp");
			rd.forward(request, response);
		}

	
	}

	private String extractFileName(Part part) {
		// form-data; name="file"; filename="C:\file1.zip"
		// form-data; name="file"; filename="C:\Note\file2.zip"
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				// C:\file1.zip
				// C:\Note\file2.zip
				String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
				clientFileName = clientFileName.replace("\\", "/");
				int i = clientFileName.lastIndexOf('/');
				// file1.zip
				// file2.zip
				return clientFileName.substring(i + 1);
			}
		}
		return null;
	}
}
