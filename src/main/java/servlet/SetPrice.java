package servlet;

import database.DBManager;
import database.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/set-price")
public class SetPrice extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("request_id"));
        try {
            DBManager.getInstance().setRepairPrice(id,
                    Double.parseDouble(req.getParameter("price")));
            resp.sendRedirect("/request-list/display?request_id="+id);
        } catch (DBException e) {
            //todo log and redirect
        }

    }
}
