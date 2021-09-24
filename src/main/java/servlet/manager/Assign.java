package servlet.manager;

import database.DBManager;
import database.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/assign")
public class Assign extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestId = Long.parseLong(req.getParameter("request_id"));
        long repId = Long.parseLong(req.getParameter("repairman"));
        try {
            DBManager.getInstance().insertRequestAssignment(requestId,repId);
            DBManager.getInstance().setStatus(requestId, "assigned");
            resp.sendRedirect("/request-list/display?request_id="+requestId);
        } catch (DBException e){
            //todo log and redirect
        }
    }
}
