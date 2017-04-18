package com.avalanche.tmcs.auth;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.ValidationException;

/**
 * @author David Dubois
 * @since 18-Apr-17.
 */
public class UserTest {
    @Test
    public void testValidateNewUser_passwordNull() {
        User user = new User();
        boolean exceptionThrown = false;

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Password cannot be empty", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_passwordEmpty() {
        User user = new User();
        boolean exceptionThrown = false;

        user.setPassword("");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Password cannot be empty", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_passwordTooShort() {
        User user = new User();
        boolean exceptionThrown = false;

        user.setPassword("short");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Password must be at least eight characters long", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_passwordConfirmNull() {
        User user = new User();
        boolean exceptionThrown = false;

        user.setPassword("LongEnoughPassword");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Password confirmation cannot be empty", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_passwordConfirmEmpty() {
        User user = new User();
        boolean exceptionThrown = false;

        user.setPassword("LongEnoughPassword");
        user.setPasswordConfirm("");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Password confirmation cannot be empty", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_passwordsDontMatch() {
        User user = new User();
        boolean exceptionThrown = false;

        user.setPassword("LongEnoughPassword");
        user.setPasswordConfirm("LongEnoughPassword2");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Password and password confirmation must match", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_usernameNull() {
        User user = new User();
        boolean exceptionThrown = false;

        user.setPassword("LongEnoughPassword");
        user.setPasswordConfirm("LongEnoughPassword");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Email cannot be empty", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_usernameEmpty() {
        User user = new User();
        boolean exceptionThrown = false;

        user.setPassword("LongEnoughPassword");
        user.setPasswordConfirm("LongEnoughPassword");
        user.setUsername("");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            exceptionThrown = true;
            Assert.assertEquals("Email cannot be empty", e.getMessage());
        }

        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testValidateNewUser_allGood() {
        User user = new User();

        user.setPassword("LongEnoughPassword");
        user.setPasswordConfirm("LongEnoughPassword");
        user.setUsername("Username");

        try {
            user.validateNewUser();
        } catch(ValidationException e) {
            Assert.fail();
        }
    }
}
