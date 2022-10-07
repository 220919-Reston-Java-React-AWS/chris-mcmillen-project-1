package com.revature.service;

import com.revature.exception.*;
import com.revature.model.User;
import com.revature.repository.UserRepository;

import java.sql.SQLException;

public class AuthService {

    private UserRepository userRepo = new UserRepository();

    public User login(String username, String password) throws SQLException, InvalidLoginException {
        User user = userRepo.getUserByUsernameAndPassword(username, password);

        if (user == null){
            throw new InvalidLoginException("Invalid username and/or password");
        }

        return user;

    }
    public boolean register(String username, String password, String firstName, String lastName, int userAge, String email) throws SQLException, InvalidUsernameException, InvalidAuthorizationException {
        User user1 = userRepo.getUserByUsername(username);
        if (user1 == null){
            throw new InvalidUsernameException("Invalid username, please try again.");
        } else if(username == null || password == null || firstName == null || lastName == null || userAge <= 17 || email == null){
            throw new InvalidAuthorizationException("Must have valid input for all registration fields, and must be 18 or older to register.");
        } else if(username.equals("") || password.equals("") || firstName.equals("") || lastName.equals("") || email.equals("")){
            throw new InvalidAuthorizationException("Must provide username, password, first name, last name, and email.");
        }
        return userRepo.addUser(username, password, firstName, lastName, userAge, email);
    }
    public boolean updateRoleId(int userId, int roleId) throws SQLException, InvalidRoleException, InvalidUpdateException, UserNotFoundException {
        User user1 = userRepo.getUserByUserId(userId);
        int currentRole = user1.getRoleId();
        if (currentRole == roleId){
            throw new InvalidUpdateException("User is already has this role.");
        } else if (roleId == 1){
            userRepo.updateRoleId(userId, roleId);
            int end1 = 1;
            return end1 == 1;
        } else if (roleId == 2){
            userRepo.updateRoleId(userId, roleId);
            int end2 = 2;
            return end2 == 2;
        } else {
            throw new InvalidRoleException("Must choose acceptable option. 1 being an employee, or 2 being a manager.");
        }
    }
}
