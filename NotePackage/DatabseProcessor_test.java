package NotePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.junit.jupiter.api.Test;

class DatabseProcessor_test {
	
	static Connection connect;
	DatabaseProcessor dbProcessor;
	TestHelper helper = new TestHelper();
	
	@BeforeEach
	void initialize() {
		dbProcessor = new DatabaseProcessor();
		helper.createConnection(); 
		helper.addTestDataToDb();
	}
	
	// message creation/ reading tests multiple users
	
	@Test
	void canReadMessageTestData() {
		for(int i = 0; i < helper.setTestMessages().length - 1; i++) {
			//assertEquals(helper.setTestMessages()[i], dbProcessor.readData("messages", "none").get(i));
		}
	}
	
	@Test
	void canWriteNewMessageData() {
		Message message = helper.setInputMessages()[0];
		dbProcessor.writeMessage(message, "messages");
		//assertEquals(helper.setInputMessages()[0], dbProcessor.readData("messages", "none").get(7));
	}
	
	@Test
	void canReadAllUserTestData() {
		List<Hashtable> result = dbProcessor.readData("users", "none");
		int length = result.size();
		for(int i = 0; i < length; i++) {
			
		}
	}
	
	// single user tests
	
	@Test 
	void canReadSingleUserLogin() {
		String[] expectedData = { "JackIsCool", "123qweasdzx" };
		assertEquals( expectedData[0], dbProcessor.readData("users", "JackIsCool").get(0));
		assertEquals( expectedData[1], dbProcessor.readData("users", "JackIsCool").get(1));
	}
	
	@Test 
	void singleUserCanReadAllMsg() {
		
	}
	
	@Test
	void canWriteUserData() {
		String testUser = "im a test user!";
		dbProcessor.writeUser(testUser, "users");
		// assertEquals(testUser, dbProcessor.readData("users", "none").get(4));
	}
	
	@Test
	void canDeleteMessages() {
		dbProcessor.deleteData("messages");
		assertTrue(dbProcessor.readData("messages", "none").isEmpty());
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
	void writeThrowsErrorWhenUserInvalid() {
		
	}
	
	@Test
	void deleteThrowsExceptionWithInvalidInput() {
		try {
			dbProcessor.deleteData("fakeTable");
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
