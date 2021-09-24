package servlet;

import database.DBManager;
import database.exception.DBException;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class Login extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Login.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            //TODO log
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();
        try {
            User user = manager.getUserByLogin(request.getParameter("login"));
            if (user == null || !user.getPassword().equals(request.getParameter("password"))) {
                session.setAttribute("userNotFound", "userNotFound");
                response.sendRedirect("/login");
            } else if (user.getPassword().equals(request.getParameter("password"))) {
                session.setAttribute("user", user);
                logger.info("User " + user.getLogin() + " entered");
                response.sendRedirect("/profile");
            }
        } catch (DBException e) {
            //todo log
            //todo redirect to err page
        }

    }
}
