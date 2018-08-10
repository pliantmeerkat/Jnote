package noteAppPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Verification_test {

TestHelper helper = new TestHelper();
User testExistingUser = new User("JackIsCool", "123qweasdzx");
User testNewUser = new User("imANewUser", "validpa44wor0");
	
	@BeforeEach
	void initialize() {
		helper.createConnection();
		helper.addTestDataToDb();
	}
	
	// valid data return
	
	@Test
	void acceptsValidLogin() {
		// assertTrue(Verification.isLoginValid("JackIsCool", testExistingUser.password));
	}
	
	// new user creation
	
	@Test
	void flagsUnusedUsername() {
		assertFalse(Verification.isUsernameExists("imNonExistant!")) ;
	}
	
	@Test
	void flagsUnusedUsernameBadPassword() {
		assertFalse(Verification.isPasswordFormatValid("123short"));
		assertFalse(Verification.isPasswordFormatValid("invalidpasswordnonums"));
	}
	
	@Test 
	void noFlagGoodPassword() {
		assertTrue(Verification.isPasswordFormatValid("validpa44word"));
	}
	
	// illegal characters
	
	@Test
	void flagsIllegalCharacters() {
		assertTrue(Verification.isIllegalCharsPresent("lot'sof'bad'chars"));
		assertTrue(Verification.isIllegalCharsPresent("morebad\"char\"s"));
		assertFalse(Verification.isIllegalCharsPresent("nobadchars"));
	}
	
	// login failures 
	
	@Test
	void flagsUsedUsernameInvalidPassword() {
		
	}
	
	@Test
	void flagsUsedUsernameBadPassword() {
		
	}
	
}
