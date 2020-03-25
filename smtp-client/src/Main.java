import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Main {

	/* Comentarios:
	 * Este client es una version de prueba para
	 * debuggear la conexion de los sockets entre el 
	 * servidor y el cliente.
	 */
	
	
	public static void main(String[] args) {

		String SERVER_NAME = "server.local";
		int SERVER_PORT = 25;
		
		BufferedReader input;
		PrintWriter output;
		
		BufferedReader inputKeyboard;
		String data;
		String result;
		
		Socket Socket_Connection;
		
		
		try {
			Socket_Connection = new Socket("localhost",SERVER_PORT);

			input = new BufferedReader(new InputStreamReader(Socket_Connection.getInputStream()));
			output = new PrintWriter(Socket_Connection.getOutputStream(), true);
			
			// Get text from the keyboard
			inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Write text (END to close the server): ");
			data = inputKeyboard.readLine();
			
			// Send data to the server
			output.println(data);
			// Read data from the server
			result = input.readLine();
			
			System.out.println("Data = " + data + " --- Result = " + result);		
			
			Socket_Connection.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
