package servlet;

import database.DBManager;
import database.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/refill")
public class RefillAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long requestId = Long.parseLong(req.getParameter("request_id"));
        Long userId = Long.parseLong(req.getParameter("user_id"));
        Double value = Double.parseDouble(req.getParameter("value"));
        try {
            double acc = DBManager.getInstance().getAccountByUser(userId);
            DBManager.getInstance().updateAccount(userId, acc + value);
            resp.sendRedirect("/request-list/display?request_id=" + requestId);
        } catch (DBException e) {
            //todo log and redirect
        }

    }
}
