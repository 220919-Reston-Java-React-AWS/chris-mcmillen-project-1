package com.revature.contoller;

import com.revature.exception.EmptyListException;
import com.revature.exception.ReimbursementActionException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ReimbursementController {

    private ReimbursementService reimbursementService = new ReimbursementService();
    public void mapEndpoints(Javalin app) {
        app.get("/reimbursements", (ctx) -> {
                    HttpSession httpSession = ctx.req.getSession();
                    ReimbursementService reimbursementService1 = new ReimbursementService();

                    User user = (User) httpSession.getAttribute("user");
                    if (user != null) {
                        if (user.getRoleId() == 2) {
                            try {
                                List<Reimbursement> reimbursements = reimbursementService1.getAllReimbursements(user.getUserID());
                                ctx.json(reimbursements);
                            } catch (EmptyListException e) {
                                ctx.result(e.getMessage());
                            }

                        } else if ((user.getRoleId() == 1)) {
                            try {
                                int employeeId = user.getUserID();
                                List<Reimbursement> reimbursements = reimbursementService1.getReimbursementsForEmployees(employeeId);
                                ctx.json(reimbursements);
                            } catch (EmptyListException e) {
                                ctx.result(e.getMessage());
                            }
                        } else {
                            ctx.result("You are logged in, but you are not a manager or employee.");
                            ctx.status(401);
                        }}
                    else {
                        ctx.result("You are not logged in");
                        ctx.status(401);
                    }
                });

        app.patch("/reimbursements/{reimbursement-id}", (ctx) -> {
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");
            ReimbursementService reimbursementService1 = new ReimbursementService();

            if (user != null){
                if (user.getRoleId() == 2){
                    int managerId = user.getRoleId();
                    int reimbursementId = Integer.parseInt(ctx.pathParam("reimbursement-id"));

                    Reimbursement r = ctx.bodyAsClass(Reimbursement.class);
                    String status = r.getStatus();

                    try{
                        reimbursementService1.updateReimbursementStatus(reimbursementId, status, managerId);
                        ctx.result("Reimbursement status successfully updated.");
                    }catch (ReimbursementActionException | IllegalArgumentException e){
                        ctx.result(e.getMessage());
                        ctx.status(400);
                    } catch (ReimbursementNotFoundException e){
                        ctx.result(e.getMessage());
                        ctx.status(404);
                    }
                } else {
                    ctx.result("You are logged in, but you are not a manager.");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in.");
                ctx.status(401);
            }
        });

        app.get("/reimbursements/{reimbursement-type}", (ctx) ->{
            HttpSession httpSession = ctx.req.getSession();
            ReimbursementService reimbursementService1 = new ReimbursementService();

            User user =  (User) httpSession.getAttribute("user");
            int employeeId = user.getUserID();
            String reimburseType = ctx.pathParam("reimbursement-type");
            if (user != null) {
                if (user.getRoleId() == 2){
                    ctx.result("You are logged in as a manager, only employees can filter by request type.");
                    ctx.status(401);

                } else if((user.getRoleId() == 1)){
                    try {
                        List<Reimbursement> reimbursements = reimbursementService1.getReimbursementByType(reimburseType, employeeId);
                        ctx.json(reimbursements);
                    }catch(EmptyListException e){
                        ctx.result(e.getMessage());
                    }
                }else{
                    ctx.result("You are logged in, but you are not a manager or employee.");
                    ctx.status(401);
                }
            } else {
                ctx.result("You are not logged in");
                ctx.status(401);
            }
        });
    }
}
