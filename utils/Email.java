public class Email {
	
	private String subject;
	private String fromSender;
	private String toReceiver;
	private String bodyContent;
	private String ID;
	
	public Email(String subject, String fromSender, String toReceiver, String bodyContent, String ID) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		this.ID = ID;
	}


	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSender() {
		return fromSender;
	}

	public void setSender(String fromSender) {
		this.fromSender = fromSender;
	}
	
	public String getReceiver() {
		return toReceiver;
	}

	public void setReceiver(String toReceiver) {
		this.toReceiver = toReceiver;
	}
	
	public String getBody() {
		return bodyContent;
	}

	public void setBody(String bodyContent) {
		this.bodyContent = bodyContent;
	}


}