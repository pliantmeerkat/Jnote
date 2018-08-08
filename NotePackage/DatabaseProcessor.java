package NotePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseProcessor {

	static Connection connection;
	
	public DatabaseProcessor() {
		createConnection();
	}
		
	public String readMessage() {
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM messages");
			while(result.next()) {
				return result.getString(2);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	public void writeMessage(String text) {
		String command = "INSERT INTO messages (message) VALUES ('" + text + "') ;";
		try {
			Statement statement = connection.createStatement();
			statement.execute(command);
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createConnection() {
		
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/noteApp", "jackbranch", "");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
