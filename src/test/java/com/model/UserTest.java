package com.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class UserTest {
    @Test
    public void testTesting(){
        assertTrue(true);
    }

	@Test
	public void testValidLogin() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login("ffredrickson@gmail.com", "secureP@ss987");
		assertNotNull(facade.getCurrentUser());
	}

	@Test
	public void testInvalidLogin() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login("ffredrickson@gmail.com", "wrongPword1234!");
		assertNull(facade.getCurrentUser());
	}

	@Test
	public void testNullLoginCredentials() {
		MusicAppFacade facade = MusicAppFacade.getInstance();
		facade.login(null, null);
		assertNull(facade.getCurrentUser());
	}
}