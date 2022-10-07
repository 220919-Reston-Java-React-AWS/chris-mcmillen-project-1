package com.revature.service;

import com.revature.exception.EmptyListException;
import com.revature.exception.InvalidReimbursementException;
import com.revature.exception.ReimbursementActionException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.repository.ReimbursementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {
    @Mock
    private ReimbursementRepository reimbursementRepository;


    @InjectMocks
    private ReimbursementService rs;

    @Test
    public void testReimbursementUpdateIsNotValidStatus() throws ReimbursementNotFoundException, ReimbursementActionException, SQLException, IllegalArgumentException {
        //AAA (Arrange act assert)
        // arranged with inject mocks
        when(reimbursementRepository.getReimbursementById(eq(2))).thenReturn(new Reimbursement(2, "FOOD", "employee dinner", 150, "PENDING", 2, 2));
        // act + assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            rs.updateReimbursementStatus(2, "no way", 2);
        });
    }

    @Test
    public void testReimbursementUpdateReimbursementDoesNotExist() throws SQLException, NullPointerException {

        when(reimbursementRepository.getReimbursementById(eq(100))).thenReturn(null);

        Assertions.assertThrows(ReimbursementNotFoundException.class, () ->

        {
            rs.updateReimbursementStatus(100, "DENIED", 2);
        });
    }

    @Test
    public void testReimbursementUpdateStatusAlreadyUpdated() throws ReimbursementActionException, SQLException {

        when(reimbursementRepository.getReimbursementById(eq(1))).thenReturn(new Reimbursement(1, "OTHER", "Certification training", 150, "APPROVED", 5, 6));

        Assertions.assertThrows(ReimbursementActionException.class, () -> {
            rs.updateReimbursementStatus(1, "DENIED", 6);
        });
    }
    @Test
    public void testReimbursementUpdateSuccess() throws SQLException, ReimbursementActionException, ReimbursementNotFoundException, IllegalArgumentException{

        when(reimbursementRepository.getReimbursementById(eq(2))).thenReturn(new Reimbursement(2, "TRAVEL", "Travel to New York", 300, "PENDING", 5, 0));

        boolean actual = rs.updateReimbursementStatus(2, "APPROVED", 6);

        Assertions.assertFalse(actual);
    }
    @Test
    public void testGetReimbursementsForEmployees() throws SQLException, EmptyListException{

        Assertions.assertThrows(EmptyListException.class, () -> {
            rs.getReimbursementsForEmployees(0);
        });
    }
    @Test
    public void testGetReimbursementsByType() throws SQLException, EmptyListException{

        when(reimbursementRepository.getReimbursementByType(eq("OTHER"), eq(1))).thenReturn(List.of()
        );

        Assertions.assertThrows(EmptyListException.class, () -> {
            rs.getReimbursementByType("OTHER",1);
        });
    }
    @Test
    public void testSubmitReimbursementInvalidUser() throws SQLException, InvalidReimbursementException {
        Assertions.assertThrows(InvalidReimbursementException.class, () -> {
            rs.submitReimbursement("OTHER", "office supplies", 50, 2, 2);
        });
    }
    @Test
    public void testSubmitReimbursementInvalidType() throws InvalidReimbursementException, SQLException {

        Assertions.assertThrows(InvalidReimbursementException.class, () -> {
            rs.submitReimbursement("decor", "office supplies", 30, 2, 1);
        });
}

    @Test
    public void testSubmitReimbursementInvalidDesc() throws InvalidReimbursementException, SQLException {

        Assertions.assertThrows(InvalidReimbursementException.class,()->{
        rs.submitReimbursement("OTHER","",20,2,1);
        });
}

    @Test
    public void testSubmitReimbursementInvalidAmount() throws InvalidReimbursementException, SQLException {

        Assertions.assertThrows(InvalidReimbursementException.class,()->{
        rs.submitReimbursement("OTHER","decor",0,2,1);
        });
}
    @Test
    public void testSubmitReimbursementSuccess() throws InvalidReimbursementException, SQLException {

        boolean actual = rs.submitReimbursement("OTHER", "paper", 20, 0, 0);
        Assertions.assertTrue(actual != true);
    }
}
