
public class Mailbox {

	//Define the size of our Mailbox, final because we only declare it one time
	private static final int MAILBOXSIZE = 999;			
	//We create an array for the mails in this mailbox
	private Email arrayMails[] = new Email[MAILBOXSIZE];
	
	public static void main(String[] args) {
		System.out.println(stat());
	    System.out.println(arrayMails[0]);	
	} 
	
	public int stat() {
		return arrayMails.length;
	}
	
	public String list() {
		
	}
	
	public Email returnMailID() {
		
	}
	
	public void deleteID(String ID) {
		
	}
}
