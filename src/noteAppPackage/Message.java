package noteAppPackage;

public class Message {
	
	public String messageText;
	public String messageTime;
	public String messageUser;
	
	public Message(String text, String time, String user) {
		this.messageText = text;
		this.messageTime = time;
		this.messageUser = user;
	}
}
