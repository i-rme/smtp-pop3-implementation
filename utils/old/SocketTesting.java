import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SocketTesting {

	public void SocketServer(int port, ServerSocket sv_soc, Socket conn_soc, String data, BufferedReader input, PrintWriter output)
	{
		try {
			sv_soc = new ServerSocket(port);
			System.out.println("Trying to connect the Client...");
			
			while(data.compareTo("END") != 0)
			{
				conn_soc = sv_soc.accept();	
				System.out.println("Connection accepted.");
				input = new BufferedReader(new InputStreamReader(conn_soc.getInputStream()));
				output = new PrintWriter(conn_soc.getOutputStream(), true);
				data =  input.readLine();
			    System.out.println("Server receives: "+data);
		        output.println(data);			
		        conn_soc.close();		
			}
			
			sv_soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SocketClient(int port, ServerSocket sv_soc, Socket conn_soc, String data, BufferedReader input, PrintWriter output)
	{

		BufferedReader inputKeyboard;
		String result;
		
		
		try {
			conn_soc = new Socket("localhost",port);

			input = new BufferedReader(new InputStreamReader(conn_soc.getInputStream()));
			output = new PrintWriter(conn_soc.getOutputStream(), true);
			
			// Get text from the keyboard
			inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Write text (END to close the server): ");
			data = inputKeyboard.readLine();
			
			// Send data to the server
			output.println(data);
			// Read data from the server
			result = input.readLine();
			
			System.out.println("Data = " + data + " --- Result = " + result);		
			
			conn_soc.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
}
