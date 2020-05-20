package data;

import java.util.ArrayList;

public class Server implements java.io.Serializable{

	public ArrayList<User> users = new ArrayList<User>();

	public Server() {
		
	}
	
	public Server(boolean createDemoDatabase) {
		
		if(createDemoDatabase) {
			System.out.println("INFO: Creating demo users");
			createDemoDatabase();
		}
		
	}

	public void createDemoDatabase() {

		User user1 = new User("1", "paco", "pack1");
		User user2 = new User("2", "rocio", "rocy21");

		Mail mail1 = new Mail("The exam", "Luisteacher@gmail.com", user1.getUsername() + "@gmail.com",
				"Hi the exam is the 23th of May, via test");
		Mail mail2 = new Mail("The groupwork", "AndresCoworker@gmail.com", user1.getUsername() + "@gmail.com",
				"How about do a meeting this weekend? I only can these days");
		Mail mail3 = new Mail("Electric bill", "ENDESA@gmail.com", user1.getUsername() + "@gmail.com",
				"You have to pay 5000$ if u want to keep ur internet on");
		Mail mail4 = new Mail("About Covid-19", "USJ@gmail.com", user1.getUsername() + "@gmail.com",
				"Get the kids out of the house, we no longer have covid19, now covid20");
		Mail mail5 = new Mail("New hamburguers!", "BurguerQueen@gmail.com", user1.getUsername() + "@gmail.com",
				"New burger with papaya flavor");
		
		Mail mail6 = new Mail("Come and get a cut!", "peluqueros@gmail.com", user2.getUsername() + "@gmail.com",
				"Hi the hairdressing service is open already! Come!");
		Mail mail7 = new Mail("YOU WIN A 1.000.000$ CAR", "spam@gmail.com", user2.getUsername() + "@gmail.com",
				"We only need your credit card credentials!");
		Mail mail8 = new Mail("New game", "steam@gmail.com", user2.getUsername() + "@gmail.com",
				"You can now play for free the new FIFA game because the quarentine!");
		Mail mail9 = new Mail("Send your dish", "cookingcourse@gmail.com", user2.getUsername() + "@gmail.com",
				"Send your dish to evaluate it as soon as posible, please!");
		Mail mail10 = new Mail("Mark of Network & Communications II", "profe@gmail.com", user2.getUsername() + "@gmail.com",
				"Nice job, guys, you get a [_]");
		
		user1.getMailbox().mails.add(mail1);
		user1.getMailbox().mails.add(mail2);
		user1.getMailbox().mails.add(mail3);
		user1.getMailbox().mails.add(mail4);
		user1.getMailbox().mails.add(mail5);
		
		user2.getMailbox().mails.add(mail6);
		user2.getMailbox().mails.add(mail7);
		user2.getMailbox().mails.add(mail8);
		user2.getMailbox().mails.add(mail9);
		user2.getMailbox().mails.add(mail10);
		
		users.add(user1);
		users.add(user2);
		


	}

	public boolean checkUser(String username) {
		
		System.out.println("users.size() " + users.size());
		for (int i = 0; i < users.size(); i++) {
			System.out.println("i " + i);
			if(users.get(i) != null) {
				if (username.equals(users.get(i).getUsername())) {
					return true;
				}
			}else {
				return false;
			}
		}
		return false;
	}
	

	public User getUser(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				System.out.println("SE QUIEN ES");
				return users.get(i);
			}else {
				System.out.println("NO SE QUIEN ES");
			}
		}
		return null;
	}

	public User getUser(String username, String password) {
		for (int i = 0; i < users.size(); i++) {
			if (username.compareToIgnoreCase(users.get(i).getUsername()) == 0) {
				if (password.compareTo(users.get(i).getPassword()) == 0) {
					return users.get(i);
				}
				return null;
			} else {
				// System.out.println("ELSE: " +username + " distinto de " +
				// users.get(i).getUsername());
			}
		}
		return null;
	}

	public String status(User user) {
		// STAT from user's Mailbox
		return user.getMailbox().status();

	}

	public String delete(User user, int mailId) {
		// DELE mailId from user's Mailbox
		return user.getMailbox().delete(mailId);
	}
	
	public String add(User user, Mail mail) {
		// ADD mail (smtp)
		user.getMailbox().mails.add(mail);
		return "250 Requested mail action okay, completed \r\n";
	}

	public String reset(User user) {
		// RSET Mailbox from user
		return user.getMailbox().reset();
	}

	public String retrieve(User user, int mailId) {
		// RETR mailId content from user's Mailbox
		return user.getMailbox().retrieve(mailId);
	}

	public String list(User user) {
		// LIST all Mail from user's Mailbox
		return user.getMailbox().list();
	}

}
