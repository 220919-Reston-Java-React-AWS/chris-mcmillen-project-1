package com.revature.service;

import com.revature.exception.*;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService as;

    @Test
    public void testLoginInvalidUsername() throws SQLException, InvalidLoginException{
        ///AAA

        when(userRepository.getUserByUsernameAndPassword(eq("username22"), eq("password22"))).thenReturn(null);

        Assertions.assertThrows(InvalidLoginException.class, () ->{
            as.login("username22", "password22");
        });
    }
    @Test
    public void testLoginInvalidPassword() throws SQLException, InvalidLoginException{
        ///AAA

        when(userRepository.getUserByUsernameAndPassword(eq("username22"), eq(""))).thenReturn(null);

        Assertions.assertThrows(InvalidLoginException.class, () ->{
            as.login("username22", "");
        });
    }
    @Test
    public void testLoginSuccess() throws SQLException, InvalidLoginException{
        when(userRepository.getUserByUsernameAndPassword(eq("username22"), eq("password22"))).thenReturn(new User(1, "username22", "password22", "user", "pass", 30, "myemail", 1));

        User actual = as.login("username22", "password22");
        User user = new User (1, "username22", "password22", "user", "pass", 30, "myemail", 1);
            Assertions.assertEquals(user, actual);
    }
    @Test
    public void testRegisterExistingUsername() throws SQLException, InvalidUsernameException{
        when(userRepository.getUserByUsername(eq("username1"))).thenReturn(null);

        Assertions.assertThrows(InvalidUsernameException.class, () -> {
            as.register("username1", "pass2", "me", "myself", 20, "email");
        });
    }
    @Test
    public void testRegisterInvalidInfo() throws SQLException, InvalidAuthorizationException {
        when(userRepository.getUserByUsername(eq("username1"))).thenReturn(new User(0,"","","","",0,"",0));

        Assertions.assertThrows(InvalidAuthorizationException.class, () -> {
            as.register("username1", "", "me", "myself", 20, "email");
        });
    }
    @Test
    public void testRegisterInvalidAge() throws SQLException, InvalidAuthorizationException {
        when(userRepository.getUserByUsername(eq("username1"))).thenReturn(new User(0,"","","","",0,"",0));

        Assertions.assertThrows(InvalidAuthorizationException.class, () -> {
            as.register("username1", "pass", "me", "myself", 10, "email");
        });
    }
    @Test
    public void testRegisterSuccess() throws SQLException, InvalidUsernameException, InvalidAuthorizationException {
        when(userRepository.getUserByUsername(eq("username1"))).thenReturn(new User(0, "", "", "", "", 0, "", 0));
        when(userRepository.addUser("username1", "pass2", "me", "myself", 20, "email")).thenReturn(true);
        Boolean actual = as.register("username1", "pass2", "me", "myself", 20, "email");
        Assertions.assertTrue(actual);
    }
    @Test
    public void testRoleCurrentRole() throws SQLException, InvalidUpdateException {
        when(userRepository.getUserByUserId(eq(1))).thenReturn(new User(1,"user1","pass2","me","myself",25,"email",1));

        Assertions.assertThrows(InvalidUpdateException.class, () -> {
            as.updateRoleId(1, 1);
        });
    }
    @Test
    public void testRoleInvalidRole() throws SQLException, InvalidRoleException {
        when(userRepository.getUserByUserId(eq(1))).thenReturn(new User(1,"user1","pass2","me","myself",25,"email",1));

        Assertions.assertThrows(InvalidRoleException.class, () -> {
            as.updateRoleId(1, 3);
        });
    }
    @Test
    public void testRoleSuccess() throws SQLException, InvalidRoleException, InvalidUpdateException, UserNotFoundException {
        when(userRepository.getUserByUserId(eq(1))).thenReturn(new User(1,"user1","pass2","me","myself",25,"email",1));

        Boolean actual = as.updateRoleId(1, 2);
        Assertions.assertTrue(actual);
    }
}
