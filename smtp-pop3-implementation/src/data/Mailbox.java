package data;
import java.util.ArrayList;

public class Mailbox {
	
	private ArrayList<Mail> mails = new ArrayList<Mail>();
	
	//crear constructor mailbox arraylist de unos 5 mails aniadirlso 
	public Mailbox(String username) {
		createDatabase(username);
	}
	
	public String status() {
		// TODO STAT from user's Mailbox
		String output = "+OK " + mails.size() + " " + mails.get(0).getBody().length() + "\r\n";
		return output;


	}
	
	public void createDatabase(String username){
		
		Mail mail1 = new Mail("The exam", "Luisteacher@gmail.com", username+"@gmail.com","Hi the exam is the 23th of May, via test",1);
		Mail mail2 = new Mail("The groupwork", "AndresCoworker@gmail.com", username+"@gmail.com","How about do a meeting this weekend? I only can these days",2);
		Mail mail3 = new Mail("Electric bill", "ENDESA@gmail.com", username+"@gmail.com","You have to pay 5000$ if u want to keep ur internet on",3);
		Mail mail4 = new Mail("About Covid-19", "USJ@gmail.com", username+"@gmail.com","Get the kids out of the house, we no longer have covid19, now covid20",4);
		Mail mail5 = new Mail("New hamburguers!", "BurguerQueen@gmail.com", username+"@gmail.com","New burger with papaya flavor",5);
		
		mails.add(mail1);
		mails.add(mail2);
		mails.add(mail3);
		mails.add(mail4);
		mails.add(mail5);
	
	}
	
	public String list() {
		// TODO LIST all Mail from user's Mailbox
		//ArrayList<String> IDs = new ArrayList<String>();	//we will return the IDs of those elements
		String output = "+OK \r\n";
		
		for(int i = 0; i<mails.size(); i++){
		   //IDs.add(mails.get(i).getId()+"");
			output += mails.get(i).getId() + " " + mails.get(i).getBody().length() + "\r\n";
		   
		}
		return output;
	}
	
	public String retrieve(int id) {
		// TODO RETR mailId content from user's Mailbox
		for(int i = 0; i<mails.size(); i++)
		{
			if(mails.get(i).getId() == id ) {
				return (mails.get(i).toString());	//just return the email itself on that position
			}			
		}
		return null;
		 
	}
	
	public String delete(int mailId) {
		// TODO DELE mailId from user's Mailbox
		for(int i = 0; i<mails.size(); i++)
		{
			if(mails.get(i).getId() == mailId) {
				mails.remove(i);
			}			
		}		
		return null;
	}

	public String reset() {
		// TODO RSET Mailbox from user
		return null;
	}
}

