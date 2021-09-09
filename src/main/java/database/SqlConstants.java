package database;

import com.sun.imageio.plugins.jpeg.JPEGStreamMetadataFormat;

public class SqlConstants {

    //for table user
    public static final String INSERT_USER = "insert into user (login,password,role) values (?,?,?);";
    public static final String FIND_USER_BY_LOGIN = "select * from user where login like ?;";
    public static final String FIND_USER_BY_ID = "select * from user where id = ?";
    public static final String FIND_ALL_REPAIRMAN = "select * from user where role like 'repairman';";

    //for table user_account
    public static final String INSERT_USER_ACCOUNT = "insert into user_account (user_id, account_value) values (?, ?);";
    public static final String FIND_ACCOUNT_BY_USER = "select * from user_account where user_id = ?;";
    public static final String UPDATE_ACCOUNT = "update user_account set account_value = ? where user_id = ?";


    //for table request
    public static final String INSERT_REQUEST = "insert into repair_request (title, description, status, time) values (?,?,?,?);";
    public static final String FIND_ALL_REQUESTS = "select * from repair_request as rr, user_request as ur, user as u" +
            " where ur.request_id = rr.id" +
            " and u.id = ur.user_id";
    public static final String FIND_REQUEST_BY_ID = "select * from repair_request as rr, user_request as ur" +
            " where id = ?" +
            " and rr.id = ur.request_id;";
    public static final String INSERT_USER_REQUEST = "insert into user_request (request_id, user_id) values (?,?);";
    public static final String FIND_REQUESTS_BY_USER = "select * from repair_request as rr, user_request as ur"
            + " where ur.user_id = ? "
            + " and ur.request_id = rr.id";
    public static final String SET_PRICE = "update repair_request set price = ?, status = 'waiting_for_payment' where id = ?";
    public static final String SET_STATUS = "update repair_request set status = ? where id = ?";

    //for table request_assignment
    public static final String INSERT_REQUEST_ASSIGNMENT = "insert into request_assignment (request_id, repairman_id) values (?,?);";
    public static final String FIND_REQUESTS_BY_REPAIRMAN = "select * from repair_request as rr, request_assignment as ra"
            + " where ra.repairman_id = ? "
            + " and ra.request_id = rr.id";

    public static final String FIND_REQUESTS_BY_REPAIRMAN_MORE_THAN_ONE = "select * from repair_request as rr, request_assignment as ra "
            + "where ra.repairman_id = ? ";
    public static final String ADD_REPAIRMAN_TO_FILTER = "or ra.repairman_id = ? ";
    public static final String FIND_BY_MORE_THAN_ONE_END = "and ra.request_id = rr.id";
}
