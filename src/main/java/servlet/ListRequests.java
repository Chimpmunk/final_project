package servlet;

import database.DBManager;
import database.SqlConstants;
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


        try {
            String repairmanStr = req.getParameter("repairman");
            String[] repairmanStrArr = null;
            long[] repairmanArr = null;
            if (repairmanStr != null && user.getRole().equals("manager")) {
                repairmanStrArr = req.getParameterValues("repairman");
                repairmanArr = new long[repairmanStrArr.length];
                for (int i = 0; i < repairmanArr.length; i++) {
                    repairmanArr[i] = Long.parseLong(repairmanStrArr[i]);
                }
            }
            String statusStr = req.getParameter("status");
            String[] statusArr = null;
            if (statusStr != null) {
                statusArr = req.getParameterValues("status");
            }
            String pageStr = req.getParameter("page");

            int offset = 0;
            if (pageStr != null) {
                offset = 20 * (Integer.parseInt(pageStr) - 1);
                req.setAttribute("currentPage", "page=" + pageStr);
            } else {
                req.setAttribute("currentPage", "page=1");
            }
            String sql = prepareSql(req);
            String[] sqlArr = sql.split("limit");
            int requestCount = DBManager.getInstance().countFilteredRequests(sqlArr[0], repairmanArr, statusArr, user);
            int pageCount = 1 + requestCount / 20;
            int[] pages = new int[pageCount];
            for (int i = 0; i < pageCount; i++) {
                pages[i] = i + 1;
            }
            List<RepairRequest> list = DBManager.getInstance()
                    .getRequestsFilteredSorted(sql, repairmanArr, statusArr, offset, user);
            req.setAttribute("repairmanList", DBManager.getInstance().getRepairmanList());
            req.setAttribute("pages", pages);
            req.setAttribute("requests", list);
        } catch (DBException e) {
            e.printStackTrace();
            //todo log and redirect
        }
        req.getRequestDispatcher("/request-list.jsp").forward(req, resp);
    }


    private String prepareSql(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        String repairmanStr = req.getParameter("repairman");
        String[] repairmanArr = null;
        String statusStr = req.getParameter("status");
        String[] statusArr = null;
        String sortStr = req.getParameter("sort");
        StringBuilder sql = new StringBuilder();
        sql.append(SqlConstants.FIND_REQUESTS_SORTED_AND_FILTERED);
        if (repairmanStr != null && user.getRole().equals("manager")) {
            repairmanArr = req.getParameterValues("repairman");
            sql.append(SqlConstants.FROM_RR);
            sql.append(SqlConstants.FROM_RA);
            sql.append(SqlConstants.WHERE);
            sql.append(SqlConstants.REPAIRMAN);
            for (int i = 0; i < repairmanArr.length - 1; i++) {
                sql.append(SqlConstants.ADD_REPAIRMAN);
            }
            sql.append(SqlConstants.CLOSE_BRACKETS);
        } else {
            sql.append(SqlConstants.FROM_RR);
        }
        if (user.getRole().equals("customer")) {
            sql.append(SqlConstants.FROM_UR);
        }
        if (user.getRole().equals("repairman")) {
            sql.append(SqlConstants.FROM_RA);
        }
        if (statusStr != null) {
            statusArr = req.getParameterValues("status");
            if (repairmanArr != null) {
                sql.append(SqlConstants.AND);
            } else {
                sql.append(SqlConstants.WHERE);
            }
            sql.append(SqlConstants.STATUS);
            for (int i = 0; i < statusArr.length - 1; i++) {
                sql.append(SqlConstants.ADD_STATUS);
            }
            sql.append(SqlConstants.CLOSE_BRACKETS);
        }
        if (user.getRole().equals("customer")) {
            if (statusStr != null) {
                sql.append(" and ");
            } else {
                sql.append(" where ");
            }
            sql.append(SqlConstants.USER);
            sql.append(SqlConstants.IF_USER);
        }
        if (user.getRole().equals("repairman")) {
            if (statusStr != null) {
                sql.append(" and ");
            } else {
                sql.append(" where ");
            }
            sql.append(SqlConstants.REPAIRMAN);
            sql.append(SqlConstants.IF_REPAIRMAN);
        }
        if (repairmanArr != null) {
            sql.append(SqlConstants.IF_REPAIRMAN);
        }
        if (sortStr != null) {
            sql.append(String.format(SqlConstants.ORDER, sortStr));
        }
        sql.append(SqlConstants.LIMIT);
        return sql.toString();
    }
}
