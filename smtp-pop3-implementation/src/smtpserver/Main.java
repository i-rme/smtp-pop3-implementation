package smtpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import utils.NetworkUtils;

public class Main {

	public static final int 	PORT = 25;
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
	
	public static void main(String[] args) throws InterruptedException, IOException {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);

    	serverSocket = NetworkUtils.getServerSocket(PORT);
    	socket = serverSocket.accept();
    	input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);
        
        NetworkUtils.sendMessageCode(220, HOSTNAME + " Service Ready", output);
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(250, "Request mail action okay, completed", output);
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(250, "OK", output);
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(354, "Start mail input; end with <CRLF>.<CRLF>", output);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(250, "OK", output);
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(221, HOSTNAME + " Service closing transmission channel", output);
               
        System.out.println("AQUI no deberia llegar ya que deberia esperar");
  
        while(true) NetworkUtils.waitMessage(input);
        
        //socket.close();
 
    }   
    
}


	//sendMessage(220, SERVER_NAME + " Service Ready");
	//waitClientMessageRegex("(HELO) (([a-zA-Z0-9]+)(\\.([a-zA-Z0-9]+))*)");	// HELO client.example.com\r\n
	//
	//
	//sendMessage(250, "Request mail action okay, completed");
	//waitClientMessageRegex("(MAIL FROM:) <([a-zA-Z0-9]+@(([a-zA-Z0-9]+)(.([a-zA-Z0-9]+))+))>");	// MAIL FROM: <user@example.com>\r\n
	//
	//sendMessage(250, "OK");
	//waitClientMessageRegex("((DATA))");	// DATA\r\n
	//      
	//sendServerMessage(354, "Start mail input; end with <CRLF>.<CRLF>");
	//waitMultilineClientMessage();
	////Subject:<Subject>\r\n
	////From:<email@domain.com>\r\n
	////To:<user1@server.local>,<user2@server.local>\r\n
	//// LINEA VACIA AQUI
	//// Esto es un mensaje de prueba, /nDos lineas..
	//// \r\n.\r\n.
	//
	//sendServerMessage(250, "OK");
	//waitClientMessageRegex("((QUIT))");	// QUIT\r\n
	//
	//sendServerMessage(221, SERVER_NAME + " Service closing transmission channel");