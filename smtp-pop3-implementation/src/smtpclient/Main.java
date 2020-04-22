package smtpclient;

import java.net.*;
import java.util.concurrent.TimeUnit;
import utils.NetworkUtils;
import java.io.*;

public class Main {

	public static final int 	PORT = 25;
	public static final String 	TYPE = "CLIENT";
	public static final String 	SERVICE = "SMTP";
	public static final String 	HOSTNAME = SERVICE + TYPE + ".local";
	public static final String  USERNAME = "paco123";
	public static final String 	DATABASE = "database.txt";
	//public static final String 	ENDPOINT = "127.0.0.1";
	public static final String 	ENDPOINT = "smtp.unverified.email";
	//public static final String 	ENDPOINT = "ethereal.email";
	//public static final String 	ENDPOINT = "mailspons.com";
	public static final int 	RETRY_TIME = 2;
	
	public static Socket socket;
	public static BufferedReader input;
	public static PrintWriter output;
	
	public static void main(String[] args) throws InterruptedException {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);

    	socket = NetworkUtils.getSocket(ENDPOINT, PORT, RETRY_TIME);
        input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);

        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("HELO " + USERNAME, output);

        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("QUIT", output);
        //NetworkUtils.sendMessage("MAIL FROM: <user@"+HOSTNAME+">", output);
        
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("RCPT TO: <user2@"+ENDPOINT+">", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("DATA", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("Subject: Example Message", output);
        NetworkUtils.sendMessage("From: <user@SMTPCLIENT.local>", output);
        NetworkUtils.sendMessage("To: <user2@SMTPCLIENT.local>", output);
        NetworkUtils.sendMessage("", output);
        NetworkUtils.sendMessage("This is the body", output);
        NetworkUtils.sendMessage(".", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(3);
        NetworkUtils.sendMessage("QUIT", output);
        
        NetworkUtils.waitMessage(input);	//Bye
        
        System.out.println("AQUI no deberia llegar ya que deberia esperar");
  
        while(true) NetworkUtils.waitMessage(input);
        
        //socket.close();
 
    }   
    
    
}