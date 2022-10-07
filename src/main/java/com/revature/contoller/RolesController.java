package com.revature.contoller;

import com.revature.exception.*;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;

public class RolesController {
    private AuthService authService = new AuthService();
    public void mapEndpoints(Javalin app){
        app.patch("/roles/{user-id}", (ctx) -> {
        HttpSession httpSession = ctx.req.getSession();
        User user = (User) httpSession.getAttribute("user");

        if (user != null){
            if (user.getRoleId() == 2){
                int userId = Integer.parseInt(ctx.pathParam("user-id"));
                AuthService authService1 = new AuthService();

                User r = ctx.bodyAsClass(User.class);
                int roleId = r.getRoleId();
                try{
                    authService1.updateRoleId(userId, roleId);
                    ctx.result("Role successfully updated.");
                } catch (InvalidRoleException | InvalidUpdateException e){
                    ctx.result(e.getMessage());
                    ctx.status(400);
                } catch (UserNotFoundException e){
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
}}
