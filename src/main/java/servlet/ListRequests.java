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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/request-list")
public class ListRequests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole().equals("manager")) {
                try {
                    req.setAttribute("requests", requestFiltering(req));
                } catch (DBException e) {
                    //todo log and redirect
                }
            } else if (user.getRole().equals("repairman")) {
                try {
                    req.setAttribute("requests", DBManager.getInstance().getRequestsByRepairman(user));
                } catch (DBException e) {
                    //todo log and redirect
                }
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

    private Collection<RepairRequest> requestFiltering(HttpServletRequest req) throws DBException {
        String repairmanStr = req.getParameter("repairman");
        String statusStr = req.getParameter("status");
        List<RepairRequest> requests = null;
        List<RepairRequest> res = new ArrayList<>();
        if (repairmanStr != null) {
            String[] strArr = repairmanStr.split(",");
            long[] idArr = new long[strArr.length];
            for (int i = 0; i < idArr.length; i++) {
                idArr[i] = Long.parseLong(strArr[i]);
            }
            requests = DBManager.getInstance().getRequestsByMoreTanOneRepairman(idArr);
        } else {
            requests = DBManager.getInstance().findAllRequests();
        }
        if (statusStr != null) {
            String[] statuses = statusStr.split(",");
            requests.stream().filter(s -> {
                for (String str : statuses) {
                    if (str.equals(s.getStatus())) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toCollection(()->res));
            return res;
        } else {
            return requests;
        }
    }
}
