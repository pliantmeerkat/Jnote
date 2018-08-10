package noteAppPackage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessor_test {
	
	
	TestHelper helper = new TestHelper();
	
	@BeforeEach
	void initialize() {
		helper.createConnection();
		helper.addTestDataToDb();
	}
	
	// read methods
	
	@Test
	void canReadUserCorrectly() {
		 User user1 = helper.addTestUsers()[0];
		 User user2 = DataProcessor.getUser("JackIsCool", "123qweasdzx");
		 assertEquals(user1.username, user2.username);
		 assertEquals(user1.password, user2.password);
	}
	
	@Test
	void canReadOneMessageCorrectly() {
		Message message1 = helper.setTestMessages()[0];
		List<Message> message2 = DataProcessor.getUserMessageList("JackIsCool");
		assertEquals(message1.messageText, message2.get(0).messageText);
		assertEquals(message1.messageTime, message2.get(0).messageTime);
		assertEquals(message1.messageUser, message2.get(0).messageUser);
	}
	
	@Test 
	void canReadAllOneUsersMessages() {
		// write a new message to database
		Message testMessage = helper.setInputMessages()[0];
		DataProcessor.writeUserMessage(testMessage.messageText, testMessage.messageTime, testMessage.messageUser);
		Message[] testmessageList = { helper.setTestMessages()[0], helper.setInputMessages()[0] };
		List<Message> resultMessageList = DataProcessor.getUserMessageList("JackIsCool");
		assertEquals(testmessageList[0].messageText, resultMessageList.get(0).messageText);
		assertEquals(testmessageList[0].messageTime, resultMessageList.get(0).messageTime);
		assertEquals(testmessageList[0].messageUser, resultMessageList.get(0).messageUser);
		assertEquals(testmessageList[1].messageText, resultMessageList.get(1).messageText);
		assertEquals(testmessageList[1].messageTime, resultMessageList.get(1).messageTime);
		assertEquals(testmessageList[1].messageUser, resultMessageList.get(1).messageUser);
	}
	
	// write methods

	@Test
	void canWriteAUserCorrectly() {
		User testUser = new User("imATestUser", "testpa44word");
		DataProcessor.WriteUser("imATestUser", "testpa44word");
		User resultUser = DataProcessor.getUser("imATestUser", "testpa44word");
		assertEquals(testUser.username, resultUser.username);
		assertEquals(testUser.password, resultUser.password);
	}
	
	@Test 
	void canWriteAMssageCorrectly() {
		Message testMessage = helper.setInputMessages()[0];
		DataProcessor.writeUserMessage(testMessage.messageText, testMessage.messageTime, testMessage.messageUser);
		Message resultMessage = DataProcessor.getUserMessageList("JackIsCool").get(1);
		assertEquals(testMessage.messageText, resultMessage.messageText);
		assertEquals(testMessage.messageTime, resultMessage.messageTime);
		assertEquals(testMessage.messageUser, resultMessage.messageUser);
	}
	
	// new user methods
	
	
	@AfterEach
	void clearTestDatabase() {
		helper.clearDatabase();
	}
}
