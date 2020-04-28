package data;
import java.util.ArrayList;
public class Server {

	public ArrayList<User> users = new ArrayList<User>();
	
	public Server() {
		createDatabase();
	}
	
	public void createDatabase(){
	
		User user1 = new User("paco123","paco@gmail.com", "pack1");
		User user2 = new User("rocio123","rocio@gmail.com", "rocy21");
		User user3 = new User("maria123","maria@gmail.com", "11mary2223");
		User user4 = new User("victor123","victor@gmail.com", "vickingtor");
		User user5 = new User("xavi123","xavi@gmail.com.com", "xaviondo77");
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
	
	}
	
	public boolean checkUser(String username){
		for(int i= 0; i<users.size(); i++)
		{
			if(username==(users.get(i).getId()))
			{
				return true;
			}
		}
		return false;
	}
	
	public User getUser(String username, String password){
		return users.get(0);
	}

	public String status(User user) {
		// TODO STAT from user's Mailbox
		return user.getMailbox().status();
		
	}

	public String delete(User user, int mailId) {
		// TODO DELE mailId from user's Mailbox
		return user.getMailbox().delete(mailId);
	}

	public String reset(User user) {
		// TODO RSET Mailbox from user
		return user.getMailbox().reset();
	}

	public String retrieve(User user, int mailId) {
		// TODO RETR mailId content from user's Mailbox
		return user.getMailbox().retrieve(mailId);
	}

	public String list(User user) {
		// TODO LIST all Mail from user's Mailbox
		return user.getMailbox().list();
	}
	
}
