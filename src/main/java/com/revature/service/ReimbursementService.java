package com.revature.service;

import com.revature.exception.*;
import com.revature.model.Reimbursement;
import com.revature.repository.ReimbursementRepository;

import java.sql.SQLException;
import java.util.List;

public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();

    public List<Reimbursement> getAllReimbursements(int userId) throws SQLException, EmptyListException {
        ReimbursementRepository reimbursement1 = new ReimbursementRepository();
        List<Reimbursement> reimbursements = reimbursement1.getAllReimbursements(userId);
        if (reimbursements.size() == 0) {
            throw new EmptyListException("There are no reimbursements in the list.");
        }
        return reimbursements;
    }

    public List<Reimbursement> getReimbursementsForEmployees(int employeeId) throws SQLException, EmptyListException {
        ReimbursementRepository reimbursement1 = new ReimbursementRepository();
        List<Reimbursement> reimbursements = reimbursement1.getReimbursementsForEmployees(employeeId);
        if (reimbursements.size() == 0){
            throw new EmptyListException("There are no reimbursements in the list.");
        }
        return reimbursements;
    }

    public boolean updateReimbursementStatus(int reimburseId, String status, int managerId) throws SQLException, ReimbursementActionException, ReimbursementNotFoundException {
        Reimbursement reimbursement1 = reimbursementRepository.getReimbursementById(reimburseId);
        ReimbursementRepository reimbursement3 = new ReimbursementRepository();
        if(reimbursement1 == null){
            throw new ReimbursementNotFoundException("Reimbursement with " + reimburseId + " not found.");
        }
        String reimbursement2 = reimbursement1.getStatus();
        String check = status.toUpperCase();
        if ((reimbursement2.equals("APPROVED") || reimbursement2.equals("DENIED"))) {
            throw new ReimbursementActionException("Reimbursement with id " + reimburseId + " has already had its status updated.");
        } else if (check.equals("APPROVED")) {
            return reimbursement3.updateReimbursementStatus(reimburseId, status, managerId);
        } else if (check.equals("DENIED")) {
            return reimbursement3.updateReimbursementStatus(reimburseId, status, managerId);
        } else {
            throw new IllegalArgumentException("Status must be changed to either 'APPROVED' or 'DENIED'.");
        }
        }

    public boolean submitReimbursement(String reimburseType, String reimburseDesc, float amount, int userId, int roleId) throws SQLException, InvalidReimbursementException{
        String mod = reimburseType.toUpperCase();
        ReimbursementRepository reimbursementRepository1 = new ReimbursementRepository();
        if (roleId == 2) {
            throw new InvalidReimbursementException("You must be an employee to submit reimbursement tickets.");
        } else if (reimburseDesc == "" || reimburseDesc == null || amount <= 0) {
            throw new InvalidReimbursementException("Invalid reimbursement, must have a description.");
        } else if (amount <= 0){
            throw new InvalidReimbursementException("Must also have a positive dollar amount. i.e. 100.00");
        }else if(mod.equals("LODGING")){
            if (roleId == 1) {
                return reimbursementRepository1.submitReimbursement(reimburseType, reimburseDesc, amount, userId);

            } else {
                return false;
            }
        } else if(mod.equals("TRAVEL")) {
            if (roleId == 1) {
                return reimbursementRepository1.submitReimbursement(reimburseType, reimburseDesc, amount, userId);
            } else{
                return false;
        }
        } else if(mod.equals("FOOD")){
                if (roleId == 1) {
                    return reimbursementRepository1.submitReimbursement(reimburseType, reimburseDesc, amount, userId);
                } else {
                    return false;
                }}
        else if(mod.equals("OTHER")) {
                if (roleId == 1) {
                    return reimbursementRepository1.submitReimbursement(reimburseType, reimburseDesc, amount, userId);
                } else{
                    return false;
                }
            }else {
            throw new InvalidReimbursementException("Invalid reimbursement type, must be 'lodging', 'travel', 'food', or 'other'.");
        }
    }
    public List<Reimbursement> getReimbursementByType(String type, int employeeId) throws SQLException, EmptyListException{
        String mod = type.toUpperCase();
        List<Reimbursement> reimbursements = reimbursementRepository.getReimbursementByType(mod, employeeId);;
        if (reimbursements.size() == 0){
            throw new EmptyListException("There are no reimbursements in the list.");
        }
        return reimbursements;
    }
}