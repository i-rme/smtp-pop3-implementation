package pop3client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import utils.CustomThread;
import utils.NetworkUtils;
import utils.Utils;

public class Pop3Client extends CustomThread {

	public final int PORT = 110;
	// private final int PORT = 1100;
	public static final String TYPE = "CLIENT";
	public static final String SERVICE = "POP3";
	public final String HOSTNAME = SERVICE + TYPE + ".local";
	// private final String DATABASE = "database.txt";
	private final String ENDPOINT = "127.0.0.1";
	// private final String ENDPOINT = "pop3.mailtrap.io";
	private final int RETRY_TIME = 2;
	public volatile boolean RUNNING = true;

	private Socket socket;
	public BufferedReader input;
	public PrintWriter output;

	public void run() {

		System.out.println("INFO: Starting the " + SERVICE + " " + TYPE);

		socket = NetworkUtils.getSocket(ENDPOINT, PORT, RETRY_TIME);
		input = NetworkUtils.getInput(socket);
		output = NetworkUtils.getOutput(socket);

		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("USER paco", output);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("PASS pack1", output);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("STAT", output);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("LIST", output);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("RETR 1", output);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("LIST", output);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("DELE 2", output);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("LIST", output);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("RSET", output);
		NetworkUtils.waitMessage(this);

		Utils.sleep(1000);
		NetworkUtils.sendMessage("LIST", output);

		// NetworkUtils.waitMessage(this);
		// Utils.sleep(2000);
		// NetworkUtils.sendMessage("STAT", output);

		/*
		 * NetworkUtils.waitMessage(this); Utils.sleep(2000);
		 * NetworkUtils.sendMessage("PASS a4612d53d01efd", output);
		 * 
		 * NetworkUtils.waitMessage(this); Utils.sleep(2000);
		 * NetworkUtils.sendMessage("STAT", output);
		 * 
		 * NetworkUtils.waitMessage(this); Utils.sleep(2000);
		 * NetworkUtils.sendMessage("RETR 1", output);
		 * 
		 * NetworkUtils.waitMessage(this); Utils.sleep(2000);
		 * NetworkUtils.sendMessage("QUIT", output);
		 * 
		 * NetworkUtils.waitMessage(this); Utils.sleep(2000);
		 * NetworkUtils.sendMessage("STAT", output);
		 * 
		 * System.out.println("AQUI no deberia llegar ya que deberia esperar");
		 */

		while (RUNNING)
			NetworkUtils.waitMessage(this);

	}

	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getInput() {
		return input;
	}

	public PrintWriter getOutput() {
		return output;
	}

}