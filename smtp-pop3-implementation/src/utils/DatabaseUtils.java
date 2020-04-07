package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DatabaseUtils {
	
	public static void Serialize(Object obj, String filepath){
		try {		

			FileOutputStream f = new FileOutputStream(new File(filepath), false);	//true:append
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(obj);

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
	
	public static Object Deserialize(String filepath){
		try {
			
			FileInputStream fi = new FileInputStream(new File(filepath));
			ObjectInputStream oi = new ObjectInputStream(fi);

			Object obj = oi.readObject();
			
			oi.close();
			fi.close();
			
			return obj;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
