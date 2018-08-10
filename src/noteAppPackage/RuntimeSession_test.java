package noteAppPackage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RuntimeSession_test {

	RuntimeSession session;
	TestHelper helper = new TestHelper();
	User testUser;
	User realUser = helper.addTestUsers()[0];
	Message testMessage = helper.setInputMessages()[0];
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
	
	@BeforeEach
	void initialize() {
		testUser = new User("ImATestuser", "testpa44word7");
		helper.createConnection();
		helper.addTestDataToDb();
		session = new RuntimeSession();
	}
	
	// pre login tests
	
	@Test
	void activeUserExistsAndStartsAsNull() {
		assertNull(session.activeUser);
	}
	
	@Test
	void noMessagesAreDisplayed() {
		assertTrue(session.activeMessages.isEmpty());
	}
	
	// bad logins
	
	@Test
	void throwsExceptionIfBadPassword() {
		try {
			session.loginUser("imABaduser", "badpassword");
			fail("no exception thrown");
			
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("password format invalid")) ;
		}
	}
	 
	@Test
	void cannotLoginIfNoUsernameentered() {
		try {
			session.loginUser("", "badpassword");
			fail("no exception thrown");
			
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("no username entered")) ;
		}
	}
	
	// post login tests
	
	@Test
	void userCanLoginAndIsActiveUser() {
		session.loginUser(realUser.username, realUser.password);
		assertEquals(session.activeUser.username, realUser.username);
	}
	
	@Test
	void userCanLoginAndAutoRegister() {
		session.loginUser(testUser.username, testUser.password);
		assertEquals(session.activeUser.username, testUser.username);
	}
	
	@Test
	void userCanSeeAllMessages() {
		session.loginUser(realUser.username, realUser.password);
		session.getUserMessages();
		assertEquals(session.activeMessages.get(0).messageText, helper.setTestMessages()[0].messageText);
	}
	
	@Test
	void userCanSendMessageAndStoreInSession() {
		session.loginUser(realUser.username, realUser.password);
		LocalDateTime sendDate = LocalDateTime.now();
		String messageDate = dateFormatter.format(sendDate);
		session.sendUserMessage(testMessage.messageText);
		session.getUserMessages();
		assertEquals(session.activeMessages.get(1).messageText, testMessage.messageText);
		assertEquals(session.activeMessages.get(1).messageTime, messageDate);
	}
	
	@Test 
	void canConvertMessagesToOutputString() {
		LocalDateTime sendDate = LocalDateTime.now();
		String messageDate = dateFormatter.format(sendDate);
		session.loginUser(realUser.username, realUser.password);
		session.sendUserMessage("this is a test message");
		//assertEquals(session.setuserMessagesAsString(), helper.outputStringMessage(messageDate));
	}
	
	@Test
	void canDeleteUserMessages() {
		session.loginUser(realUser.username, realUser.password);
		session.deleteUserMessageData();
		assertTrue(session.activeMessages.isEmpty());
	}
	
	@Test
	void flagsMessagesWithDangerousChars() {
		session.loginUser(realUser.username, realUser.password);
		try {
			session.sendUserMessage("dangerous\"\" messag'e's");
			fail("no exception thrown");
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("illegal characters detected"));
		}
	}
	
	@Test
	void flagsUserInputWithDangerousChars() {
		session.loginUser(realUser.username, realUser.password);
		try {
			session.loginUser("dangerous\"\"", "messag'e's");
			fail("no exception thrown");
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("illegal characters detected"));
		}
	}
	
	// post logout tests

	@Test
	void userCanLogoutAndActiveUserIsThenNull() {
		session.loginUser(realUser.username, realUser.password);
		session.logoutActiveUser();
		assertNull(session.activeUser);
	}
	
	@Test
	void noMessagesAreDisplayedAfterLogout() {
		session.loginUser(realUser.username, realUser.password);
		session.logoutActiveUser();
		assertTrue(session.activeMessages.isEmpty());
	}
	
	@Test
	void cannotLogoutIfNoActiveUser() {
		try {
			session.logoutActiveUser();
			fail("no exception thrown");
		} catch(NullPointerException e) {
			assertThat(e.getMessage(), is("Cannot log out no active user"));
		}
	}
	
	@Test
	void postLoginTestsStillPassAfterOneLogout() {
		session.loginUser(realUser.username, realUser.password);
		session.logoutActiveUser();
		session.loginUser(realUser.username, realUser.password);
		session.getUserMessages();
		assertEquals(session.activeUser.username, realUser.username);
		assertEquals(session.activeMessages.get(0).messageText, helper.setTestMessages()[0].messageText);
	}
	
	@AfterEach
	void clearTestData() {
		helper.clearDatabase();
	}
}
