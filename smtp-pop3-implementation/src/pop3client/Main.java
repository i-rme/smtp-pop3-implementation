package pop3client;

import java.net.*;
import java.util.concurrent.TimeUnit;
import utils.NetworkUtils;
import java.io.*;

public class Main {

	public static final int 	PORT = 110;
	public static final String 	TYPE = "CLIENT";
	public static final String 	SERVICE = "POP3";
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
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("USER pacoS8", output);
       
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("PASS passS8", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("STAT", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("RETR 1", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("QUIT", output);
        
        NetworkUtils.waitMessage(input);
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage("STAT", output);
        
        System.out.println("AQUI no deberia llegar ya que deberia esperar");
  
        while(true) NetworkUtils.waitMessage(input);
        
        //socket.close();
 
    }   
    
    
}