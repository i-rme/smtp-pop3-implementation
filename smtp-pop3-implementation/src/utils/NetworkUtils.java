package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkUtils {

	public static ServerSocket getServerSocket(int port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Socket acceptServerSocket(ServerSocket serverSocket) {
		try {
			return serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void closeServerSocket(ServerSocket serverSocket) {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeSocket(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Socket getSocket(String endpoint, int port, int retry_time) {
		while (true) {
			try {
				Utils.sleep(retry_time * 1000);
				System.out.println("INFO: Trying to connect to the server...");
				Socket socket = new Socket(endpoint, port);
				System.out.println("INFO: Connected sucessfully");
				return socket;
			} catch (Exception e) {
			}
		}
	}

	public static BufferedReader getInput(Socket socket) {
		try {
			return new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PrintWriter getOutput(Socket socket) {
		try {
			return new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void sendMessageCode(int code, String comment, PrintWriter output) {
		String message = code + " " + comment;

		sendMessage(message, output);
	}

	public static void sendMessage(String comment, PrintWriter output) {
		String message = comment;

		ConsoleUtils.logServer(message);
		output.println(message);
	}

	public static String waitMessage(CustomThread thread) {
		BufferedReader input = thread.getInput();
		String message;
		do {
			try {
				message = input.readLine();
			} catch (IOException e) {
				message = "@error_endpoint_disconnected";
				// e.printStackTrace();
				break;
			}

			Utils.sleep(25);
		} while (message.length() == 0);

		ConsoleUtils.logClient(message + "\r\n");
		return message;
	}

	public static String waitMessages(CustomThread thread) {
		String output = "";
		long end = System.currentTimeMillis() + 1000; // Wait 1000ms for more messages

		while (end != 0) {
			
			if(output.length() < 3) {
				output += NetworkUtils.waitMessage(thread) + "\r\n";
			}else if(!output.substring(output.length() - 3).equals(".\r\n")) {
				output += NetworkUtils.waitMessage(thread) + "\r\n";
				NetworkUtils.waitMessage(thread);
				end = 0;
			}
		}
		return output;
	}

	public static String waitMessage(BufferedReader input) {
		String message;
		do {
			try {
				message = input.readLine();
			} catch (IOException e) {
				message = "@error_endpoint_disconnected";
				// e.printStackTrace();
				break;
			}

			Utils.sleep(25);
		} while (message.length() == 0);

		ConsoleUtils.logClient(message + "\r\n");
		return message;
	}

	public static String waitMessageRegex(String pattern, BufferedReader input) {
		String message = waitMessage(input);

		if (message.charAt(0) == '@') {
			return message;
		} // if is error return it as is

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(message);

		if (m.matches()) {
			return m.group(2); // 0: whole matched expression, 1: first expression in brackets, 2: second exp,
								// ...
		} else {
			System.out.println("Error: Incorrect message for client");
			return null;
		}

	}
	

	public static String[] waitMessageRegexArray(String pattern, BufferedReader input) {
		String message = waitMessage(input);
		String[] array;

		if (message.charAt(0) == '@') { // if is error return it as is
			array = new String[3];
			array[2] = message;
			return array;
		}

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(message);

		int i = 0;
		array = new String[m.groupCount() + 1];
		while (m.find()) {
			for (int j = 0; j <= m.groupCount(); j++) {
				array[i] = m.group(j);
				i++;
			}
		}

		if (i > 0) {
			return array; // 0: whole matched expression, 1: first expression in brackets, 2: second exp,
							// ...
		} else {
			System.out.println("Error: Incorrect message for client");
			return null;
		}

	}

	public static boolean checkCommand(String command, String input) {

		String message = input;

		if (message == "") {
			return false;
		}

		else {
			if (message.charAt(0) == '@') {
				return false;
			} // if is error return it as is

			String[] split = message.split("\\s");

			if (split[0].equals(command)) {
				return true;
			} else {
				return false;
			}
		}

	}

	public static String parseMessageRegex(String pattern, String input) {
		String message = input;

		if (message.charAt(0) == '@') {
			return message;
		} // if is error return it as is

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(message);

		if (m.matches()) {
			return m.group(2); // 0: whole matched expression, 1: first expression in brackets, 2: second exp,
								// ...
		} else {
			System.out.println("Error: Incorrect message for client");
			return null;
		}
	}

}
