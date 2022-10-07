package com.revature.contoller;

import com.revature.exception.InvalidAuthorizationException;
import com.revature.exception.InvalidUsernameException;
import com.revature.model.User;
import com.revature.service.AuthService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;

public class RegisterController {

    private AuthService authService = new AuthService();

    public void mapEndpoints(Javalin app){
        app.post("/register", (ctx) -> {
            User info = ctx.bodyAsClass(User.class);
            HttpSession httpSession = ctx.req.getSession();

            try{
                authService.register(info.getUsername(), info.getPassword(), info.getFirstName(), info.getLastName(), info.getUserAge(), info.getEmail());
                ctx.result("Successfully registered, go to login page to sign in.");

            }catch (InvalidUsernameException e) {
                ctx.result(e.getMessage());
                ctx.status(400);
            }catch (InvalidAuthorizationException e){
                ctx.result(e.getMessage());
                ctx.status(400);
            }
        });
    }
}
