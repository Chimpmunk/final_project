package database;

public class SqlConstants {

    public static final String INSERT_USER = "insert into user (login,password,role) values (?,?,?);";
    public static final String INSERT_USER_ACCOUNT = "insert into user_account (user_id, account_value) values (?, ?);";
    public static final String FIND_USER_BY_LOGIN = "select * from user where login like ?;";
    public static final String INSERT_REQUEST = "insert into repair_request (title, description, status, time, user_id) values (?,?,?,?,?);";
    public static final String FIND_REQUESTS_BY_USER = "select * from repair_request where user_id = ?;";
    public static final String FIND_ALL_REQUESTS = "select * from repair_request;";
    public static final String INSERT_REQUEST_ASSIGNMENT = "insert into request_assignment (request_id, repairman_id) values (?,?);";
}
