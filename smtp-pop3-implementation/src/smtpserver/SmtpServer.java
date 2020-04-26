package smtpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import data.Mail;
import data.Server;
import data.User;
import utils.CustomThread;
import utils.FormatUtils;
import utils.NetworkUtils;

public class SmtpServer extends CustomThread {
	
	public final int 	PORT = 25;
	public final String 	TYPE = "SERVER";
	public final String 	SERVICE = "SMTP";
	public final String 	HOSTNAME = SERVICE + TYPE + ".local";
	public final String 	DATABASE = HOSTNAME + "_database.txt";
	public final String 	ENDPOINT = "127.0.0.1";
	public final int 	RETRY_TIME = 2;
	
	public ServerSocket serverSocket;
	public Socket socket;
	public BufferedReader input;
	public PrintWriter output;
	public Server server;
	public User user;
	
	public static String username;
	
	public void run() {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);

    	serverSocket = NetworkUtils.getServerSocket(PORT);
    	try {
			socket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        } else
			try {
				if(NetworkUtils.checkCommand("HELO", input.readLine())) {
					SMTPCloseConnection(500);
				}
				else
				{
					SMTPCloseConnection(501);   
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  
        
        //SWITCH FOR MAIL OR QUIT  
        NetworkUtils.waitMessage(input);
        String command = null;
		try {
			command = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
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
	
	public String SMTPMessage(int code) {
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
	
	public int SMTPCompareCommand(String command) {
		System.out.println("DEBUG: "+command);
		
		if(NetworkUtils.checkCommand("MAIL", command)) return 0;

		if(command == "QUIT") return 1;

		else return 99;
		
	}
	
	public void SMTPCloseConnection(int code) {
		if(code!=0)
			NetworkUtils.sendMessageCode(code, SMTPMessage(code), output);
		
		NetworkUtils.sendMessageCode(221, SMTPMessage(221), output);
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.exit(0);	
	}
	
	public void relay(String endpoint, int port, int retry_time, String hostname, String sender, String recipient, Mail mail) {
    	socket = NetworkUtils.getSocket(endpoint, port, retry_time);
        input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);

        NetworkUtils.waitMessage(input);
        NetworkUtils.sendMessage("HELO " + hostname, output);

        NetworkUtils.waitMessage(input);
        NetworkUtils.sendMessage("MAIL FROM: <"+sender+">", output);
        
        NetworkUtils.waitMessage(input);
        NetworkUtils.sendMessage("RCPT TO: <"+recipient+">", output);
        
        NetworkUtils.waitMessage(input);
        NetworkUtils.sendMessage("DATA", output);
        
        NetworkUtils.waitMessage(input);
        
        Map<String, String> mailElements = FormatUtils.mailToMap(mail);
		String imf_subject = mailElements.get("Subject").toString(); 
		String imf_sender = mailElements.get("From").toString(); 
		String imf_recipient = mailElements.get("To").toString(); 
		String imf_body = mailElements.get("Body").toString(); 
        
        NetworkUtils.sendMessage("Subject: "+imf_subject, output);
        NetworkUtils.sendMessage("From: <"+imf_sender+">", output);
        NetworkUtils.sendMessage("To: <"+imf_recipient+">", output);
        NetworkUtils.sendMessage("", output);
        NetworkUtils.sendMessage(imf_body, output);
        NetworkUtils.sendMessage(".", output);
        
        NetworkUtils.waitMessage(input);
        NetworkUtils.sendMessage("QUIT", output);
        
        NetworkUtils.waitMessage(input);        
        NetworkUtils.closeSocket(socket);
	}

}