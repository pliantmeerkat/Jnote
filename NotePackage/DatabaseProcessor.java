package NotePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class DatabaseProcessor {

	static Connection connection;
	
	public DatabaseProcessor() {
		createConnection();
	}
		
	public List<Hashtable> readData(String table, String username) {
		
		List<Hashtable> messageList = new ArrayList<Hashtable>();
		int resultIndexCounter = 0;
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result;
			// switch to toggle read one or all users data - none means all users data
			if(username == "none") {
				result = statement.executeQuery("SELECT * FROM " + table );
			}
			else {
				result = statement.executeQuery("SELECT * FROM " + table + " where username LIKE '" + username + "'");
			}
			
			while(result.next()) {
				if(table == "users") {
					
				}
				else {
					
				}
			}
		}
		catch(SQLException e) {
			//e.printStackTrace();
			throw new IllegalArgumentException("table not found");
		}
		return messageList;
	}
	
	public void writeMessage(Message message, String table) {
		String command = "INSERT INTO " + table + " (message, time, username) VALUES ('" + message.messageText + "', '"
																					 + message.messageTime + "', '"
																					 + message.messageUser + "') ;";
		try {
			Statement statement = connection.createStatement();
			statement.execute(command);
			statement.close();
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new IllegalArgumentException("table not found");
		}
	}
	
	public void writeUser(String text, String table) {
		String command = "INSERT INTO " + table + " (username) VALUES ('" + text + "') ;";
		try {
			Statement statement = connection.createStatement();
			statement.execute(command);
			statement.close();
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new IllegalArgumentException("table not found");
		}
	}
	
	public void deleteData(String table) {
		String command = "DELETE FROM " + table ;
		try {
			Statement statement = connection.createStatement();
			statement.execute(command);
			statement.close();
		} catch(SQLException e) {
			// e.printStackTrace();
			throw new IllegalArgumentException("table not found");
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
