package data;
import java.util.ArrayList;
public class Server {

	public ArrayList<User> users = new ArrayList<User>();
	
	public Server() {
		createDatabase();
	}
	
	public void createDatabase(){
	
		User user1 = new User("1","paco", "pack1");
		User user2 = new User("2","rocio", "rocy21");
		User user3 = new User("3","maria", "11mary2223");
		User user4 = new User("4","victor", "vickingtor");
		User user5 = new User("5","xavi", "xaviondo77");
		
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
		for(int i= 0; i<users.size(); i++)
		{
			if ( username.compareToIgnoreCase( users.get(i).getUsername() ) == 0 )
			{
				if(password.compareTo( users.get(i).getPassword() ) == 0) {
					return users.get(i);
				}
				return null;
			}else {
				//System.out.println("ELSE: " +username + " distinto de " + users.get(i).getUsername());
			}
		}
		return null;
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
