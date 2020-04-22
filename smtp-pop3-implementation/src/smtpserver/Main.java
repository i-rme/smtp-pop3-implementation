package smtpserver;

public class Main {

	public static void main(String[] args) {
		
		SmtpServer smtpServer = new SmtpServer();
		smtpServer.start();

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