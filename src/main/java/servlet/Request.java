package servlet;


import database.DBManager;
import database.exception.DBException;
import entity.RepairRequest;
import entity.Status;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

@WebServlet("/request")
public class Request extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher("/request.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            //TODO log
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        RepairRequest request = new RepairRequest();
        request.setTitle(req.getParameter("title"));
        request.setDescription(req.getParameter("description"));
        request.setUserId(user.getId());
        request.setTime(LocalDateTime.now());
        request.setStatus(Status.waiting_for_acceptance.toString());


        try{
            DBManager.getInstance().insertRequest(request);
        } catch (DBException e){
            //todo log and redirect

        }
        resp.sendRedirect("/profile");
    }
}
