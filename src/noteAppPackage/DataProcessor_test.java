package noteAppPackage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessor_test {
	
	static DataProcessor dataProcessor;
	TestHelper helper = new TestHelper();
	
	@BeforeEach
	void initialize() {
		dataProcessor = new DataProcessor();
		helper.createConnection();
		helper.addTestDataToDb();
		//dataProcessor = new DataProcessor();
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
		// Message[] messages = { helper.setTestMessages()[0], helper.setInputMessages()[0] };
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
		
	}
	
	//login methods
	
	@Test
	void RecognisesUserNames() {
		
	}
	
	@Test
	void canLoginAUserWithCorrectPassword() {
		
	}
	
	// Authentication methods
	
	@Test
	void RejectsLoginWithNoUsername() {
		
	}
	
	@Test 
	void RejectsBadPassword() {
		
	}
	
	@Test
	void RejectsUserNameWithWorngPassword() {
		
	}
	
	@AfterEach
	void clearTestDatabase() {
		helper.clearDatabase();
	}
}
