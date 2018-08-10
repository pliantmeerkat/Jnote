package noteAppPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseProcessor {

	static Connection connection;
	
	public DatabaseProcessor() {
		createConnection();
	}
		
	public ArrayList<String> readData(String table, String username) {

		ArrayList<String> resultArray = new ArrayList<String>();
		
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
					resultArray.add(result.getString(2));
					resultArray.add(result.getString(3));
				}
				else {
					resultArray.add(result.getString(2));
					resultArray.add(result.getString(3));
					resultArray.add(result.getString(4));
				}
			}
		}
		catch(SQLException e) {
			//e.printStackTrace();
			throw new IllegalArgumentException("table not found");
		}
		if(resultArray.isEmpty()) {
			throw new IllegalArgumentException("User not found");
		}
		return resultArray;
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
	
	public void writeUser(User user, String table) {
		String command = "INSERT INTO " + table + " (username, password) VALUES ('" + user.username + "','"
											                                        + user.password + "') ;";
		try {
			Statement statement = connection.createStatement();
			statement.execute(command);
			statement.close();
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new IllegalArgumentException("table not found");
		}
	}
	
	public void updateUserPassword(String username, String newPassword) {
		String command = "UPDATE users SET password = '" + newPassword + "' WHERE username = '" 
														 + username  + "';";
		try {
			Statement statement = connection.createStatement();
			statement.executeQuery(command);
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("username invalid");
		}
		
	}
	
	public void deleteUserData(String username, boolean isAllData) {
		try { 
			Statement statement = connection.createStatement();
			String messageCommand = "Delete From messages WHERE username LIKE '" + username + "';";
			if(isAllData) {
				String userCommand = "Delete From users WHERE username LIKE '" + username + "';";
				statement.execute(userCommand);
			}
			statement.execute(messageCommand);
			statement.close();
		} catch(SQLException e) {
			// e.printStackTrace();
			throw new IllegalArgumentException("username invalid");
		}
	}
	
	public void deleteAllData(String table) {
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
