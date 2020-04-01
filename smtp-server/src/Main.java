import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import utils.NetworkUtils;

public class Main {

	public static void main(String[] args) throws IOException {

		String SERVER_NAME = "server.local";
		String DATABASE_FILE = "database.txt";
		int SMTP_PORT = 25;
		
		System.out.println("(THIS IS SMTP SERVER)");
		
        ServerSocket sServ = new ServerSocket(SMTP_PORT);
        Socket sCon = sServ.accept();
        PrintWriter output = new PrintWriter(sCon.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
		

        NetworkUtils.sendMessage(1, SERVER_NAME + " Service Ready", output);
        System.out.println( NetworkUtils.waitMessage(input) );
        
        NetworkUtils.sendMessage(3, SERVER_NAME + " Hola amigo", output);
        System.out.println( NetworkUtils.waitMessage(input) );
        
        NetworkUtils.sendMessage(5, SERVER_NAME + " adios", output);
        System.out.println( NetworkUtils.waitMessage(input) );
        
        System.out.println( NetworkUtils.waitMessage(input) );
        
        System.out.println( "AQUI no deberia llegar ya que deberia esperar" );
        
        
        sServ.close();
		
//		sendMessage(220, SERVER_NAME + " Service Ready");
//		waitClientMessageRegex("(HELO) (([a-zA-Z0-9]+)(\\.([a-zA-Z0-9]+))*)");	// HELO client.example.com\r\n
//        
//        
//		sendMessage(250, "Request mail action okay, completed");
//		waitClientMessageRegex("(MAIL FROM:) <([a-zA-Z0-9]+@(([a-zA-Z0-9]+)(.([a-zA-Z0-9]+))+))>");	// MAIL FROM: <user@example.com>\r\n
//        
//		sendMessage(250, "OK");
//		waitClientMessageRegex("((DATA))");	// DATA\r\n
//              
//        sendServerMessage(354, "Start mail input; end with <CRLF>.<CRLF>");
//        waitMultilineClientMessage();
//        //Subject:<Subject>\r\n
//        //From:<email@domain.com>\r\n
//        //To:<user1@server.local>,<user2@server.local>\r\n
//        // LINEA VACIA AQUI
//        // Esto es un mensaje de prueba, /nDos lineas..
//        // \r\n.\r\n.
//        
//		sendServerMessage(250, "OK");
//		waitClientMessageRegex("((QUIT))");	// QUIT\r\n
//        
//		sendServerMessage(221, SERVER_NAME + " Service closing transmission channel");
        
	}
	
}