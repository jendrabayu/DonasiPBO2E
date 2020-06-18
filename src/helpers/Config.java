package helpers;

import java.sql.Connection;
import java.sql.Statement;

public class Config {
    public static Connection connection;
    public static Statement statement;
    
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String DATABASE = "pbo_donasi";
}
