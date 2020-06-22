package models;

import helpers.DBHelper;
import helpers.MyHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PenerimaModel extends Person{
    private final static String TABLE = "penerima";
    private int jumlahOrang;
    
    public PenerimaModel(int id, String nama, String email, String telepon, String alamat, int jumlahOrang) {
        super(id, nama, email, telepon, alamat);
        this.jumlahOrang = jumlahOrang;
    }
    
    public PenerimaModel(){
        
    }

    public static ArrayList<PenerimaModel> getAll(String keyword){
        ArrayList<PenerimaModel> result = new ArrayList<PenerimaModel>();
        ResultSet resultSet = DBHelper.selectAll(TABLE, "nama LIKE '%"+keyword+"%'");
        
        try {
            while (resultSet.next()){
                PenerimaModel penerima = new PenerimaModel();
                penerima.setId(resultSet.getInt("id"));
                penerima.setNama(resultSet.getString("nama"));
                penerima.setEmail(resultSet.getString("email"));
                penerima.setNo_telp(resultSet.getString("no_telp"));
                penerima.setAlamat(resultSet.getString("alamat"));
                penerima.setJumlahOrang(resultSet.getInt("jumlah_orang"));
                result.add(penerima);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        return result;
    }
    
    public static ArrayList<PenerimaModel> getAll(){
        ArrayList<PenerimaModel> result = new ArrayList<PenerimaModel>();
        ResultSet resultSet = DBHelper.selectAll(TABLE, "deleted_at IS NULL");
        try {
             while (resultSet.next()){
                PenerimaModel penerima = new PenerimaModel();
                penerima.setId(resultSet.getInt("id"));
                penerima.setNama(resultSet.getString("nama"));
                penerima.setEmail(resultSet.getString("email"));
                penerima.setNo_telp(resultSet.getString("no_telp"));
                penerima.setAlamat(resultSet.getString("alamat"));
                penerima.setJumlahOrang(resultSet.getInt("jumlah_orang"));
                result.add(penerima);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static ArrayList<PenerimaModel> get(int id){
        ArrayList<PenerimaModel> result = new ArrayList<PenerimaModel>();
        ResultSet resultSet = DBHelper.selectAll(TABLE, "id = '"+id+"' AND deleted_at IS NULL");
        try {
            while (resultSet.next()){
               PenerimaModel penerima = new PenerimaModel();
               penerima.setId(resultSet.getInt("id"));
               penerima.setNama(resultSet.getString("nama"));
               penerima.setEmail(resultSet.getString("email"));
               penerima.setNo_telp(resultSet.getString("no_telp"));
               penerima.setAlamat(resultSet.getString("alamat"));
               penerima.setJumlahOrang(resultSet.getInt("jumlah_orang"));
               result.add(penerima);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
       
    }
    
    public static boolean store(String nama, String email, String no_telp, int jumlah_orang, String alamat){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("`nama`", String.format("'%s'",nama));
        params.put("`email`", String.format("'%s'", email));
        params.put("`no_telp`", String.format("'%s'", no_telp));
        params.put("`jumlah_orang`", String.format("'%s'", jumlah_orang));
        params.put("`alamat`", String.format("'%s'", alamat));         
        return DBHelper.insert("penerima", params);
    }
    
    public static boolean update(int id, String nama, String email, String no_telp, int jumlah_orang, String alamat){        
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("`nama`", String.format("'%s'", nama));
        params.put("`email`", String.format("'%s'", email));
        params.put("`no_telp`", String.format("'%s'", no_telp)); 
        params.put("`jumlah_orang`", String.format("'%s'", jumlah_orang));
        params.put("`alamat`", String.format("'%s'", alamat));    
        return DBHelper.update("penerima", params, String.format("id = %s", id));
    }
    
    public static boolean delete(int id){
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("`deleted_at`", String.format("'%s'", MyHelper.getCurrentTimeStamp()));  
        return DBHelper.update("penerima", params, String.format("id = %s", id));
    }

    public int getJumlahOrang() {
        return jumlahOrang;
    }

    public void setJumlahOrang(int jumlahOrang) {
        this.jumlahOrang = jumlahOrang;
    }
    
}
