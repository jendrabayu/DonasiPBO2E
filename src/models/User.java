/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ACER
 */
public class User {
    
     public static final String TABLE = "user";
    private static String id;
    private static String nama;
    private static String email;
    private static String password;
    private static String role;

    public User(String id, String nama, String email, String password, String role) {
        
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.role = role;
        
    }
    
    public static String getRole(String email, String password){
        email = email.replaceAll("'", "").replaceAll(" ", "");
        password = password.replaceAll("'", "").replaceAll(" ", "");
        
        String role = "";
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("email = '%s' and password = '%s'", email, password));
        try {
            while ( rs.next() ) {           
            role = rs.getString("role");
        }
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        return role;
    }
    
    public static int getLoginStatus(String email, String password){
        email = email.replaceAll("'", "").replaceAll(" ", "");
        password = password.replaceAll("'", "").replaceAll(" ", "");
        
        ResultSet rs = DBHelper.selectAll(TABLE,  String.format("email = '%s' and password = '%s'", email, password));
        try {
             if (!rs.next()) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return 0;
    }
    
    public static int addNewUser(String name, String email, String password){
        
        User user = new User(null, name, email, password, "USER");
         
        Map<String, String> params = new LinkedHashMap<>();
        params.put("`id`", User.id);
        params.put("`nama_lengkap`", String.format("'%s'", User.nama)); 
        params.put("`email`", String.format("'%s'", User.email));
        params.put("`password`", String.format("'%s'", User.password));
        params.put("`role`", String.format("'%s'", User.role));
        
        if (DBHelper.insert(TABLE, params)) {
            return 1;
        }
        
        return 0;
    }
    
}
