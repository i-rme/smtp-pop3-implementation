package data;

import java.util.ArrayList;

public class Mailbox implements java.io.Serializable {

	public ArrayList<Mail> mails = new ArrayList<Mail>();

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
