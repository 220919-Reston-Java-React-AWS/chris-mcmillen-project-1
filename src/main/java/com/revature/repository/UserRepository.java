package com.revature.repository;

import com.revature.model.User;

import java.sql.*;

public class UserRepository {

    //register
    public boolean addUser(String username, String password, String firstName, String lastName, int userAge, String email) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "insert into users (user_name, passphrase, first_name, last_name, user_age, email, role_id) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setInt(5, userAge);
            pstmt.setString(6, email);
            pstmt.setInt(7, 1);

            int numberOfRecordsAdded = pstmt.executeUpdate();

            ResultSet rs2 = pstmt.getGeneratedKeys();
            rs2.next();
            int id = rs2.getInt(1);

            return true;


        }
    }

    //login
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "select * from users as u where u.user_name = ? and u.passphrase = ?";
            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                int id = rs.getInt("user_id");
                String un = rs.getString("user_name");
                String passphrase = rs.getString("passphrase");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int userAge = rs.getInt("user_age");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");


                return new User(id, un, passphrase, firstName, lastName, userAge, email, roleId);
            } else {
                return null;
            }
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String check = "select * from users as u where u.user_name = ?";
            PreparedStatement search = connectionObject.prepareStatement(check);
            search.setString(1, username);
            ResultSet rs1 = search.executeQuery();
            if (rs1.next()) {
                return null;
            } else {
                return new User(0,"","","","",0,"",0);
            }
        }
}
    public boolean updateRoleId(int userId, int roleId) throws SQLException{
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "update users set role_Id = ? where user_id = ?";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql);
            pstmt.setInt(1, roleId);
            pstmt.setInt(2, userId);

            int numberOfRecordsUpdated = pstmt.executeUpdate();
            return numberOfRecordsUpdated == 1;
        }
    }
    public User getUserByUserId(int userId) throws SQLException{
        try(Connection connectionObject=ConnectionFactory.createConnection()){
            String check="select * from users as u where u.user_id = ?";
            PreparedStatement search=connectionObject.prepareStatement(check);
            search.setInt(1,userId);
            ResultSet rs=search.executeQuery();
            if(rs.next()){
                int id = rs.getInt("user_id");
                String un = rs.getString("user_name");
                String passphrase = rs.getString("passphrase");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int userAge = rs.getInt("user_age");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");

                return new User(id, un, passphrase, firstName, lastName, userAge, email, roleId);
            }else{
                return null;
            }
        }
}
}
