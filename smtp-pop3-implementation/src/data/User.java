package data;

public class User implements java.io.Serializable {
	private String id;
	private String username;
	private Mailbox mailbox;
	private String password;

	public User(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.mailbox = new Mailbox(username);
		this.password = password;
	}

	public boolean checkPassword(String password) {
		return (this.password == password);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
