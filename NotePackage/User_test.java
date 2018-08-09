package notePackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class User_test {
	
	User user;
	
	@BeforeEach 
	void initialize() {
		user = new User("JackIsCool", "123qweasdzx");
	}
	
	@Test
	void getUserName() {
		assertEquals("JackIsCool", user.username);
	}
	
	@Test
	void getUserPassword() {
		assertEquals("123qweasdzx", user.password);
	}
}
