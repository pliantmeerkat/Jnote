package noteAppPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RuntimeSession {
	
	public User activeUser;
	public List<Message> activeMessages;
	
	public RuntimeSession() {
		this.activeUser = null;
		this.activeMessages = new ArrayList<Message>();
	}
	 
	public void loginUser(String username, String password) {
		
		validateUser(username, password);
		
		this.activeUser = new User(username, password);
		//this.activeUser = new User("JackIsCool", "123qweasdzx");
		try {
			getUserMessages();
		} catch(IllegalArgumentException e) {
			throw new NullPointerException("account has no messages");
		}
	}
	
	public void logoutActiveUser() {
		
		if(this.activeUser == null) {
			throw new NullPointerException("Cannot log out no active user");
		}
		this.activeUser = null;
		this.activeMessages.clear();
	}
	
	public void getUserMessages() {
		// may remove later as mostly defunct
		this.activeMessages = DataProcessor.getUserMessageList(this.activeUser.username);
	}
	
	public String setuserMessagesAsString() {
		String output = "";
		
		// loop over message array
		for(int i = 0; i < this.activeMessages.size(); i ++) {
			output += this.activeMessages.get(i).messageTime;
			output += "\n";
			output += this.activeMessages.get(i).messageText;
			output += "\n";
		}
		getUserMessages();
		return output;
	}
	
	public void sendUserMessage(String text) {
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
		LocalDateTime sendDate = LocalDateTime.now();
		String messageDate = dateFormatter.format(sendDate);
		
		if(Verification.isIllegalCharsPresent(text)) {
			throw new IllegalArgumentException("illegal characters detected");
		}
		
		DataProcessor.writeUserMessage(text, messageDate, this.activeUser.username);
		getUserMessages();
		
	}
	
	public void deleteUserMessageData() {
		DataProcessor.deleteUserMessageData(this.activeUser.username);
		this.activeMessages.clear();
	}
	
	private void validateUser(String username, String password) {
		
		if(username.length() < 1) {
			throw new IllegalArgumentException("no username entered");
		}
		else if(Verification.isIllegalCharsPresent(username) || Verification.isIllegalCharsPresent(password)) {
			throw new IllegalArgumentException("illegal characters detected");
		}
		else if(!Verification.isPasswordFormatValid(password)) {
			throw new IllegalArgumentException("password format invalid");
		}
		else if(Verification.isUsernameExists(username)) {
			DataProcessor.WriteUser(username, password);
		}
	}
}
