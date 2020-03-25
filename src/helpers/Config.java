/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author ACER
 */
public class Config {
    public static Connection connection;
    public static Statement statement;
    
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String DATABASE = "donasi";
}
