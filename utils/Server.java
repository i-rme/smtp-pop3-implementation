import java.util.ArrayList;
public class Server {

	public ArrayList<User> usersServer = new ArrayList<User>();
	
	public void createDatabase(){
		Mailbox mailbox1 = new Mailbox();
		Mailbox mailbox2 = new Mailbox();
		Mailbox mailbox3 = new Mailbox();
		Mailbox mailbox4 = new Mailbox();
		Mailbox mailbox5 = new Mailbox();
		
		User user1 = new User("paco123","paco@gmail.com", mailbox1, "pack1");
		User user2 = new User("rocio123","rocio@gmail.com", mailbox2, "rocy21");
		User user3 = new User("maria123","maria@gmail.com", mailbox3, "11mary2223");
		User user4 = new User("victor123","victor@gmail.com", mailbox4, "vickingtor");
		User user5 = new User("xavi123","xavi@gmail.com.com", mailbox5, "xaviondo69");
		
		usersServer.add(user1);
		usersServer.add(user2);
		usersServer.add(user3);
		usersServer.add(user4);
		usersServer.add(user5);
	
	}
	
	public boolean checkUser(String username){
		for(int i= 0; i<usersServer.size(); i++)
		{
			if(username==(usersServer.get(i).getUsernameID()))
			{
				return true;
			}
		}
		return false;
	}
	
}
