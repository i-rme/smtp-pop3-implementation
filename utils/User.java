public class User {

	private String usernameID;
	private String mailDirection;
	private String mailbox;
	private String password;
	
	public User(String usernameID, String mailDirection, String mailbox, String password) {
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		this.ID = ID;
		this.password = password;
	}
	
	public bool checkPassword(String password)
	{
		if(this.password==password){
			return true;
		}else{
			return false;
		}
	}
}
