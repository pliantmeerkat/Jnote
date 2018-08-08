package NotePackage;

import java.sql.*;

public class TestHelper {
	
	//object declaration
	static Connection connect;
	
	//test variable declarations test messages are insert BY the helper, input by the unit tests
	
	public String[] testMessages() {
		String[] messages = { "Hello this is a test entry", "Jack is cool","Im Wrtiting one with l3tters numb34s and @ybol$",
							  "punctuation;;::{}||?><>~", "numbers1234567890","  lots  of  sp  a c e s", "CATPITALLETTERS", "'willbreak'the'database'" };
		return messages;		
	}
	
	public String[] inputMessages() {
		String[] messages = { "this is a test message", "numbers 1234567890", "symbols !@£$%^&*()_+=[]{};:,<.>/?~", "CAPITals", "advanced sumbols ''\"\" " };
		return messages;
	}
	
	public void createConnection() {
		
		try {
			connect = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/noteApp", "jackbranch", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addTestDataToDb() {
		String command = "INSERT INTO messages (message) VALUES ('" + testMessages()[0] + "');";
		try {
			Statement statement = connect.createStatement();
			statement.execute(command);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clearDatabase() {
		try {
			PreparedStatement statement = connect.prepareStatement("TRUNCATE " + "messages");
			statement.execute();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
