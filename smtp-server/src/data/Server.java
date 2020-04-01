package data;
import java.util.ArrayList;
public class Server {

	public ArrayList<User> users = new ArrayList<User>();
	
	public void createDatabase(){
	
		User user1 = new User("paco123","paco@gmail.com", "pack1");
		User user2 = new User("rocio123","rocio@gmail.com", "rocy21");
		User user3 = new User("maria123","maria@gmail.com", "11mary2223");
		User user4 = new User("victor123","victor@gmail.com", "vickingtor");
		User user5 = new User("xavi123","xavi@gmail.com.com", "xaviondo69");
		
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
	
}
