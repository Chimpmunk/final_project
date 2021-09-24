package servlet.manager;

import database.DBManager;
import database.exception.DBException;
import entity.RepairRequest;
import sun.security.pkcs11.Secmod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pay")
public class Payment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long reqId = Long.parseLong(req.getParameter("request_id"));
        long uId = Long.parseLong(req.getParameter("user_id"));
        try{
            double acc = DBManager.getInstance().getAccountByUser(uId);
            RepairRequest repairRequest = DBManager.getInstance().getRequestById(reqId);
            acc-=repairRequest.getPrice();
            DBManager.getInstance().updateAccount(uId,acc);
            DBManager.getInstance().setStatus(reqId, "paid");
            resp.sendRedirect("/request-list/display?request_id=" + reqId);
        } catch (DBException e){
            //todo log and redirect
        }
    }
}
