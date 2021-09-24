package servlet.manager;

import database.DBManager;
import database.exception.DBException;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-repairman")
public class AddRepairman extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager manager = DBManager.getInstance();
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password")); //TODO hash password
        user.setRole("repairman");
        try {
            manager.insertUser(user);
        } catch (DBException e){
            //TODO log
            //TODO redirect to registration page
        }
        resp.sendRedirect("/profile");
    }
}
