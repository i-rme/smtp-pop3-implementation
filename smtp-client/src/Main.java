import java.net.*;
import java.util.concurrent.TimeUnit;
import utils.NetworkUtils;
import java.io.*;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

    	int SMTP_PORT = 25;

    	System.out.println("(THIS IS SMTP CLIENT)");
        TimeUnit.SECONDS.sleep(3);
        
        Socket sCon = new Socket("localhost", SMTP_PORT);
        BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
        PrintWriter output = new PrintWriter(sCon.getOutputStream(), true);
        

        System.out.println( NetworkUtils.waitMessage(input) );
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage(2, "Soy el cliente amigo", output);
       
        System.out.println( NetworkUtils.waitMessage(input) );
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage(4, "sigo ", output);
        
        System.out.println( NetworkUtils.waitMessage(input) );
        TimeUnit.SECONDS.sleep(2);
        NetworkUtils.sendMessage(6, "siendo", output);
        
        System.out.println( NetworkUtils.waitMessage(input) );
        
        System.out.println( "AQUI no deberia llegar ya que deberia esperar" );
  
        sCon.close();
 
    }   
    
    
}