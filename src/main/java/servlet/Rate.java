package servlet;

import database.DBManager;
import database.exception.DBException;
import entity.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rate")
public class Rate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long reviewId = Long.parseLong(req.getParameter("request_id"));
        long repairmanId = Long.parseLong(req.getParameter("repairman"));
        Review review = new Review();
        review.setId(reviewId);
        review.setText(req.getParameter("comment"));
        review.setRating(Integer.parseInt(req.getParameter("rating")));
        review.setRepairmanId(repairmanId);
        try{
            DBManager.getInstance().insertReview(review);
            resp.sendRedirect("/request-list/display?request_id=" + reviewId);
        } catch (DBException e){
            //todo log and redirect
        }
    }
}
