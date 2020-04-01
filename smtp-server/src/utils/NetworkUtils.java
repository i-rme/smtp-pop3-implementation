package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class NetworkUtils {

	public static void sendMessage(int code, String comment, PrintWriter output){
        String message = code + " " + comment + "\r\n";
        
        output.println(message);
	}
	
	
	public static String waitMessage(BufferedReader input){
		String message;
		int i = 0;
	    do {
	    	try {
	    		i++;
				message = input.readLine();
			} catch (IOException e) {
				message = "error:ioexception_waitClientMessage";
				e.printStackTrace();
			}
	    	
	    	try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
	    }
	    while ( message.length() == 0 );
		
	    return message;
	}
	
}
