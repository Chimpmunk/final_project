package database;

import database.exception.DBException;
import entity.RepairRequest;
import entity.Review;
import entity.User;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static final Logger logger = Logger.getLogger(DBManager.class);

    private static DBManager instance;
    private DataSource ds;

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/project_db");
        } catch (NamingException e) {
            throw new IllegalStateException("Can not init DBManager", e);
        }

    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void insertUser(User user) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prstmt.setString(k++, user.getLogin());
            prstmt.setString(k++, user.getPassword());
            prstmt.setString(k++, user.getRole());

            if (prstmt.executeUpdate() > 0) {
                rs = prstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
            if (user.getRole().equals("customer")) {
                prstmt = con.prepareStatement(SqlConstants.INSERT_USER_ACCOUNT);
                prstmt.setLong(1, user.getId());
                prstmt.setInt(2, 0);
                prstmt.execute();
            }
            con.commit();

        } catch (SQLException e) {
            logger.error("Can not insert user", e);
            rollback(con);
            throw new DBException("Can not insert user", e);
        }
    }

    public void insertRequest(RepairRequest request) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prstmt.setString(k++, request.getTitle());
            prstmt.setString(k++, request.getDescription());
            prstmt.setString(k++, request.getStatus());
            prstmt.setObject(k++, request.getTime());

            if (prstmt.executeUpdate() > 0) {
                rs = prstmt.getGeneratedKeys();
                if (rs.next()) {
                    request.setId(rs.getLong(1));
                }
            }
            prstmt = con.prepareStatement(SqlConstants.INSERT_USER_REQUEST);
            k = 1;
            prstmt.setLong(k++, request.getId());
            prstmt.setLong(k++, request.getUserId());
            prstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not insert repair request", e);
            rollback(con);
            throw new DBException("Can not insert repair request", e);
        }
    }

    public User getUserByLogin(String login) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        User res = null;
        try {

            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.FIND_USER_BY_LOGIN);
            int k = 1;
            prstmt.setString(k++, login);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                res = new User();
                res.setLogin(login);
                res.setId(rs.getLong("id"));
                res.setRole(rs.getString("role"));
                res.setPassword(rs.getString("password"));
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find user", e);
            rollback(con);
            throw new DBException("Can not find user", e);
        }
        return res;
    }

    public User getUserById(long id) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        User res = null;
        try {

            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.FIND_USER_BY_ID);
            int k = 1;
            prstmt.setLong(k++, id);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                res = new User();
                res.setLogin(rs.getString("login"));
                res.setId(rs.getLong("id"));
                res.setRole(rs.getString("role"));
                res.setPassword(rs.getString("password"));
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find user", e);
            rollback(con);
            throw new DBException("Can not find user", e);
        }
        return res;
    }

    public List<RepairRequest> getRequestsByUser(User user) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        List<RepairRequest> res = new ArrayList<>();
        try {
            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.FIND_REQUESTS_BY_USER);
            int k = 1;
            prstmt.setLong(k++, user.getId());
            rs = prstmt.executeQuery();
            while (rs.next()) {
                repairRequest = new RepairRequest();
                repairRequest.setId(rs.getLong("id"));
                repairRequest.setTitle(rs.getString("title"));
                repairRequest.setDescription(rs.getString("description"));
                repairRequest.setUserId(rs.getLong("user_id"));
                repairRequest.setStatus(rs.getString("status"));
                Timestamp ts = (Timestamp) rs.getObject("time");
                repairRequest.setTime(ts.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
                res.add(repairRequest);
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find user", e);
            rollback(con);
            throw new DBException("Can not find user", e);
        }
        return res;
    }

    public List<RepairRequest> findAllRequests() throws DBException {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        List<RepairRequest> res = new ArrayList<>();
        try {
            con = getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(SqlConstants.FIND_ALL_REQUESTS);
            while (rs.next()) {
                repairRequest = new RepairRequest();
                repairRequest.setId(rs.getLong("id"));
                repairRequest.setTitle(rs.getString("title"));
                repairRequest.setDescription(rs.getString("description"));
                repairRequest.setUserId(rs.getLong("user_id"));
                repairRequest.setStatus(rs.getString("status"));
                Timestamp ts = (Timestamp) rs.getObject("time");
                repairRequest.setTime(ts.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
                res.add(repairRequest);
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find requests", e);
            rollback(con);
            throw new DBException("Can not find requests", e);
        }
        return res;
    }

    public RepairRequest getRequestById(Long id) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.FIND_REQUEST_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                repairRequest = new RepairRequest();
                repairRequest.setId(rs.getLong("id"));
                repairRequest.setTitle(rs.getString("title"));
                repairRequest.setDescription(rs.getString("description"));
                repairRequest.setUserId(rs.getLong("user_id"));
                repairRequest.setStatus(rs.getString("status"));
                repairRequest.setPrice(rs.getDouble("price"));
                Timestamp ts = (Timestamp) rs.getObject("time");
                repairRequest.setTime(ts.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find requests", e);
            rollback(con);
            throw new DBException("Can not find request", e);
        }
        return repairRequest;
    }

    public void setRepairPrice(long id, double price) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        RepairRequest repairRequest = null;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.SET_PRICE);
            int k = 1;
            statement.setDouble(k++, price);
            statement.setLong(k++, id);
            statement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not set price", e);
            rollback(con);
            throw new DBException("Can not set price", e);
        }
    }

    public void setStatus(long id, String status) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.SET_STATUS);
            int k = 1;
            statement.setString(k++, status);
            statement.setLong(k++, id);
            statement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not set status", e);
            rollback(con);
            throw new DBException("Can not set status", e);
        }
    }

    public double getAccountByUser(long id) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        double account = 0;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.FIND_ACCOUNT_BY_USER);
            int k = 1;
            statement.setLong(k++, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                account = rs.getDouble("account_value");
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find account", e);
            rollback(con);
            throw new DBException("Can not find account", e);
        }
        return account;
    }

    public void updateAccount(long id, double value) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.UPDATE_ACCOUNT);
            int k = 1;
            statement.setDouble(k++, value);
            statement.setLong(k++, id);
            statement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not update account", e);
            rollback(con);
            throw new DBException("Can not update account", e);
        }
    }


    public List<User> getRepairmanList() throws DBException {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();
        User u = null;
        try {
            con = getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(SqlConstants.FIND_ALL_REPAIRMAN);

            while (rs.next()) {
                u = new User();
                u.setId(rs.getLong("id"));
                u.setLogin(rs.getString("login"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find repairman", e);
            rollback(con);
            throw new DBException("Can not find repairman", e);
        }
        return list;
    }

    public void insertRequestAssignment(long requestId, long repairmanId) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.INSERT_REQUEST_ASSIGNMENT);
            int k = 1;
            statement.setLong(k++, requestId);
            statement.setLong(k++, repairmanId);
            statement.execute();
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not insert assignment", e);
            rollback(con);
            throw new DBException("Can not insert assignment", e);
        }
    }


    public List<RepairRequest> getRequestsByRepairman(User user) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        List<RepairRequest> res = new ArrayList<>();
        try {
            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.FIND_REQUESTS_BY_REPAIRMAN);
            int k = 1;
            prstmt.setLong(k++, user.getId());
            rs = prstmt.executeQuery();
            while (rs.next()) {
                repairRequest = new RepairRequest();
                repairRequest.setId(rs.getLong("id"));
                repairRequest.setTitle(rs.getString("title"));
                repairRequest.setDescription(rs.getString("description"));
                repairRequest.setUserId(rs.getLong("repairman_id"));
                repairRequest.setStatus(rs.getString("status"));
                Timestamp ts = (Timestamp) rs.getObject("time");
                repairRequest.setTime(ts.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
                res.add(repairRequest);
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find user", e);
            rollback(con);
            throw new DBException("Can not find user", e);
        }
        return res;
    }

    public List<RepairRequest> getRequestsFilteredSorted(String sql, long[] repairmanId, String[] statuses, int offset, User user) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        List<RepairRequest> res = new ArrayList<>();

        try {
            con = getConnection();
            prstmt = con.prepareStatement(sql);
            int k = 1;
            if (repairmanId != null) {
                for (long id : repairmanId) {
                    prstmt.setLong(k++, id);
                }
            }
            if (statuses != null) {
                for (String st : statuses) {
                    prstmt.setString(k++, st);
                }
            }
            if (!user.getRole().equals("manager")){
                prstmt.setLong(k++,user.getId());
            }
            prstmt.setInt(k++, offset);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                repairRequest = new RepairRequest();
                repairRequest.setId(rs.getLong("id"));
                repairRequest.setTitle(rs.getString("title"));
                repairRequest.setDescription(rs.getString("description"));
                repairRequest.setStatus(rs.getString("status"));
                Timestamp ts = (Timestamp) rs.getObject("time");
                repairRequest.setTime(ts.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
                res.add(repairRequest);
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find requests", e);
            rollback(con);
            throw new DBException("Can not find requests", e);
        }
        return res;
    }

    public int countFilteredRequests(String sql, long[] repairmanId, String[] statuses, User user) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        List<RepairRequest> res = new ArrayList<>();

        String[] arr = sql.split("\\*");
        String countSql = arr[0] + "count(*) as count " + arr[1];

        try {
            con = getConnection();
            prstmt = con.prepareStatement(countSql);
            int k = 1;
            if (repairmanId != null) {
                for (long id : repairmanId) {
                    prstmt.setLong(k++, id);
                }
            }
            if (statuses != null) {
                for (String st : statuses) {
                    prstmt.setString(k++, st);
                }
            }
            if (!user.getRole().equals("manager")){
                prstmt.setLong(k++,user.getId());
            }
            rs = prstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not count requests", e);
            rollback(con);
            throw new DBException("Can not count requests", e);
        }
        return 0;
    }

    public User getRepairmanByRequest(long reqId) throws DBException {
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        User res = null;
        try {

            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.FIND_REPAIRMAN_BY_REQUEST);
            int k = 1;
            prstmt.setLong(k++, reqId);
            rs = prstmt.executeQuery();
            if (rs.next()) {
                res = new User();
                res.setLogin(rs.getString("login"));
                res.setId(rs.getLong("id"));
                res.setRole(rs.getString("role"));
                res.setPassword(rs.getString("password"));
            }
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not find user", e);
            rollback(con);
            throw new DBException("Can not find user", e);
        }
        return res;
    }

    public void insertReview(Review review) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.INSERT_REVIEW);
            int k = 1;
            statement.setLong(k++, review.getId());
            statement.setString(k++, review.getText());
            statement.setInt(k++, review.getRating());
            statement.setLong(k++, review.getRepairmanId());
            statement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.error("Can not insert review", e);
            rollback(con);
            throw new DBException("Can not insert review", e);
        }
    }

    public Review getReview(long id) throws DBException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Review review = null;
        try {
            con = getConnection();
            statement = con.prepareStatement(SqlConstants.GET_REVIEW);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                review = new Review();
                review.setId(id);
                review.setText(rs.getString("text"));
                review.setRating(rs.getInt("rating"));
                review.setRepairmanId(rs.getLong("repairman_id"));
            }
        } catch (SQLException e) {
            logger.error("Can not find review", e);
            rollback(con);
            throw new DBException("Can not find review", e);
        }
        return review;
    }

    private void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            logger.error("Can not rollback connection", e);
        }
    }
}
