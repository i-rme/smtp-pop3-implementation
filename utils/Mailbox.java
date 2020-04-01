import java.util.ArrayList;

public class Mailbox {

	
	private ArrayList<Email> arrayMails = new ArrayList<Email>();
	
	public int stat() {
		return arrayMails.size();
	}
	
	public ArrayList<String> list() {
		ArrayList<String> IDs = new ArrayList<String>(); //we will return the IDs of those elements
		for(int i = 0; i<arrayMails.size(); i++)
		{
		   IDs.add(arrayMails.get(i).getID());
		}
		return IDs;
	}
	
	public Email returnMail(String ID) {
		for(int i = 0; i<arrayMails.size(); i++)
		{
			if(arrayMails.get(i).getID() == ID ) {
				return (arrayMails.get(i));//just return the email itself on that position
			}			
		}
		return null;
		 
	}
	
	public void deleteMessage(String ID) {
		for(int i = 0; i<arrayMails.size(); i++)
		{
			if(arrayMails.get(i).getID() == ID) {
				arrayMails.remove(i);
			}			
		}		
	}
}

