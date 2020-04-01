import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String raw = "Date: Thursday, 4 Jun 1998 09:43:14 -0800\n"
        + "From: Nina Marton <nina@example.com>\n"
        + "To: project-discussion@example.com\n"
        + "Subject: Happy Birthday\n"
        + "Mime-Version: 1.0\n"
        + "Content-Type: text/plain; charset=\"us-ascii\""
        + "\n"
        + "Happy Birthday!\n"
        + "See you this evening,\n"
		 + "Nina\n"
	     + ".\n";
		
		System.out.print( formatEmailIMF( parseEmailIMF(raw) ) );
		
		
		
//		Map<String, String> mailElements = new HashMap<String, String>();
//		
//		String raw = "Date: Thursday, 4 Jun 1998 09:43:14 -0800\n"
//		         + "From: Nina Marton <nina@example.com>\n"
//		         + "To: project-discussion@example.com\n"
//		         + "Subject: Happy Birthday\n"
//		         + "Mime-Version: 1.0\n"
//		         + "Content-Type: text/plain; charset=\"us-ascii\""
//		         + "\n"
//		         + "Happy Birthday!\n"
//		         + "See you this evening,\n"
//        		 + "Nina\n"
//		 	     + ".\n";
//		
//		String lines[] = raw.split("\\r?\\n");
//		
//		for (String line : lines) {
//			
//			if (line.contains(": ")) {
//				String[] parts = line.split(": ");
//				mailElements.put(parts[0], parts[1]);
//			} else {
//				mailElements.put("Body", mailElements.getOrDefault("Body", "")+"\n"+line);		//Concatenate the body
//			}
//
//			//System.out.println(line);
//			//mailElements.put(key, value)
//		}
//		
//		System.out.println(mailElements.size());
//		
//		for (String name: mailElements.keySet()){
//            String key = name.toString();
//            String value = mailElements.get(name).toString();  
//            System.out.println(key + ": " + value);  
//		} 
//
//		//System.out.print(raw);
//		
//		//System.out.println(Arrays.toString(lines));
//		
	}

}
