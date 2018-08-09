package NotePackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Message_test {
	
	Message message;
	
	@BeforeEach
	void intialize() {
		message = new Message("Hello this is a test entry", "11/11/2011 14:30", "JackIsCool");
	}

	@Test
	void getMessageText() {
		assertEquals("Hello this is a test entry", message.messageText);
	}
	
	@Test
	void getMessageTime() {
		assertEquals("11/11/2011 14:30", message.messageTime);
	}
	
	@Test
	void getMessageUser() {
		assertEquals("JackIsCool", message.messageUser);
	}

}
