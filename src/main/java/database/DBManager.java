package database;

import database.exception.DBException;
import entity.RepairRequest;
import entity.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static DBManager instance;
    private DataSource ds;

    private DBManager() {
        try{
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/project_db");
        } catch (NamingException e){
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

    public void insertUser(User user) throws DBException{
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            int k=1;
            prstmt.setString(k++,user.getLogin());
            prstmt.setString(k++,user.getPassword());
            prstmt.setString(k++,user.getRole());

            if(prstmt.executeUpdate()>0){
                rs=prstmt.getGeneratedKeys();
                if(rs.next()){
                    user.setId(rs.getLong(1));
                }
            }
            if(user.getRole().equals("customer")){
                prstmt = con.prepareStatement(SqlConstants.INSERT_USER_ACCOUNT);
                prstmt.setLong(1,user.getId());
                prstmt.setInt(2,0);
                prstmt.execute();
            }
            con.commit();

        } catch (SQLException e){
            //TODO log
            rollback(con);
            throw new DBException("Can not insert user", e);
        }
    }

    public void insertRequest(RepairRequest request) throws DBException{
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            int k=1;
            prstmt.setString(k++,request.getTitle());
            prstmt.setString(k++,request.getDescription());
            prstmt.setString(k++,request.getStatus());
            prstmt.setObject(k++,request.getTime());
            prstmt.setLong(k++,request.getUserId());

            if(prstmt.executeUpdate()>0){
                rs=prstmt.getGeneratedKeys();
                if(rs.next()){
                    request.setId(rs.getLong(1));
                }
            }
            con.commit();
        } catch (SQLException e){
            //TODO log
            rollback(con);
            throw new DBException("Can not insert repair request", e);
        }
    }

    public User getUserByLogin(String login) throws DBException{
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        User res = null;
        try{

            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.FIND_USER_BY_LOGIN);
            int k=1;
            prstmt.setString(k++,login);
            rs = prstmt.executeQuery();
            if(rs.next()){
                res = new User();
                res.setLogin(login);
                res.setId(rs.getLong("id"));
                res.setRole(rs.getString("role"));
                res.setPassword(rs.getString("password"));
            }
            con.commit();
        } catch (SQLException e){
            //TODO log
            rollback(con);
            throw new DBException("Can not insert user", e);
        }
        return res;
    }

    public List<RepairRequest> getRequestsByUser(User user) throws DBException{
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        List<RepairRequest> res = new ArrayList<>();
        try{
            con = getConnection();
            prstmt = con.prepareStatement(SqlConstants.FIND_REQUESTS_BY_USER);
            int k=1;
            prstmt.setLong(k++,user.getId());
            rs = prstmt.executeQuery();
            while(rs.next()){
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
        } catch (SQLException e){
            //TODO log
            rollback(con);
            throw new DBException("Can not insert user", e);
        }
        return res;
    }

    public List<RepairRequest> findAllRequests() throws DBException{
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        RepairRequest repairRequest = null;
        List<RepairRequest> res = new ArrayList<>();
        try{
            con = getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(SqlConstants.FIND_ALL_REQUESTS);
            while(rs.next()){
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
        } catch (SQLException e){
            //TODO log
            rollback(con);
            throw new DBException("Can not insert user", e);
        }
        return res;
    }

    private void rollback(Connection con){
        try{
            if(con!=null){
                con.rollback();
            }
        } catch (SQLException e){
            //TODO log
        }
    }
}
