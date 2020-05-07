package smtpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import data.Mail;
import data.Server;
import data.User;
import utils.ConsoleUtils;
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
	public String message;
	public String userHostname;
	
	public static String username;
	
	public void run() {

    	System.out.println("INFO: Starting the "+SERVICE+" "+TYPE);

    	serverSocket = NetworkUtils.getServerSocket(PORT);
    	socket = NetworkUtils.acceptServerSocket(serverSocket);
    	
    	input = NetworkUtils.getInput(socket);
        output = NetworkUtils.getOutput(socket);
        server = new Server();
        NetworkUtils.sendMessageCode(220, SMTPMessage(220), output);
        message = NetworkUtils.waitMessage(input);
        //Let's check the first command we receive is a HELO command, if not, we close the socket.
        if(!NetworkUtils.checkCommand("HELO", message))
        	SMTPCloseConnection(500);
        
        userHostname = message.split("\\s")[1];
        NetworkUtils.sendMessageCode(250, SMTPMessage(250), output);
        /*
        boolean checkUser = false;
        username = NetworkUtils.parseMessageRegex("(HELO) ([a-zA-Z0-9]+)", message);
        checkUser = server.checkUser(username);
        
        if(checkUser) { 
        		NetworkUtils.sendMessageCode(250, SMTPMessage(250), output);
        }
        
        else //550 <SP> <user>: Recipient address rejected: User unknown in virtual mailbox table.
        {
        	SMTPCloseConnection(550);
        }*/
        
        
        //SWITCH FOR MAIL OR QUIT  
        message = NetworkUtils.waitMessage(input);
        int code = SMTPCompareCommand(message);
        
        switch (code)
        {
        	case 0: 
        		SMTPCreateEmail();
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
	
	public int SMTPCompareCommand(String input) {
		
		if(NetworkUtils.checkCommand("MAIL", input)) return 0;

		else if(NetworkUtils.checkCommand("QUIT", input)) return 1;

		else return 99;
		
	}
	
	public void SMTPCloseConnection(int code) {
		if(code!=0)
			NetworkUtils.sendMessageCode(code, SMTPMessage(code), output);
		
		NetworkUtils.sendMessageCode(221, SMTPMessage(221), output);
		NetworkUtils.closeServerSocket(serverSocket);
		NetworkUtils.closeSocket(socket);
        System.exit(0);	
	}
	
	public void SMTPCreateEmail()
	{
		String endpoint = null;
		int port = PORT;
		int retry_time = 100;
		
		String hostname = userHostname;
		String sender = "";
		String recipient = "";
		
		String subject = "";
		String from_mail = "";
		String to_mail = "";
		String body = "";

		ArrayList<String> bodyList = new ArrayList<String>();
		Mail mail = null;

		
		
		NetworkUtils.sendMessageCode(250, SMTPMessage(250), output);// OK
		String from[] = message.split("\\s");
		sender = from[2].replaceAll("[<>]", "");
		
		//I don't know how this regex works and its syntax, needs to be checked. 
		message = NetworkUtils.waitMessageRegex("RCPT TO: <([a-zA-Z0-9]+)@([a-zA-Z0-9]+)>",input);		
		String rcpt[] = message.split("\\s");
		recipient = from[3].replaceAll("[<>]", "");
		
		message = NetworkUtils.waitMessage(input);
		if(!message.equals("DATA"))
			SMTPCloseConnection(500);
		
		
		//SUBJECT
		message = NetworkUtils.waitMessageRegex("Subject: ([a-zA-Z0-9]+)", input);
		if(message == null)
			SMTPCloseConnection(501);
		String[] subj = message.split(" ", 2);
		subject = subj[1];
		
		
		//FROM
		message = NetworkUtils.waitMessageRegex("From: <([a-zA-Z0-9]+)@([a-zA-Z0-9]+)>", input);
		if(message == null)
			SMTPCloseConnection(501);
		String[] fromMail = message.split(" ", 2);
		from_mail = fromMail[1];
		
		
		//TO
		message = NetworkUtils.waitMessageRegex("To: <([a-zA-Z0-9]+)@([a-zA-Z0-9]+)>", input);
		if(message == null)
			SMTPCloseConnection(501);
		String[] toMail = message.split(" ", 2);
		to_mail = toMail[1];
		
		
		
		int endController = 0;
		String bodyStr = "";
		boolean nextDot = false;
		do {
			bodyStr = NetworkUtils.waitMessage(input);
			bodyList.add(bodyStr);
			
			if(bodyStr.indexOf("\r\n") == bodyStr.length()-1 && !nextDot){
				endController++;
				nextDot = true;
			}
				
			else if(nextDot && bodyStr.equals("." + "\r\n")){
				endController++;
			}
			
			else {
				endController = 0;
				nextDot = false;
			}
				
			
		}while (endController != 2);
		
		body = bodyList.toString();
		mail = new Mail(subject, from_mail, to_mail, body);
		
		ConsoleUtils.logServer(hostname);
		ConsoleUtils.logServer(sender);
		ConsoleUtils.logServer(recipient);
		ConsoleUtils.logServer(body);
		
		
		
		relay(endpoint, port, retry_time, hostname, sender, recipient, mail);
		
		
	
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
