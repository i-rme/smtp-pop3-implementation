package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;

public class CustomThread extends Thread {

	public volatile boolean RUNNING;
	
	
	public void close() {
		
		RUNNING = false;
		try { getSocket().close(); } catch (IOException e) {	e.printStackTrace(); }
		
		stop();
		System.out.print("INFO: close()");
		
	}
	
	public Socket getSocket() {
		return null;
	}
	
	public BufferedReader getInput() {
		return null;
	}
	
	public PrintWriter getOutput() {
		return null;
	}
}
