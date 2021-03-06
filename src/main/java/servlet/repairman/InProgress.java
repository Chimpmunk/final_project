package servlet.repairman;

import database.DBManager;
import database.exception.DBException;
import entity.RepairRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/in-progress")
public class InProgress extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long reqId = Long.parseLong(req.getParameter("request_id"));
        try{
            DBManager.getInstance().setStatus(reqId, "in_progress");
            resp.sendRedirect("/request-list/display?request_id=" + reqId);
        } catch (DBException e){
            //todo log and redirect
        }
    }
}
