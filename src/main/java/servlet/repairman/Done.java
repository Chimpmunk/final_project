package servlet.repairman;

import database.DBManager;
import database.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/done")
public class Done extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long reqId = Long.parseLong(req.getParameter("request_id"));
        try{
            DBManager.getInstance().setStatus(reqId, "done");
            resp.sendRedirect("/request-list/display?request_id=" + reqId);
        } catch (DBException e){
            //todo log and redirect
        }
    }
}
