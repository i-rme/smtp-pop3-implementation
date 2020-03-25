
public class Mailbox {

	public static void main(String[] args) {
		
		//Define the size of our Mailbox, final because we only declare it one time
		private static final int MAILBOXSIZE = 0;
		
		//We create an array for the mails in this mailbox
		Email arrayMails[] = new Email[MAILBOXSIZE];
	} 
	
	public int stat() {
		return arrayMails.length;
	}
}
