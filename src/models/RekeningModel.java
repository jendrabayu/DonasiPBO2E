package models;

import helpers.DBHelper;
import helpers.MyHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class RekeningModel {
    
    private final static String TABLE = "rekening";
    private int id;
    private String namaBank, atasNama, noRekening;

    public RekeningModel(int id, String namaBank, String atasNama, String noRekening) {
        this.id = id;
        this.namaBank = namaBank;
        this.atasNama = atasNama;
        this.noRekening = noRekening;
    }
    
    public RekeningModel(){
        
    }

    public static boolean store(String namaBank, String atasNama, String noRekening){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("nama_bank", String.format("'%s'", namaBank));
        params.put("atas_nama", String.format("'%s'", atasNama));
        params.put("no_rekening", String.format("'%s'", noRekening));
        return DBHelper.insert(TABLE, params);
    }
    
    public static boolean delete(int id){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("deleted_at", String.format("'%s'", MyHelper.getCurrentTimeStamp() ));
        return DBHelper.update(TABLE, params, String.format("id = '%s'", id));
    }
    
    public static boolean update(int id, String namaBank, String atasNama ,String noRekening){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("nama_bank", String.format("'%s'", namaBank));
        params.put("atas_nama", String.format("'%s'", atasNama));
        params.put("no_rekening", String.format("'%s'", noRekening));
        return DBHelper.update(TABLE, params, String.format("id = '%s'", id));
    }
    
    
    public static ArrayList<RekeningModel> getAll(){
        ArrayList<RekeningModel> result = new ArrayList<>();
        ResultSet rs = DBHelper.selectAll(TABLE, "deleted_at IS NULL"); 
        try {
            while (rs.next()) {            
                result.add(new RekeningModel(
                    rs.getInt("id"), 
                    rs.getString("nama_bank"), 
                    rs.getString("atas_nama"),
                    rs.getString("no_rekening")
                ));
            } 
        } catch (SQLException e) {
            System.err.println(e);
        }     
        return result;
    }
    
    public static ArrayList<RekeningModel> get(int id){
        ArrayList<RekeningModel> result = new ArrayList<>();
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("id = '%s' AND deleted_at IS NULL", id));
        try {
            while (rs.next()) {            
                result.add(new RekeningModel(
                    rs.getInt("id"), 
                    rs.getString("nama_bank"), 
                    rs.getString("atas_nama"),
                    rs.getString("no_rekening")    
                ));
            } 
        } catch (SQLException e) {
            System.err.println(e);
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

    public String getAtasNama() {
        return atasNama;
    }

    public void setAtasNama(String atasNama) {
        this.atasNama = atasNama;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }
    
    



}
