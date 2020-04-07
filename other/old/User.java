public class User {
	private String usernameID;
	private String mailDirection;
	private Mailbox mailbox;
	private String password;
	
	public User(String usernameID, String mailDirection, Mailbox mailbox, String password) {
		this.usernameID = usernameID;
		this.mailDirection = mailDirection;
		this.password = password;
		this.mailbox = mailbox;
	}
	
	public boolean checkPassword(String password)
	{
		if(this.password==password){
			return true;
		}else{
			return false;
		}
	}
	
	public String getUsernameID() {
		return usernameID;
	}

	public void setUsernameID(String usernameID) {
		this.usernameID = usernameID;
	}

	public String getMailDirection() {
		return mailDirection;
	}

	public void setMailDirection(String mailDirection) {
		this.mailDirection = mailDirection;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}

	public void setMailbox(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
