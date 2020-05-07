package data;
public class Mail {
	
	private String subject;
	private String sender;
	private String recipient;
	private String body;
	
	public Mail(String subject, String sender, String recipient, String body) {
		this.subject = subject;
		this.sender = sender;
		this.recipient = recipient;
		this.body = body;
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
	
    @Override
    public String toString() { 
		String imf =
				"Subject: "+subject+"\r\n"
				+ "From: <"+sender+">\r\n"
				+ "To: <"+recipient+">\r\n"
				+ "\r\n"
				+ ""+body+"\r\n"
				+ ".\r\n";
        return imf;
    } 


}
