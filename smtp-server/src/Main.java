import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String server_name = "server.local";
		
		System.out.print("220 " + server_name + " Service Ready\r\n");
		
        Scanner scanner = new Scanner(System. in);
        String inputString = scanner. nextLine();
        //System.out.println("String read from console is : \n"+inputString);
        // HELO client.example.com\r\n
        
		System.out.print("250 Request mail action okay, completed\r\n");

        inputString = scanner.nextLine();
        //System.out.println("String read from console is : \n"+inputString);
        // MAIL FROM: <user@example.com>\r\n
        
        System.out.print("250 OK\r\n");
        
        inputString = scanner.nextLine();
        //System.out.println("String read from console is : \n"+inputString);
        // DATA\r\n
        
        System.out.print("354 Start mail input; end with <CRLF>.\r\n");
        
        inputString = scanner.nextLine();
        //System.out.println("String read from console is : \n"+inputString);
        //Subject:<Subject>\r\n
        //From:<email@domain.com>\r\n
        //To:<user1@server.local>,<user2@server.local>\r\n
        // LINEA VACIA AQUI
        // Esto es un mensaje de prueba, /nDos lineas..
        // \r\n.\r\n.
        
        System.out.print("250 OK\r\n");
        
        inputString = scanner.nextLine();
        //System.out.println("String read from console is : \n"+inputString);
        // QUIT\r\n
        
        System.out.print("221 " + server_name + " Service closing transmission channel\r\n");
        
	}

}
