package com.revature.repository;

import com.revature.model.Reimbursement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementRepository {

    public boolean submitReimbursement(String reimburseType, String reimburseDesc, float amount, int user) throws SQLException{
        try(Connection connectionObject = ConnectionFactory.createConnection()) {

            if (amount == 0 || reimburseType == null){
                return false;
            }
            String sql = "insert into reimbursements (reimburse_type, reimburse_desc, amount, status, employee_id) values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String mod = reimburseType.toUpperCase();

            pstmt.setString(1, mod);
            pstmt.setString(2, reimburseDesc);
            pstmt.setFloat(3, amount);
            pstmt.setString(4, "PENDING");
            pstmt.setInt(5, user);

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            return true;

        }
    }

        //manager can view reimbursement requests
    public List<Reimbursement> getAllReimbursements(int userId) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "select * from reimbursements";

            Statement stmt = connectionObject.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("reimburse_id");
                String reimburseType = rs.getString("reimburse_type");
                String reimburseDesc = rs.getString("reimburse_desc");
                float amount = rs.getFloat("amount");
                String status = rs.getString("status");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");
                if (employeeId != userId) {
                    if (status.equals("PENDING")) {
                        Reimbursement reimbursement = new Reimbursement(id, reimburseType, reimburseDesc, amount, status, employeeId, managerId);
                        reimbursements.add(reimbursement);
                    }
                }
                return reimbursements;
            }
        }
        return null;
    }
    //get reimbursements for employees
    public List<Reimbursement> getReimbursementsForEmployees(int employeeId) throws SQLException{
        try(Connection connectionObject = ConnectionFactory.createConnection()) {
            List<Reimbursement> reimbursements = new ArrayList<>();


            String sql = "select * from reimbursements where employee_id = ?";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql);

            pstmt.setInt(1, employeeId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt( "reimburse_id");
                String reimburseType = rs.getString("reimburse_type");
                String reimburseDesc = rs.getString("reimburse_desc");
                float amount = rs.getFloat("amount");
                String status = rs.getString("status");
                int eId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");

                Reimbursement reimbursement = new Reimbursement(id, reimburseType, reimburseDesc, amount, status, eId, managerId);

                reimbursements.add(reimbursement);
            }
            return reimbursements;
        }
    }

    //Update reimbursements
    public boolean updateReimbursementStatus(int reimburseId, String status, int managerId) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String mod = status.toUpperCase();
            String sql = "update reimbursements set status = ?, manager_id = ? where reimburse_id = ?";

                PreparedStatement pstmt = connectionObject.prepareStatement(sql);
                pstmt.setString(1, mod);
                pstmt.setInt(2, managerId);
                pstmt.setInt(3, reimburseId);

                int numberOfRecordsUpdated = pstmt.executeUpdate();
                return numberOfRecordsUpdated == 1;
            }
        }
    public Reimbursement getReimbursementById(int id) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "select * from reimbursements where reimburse_id = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int reimbursementId = rs.getInt("reimburse_id");
                String reimbursementType = rs.getString("reimburse_type");
                String reimburseDesc = rs.getString("reimburse_desc");
                float amount = rs.getInt("amount");
                String status = rs.getString("status");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");

                return new Reimbursement(reimbursementId, reimbursementType, reimburseDesc, amount, status, employeeId, managerId);
            } else {
                return null;
            }
        }
    }
    public List<Reimbursement> getReimbursementByType(String reimburseType, int id) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            List<Reimbursement> reimbursements = new ArrayList<>();
            String sql = "select * from reimbursements where (reimburse_type = ?) and (employee_id = ?)";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, reimburseType);
            pstmt.setInt(2, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int reimbursementId = rs.getInt("reimburse_id");
                String reimbursementType = rs.getString("reimburse_type");
                String reimburseDesc = rs.getString("reimburse_desc");
                float amount = rs.getInt("amount");
                String status = rs.getString("status");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");

                Reimbursement reimbursement = new Reimbursement(id, reimburseType, reimburseDesc, amount, status, employeeId, managerId);

                reimbursements.add(reimbursement);
            }
                return reimbursements;
            }
        }
    }
