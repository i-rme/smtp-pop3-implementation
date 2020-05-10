package smtpserver;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import data.Mail;
import utils.CustomThread;
import utils.FormatUtils;
import utils.NetworkUtils;

public class SmtpServer extends CustomThread {

	public final int PORT = 25;
	public final String TYPE = "SERVER";
	public final String SERVICE = "SMTP";
	public final String HOSTNAME = SERVICE + TYPE + ".local";
	public final String DATABASE = HOSTNAME + "_database.txt";
	public final String ENDPOINT = "127.0.0.1";
	public final int RETRY_TIME = 2;
	public volatile boolean RUNNING = true;

	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	public static String endpoint_hostname, mail_from, rcpt_to, data;
	public static Mail mail;
	public static boolean end;

	public void run() {

		System.out.println("INFO: Starting the " + SERVICE + " " + TYPE);

		while (RUNNING) {

			serverSocket = NetworkUtils.getServerSocket(PORT);
			socket = NetworkUtils.acceptServerSocket(serverSocket);
			input = NetworkUtils.getInput(socket);
			output = NetworkUtils.getOutput(socket);

			System.out.println("INFO: Connected sucessfully");

			NetworkUtils.sendMessage("+OK " + HOSTNAME + " Service Ready", output);

			do {
				endpoint_hostname = NetworkUtils.waitMessageRegex("(HELO) (.*)", input);
				if (endpoint_hostname != null) {
					NetworkUtils.sendMessage("250 OK", output);
				} else {
					NetworkUtils.sendMessage("500 Unknown command, expecting HELO.", output);
				}
			} while (endpoint_hostname == null);

			do {
				mail_from = NetworkUtils.waitMessageRegex("(MAIL FROM:) <([a-zA-Z0-9]+)@(.*)>", input);
				if (mail_from != null) {
					NetworkUtils.sendMessage("250 OK", output);
				} else {
					NetworkUtils.sendMessage("500 Unknown command, expecting MAIL FROM.", output);
				}
			} while (mail_from == null);

			do {
				rcpt_to = NetworkUtils.waitMessageRegex("(RCPT TO:) <([a-zA-Z0-9]+)@(.*)>", input);
				if (rcpt_to != null) {
					NetworkUtils.sendMessage("250 OK", output);
				} else {
					NetworkUtils.sendMessage("500 Unknown command, expecting RCPT TO.", output);
				}
			} while (rcpt_to == null);

			do {
				String command[] = NetworkUtils.waitMessageRegexArray("(([a-zA-Z]{4})) ?(.*)?", input);

				switch (command[2]) {

				case "DATA":
					NetworkUtils.sendMessage("354 Start mail input; end with <CRLF>.<CRLF>", output);
					mail = waitMail();
					deliver(mail);
					break;

				case "QUIT":
					NetworkUtils.sendMessage("221 " + HOSTNAME + " Service closing transmission channel", output);
					end = true;
					break;

				case "@error_endpoint_disconnected":
					end = true;
					break;

				default:
					NetworkUtils.sendMessage("500 Unknown command.", output);

				}
			} while (mail == null || end == false);

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
		if (HOSTNAME.equals(recipient[1])) { // Belongs to our server
			// Meterlo en la bandeja de quien sea
		} else {
			relay(ENDPOINT, 25, 2, HOSTNAME, mail.getSender(), mail.getRecipient(), mail);
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
