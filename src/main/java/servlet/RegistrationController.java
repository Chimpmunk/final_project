package servlet;

import database.DBManager;
import database.exception.DBException;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DBManager manager = DBManager.getInstance();
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password")); //TODO hash password
        user.setRole("customer");
        try {
            manager.insertUser(user);
        } catch (DBException e){
            //TODO log
            //TODO redirect to registration page
        }
        response.sendRedirect("/profile");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
            request.getRequestDispatcher("/registration.jsp").forward(request,response);
        } catch (IOException | ServletException e){
            //TODO log
        }
    }
}
