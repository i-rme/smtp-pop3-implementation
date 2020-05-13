package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.Server;

public class DatabaseUtils {

	public static void Serialize(Object obj, String filepath) {
		try {

			FileOutputStream f = new FileOutputStream(new File(filepath), false); // true:append
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(obj);

			o.close();
			f.close();
			
			System.out.println("INFO: Saving current state to the database");

		} catch (FileNotFoundException e) {
			System.out.println("INFO: Error raving state to database");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}

	public static Object Deserialize(String filepath) {
		try {

			FileInputStream fi = new FileInputStream(new File(filepath));
			ObjectInputStream oi = new ObjectInputStream(fi);

			Object obj = oi.readObject();

			oi.close();
			fi.close();
			
			System.out.println("INFO: Database was loaded from file");

			return obj;

		} catch (FileNotFoundException e) {
			System.out.println("INFO: A new database was created");
			return new Server();
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
