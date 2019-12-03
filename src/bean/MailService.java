package bean;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class MailService {

	private Session session;
	private Store store;
	private Folder folder;
	private boolean textIsHtml = false;

	// hardcoding protocol and the folder
	// it can be parameterized and enhanced as required
	private String protocol = "imaps";

	public MailService() {}

	public boolean isLoggedIn() {
		return store.isConnected();
	}

	public void login(String host, User user)
			throws Exception {
		 Properties properties = new Properties();

	     properties.put("mail.imap.host", host);
	     properties.put("mail.imap.port", "995");
	     properties.put("mail.imap.starttls.enable", "true");
		
	     session = Session.getDefaultInstance(properties);
	     store = session.getStore(protocol);
	     store.connect(host, user.getUsername(),user.getPassword());        
	}
	
	public void setFolder (String file) throws Exception {
		if (!"".equals(file)) {
	    	 folder = store.getFolder(file);
		     folder.open(Folder.READ_WRITE);	
	     } 
	}

	public void logout() throws MessagingException {
		folder.close(false);
		store.close();
		store = null;
		session = null;
	}

	public int getMessageCount() {
		int messageCount = 0;
		try {
			messageCount = folder.getMessageCount();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		return messageCount;
	}
	
	public int getUnreadMessageCount() {
		int unreadMessageCount = 0;
		try {
			unreadMessageCount = folder.getUnreadMessageCount();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		return unreadMessageCount;
	}

	public Message[] getMessages() throws MessagingException {
		return folder.getMessages();
	}
	
	public Message getMessage (int id) throws MessagingException {
		return folder.getMessage(id);
	}
	
	
	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public Message[] getUnreadMessages() throws MessagingException {
		Flags seen = new Flags(Flags.Flag.SEEN);
	    FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
	    Message messages[] = folder.search(unseenFlagTerm); 
	    return messages;
	}
	
	
	
	public String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			textIsHtml = p.isMimeType("text/html");
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}
		return null;
	}

}