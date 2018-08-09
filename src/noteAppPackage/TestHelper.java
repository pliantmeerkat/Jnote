package noteAppPackage;

import java.sql.*;
import java.util.ArrayList;

public class TestHelper {
	
	//object declaration
	static Connection connect;
	
	//test variable declarations test messages are insert BY the helper, input by the unit tests
	
	public Message[] setTestMessages() {
		Message message1 = new Message("Hello this is a test entry", 
									   "11/11/2011 14:40", 
									   "JackIsCool");
		Message message2 = new Message("Im Wrtiting one with l3tters numb34s and @ybol$", 
									   "21/12/2011 15:30", 
									   "DurainInTheMainframe");
		Message message3 = new Message("punctuation;;::{}||?><>~", 
									   "21/12/2011 16:40", 
									   "all1234567890Nums");
		Message message4 = new Message("numbers1234567890", 
									   "08/06/2017 00:00", 
									   "and!@£$%^&*()_+-={}[]|::");
		Message[] messageList = { message1, message2, message3, message4 };
		return messageList;
	}
	
	public Message[] setInputMessages() {
		Message message1 = new Message("this is a test message",
									   "09/12/2018 12:25",
									   "JackIsCool");
		Message message2 = new Message("numbers 1234567890",
									   "09/12/2018 12:25",
									   "JackIsCool");
		Message message3 = new Message("symbols !@£$%^&*()_+=[]{};:,<.>/?~",
									   "10/12/2018 12:25",
									   "JackIsCool");
		Message message4 = new Message("CAPITals",
									   "01/01/2019 12:25",
									   "DurainInTheMainframe");
		Message[] messageList = { message1, message2, message3, message4 };
		return messageList;
	}
	
	public ArrayList<String> getTestMessagesAsArray() {
		ArrayList<String> messageList = new ArrayList<String>();
		Message[] testMessages = setTestMessages();
		for(int i = 0; i < 4; i ++) {
			messageList.add(testMessages[i].messageText);
			messageList.add(testMessages[i].messageTime);
			messageList.add(testMessages[i].messageUser);
		}
		return messageList;
	}
	
	public ArrayList<String> getInputMessagesAsArray() {
		ArrayList<String> messageList = new ArrayList<String>();
		Message[] inputMessage = setInputMessages();
		for(int i = 0; i < 4; i ++) {
			messageList.add(inputMessage[i].messageText);
			messageList.add(inputMessage[i].messageTime);
			messageList.add(inputMessage[i].messageUser);
		}
		return messageList;
	}
	
	public User[] addTestUsers() {
		User user1 = new User("JackIsCool","123qweasdzx");
		User user2 = new User("DurainInTheMainframe", "thisisac00lpas4word");
		User user3 = new User("all1234567890Nums", "nomore4akepa@@word$");
		User user4 = new User("and!@£$%^&*()_+-={}[]|::", "  space$ ar% inTHis*&$NC");
		User[] userList = { user1, user2, user3, user4 };
		return userList;
	} 
	
	public ArrayList<String> getusersAsArray() {
		ArrayList<String> messageList = new ArrayList<String>();
		User[] testUsers = addTestUsers();
		for(int i = 0; i < 4; i ++) {
			messageList.add(testUsers[i].username);
			messageList.add(testUsers[i].password);
		}
		return messageList;
	}
	 
	public void createConnection() {
		
		try {
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/noteApp", "jackbranch", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addTestDataToDb() {
		User[]testUsers = addTestUsers();
		try {
			Statement statement = connect.createStatement();
			for(int i = 0; i < setTestMessages().length; i++) {
				statement.execute("INSERT INTO messages (message, time, username) VALUES ('" + setTestMessages()[i].messageText + "', '" 
																						     + setTestMessages()[i].messageTime + "', '" 
																						     + setTestMessages()[i].messageUser + "');");
			}
			for(int i = 0; i < addTestUsers().length; i++) {
				statement.execute("INSERT INTO users (username, password) VALUES ('" + testUsers[i].username + "','" 
																					 + testUsers[i].password + "');");
						
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clearDatabase() {
		try {
			PreparedStatement statement = connect.prepareStatement("TRUNCATE messages;TRUNCATE users");
			statement.execute();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
