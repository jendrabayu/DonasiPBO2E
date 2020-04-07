package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler {
    public Connection conn;
    
    public Connection getConnection(){
        String url = String.format("jdbc:mysql://%s:%s/%s", Config.HOST, Config.PORT, Config.DATABASE);
        
        try {
            //Panggil Driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            conn = DriverManager.getConnection(url,Config.USERNAME,Config.PASSWORD);
        } catch (SQLException e) {
            System.err.println("Koneksi Gagal");
            e.printStackTrace();
        }
        
        return conn;
    }
}
