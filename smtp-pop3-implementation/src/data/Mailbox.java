package data;

import java.util.ArrayList;

public class Mailbox {

	private ArrayList<Mail> mails = new ArrayList<Mail>();

	public Mailbox(String username) {
		createDatabase(username);
	}

	public String status() {
		// STAT from user's Mailbox
		String output = "+OK " + mails.size() + " " + getSize();
		return output;
	}

	public int getSize() {
		int size = 0;
		for (int i = 0; i < mails.size(); i++) {
			size += mails.get(i).getSize();
		}
		return size;
	}

	public void createDatabase(String username) {

		Mail mail1 = new Mail("The exam", "Luisteacher@gmail.com", username + "@gmail.com",
				"Hi the exam is the 23th of May, via test");
		Mail mail2 = new Mail("The groupwork", "AndresCoworker@gmail.com", username + "@gmail.com",
				"How about do a meeting this weekend? I only can these days");
		Mail mail3 = new Mail("Electric bill", "ENDESA@gmail.com", username + "@gmail.com",
				"You have to pay 5000$ if u want to keep ur internet on");
		Mail mail4 = new Mail("About Covid-19", "USJ@gmail.com", username + "@gmail.com",
				"Get the kids out of the house, we no longer have covid19, now covid20");
		Mail mail5 = new Mail("New hamburguers!", "BurguerQueen@gmail.com", username + "@gmail.com",
				"New burger with papaya flavor");

		mails.add(mail1);
		mails.add(mail2);
		mails.add(mail3);
		mails.add(mail4);
		mails.add(mail5);

	}

	public String list() {
		String output = "+OK \r\n";

		for (int i = 0; i < mails.size(); i++) {
			output += i + " " + mails.get(i).getSize() + "\r\n";
		}

		output = output.substring(0, output.length() - 2); // Remove last line break

		return output;
	}

	public String retrieve(int mailId) {
		String output;

		if (mails.size() > mailId) { // If mail exists
			output = "+OK " + mails.get(mailId).getSize() + " octets \r\n";
			output += mails.get(mailId).toString();
		} else {
			output = "-ERR no such mail " + mailId + " \r\n";
		}

		return output;
	}

	public String delete(int mailId) {
		String output;

		if (mails.size() > mailId) { // If mail exists
			mails.remove(mailId);
			output = "+OK mail " + mailId + " deleted";
		} else {
			output = "-ERR no such mail " + mailId + " \r\n";
		}

		return output;
	}

	public String reset() {
		mails = new ArrayList<Mail>();
		return "+OK mailbox resetted";
	}
}
