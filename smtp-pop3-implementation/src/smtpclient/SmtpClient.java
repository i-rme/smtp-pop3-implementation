package smtpclient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import utils.CustomThread;
import utils.NetworkUtils;
import utils.Utils;

public class SmtpClient extends CustomThread{
	
	public final int 		PORT = 25;
	public final String 	TYPE = "CLIENT";
	public final String 	SERVICE = "SMTP";
	public final String 	HOSTNAME = SERVICE + TYPE + ".local";
	public final String  	USERNAME = "paco";
	//public final String 	DATABASE = "database.txt";
	public final String 	ENDPOINT = "127.0.0.1";
	//public final String 	ENDPOINT = "smtp.unverified.email";
	//public final String 	ENDPOINT = "ethereal.email";
	//public final String 	ENDPOINT = "mailspons.com";
	public final int 		RETRY_TIME = 2;
	
	public Socket socket;
	public BufferedReader input;
	public PrintWriter output;
	
	public void run() {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);

    	socket = NetworkUtils.getSocket(ENDPOINT, PORT, RETRY_TIME);
        input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);

        NetworkUtils.waitMessage(input);
        Utils.sleep(2000);
        NetworkUtils.sendMessage("HELO " + HOSTNAME, output);

        NetworkUtils.waitMessage(input);
        Utils.sleep(2000);
        //NetworkUtils.sendMessage("QUIT", output);
        NetworkUtils.sendMessage("MAIL FROM: <"+USERNAME+"@"+HOSTNAME+">", output);
        
        NetworkUtils.waitMessage(input);
        //NetworkUtils.waitMessage(input);
        
        Utils.sleep(2000);
        NetworkUtils.sendMessage("RCPT TO: <user2@"+ENDPOINT+">", output);
        
        NetworkUtils.waitMessage(input);
        Utils.sleep(2000);
        NetworkUtils.sendMessage("DATA", output);
        
        NetworkUtils.waitMessage(input);
        Utils.sleep(2000);
        NetworkUtils.sendMessage("Subject: Example Message", output);
        NetworkUtils.sendMessage("From: <user@SMTPCLIENT.local>", output);
        NetworkUtils.sendMessage("To: <user2@SMTPCLIENT.local>", output);
        NetworkUtils.sendMessage("", output);
        NetworkUtils.sendMessage("This is the body", output);
        NetworkUtils.sendMessage(".", output);
        
        NetworkUtils.waitMessage(input);
        Utils.sleep(2000);
        NetworkUtils.sendMessage("QUIT", output);
        
        NetworkUtils.waitMessage(input);	//Bye
        
        System.out.println("AQUI no deberia llegar ya que deberia esperar");
  
        while(true) NetworkUtils.waitMessage(input);
        
        //socket.close();
 
    }   

}
