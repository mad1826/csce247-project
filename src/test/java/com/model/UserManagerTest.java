package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import com.model.managers.UserManager;


public class UserManagerTest {
    private UserManager userManager;
    private String validFirstName;
    private String validLastName;
    private String validEmail;
    private String validPassword;

    @Before
    public void setUp() {
        userManager = UserManager.getInstance();
        validFirstName = "John";
        validLastName = "Doe";
        validEmail = "john.doe@example.com";
        validPassword = "Secure123!@#";
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        UserManager instance1 = UserManager.getInstance();
        UserManager instance2 = UserManager.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testCreateUserWithValidDataCreatesStudent() {
        OperationResult<User> result = userManager.createUser(validFirstName, validLastName, validEmail, validPassword, false);
        assertTrue(result.success);
        assertTrue(result.result instanceof Student);
        assertEquals(validFirstName, result.result.getFirstName());
        assertEquals(validLastName, result.result.getLastName());
        assertEquals(validEmail, result.result.getEmailAddress());
    }

    @Test
    public void testCreateUserWithValidDataCreatesTeacher() {
        OperationResult<User> result = userManager.createUser(validFirstName, validLastName, validEmail, validPassword, true);
        assertTrue(result.success);
        assertTrue(result.result instanceof Teacher);
        assertEquals(validFirstName, result.result.getFirstName());
        assertEquals(validLastName, result.result.getLastName());
        assertEquals(validEmail, result.result.getEmailAddress());
    }

    @Test
    public void testCreateUserWithExistingEmailReturnsError() {
        userManager.createUser(validFirstName, validLastName, validEmail, validPassword, false);
        OperationResult<User> result = userManager.createUser(validFirstName, validLastName, validEmail, validPassword, false);
        assertFalse(result.success);
        assertEquals("An account with this email already exists.", result.message);
    }

    @Test
    public void testCreateUserWithEmptyFirstNameReturnsError() {
        OperationResult<User> result = userManager.createUser("", validLastName, validEmail, validPassword, false);
        assertFalse(result.success);
        assertEquals("First name cannot be empty.", result.message);
    }

    @Test
    public void testCreateUserWithEmptyLastNameReturnsError() {
        OperationResult<User> result = userManager.createUser(validFirstName, "", validEmail, validPassword, false);
        assertFalse(result.success);
        assertEquals("Last name cannot be empty.", result.message);
    }

    @Test
    public void testCreateUserWithInvalidEmailReturnsError() {
        OperationResult<User> result = userManager.createUser(validFirstName, validLastName, "invalid-email", validPassword, false);
        assertFalse(result.success);
        assertEquals("Invalid email address.", result.message);
    }

    @Test
    public void testCreateUserWithShortPasswordReturnsError() {
        OperationResult<User> result = userManager.createUser(validFirstName, validLastName, validEmail, "short", false);
        assertFalse(result.success);
        assertEquals("Password must be at least 6 characters long.", result.message);
    }

    @Test
    public void testDeleteUserWithValidIdReturnsSuccess() {
        OperationResult<User> createResult = userManager.createUser(validFirstName, validLastName, validEmail, validPassword, false);
        assertTrue(createResult.success);
        
        OperationResult<Void> deleteResult = userManager.deleteUser(createResult.result.getId());
        assertTrue(deleteResult.success);
    }

    @Test
    public void testDeleteUserWithInvalidIdReturnsError() {
        OperationResult<Void> result = userManager.deleteUser(UUID.randomUUID());
        assertFalse(result.success);
        assertEquals("User not found with the given ID.", result.message);
    }

    @Test
    public void testGetUserWithValidCredentialsReturnsUser() {
        userManager.createUser(validFirstName, validLastName, validEmail, validPassword, false);
        User user = userManager.getUser(validEmail, validPassword);
        assertNotNull(user);
        assertEquals(validEmail, user.getEmailAddress());
    }

    @Test
    public void testGetUserWithInvalidCredentialsReturnsNull() {
        User user = userManager.getUser("invalid@email.com", "wrongpassword");
        assertNull(user);
    }

    @Test
    public void testGetUserWithValidIdReturnsUser() {
        OperationResult<User> createResult = userManager.createUser(validFirstName, validLastName, validEmail, validPassword, false);
        assertTrue(createResult.success);
        
        User user = userManager.getUser(createResult.result.getId());
        assertNotNull(user);
        assertEquals(createResult.result.getId(), user.getId());
    }

    @Test
    public void testGetUserWithInvalidIdReturnsNull() {
        User user = userManager.getUser(UUID.randomUUID());
        assertNull(user);
    }

    @Test
    public void testAccountExistsWithExistingEmailReturnsTrue() {
        userManager.createUser(validFirstName, validLastName, validEmail, validPassword, false);
        assertTrue(userManager.accountExists(validEmail));
    }

    @Test
    public void testAccountExistsWithNonExistingEmailReturnsFalse() {
        assertFalse(userManager.accountExists("nonexistent@email.com"));
    }

    @Test
    public void testAccountExistsWithNullEmailReturnsFalse() {
        assertFalse(userManager.accountExists(null));
    }
} 