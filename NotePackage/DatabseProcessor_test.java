package NotePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.*;



import org.junit.jupiter.api.Test;

class DatabseProcessor_test {
	
	static Connection connect;
	DatabaseProcessor dbProcessor;
	TestHelper helper = new TestHelper();
	
	@BeforeEach
	void initialize() {
		dbProcessor = new DatabaseProcessor() ;
		helper.createConnection(); 
		helper.addTestDataToDb();
	}
	
	@Test
	void canReadTestData() {
		assertEquals(helper.testMessages()[0], dbProcessor.readMessage());
	}
	
	@Test
	void canWriteNewData() {
		String message = helper.inputMessages()[0];
		dbProcessor.writeMessage(message);
		assertEquals(helper.inputMessages()[0], dbProcessor.readMessage());
	}
	
	@AfterEach
	void clearDatabase() {
		helper.clearDatabase();
	}
}
