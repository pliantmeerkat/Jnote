package noteAppPackage;

import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
	
	static DatabaseProcessor dbProcessor = new DatabaseProcessor();
	
	// user functions
	
	public static User getUser(String username, String password) {
		User user1 = new User("no", "user");
		try {
			ArrayList<String> result = dbProcessor.readData("users", username);
			user1 = new User(result.get(0), result.get(1));
			
			
		} catch(IllegalArgumentException e){
			
			e.printStackTrace();
			throw new IllegalArgumentException("user creation faliure");
			
		} catch(NullPointerException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("error with connection and or data");
		}
		return user1;
	}
	
	public static void WriteUser(String username, String password) {
		User newUser = new User(username, password);
		// verification code
		dbProcessor.writeUser(newUser, "users");
	}
	 
	// message functions
	
	public static List<Message> getUserMessageList(String username) {
		
		List<Message> userMessageList;
		
		try {
			ArrayList<String> result = dbProcessor.readData("messages", username);
			userMessageList = convertDbResultToMessages(result);
			
		} catch(IllegalArgumentException e){
			e.printStackTrace();
			throw new IllegalArgumentException("User not found");
			
		} catch(NullPointerException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("error with connection and or data");
		}
		
		return userMessageList;
	}
	
	public static void writeUserMessage(String text, String date, String username) {
		Message newMessage = new Message(text, date, username);
		// verification code
		dbProcessor.writeMessage(newMessage, "messages");
	}
	
	public static void deleteUserMessageData(String username) {
		dbProcessor.deleteUserData(username, false);
	}
	
	public static void deleteUserData(String username) {
		dbProcessor.deleteUserData(username, true);
	}
	
	private static List<Message> convertDbResultToMessages(ArrayList<String> result) {
		
		// take result formula is as follows
		// %3 == 0 then message text, 1 then time, 2 then username

		List<Message> messageList = new ArrayList<Message>();
		
		for(int i = 0; i < result.size(); i++) {
			if(i == 0) {
				continue;
			}
			switch(i % 3) {
			case 2:
				messageList.add(new Message(result.get(i-2), result.get(i-1), result.get(i)));
				break;
			}
		}
		
		return messageList ;
	}
}
