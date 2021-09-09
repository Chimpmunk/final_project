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
import java.io.IOException;

@WebServlet("/request-list/display")
public class DisplayRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("request_id");
        if(id!=null){
            long requestId = Long.parseLong(id);
            try{
                RepairRequest repairRequest = DBManager.getInstance().getRequestById(requestId);
                User author = DBManager.getInstance().getUserById(repairRequest.getUserId());
                req.setAttribute("req",repairRequest);
                req.setAttribute("requestAuthor",author);

                if (repairRequest.getStatus().equals("paid")){
                    req.setAttribute("repairmanList", DBManager.getInstance().getRepairmanList());
                }
                req.getRequestDispatcher("/view-request.jsp").forward(req,resp);
            } catch (DBException e){
                //todo log and redirect
            }
        } else {
            //todo redirect to err
        }


    }
}
