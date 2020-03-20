import java.io.Serializable;

public class Packet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sender;
	private String receiver;
	private String text;

	
	public Packet(String sender, String receiver, String text) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
	}
	
	
	
	
	

	public String getSender() {
		return sender;
	}






	public void setSender(String sender) {
		this.sender = sender;
	}






	public String getReceiver() {
		return receiver;
	}






	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}






	public String getText() {
		return text;
	}






	public void setText(String text) {
		this.text = text;
	}






	@Override
	public String toString() {
		return "Sender:" + sender + "\nReceiver: " + receiver + "\nMessage: " + text;
	}





	
}
