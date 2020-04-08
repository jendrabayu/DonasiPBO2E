/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.DBHelper;
import helpers.MyHelper;
import helpers.Session;
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
    private static String telepon;
    private static String alamat;
    private static String password;
    private static String role;

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        User.nama = nama;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getTelepon() {
        return telepon;
    }

    public static void setTelepon(String telepon) {
        User.telepon = telepon;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        User.alamat = alamat;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        User.role = role;
    }

    

    
    public User(String id, String nama, String email, String telepon, String alamat, String password, String role) {
        
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.role = role;
        
    }
    
    public User(String id, String nama, String telepon, String alamat, String foto){
           
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;

        
    }
    
    public static int getRole(String email_user, String password_user){
        email_user = email_user.replaceAll("'", "").replaceAll(" ", "");
        password_user = password_user.replaceAll("'", "").replaceAll(" ", "");
        
        String id,nama,email,telepon,alamat,foto,password, role = null;
       
        
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("email = '%s' and password = '%s'", email_user, password_user));
        try {
            while ( rs.next() ) {  
                id = rs.getString("id");
                System.out.println(id);
                nama = rs.getString("nama");
                email = rs.getString("email");
                telepon = rs.getString("telepon");
                alamat = rs.getString("alamat");
             
                password = rs.getString("password");
                role = rs.getString("role");
                
                Session.buatSessionXML(id, nama, email, telepon, alamat, password, role);
              
        }
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        return Integer.parseInt(role);
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
        
        User user = new User(null, name, email, null, null, password, "0");
         
        Map<String, String> params = new LinkedHashMap<>();
        params.put("`id`", User.id);
        params.put("`nama`", String.format("'%s'", User.nama)); 
        params.put("`email`", String.format("'%s'", User.email));
        params.put("`telepon`", String.format("'%s'", User.telepon));
        params.put("`alamat`", String.format("'%s'", User.alamat));
        params.put("`password`", String.format("'%s'", User.password));
        params.put("`role`", String.format("'%s'", User.role));
        
        if (DBHelper.insert(TABLE, params)) {
            return 1;
        }
        return 0;
    }
    
    public static int updateUser(String id, String nama, String telepon, String alamat){
        
           
//        User user = new User(id, nama, telepon, alamat, foto);
         
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("`nama`", String.format("'%s'", nama));
        params.put("`telepon`", String.format("'%s'", telepon)); 
        params.put("`alamat`", String.format("'%s'", alamat)); 
//        params.put("`foto`", String.format("'%s'", nama)); 
        String clause = String.format("id = %s", id);
        if (DBHelper.update(TABLE, params, clause)) {
            return 1;
        }
        return 0;
       
    }
    
    public static int updatePassword(String id, String password){
           
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("`password`", String.format("'%s'", MyHelper.getMd5(password)));
        String clause = String.format("id = %s", id);
        if (DBHelper.update(TABLE, params, clause)) {
            return 1;
        }
        return 0;
    }
    
}
