package models;

import helpers.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class RekeningModel {
    
    private final static String TABLE = "rekening";
    
    private int id;
    private String namaBank,  noRekening, createdAt, updatedAt;

    public RekeningModel(int id, String namaBank, String noRekening, String createdAt, String updatedAt) {
        this.id = id;
        this.namaBank = namaBank;
        this.noRekening = noRekening;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public RekeningModel(String namaBank, String noRekening) {
        this.namaBank = namaBank;
        this.noRekening = noRekening;
    }
    
    

    public static boolean create(String namaBank, String noRekening){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("nama_bank", String.format("'%s'", namaBank));
        params.put("no_rekening", String.format("'%s'", noRekening));
        return DBHelper.insert(TABLE, params);
    }
    
    public static boolean delete(int id){
        return DBHelper.delete(TABLE, Integer.toString(id));
    }
    
    public static boolean update(int id, String namaBank, String noRekening){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("nama_bank", String.format("'%s'", namaBank));
        params.put("no_rekening", String.format("'%s'", noRekening));
        return DBHelper.update(TABLE, params, String.format("id = '%s'", id));
    }
    
    public static String getUpdateAt(int id) throws SQLException{
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("id = '%s'", id));
        while (rs.next()) {            
            return rs.getString("updated_at");
        }
        return "";
    }
    
    public static ArrayList<RekeningModel> getAll() throws SQLException{
        ArrayList<RekeningModel> result = new ArrayList<>();
        ResultSet rs = DBHelper.selectAll(TABLE);
        while (rs.next()) {            
            result.add(new RekeningModel(
                    rs.getInt("id"), 
                    rs.getString("nama_bank"), 
                    rs.getString("no_rekening"),
                    rs.getString("created_at"),
                    rs.getString("updated_at")
            ));
        } 
        return result;
    }
    
    public static ArrayList<RekeningModel> get(int id) throws SQLException{
        ArrayList<RekeningModel> result = new ArrayList<>();
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("id = '%s'", id));
        while (rs.next()) {            
            result.add(new RekeningModel(
                    rs.getInt("id"), 
                    rs.getString("nama_bank"), 
                    rs.getString("no_rekening"),
                    rs.getString("created_at"),
                    rs.getString("updated_at")
            ));
        } 
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
