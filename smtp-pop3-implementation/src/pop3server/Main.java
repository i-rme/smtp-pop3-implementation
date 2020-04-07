package pop3server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import data.Server;
import data.User;
import utils.NetworkUtils;

public class Main {

	public static final int 	PORT = 110;
	public static final String 	TYPE = "SERVER";
	public static final String 	SERVICE = "SMTP";
	public static final String 	HOSTNAME = SERVICE + TYPE + ".local";
	public static final String 	DATABASE = HOSTNAME + "_database.txt";
	public static final String 	ENDPOINT = "127.0.0.1";
	public static final int 	RETRY_TIME = 2;
	
	public static ServerSocket serverSocket;
	public static Socket socket;
	public static BufferedReader input;
	public static PrintWriter output;
	
	public static void main(String[] args) throws IOException {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);

    	serverSocket = NetworkUtils.getServerSocket(PORT);
    	socket = serverSocket.accept();
    	input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);
        
		System.out.println("INFO: Connected sucessfully");
        
        User user = null;
        
        NetworkUtils.sendMessage("+OK Service Ready", output);
        
        Server server = new Server();
        
        do {
                String username = NetworkUtils.waitMessageRegex("(USER) ([a-zA-Z0-9]+)", input);
                NetworkUtils.sendMessage("+OK Password required", output);
                String password = NetworkUtils.waitMessageRegex("(PASS) ([a-zA-Z0-9]+)", input);
                user = server.getUser(username, password);
                if(user != null) {
                        NetworkUtils.sendMessage("+OK User logged in.", output);
                }else {
                        NetworkUtils.sendMessage("–ERR Login Failed.", output);
                }
        }
        while ( user == null );
        
        
        do {
                String command = NetworkUtils.waitMessageRegex("(([a-zA-Z]{4})).*", input);
                switch(command) {
        
                case "QUIT":
                        NetworkUtils.sendMessage("+OK", output);
                        user = null;
                    break;
        
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
        
                default:
                          NetworkUtils.sendMessage("–ERR Unknown command.", output);
        
                }
        }
        while ( user != null );
        
        
        while(true) NetworkUtils.waitMessage(input);
        
        //socket.close();
 
    }   
    
}