package data;
public class Mail {
	
	private String subject;
	private String sender;
	private String recipient;
	private String body;
	private String id;
	
	public Mail(String subject, String sender, String recipient, String body, String id) {
		this.subject = subject;
		this.sender = sender;
		this.recipient = recipient;
		this.body = body;
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getId() {
		return id;
	}


}
