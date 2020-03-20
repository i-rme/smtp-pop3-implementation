import java.io.BufferedWriter;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class WriterReader {

	public static void main(String[] args) {

		//Packet p1 = new Packet("Lucia", "Robles", "Male");
		 Packet p2 = new Packet("Jhon", "Garcia", "Male");
		
       Serialization(p2);
       Deserialization(p2);
       
      
      
		

}



public static void Serialization(Packet p1){
	try {		
		/*
		 * 
		 File email =  new File(p1.getReceiver()+".txt");
		 if(email.exists()){
		 
		 
		 }else{
		 
		 
		 
		 }
		 * 
		 */
		FileOutputStream f = new FileOutputStream( new File(p1.getReceiver()+".txt"),false); //true y false es para dictaminar si se crea uno nuevo o se van añadiendo
		ObjectOutputStream o = new ObjectOutputStream(f);

		// Write objects to file
		o.writeObject(p1);
	

		o.close();
		f.close();


	} catch (FileNotFoundException e) {
		System.out.println("File not found");
	} catch (IOException e) {
		System.out.println("Error initializing stream");
	}

}








public static void Deserialization(Packet p1){
	try {
		

		FileInputStream fi = new FileInputStream(new File(p1.getReceiver()+".txt"));
		ObjectInputStream oi = new ObjectInputStream(fi);

		// Read objects
		Packet pr1 = (Packet) oi.readObject();
		

		System.out.println(pr1.toString());
		

		oi.close();
		fi.close();

	} catch (FileNotFoundException e) {
		System.out.println("File not found");
	} catch (IOException e) {
		System.out.println("Error initializing stream");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}






}




