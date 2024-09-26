package com.example.greenplate.ui.account;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountCreationViewModelTest {

    @Test
    public void passwordTestAllFieldsCorrect() {
        AccountCreationViewModel test = new AccountCreationViewModel();

        // Test for all fields filled
        boolean result = test.filterPasswords("First", "Last", "firstlast", "password", "password");
        assertTrue("Return true when all conditions are met", result);

        // Test for empty fields
        result = test.filterPasswords("", "Last", "firstlast", "password", "password");
        assertFalse("Return false when any field is empty", result);
    }

    @Test
    public void passwordTestMismatch() {
        AccountCreationViewModel test = new AccountCreationViewModel();

        // Test for password mismatch
        boolean result = test.filterPasswords("First", "Last", "firstlast", "password", "password123");
        assertFalse("Return false when passwords do not match", result);
    }

    @Test
    public void passwordTestUsername() {
        AccountCreationViewModel test = new AccountCreationViewModel();

        // Test for space in username
        boolean result = test.filterPasswords("First", "Last", "first last", "password", "password");
        assertFalse("Return false when username contains space", result);
    }

    @Test
    public void passwordTestPassword() {
        AccountCreationViewModel test = new AccountCreationViewModel();

        // Test for space in password
        boolean result = test.filterPasswords("First", "Last", "firstlast", "pass word", "pass word");
        assertFalse("Return false when password contains space", result);
    }
}