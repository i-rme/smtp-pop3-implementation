package data;
import java.util.ArrayList;

public class Mailbox {
	
	private ArrayList<Mail> mails = new ArrayList<Mail>();
	
	public int stat() {
		return mails.size();
	}
	
	public ArrayList<String> list() {
		ArrayList<String> IDs = new ArrayList<String>();	//we will return the IDs of those elements
		for(int i = 0; i<mails.size(); i++)
		{
		   IDs.add(mails.get(i).getId());
		}
		return IDs;
	}
	
	public Mail retrieve(String id) {
		for(int i = 0; i<mails.size(); i++)
		{
			if(mails.get(i).getId() == id ) {
				return (mails.get(i));	//just return the email itself on that position
			}			
		}
		return null;
		 
	}
	
	public void delete(String id) {
		for(int i = 0; i<mails.size(); i++)
		{
			if(mails.get(i).getId() == id) {
				mails.remove(i);
			}			
		}		
	}
}

