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
	public static final String 	DATABASE = "database.txt";
	public static final String 	ENDPOINT = "127.0.0.1";
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
        TimeUnit.SECONDS.sleep(3);
        NetworkUtils.sendMessage("HELO " + HOSTNAME, output);
       
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(3);
        NetworkUtils.sendMessage("MAIL FROM: <user@"+HOSTNAME+">", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(3);
        NetworkUtils.sendMessage("DATA", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(3);
        NetworkUtils.sendMessage("Subject:<Subject>", output);
        NetworkUtils.sendMessage("From:<email@domain.com>", output);
        NetworkUtils.sendMessage("To:<user1@server.local>,<user2@server.local>", output);
        NetworkUtils.sendMessage(" ", output);
        NetworkUtils.sendMessage("This is the body", output);
        NetworkUtils.sendMessage("\r\n.", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(3);
        NetworkUtils.sendMessage("QUIT", output);
        
        System.out.println("AQUI no deberia llegar ya que deberia esperar");
  
        while(true) NetworkUtils.waitMessage(input);
        
        //socket.close();
 
    }   
    
    
}