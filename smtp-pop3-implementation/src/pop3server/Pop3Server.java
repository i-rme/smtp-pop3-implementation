package pop3server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import data.Server;
import data.User;
import utils.CustomThread;
import utils.NetworkUtils;

public class Pop3Server extends CustomThread {

	private final int 		PORT = 110;
	public final String 	TYPE = "SERVER";
	public final String 	SERVICE = "POP3";
	public final String 	HOSTNAME = SERVICE + TYPE + ".local";
	private final String 	DATABASE = HOSTNAME + "_database.txt";
	private final String 	ENDPOINT = "127.0.0.1";
	private final int 		RETRY_TIME = 2;
	public volatile boolean RUNNING = true;
	
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private Server server;
	private User user;
	
	public void run() {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);
    	
    	//Faltan: LIST, DELE, RSET
    	while(RUNNING) {

    	serverSocket = NetworkUtils.getServerSocket(PORT);
    	socket = NetworkUtils.acceptServerSocket(serverSocket);
    	input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);
        
		System.out.println("INFO: Connected sucessfully");
              
        NetworkUtils.sendMessage("+OK Service Ready", output);
        
        server = new Server();
        
                
        do {
                String username = NetworkUtils.waitMessageRegex("(USER) ([a-zA-Z0-9]+)", input);
                NetworkUtils.sendMessage("+OK Password required", output);
                String password = NetworkUtils.waitMessageRegex("(PASS) ([a-zA-Z0-9]+)", input);
                user = server.getUser(username, password);
                if(user != null) {
                        NetworkUtils.sendMessage("+OK User logged in.", output);
                }else {
                        NetworkUtils.sendMessage("-ERR Login Failed.", output);
                }
        }
        while ( user == null );
        
        
        do {
                String command = NetworkUtils.waitMessageRegex("(([a-zA-Z]{4})).*", input);
                switch(command) {
        
                case "STAT":
                    NetworkUtils.sendMessage("+OK 288 69420", output);
                    break;
        
                case "RETR":
                    NetworkUtils.sendMessage("+OK", output);
                    break;
        
                case "LIST":
                    NetworkUtils.sendMessage("+OK", output);
                    NetworkUtils.sendMessage("1 288", output);
                    NetworkUtils.sendMessage("2 144", output);
                    break;
                    
                case "QUIT":
                    NetworkUtils.sendMessage("+OK", output);
                    user = null;
                    break;
        
                case "@error_endpoint_disconnected":
                    user = null;
                    break;
        
                default:
                    NetworkUtils.sendMessage("-ERR Unknown command.", output);
        
                }
        }
        while ( user != null );
        
        //while(true) NetworkUtils.waitMessage(input);
        
        NetworkUtils.closeServerSocket(serverSocket);
        NetworkUtils.closeSocket(socket);
 
    	} // while
    	
	} // run
	
	public void CheckUser() {
		
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public BufferedReader getInput() {
		return input;
	}
	
	public PrintWriter getOutput() {
		return output;
	}
	
}
