package smtpclient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import data.Mail;
import smtpgui.Main;
import utils.CustomThread;
import utils.NetworkUtils;
import utils.Utils;

public class SmtpClient extends CustomThread {

	public final int PORT = 25;
	public final String TYPE = "CLIENT";
	public final String SERVICE = "SMTP";
	public final String HOSTNAME = SERVICE + TYPE + ".local";
	public final String USERNAME = "paco";
	public final String ENDPOINT = "127.0.0.1";
	// public final String ENDPOINT = "smtp.unverified.email";
	// public final String ENDPOINT = "ethereal.email";
	// public final String ENDPOINT = "mailspons.com";
	public final int RETRY_TIME = 2;

	public Socket socket;
	public BufferedReader input;
	public PrintWriter output;
	
	// Used for the GUI
	public boolean GUI_HAS_CONNECTED = false;
	public int GUI_EMAIL_SENT_RESPONSE = 0;

	public boolean GUI_HAS_SENT = false;
	public String GUI_MAIL_FROM;
	public String GUI_MAIL_TO;
	public Mail GUI_MAIL = null;
	
	// Used by the command line
	public void run() {

		System.out.println("INFO: Starting the " + SERVICE + " " + TYPE);

		socket = NetworkUtils.getSocket(ENDPOINT, PORT, RETRY_TIME);
		input = NetworkUtils.getInput(socket);
		output = NetworkUtils.getOutput(socket);

		NetworkUtils.waitMessage(input);
		Utils.sleep(2000);
		NetworkUtils.sendMessage("HELO " + HOSTNAME, output);

		NetworkUtils.waitMessage(input);
		Utils.sleep(2000);
		// NetworkUtils.sendMessage("QUIT", output);
		NetworkUtils.sendMessage("MAIL FROM: <" + USERNAME + "@SMTPSERVER.local>", output);

		NetworkUtils.waitMessage(input);
		// NetworkUtils.waitMessage(input);

		Utils.sleep(2000);
		NetworkUtils.sendMessage("RCPT TO: <rocio@SMTPSERVER.local>", output);

		NetworkUtils.waitMessage(input);
		Utils.sleep(2000);
		NetworkUtils.sendMessage("DATA", output);

		NetworkUtils.waitMessage(input);
		Utils.sleep(2000);
		NetworkUtils.sendMessage("Subject: Example Message", output);
		NetworkUtils.sendMessage("From: <paco@SMTPSERVER.local>", output);
		NetworkUtils.sendMessage("To: <rocio@SMTPSERVER.local>", output);
		NetworkUtils.sendMessage("", output);
		NetworkUtils.sendMessage("This is the body", output);
		NetworkUtils.sendMessage(".", output);

		NetworkUtils.waitMessage(input);
		Utils.sleep(2000);
		NetworkUtils.sendMessage("QUIT", output);

		NetworkUtils.waitMessage(input); // Bye

		while (true)
			NetworkUtils.waitMessage(input);

		// socket.close();

	}
	
	// Used by the GUI
	public void start(String hostname, String endpoint, int port) {
		
		Thread one = new Thread() {
		    public void run() {
		        System.out.println("INFO: Starting the " + SERVICE + " " + TYPE);

				socket = NetworkUtils.getSocket(endpoint, port, RETRY_TIME);
				input = NetworkUtils.getInput(socket);
				output = NetworkUtils.getOutput(socket);

				NetworkUtils.waitMessage(input);
				Utils.sleep(50);
				NetworkUtils.sendMessage("HELO " + hostname, output);
				
				Utils.sleep(50);
				NetworkUtils.waitMessage(input);
				
				GUI_HAS_CONNECTED = true;
					
				while(GUI_HAS_CONNECTED) {
				
					while(GUI_MAIL == null) {
						Utils.sleep(150);
						//System.out.println("Waiting GUI_MAIL");
					}
					GUI_HAS_SENT = false;
					

					NetworkUtils.sendMessage("MAIL FROM: <" + GUI_MAIL_FROM + ">", output);
					
					Utils.sleep(50);
					NetworkUtils.waitMessage(input);
					NetworkUtils.sendMessage("RCPT TO: <" + GUI_MAIL_TO + ">", output);
	
					Utils.sleep(50);
					NetworkUtils.waitMessage(input);
					NetworkUtils.sendMessage("DATA", output);
	
					Utils.sleep(50);
					NetworkUtils.waitMessage(input);
					NetworkUtils.sendMessage(GUI_MAIL.toString(), output);
	
					Utils.sleep(50);
					NetworkUtils.waitMessage(input);
	
					GUI_HAS_SENT = true;
					GUI_MAIL_FROM = "";
					GUI_MAIL_TO = "";
					GUI_MAIL = null;
				
				}
				
				NetworkUtils.sendMessage("QUIT", output);
				NetworkUtils.waitMessage(input); // Bye
				
				
		    }  
		};

		one.start();

		
	}

}
