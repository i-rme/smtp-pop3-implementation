package utils;

import java.util.HashMap;
import java.util.Map;

public class FormatUtils {
	
	public static Map<String, String> parseEmailIMF(String imf) {
		Map<String, String> mailElements = new HashMap<String, String>();
		String lines[] = imf.split("\\r?\\n");
		
		for (String line : lines) {
			if (line.contains(": ")) {
				String[] parts = line.split(": ");
				mailElements.put(parts[0], parts[1]);
			} else {	//Concatenate the body as they are multiple lines
				mailElements.put("Body", mailElements.getOrDefault("Body", "")+"\n"+line);
			}
		}
		
		return mailElements;
	}
	
	public static String formatEmailIMF(Map<String, String> mailElements) {
		StringBuilder sb = new StringBuilder();  
		for (String name: mailElements.keySet()){
            String key = name.toString();
            String value = mailElements.get(name).toString();  
            if(key == "Body") {
                sb.append(value + "\n");
            }else {
                sb.append(key + ": " + value + "\n");
            }
		} 
		
		return sb.toString();
	}
}
