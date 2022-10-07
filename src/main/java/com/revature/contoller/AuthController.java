package com.revature.contoller;

import com.revature.exception.InvalidLoginException;
import com.revature.model.User;
import com.revature.service.AuthService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;

public class AuthController {

    private AuthService authService = new AuthService();

    public void mapEndpoints(Javalin app) {
        app.post("/login", (ctx) -> {
            User credentials = ctx.bodyAsClass(User.class);

            try{
                User user = authService.login(credentials.getUsername(), credentials.getPassword());
                HttpSession session = ctx.req.getSession();
                session.setAttribute("user", user);
                ctx.result("Successfully logged in.");
            } catch(InvalidLoginException e){
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });

        app.post("/logout", (ctx) -> {
            ctx.req.getSession().invalidate();
            ctx.result("Successfully logged out.");
        });

    }
}
