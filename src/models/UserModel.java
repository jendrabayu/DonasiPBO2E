package models;

import helpers.DBHelper;
import helpers.MyHelper;
import helpers.Session;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class UserModel{
    
    public static String TABLE = "users";
 
    
    private static int id, role;
    private static String
            nama,
            email,
            no_telp,
            alamat,
            password,
            created_at,
            updated_at;

    public UserModel(int id, String nama, String email, String no_telp, String alamat, String password, int role, String created_at, String updated_at ) {
        UserModel.id = id; 
        UserModel.nama = nama;
        UserModel.email = email;
        UserModel.no_telp = no_telp;
        UserModel.alamat = alamat;
        UserModel.password = password;
        UserModel.role = role;
        UserModel.created_at = created_at;
        UserModel.updated_at = updated_at;
    }
    
    public UserModel(){
        
    }

    
    public static boolean store(String nama, String email, String no_telp, String alamat, String password){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("`nama`", String.format("'%s'", nama));
        params.put("`email`", String.format("'%s'", email));
        params.put("`no_telp`", String.format("'%s'", no_telp));
        params.put("`alamat`", String.format("'%s'", alamat));
        params.put("`password`", String.format("'%s'", MyHelper.getMd5(password)));
        return DBHelper.insert(TABLE, params);  
    }
    
    
    public static boolean update(String nama, String no_telp, String alamat){        
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("`nama`", String.format("'%s'", nama));
        params.put("`no_telp`", String.format("'%s'", no_telp)); 
        params.put("`alamat`", String.format("'%s'", alamat)); 
        String clause = String.format("id = %s", UserModel.id);    
        return DBHelper.update(TABLE, params, clause);
    }


    public static boolean updatePassword(String password){           
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("`password`", String.format("'%s'", MyHelper.getMd5(password)));
        return DBHelper.update(TABLE, params, String.format("id = %s", UserModel.id));
    }
    
    public static int getRole(String email, String password) throws SQLException{    
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("email = '%s' and password = '%s'", email, MyHelper.getMd5(password)));
        while ( rs.next() ) {
            UserModel.setId(rs.getInt("id"));
            return rs.getInt("role");                
        }  
        return 0;
    }
    
    public static void CreateSession() throws SQLException{
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("id = '%s'", UserModel.getId()));
        while ( rs.next() ) {       
               UserModel user = new UserModel(
                   rs.getInt("id"), 
                   rs.getString("nama"), 
                   rs.getString("email"), 
                   rs.getString("no_telp"), 
                   rs.getString("alamat"),
                   rs.getString("password"), 
                   rs.getInt("role"), 
                   rs.getString("created_at"), 
                   rs.getString("updated_at"));
               Session.createSession(user);
               Session.cekSession();    
        }
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserModel.id = id;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        UserModel.nama = nama;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserModel.email = email;
    }

    public static String getNo_telp() {
        return no_telp;
    }

    public static void setNo_telp(String no_telp) {
        UserModel.no_telp = no_telp;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        UserModel.alamat = alamat;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserModel.password = password;
    }

    public static int getRole() {
        return role;
    }

    public static void setRole(int role) {
        UserModel.role = role;
    }

    public static String getCreated_at() {
        return created_at;
    }

    public static void setCreated_at(String created_at) {
        UserModel.created_at = created_at;
    }

    public static String getUpdated_at() {
        return updated_at;
    }

    public static void setUpdated_at(String updated_at) {
        UserModel.updated_at = updated_at;
    }
}
