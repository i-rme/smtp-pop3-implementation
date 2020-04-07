package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkUtils {
	
	public static ServerSocket getServerSocket(int port){
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Socket getSocket(String endpoint, int port, int retry_time){
        while (true) {
            try {
            	TimeUnit.SECONDS.sleep(retry_time);
            	System.out.println("INFO: Trying to connect to the server...");
            	Socket socket = new Socket(endpoint, port);
            	System.out.println("INFO: Connected sucessfully");
            	return socket;
            } catch (Exception e) {}
        }    
	}
	
	public static BufferedReader getInput(Socket socket){
		try {
			return new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static PrintWriter getOutput(Socket socket){
		try {
			return new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void sendMessageCode(int code, String comment, PrintWriter output){
        String message = code + " " + comment;
        
        sendMessage(message, output);
	}
	
	public static void sendMessage(String comment, PrintWriter output){
        String message = comment + "\r\n";
        
        ConsoleUtils.logServer(message);
        output.println(message);
	}
	
	
	public static String waitMessage(BufferedReader input){
		String message;
	    do {
	    	try {
				message = input.readLine();
			} catch (IOException e) {
				message = "error:ioexception_waitClientMessage";
				e.printStackTrace();
			}
	    	
	    	try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
	    }
	    while ( message.length() == 0 );
		
        ConsoleUtils.logClient(message + "\r\n");
	    return message;
	}
	
	public static String waitMessageRegex(String pattern, BufferedReader input){
        String message = waitMessage(input);
        
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(message);

        if (m.matches()) {
        	return m.group(2);	// 0: whole matched expression, 1: first expression in brackets, 2: second exp, ...
        }else {
        	System.out.println("Error: Incorrect message for client");
        	return null;
        }
        
	}
	
}
