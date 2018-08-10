package noteAppPackage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {
	
	static DatabaseProcessor dbProcessor = new DatabaseProcessor();
	
	public static boolean isLoginValid(String username, String password) {
		
		return false;	
	}
	
	public static boolean isUsernameExists(String username) {
		
		try {
			dbProcessor.readData("users", username);
		} catch(IllegalArgumentException e) {
			return false;
		}	
		return true;
	}
	
	public static boolean isPasswordFormatValid(String password) {

			Pattern pattern = Pattern.compile("\\w+\\d");
			Matcher matcher = pattern.matcher(password);
			
			if(password.length() > 9 && matcher.find()) {
				return true;
			}
		return false;
	}
	
	public static boolean isIllegalCharsPresent(String text) {
		
		Pattern pattern = Pattern.compile("\\'|\"");
		Matcher matcher = pattern.matcher(text);
		
		if(matcher.find()) {
			return true;
		}
		
		return false;
	}
	
}
