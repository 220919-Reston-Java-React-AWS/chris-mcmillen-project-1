package com.revature;

import com.revature.contoller.*;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.repository.ReimbursementRepository;
import com.revature.repository.UserRepository;

import io.javalin.Javalin;

import java.sql.SQLException;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        Javalin app = Javalin.create();

        UserRepository ur = new UserRepository();
        ReimbursementRepository rr = new ReimbursementRepository();

        AuthController ac = new AuthController();
        ac.mapEndpoints(app);

        ReimbursementController rc = new ReimbursementController();
        rc.mapEndpoints(app);

        RegisterController registerc = new RegisterController();
        registerc.mapEndpoints(app);


        SubmitReimbursementController src = new SubmitReimbursementController();
        src.mapEndpoints(app);

        RolesController rc2 = new RolesController();
        rc2.mapEndpoints(app);

       app.start(8080);
    }
}
