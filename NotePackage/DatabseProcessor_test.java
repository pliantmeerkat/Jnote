package notePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DatabseProcessor_test {
	
	static Connection connect;
	DatabaseProcessor dbProcessor;
	TestHelper helper = new TestHelper();
	ArrayList<String>expectedData = new ArrayList<String>();
	
	@BeforeEach
	void initialize() {
		dbProcessor = new DatabaseProcessor();
		helper.createConnection(); 
		helper.addTestDataToDb();
	}
	
	// message creation/ reading tests multiple users
	
	@Test
	void canReadMessageTestData() {
		expectedData = helper.getTestMessagesAsArray();
		assertEquals(expectedData, dbProcessor.readData("messages", "none"));
	}
	
	@Test
	void canWriteNewMessageData() {
		expectedData = helper.getInputMessagesAsArray();
		Message message = helper.setInputMessages()[0];
		dbProcessor.writeMessage(message, "messages");
		assertEquals(expectedData.get(0), dbProcessor.readData("messages", "none").get(12));
		assertEquals(expectedData.get(1), dbProcessor.readData("messages", "none").get(13));
		assertEquals(expectedData.get(2), dbProcessor.readData("messages", "none").get(14));
	}
	
	@Test
	void canReadAllUserTestData() {
		expectedData = helper.getusersAsArray();
		assertEquals(expectedData, dbProcessor.readData("users", "none"));
	}
	
	// single user tests
	
	@Test 
	void canReadSingleUserLogin() {
		expectedData.add(helper.getusersAsArray().get(0));
		expectedData.add(helper.getusersAsArray().get(1));
		assertEquals(expectedData, dbProcessor.readData("users", "JackIsCool"));
	}
	
	@Test 
	void singleUserCanReadAllMsg() {
		expectedData = helper.getTestMessagesAsArray();
		assertEquals(expectedData.get(3), dbProcessor.readData("messages", "DurainInTheMainframe").get(0));
		assertEquals(expectedData.get(4), dbProcessor.readData("messages", "DurainInTheMainframe").get(1));
		assertEquals(expectedData.get(5), dbProcessor.readData("messages", "DurainInTheMainframe").get(2));
	}
	
	@Test
	void canWriteUserData() {
		User testUser = new User("Im a test user!", "testpassword");
		dbProcessor.writeUser(testUser, "users");
		assertEquals("Im a test user!", dbProcessor.readData("users", "none").get(8));
		assertEquals("testpassword", dbProcessor.readData("users", "none").get(9));
	}
	
	// deletion tests
	
	@Test 
	void canDeleteAllOneUsersData() {
		dbProcessor.deleteUserData("JackIsCool", true);
		assertTrue(dbProcessor.readData("messages", "JackIsCool").isEmpty());
		assertTrue(dbProcessor.readData("users", "JackIsCool").isEmpty());
		assertFalse(dbProcessor.readData("messages", "none").isEmpty());
		assertFalse(dbProcessor.readData("users", "none").isEmpty());
	}
	
	@Test 
	void canDeleteAllOneUsersMessages() {
		dbProcessor.deleteUserData("JackIsCool", false);
		assertTrue(dbProcessor.readData("messages", "JackIsCool").isEmpty());
		assertFalse(dbProcessor.readData("users", "JackIsCool").isEmpty());
		assertFalse(dbProcessor.readData("messages", "none").isEmpty());
	}
	
	@Test
	void canDeleteAllstoredData() {
		dbProcessor.deleteAllData("messages");
		dbProcessor.deleteAllData("users");
		assertTrue(dbProcessor.readData("messages", "none").isEmpty());
		assertTrue(dbProcessor.readData("users", "none").isEmpty());
	}
	
	// bad input tests
	
	@Test
	void readThrowsExceptionWithInvalidInput() {
		try {
			dbProcessor.readData("fakeColumn", "none");
			fail("no exception thrown");
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("table not found")) ;
		}
	}
	
	@Test
	void writeThrowsExceptionWithInvalidInput() {
		Message badMessage = new Message("willEror", "6/6/66", "EvilUser");
		try {
			dbProcessor.writeMessage(badMessage, "fakeColumn");
			fail("no exception thrown");
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("table not found")) ;
		}
	}
	
	@Test
	void deleteThrowsExceptionWithInvalidInput() {
		try {
			dbProcessor.deleteAllData("fakeTable");
			fail("no exception thrown");
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is("table not found")) ;
		}
	}
	
	
	@AfterEach
	void clearDatabase() {
		helper.clearDatabase();
	}
}
