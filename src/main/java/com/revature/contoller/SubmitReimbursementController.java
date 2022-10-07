package com.revature.contoller;

import com.revature.exception.InvalidLoginException;
import com.revature.exception.InvalidReimbursementException;
import com.revature.exception.InvalidUsernameException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;

public class SubmitReimbursementController {

    private ReimbursementService reimbService = new ReimbursementService();

    public void mapEndpoints(Javalin app){
        app.post("/submit-ticket", (ctx) -> {
            Reimbursement info = ctx.bodyAsClass(Reimbursement.class);
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");

            if (user != null) {
                try {
                    int userId = user.getUserID();
                    int roleId = user.getRoleId();
                    reimbService.submitReimbursement(info.getReimburseType(), info.getReimburseDesc(), info.getAmount(), userId, roleId);
                    ctx.result("Successfully submitted ticket!");

                } catch (InvalidReimbursementException e) {
                    ctx.result(e.getMessage());
                    ctx.status(400);
                }
            }else {
                ctx.result("You are not logged in.");
                ctx.status(401);
            }
        });
    }
}