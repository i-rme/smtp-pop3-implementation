package smtpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import data.*;
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
	public static Server server;
	public static User user;
	
	public static String username;
	
	public static void main(String[] args) throws InterruptedException, IOException {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);

    	serverSocket = NetworkUtils.getServerSocket(PORT);
    	socket = serverSocket.accept();
    	input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);
        server = new Server();
        NetworkUtils.sendMessageCode(220, SMTPMessage(220), output);
        NetworkUtils.waitMessage(input);
        
        boolean checkUser = false;
        username = NetworkUtils.waitMessageRegex("(HELO) ([a-zA-Z0-9]+)", input);
        checkUser = server.checkUser(username);
        
        if(checkUser) { 
        		NetworkUtils.sendMessageCode(250, SMTPMessage(250), output);
        }
        
        else if(!checkUser) //550 <SP> <user>: Recipient address rejected: User unknown in virtual mailbox table.
        {
        	SMTPCloseConnection(550);
        }
        
        else if(NetworkUtils.checkCommand("HELO", input.readLine())) {
        	SMTPCloseConnection(500);
        }
        else
        {
        	SMTPCloseConnection(501);   
        }
  
        
        //SWITCH FOR MAIL OR QUIT  
        NetworkUtils.waitMessage(input);
        String command = input.readLine();
        
        int code = SMTPCompareCommand(command);
        
        switch (code)
        {
        	case 0: // MAIL TO DO
        		break;
        		
        	case 1: // QUIT
        		NetworkUtils.sendMessageCode(250, SMTPMessage(250), output);// OK
            	SMTPCloseConnection(0); 
        		break;
        		
    		default:
    			SMTPCloseConnection(501);
    			break;
        }
        
        
        
        
        /*
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(354, SMTPMessage(354), output);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(250, SMTPMessage(250), output);
        NetworkUtils.waitMessage(input);
        
        NetworkUtils.sendMessageCode(221, SMTPMessage(221), output);
               
        System.out.println("AQUI no deberia llegar ya que deberia esperar");
  		*/
        while(true) NetworkUtils.waitMessage(input);
        
        //socket.close();
 
    }   
    
	public static String SMTPMessage(int code) {
        switch(code)
        {

        	case 0:
        		return "";
        	case 220:
        		return HOSTNAME + " Service Ready";
        		
        	case 221:
        		return HOSTNAME + " Service closing transmission channel";
    	
        	case 250:
        		return "OK";
        	
        	case 354:
        		return "Start mail input; end with <CRLF>.<CRLF>";
        		
        	case 500:
        		return "Syntax error, command unrecognized";

        	case 501:
        		return "Syntax error in parameters or arguments";

        	case 503:
        		return "Bad sequence of commands";

        	case 550:
        		return username + ": Recipient address rejected: User unknown in virtual mailbox table.";
        				
        	case 554:
        		return "Not valid recipients (Only after DATA command)";

        	default:
        		return "UNDEFINED ERR_CODE";
        }
        
	}
	
	public static int SMTPCompareCommand(String command) {
		System.out.println("DEBUG: "+command);
		
		if(NetworkUtils.checkCommand("MAIL", command)) return 0;

		if(command == "QUIT") return 1;

		else return 99;
		
	}
	
	public static void SMTPCloseConnection(int code) throws IOException
	{
		if(code!=0)
			NetworkUtils.sendMessageCode(code, SMTPMessage(code), output);
		
		NetworkUtils.sendMessageCode(221, SMTPMessage(221), output);
		socket.close();
        serverSocket.close();
        System.exit(0);	
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