package data;
import java.util.ArrayList;

public class Mailbox {
	
	private ArrayList<Mail> mails = new ArrayList<Mail>();
	
	public String status() {
		// TODO STAT from user's Mailbox
		return mails.size()+"";
	}
	
	public String list() {
		// TODO LIST all Mail from user's Mailbox
		ArrayList<String> IDs = new ArrayList<String>();	//we will return the IDs of those elements
		for(int i = 0; i<mails.size(); i++)
		{
		   IDs.add(mails.get(i).getId()+"");
		}
		return IDs.toString();
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

