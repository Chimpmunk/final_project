package servlet;

import database.DBManager;
import database.exception.DBException;
import entity.RepairRequest;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/request-list")
public class ListRequests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals("manager")) {
                try {
                    req.setAttribute("requests", DBManager.getInstance().findAllRequests());
                } catch (DBException e) {
                    //todo log and redirect
                }
            } else if (user.getRole().equals("repairman")) {
                //todo write code here
            } else {
                try {
                    req.setAttribute("requests", DBManager.getInstance().getRequestsByUser(user));
                } catch (DBException e) {
                    //todo log and redirect
                }
            }

            req.getRequestDispatcher("/request-list.jsp").forward(req, resp);

        }
    }
}
