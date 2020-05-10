package utils;

import java.util.HashMap;
import java.util.Map;

import data.Mail;

public class FormatUtils {

	public static Map<String, String> imfToMap(String imf) {
		Map<String, String> mailElements = new HashMap<String, String>();
		String lines[] = imf.split("\\r?\\n");

		for (String line : lines) {
			if (line.contains(": ")) {
				String[] parts = line.split(": ");
				mailElements.put(parts[0], parts[1]);
			} else { // Concatenate the body as they are multiple lines
				mailElements.put("Body", mailElements.getOrDefault("Body", "") + "\n" + line);
			}
		}

		// Cleaning
		mailElements.replace("From", mailElements.get("From").replace("<", "").replace(">", "")); // removes <>
		mailElements.replace("To", mailElements.get("To").replace("<", "").replace(">", "")); // removes <>
		mailElements.replace("Body", mailElements.get("Body").substring(2)); // Removes excessive \r\n
		mailElements.replace("Body", mailElements.get("Body").substring(0, mailElements.get("Body").length() - 2)); // Removes
																													// excessive
																													// .
		return mailElements;
	}

	public static String mapToImf(Map<String, String> mailElements) {
		StringBuilder sb = new StringBuilder();
		for (String name : mailElements.keySet()) {
			String key = name.toString();
			String value = mailElements.get(name).toString();
			if (key == "Body") {
				sb.append(value + "\n");
			} else {
				sb.append(key + ": " + value + "\n");
			}
		}

		return sb.toString();
	}

	public static Mail mapToEmail(Map<String, String> mailElements) {
		Mail mail;
		String subject = mailElements.get("Subject").toString();
		String sender = mailElements.get("From").toString();
		String recipient = mailElements.get("To").toString();
		String body = mailElements.get("Body").toString();

		mail = new Mail(subject, sender, recipient, body);

		return mail;
	}

	public static Mail imfToMail(String imf) {
		return mapToEmail(imfToMap(imf));
	}

	public static String mailToImf(Mail mail) {
		return mail.toString();
	}

	public static Map<String, String> mailToMap(Mail mail) {
		return imfToMap(mail.toString());
	}
}
