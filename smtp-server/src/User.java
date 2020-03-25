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
}
