package bean;

import java.util.Date;

public class MessageView {
	private int id;
	private String subject;
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String content;
	private Date date;
	private boolean flag;

	public MessageView() {
	}

	public MessageView(int id, String subject, String from, String to, String cc, String bcc, String content, Date date,
			boolean flag) {
		super();
		this.id = id;
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.content = content;
		this.date = date;
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
