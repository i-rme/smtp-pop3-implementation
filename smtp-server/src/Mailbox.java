import java.util.Arrays;
//import org.apache.commons.lang.ArrayUtils;

public class Mailbox {

	//Define the size of our Mailbox, final because we only declare it one time
	public static final int MAILBOXSIZE = 999;			
	//We create an array for the mails in this mailbox
	public Mail arrayMails[] = new Mail[MAILBOXSIZE];
	
	public int stat() {
		int counter=0;
		for(int i = 0; i<arrayMails.length; i++)
		{
		    if(arrayMails[i]!=null)
		    {
		        counter++;
		    }
		}
		return counter;
	}	
	
	public String[] list() {
		int SIZE = stat(); //check where are elements
		String [] IDs = new String[SIZE]; //we will return the IDs of those elements
		for(int i = 0; i<SIZE; i++)
		{
		   IDs[i]=arrayMails[i].ID;
		}
		return IDs;
	}
	
	public Mail returnMail(String ID) {
		int SIZE = stat(); //check where are elements
		for(int i = 0; i<SIZE; i++)
		{
			if(arrayMails[i].ID==ID) {
				return (arrayMails[i]);//just return the email itself on that position
			}			
		}
		 return null;
	}
	
	public void deleteMessage(String ID) {
		int SIZE = stat(); //check where are elements
		for(int i = 0; i<SIZE; i++)
		{
			if(arrayMails[i].ID==ID) {
				//ArrayUtils.remove(arrayMails, i);
			}			
		}		
	}
}