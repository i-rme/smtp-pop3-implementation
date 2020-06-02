package smtpserver;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import data.Mail;
import data.Server;
import data.User;
import utils.CustomThread;
import utils.DatabaseUtils;
import utils.FormatUtils;
import utils.NetworkUtils;

public class SmtpServer extends CustomThread {

	public final int PORT = 25;
	public final String TYPE = "SERVER";
	public final String SERVICE = "SMTP";
	public final String HOSTNAME = SERVICE + TYPE + ".local";
	public final String DATABASE = HOSTNAME + "_database.db";
	public final String ENDPOINT = "127.0.0.1";
	public final int RETRY_TIME = 2;
	public volatile boolean RUNNING = true;
	public volatile boolean CLIENT_CONNECTED = false;
	public boolean DEMO = true;

	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private Server server;

	public static String endpoint_hostname, mail_from, rcpt_to, data;
	public static Mail mail;
	public static boolean end;

	public void run() {

		System.out.println("INFO: Starting the " + SERVICE + " " + TYPE);
		
		server = (Server) DatabaseUtils.Deserialize(DATABASE, DEMO);
		System.out.println("INFO: " + server.getStatus());
		DatabaseUtils.Serialize(server, DATABASE);

		while (RUNNING) {

			serverSocket = NetworkUtils.getServerSocket(PORT);
			socket = NetworkUtils.acceptServerSocket(serverSocket);
			input = NetworkUtils.getInput(socket);
			output = NetworkUtils.getOutput(socket);

			System.out.println("INFO: Connected sucessfully");
			CLIENT_CONNECTED = true;

			NetworkUtils.sendMessage("+OK " + HOSTNAME + " Service Ready", output);

			do {
				String command[] = NetworkUtils.waitMessageRegexArray("((HELO|MAIL FROM|RCPT TO|DATA|QUIT)) ?(.*)", input);

				switch (command[2]) {
					case "HELO":
						NetworkUtils.sendMessage("250 OK", output);
						break;
					case "MAIL FROM":
						mail_from = NetworkUtils.parseMessageRegex("(MAIL FROM:) <([a-zA-Z0-9]+)@(.*)>", command[0]);
						if (mail_from != null) {
							NetworkUtils.sendMessage("250 OK", output);
						} else {
							NetworkUtils.sendMessage("500 Unknown command, expecting MAIL FROM.", output);
						}
						break;
					case "RCPT TO":
						rcpt_to = NetworkUtils.parseMessageRegex("(RCPT TO:) <([a-zA-Z0-9]+)@(.*)>", command[0]);
						if (rcpt_to != null) {
							NetworkUtils.sendMessage("250 OK", output);
						} else {
							NetworkUtils.sendMessage("500 Unknown command, expecting RCPT TO.", output);
						}
						break;
					case "DATA":
						if(mail_from != null && rcpt_to != null) {
							NetworkUtils.sendMessage("354 Start mail input; end with <CRLF>.<CRLF>", output);
							mail = waitMail();
							deliver(mail);
							
							DatabaseUtils.Serialize(server, DATABASE);
							System.out.println("INFO: " + server.getStatus());
						}else {
							NetworkUtils.sendMessage("500 Unknown command, expecting MAIL FROM or RCPT TO.", output);
						}
						break;
					case "QUIT":
						CLIENT_CONNECTED = false;
						NetworkUtils.sendMessage("221 " + HOSTNAME + " Service closing transmission channel", output);
						break;
					case "@error_endpoint_disconnected":
						CLIENT_CONNECTED = false;
						break;
					default:
						//do nothing
						break;
				}
				
			} while (CLIENT_CONNECTED);
			
			System.out.println("INFO: Current client disconnected.");
			
			NetworkUtils.closeServerSocket(serverSocket);
			NetworkUtils.closeSocket(socket);

		} // while

	} // run

	public Mail waitMail() {
		Mail mail = null;
		String imf = "";

		while (mail == null) {
			String line = "";

			line = NetworkUtils.waitMessage(input) + "\r\n";
			imf += line;

			if (line.equals(".\r\n")) {
				mail = FormatUtils.imfToMail(imf);
			}
		}

		return mail;
	}

	private void deliver(Mail mail) {
		String[] recipient = mail.getRecipient().split("@");
		String[] sender = mail.getSender().split("@");
		
		if (HOSTNAME.equals(recipient[1])) { // Belongs to our server

			String username = recipient[0];
			
			
			if(server.checkUser(username)) {
				
				if(server.getUser(username) != null) {
					User user = server.getUser(username);
					NetworkUtils.sendMessage( server.add(user, mail), output );
				}else {
					NetworkUtils.sendMessage("550 "+username+": Recipient address rejected: User unknown in virtual mailbox table.", output);
				}

			}else {
				NetworkUtils.sendMessage("550 "+username+": Recipient address rejected: User unknown in virtual mailbox table.", output);
			}

		} else {
			if(HOSTNAME.equals(sender[1]))
				relay(ENDPOINT, 25, 2, HOSTNAME, mail.getSender(), mail.getRecipient(), mail);
			else
				NetworkUtils.sendMessage("550 Recipient address rejected: User unknown in virtual mailbox table.", output);
		}
	}

	public void relay(String endpoint, int port, int retry_time, String hostname, String sender, String recipient,
			Mail mail) {
		socket = NetworkUtils.getSocket(endpoint, port, retry_time);
		input = NetworkUtils.getInput(socket);
		output = NetworkUtils.getOutput(socket);

		NetworkUtils.waitMessage(input);
		NetworkUtils.sendMessage("HELO " + hostname, output);

		NetworkUtils.waitMessage(input);
		NetworkUtils.sendMessage("MAIL FROM: <" + sender + ">", output);

		NetworkUtils.waitMessage(input);
		NetworkUtils.sendMessage("RCPT TO: <" + recipient + ">", output);

		NetworkUtils.waitMessage(input);
		NetworkUtils.sendMessage("DATA", output);

		NetworkUtils.waitMessage(input);

		Map<String, String> mailElements = FormatUtils.mailToMap(mail);
		String imf_subject = mailElements.get("Subject").toString();
		String imf_sender = mailElements.get("From").toString();
		String imf_recipient = mailElements.get("To").toString();
		String imf_body = mailElements.get("Body").toString();

		NetworkUtils.sendMessage("Subject: " + imf_subject, output);
		NetworkUtils.sendMessage("From: <" + imf_sender + ">", output);
		NetworkUtils.sendMessage("To: <" + imf_recipient + ">", output);
		NetworkUtils.sendMessage("", output);
		NetworkUtils.sendMessage(imf_body, output);
		NetworkUtils.sendMessage(".", output);

		NetworkUtils.waitMessage(input);
		NetworkUtils.sendMessage("QUIT", output);

		NetworkUtils.waitMessage(input);
		NetworkUtils.closeSocket(socket);
	}

}
