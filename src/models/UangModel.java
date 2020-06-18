package models;

import helpers.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class UangModel {
    
    private final static String TABLE = "uang";
    
    public static boolean store(String namaBank, String atasNama, String noRekening, long jumlah, int rekeningId){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("user_id", String.format("'%s'", UserModel.getId()));
        params.put("rekening_id", String.format("'%s'", rekeningId));
        params.put("nama_bank", String.format("'%s'", namaBank));
        params.put("atas_nama", String.format("'%s'", atasNama));
        params.put("no_rekening", String.format("'%s'", noRekening));
        params.put("jumlah", String.format("'%s'", jumlah));
        return DBHelper.insert(TABLE, params);
    }
    

    public static long getTotal(){
        long total = 0;
        ResultSet rs = DBHelper.selectAll("total_uang", "id = '1'");
        try {
            while (rs.next()) {                
                total = rs.getLong("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public static long getJumlah(int id){
        long jumlahUang = 0;
                
        ResultSet rs = DBHelper.selectAll("uang", String.format("id = '%s'", id));
        try {
            while (rs.next()) {                
                jumlahUang = rs.getLong("jumlah");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return jumlahUang;
    }

    
    public static boolean update(int id, int status){
        Map<String, String> paramsUang = new LinkedHashMap<String, String>();   
        Map<String, String> paramsTotalUang = new LinkedHashMap<String, String>();
        
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("id = '%s'", id));
        long jumlah = 0;
        
        try {
            while (rs.next()) {                
                jumlah = rs.getLong("jumlah");
            }
            
            if(status == 2){
                paramsUang.put("`status_id`", String.format("'%s'", 2));
                paramsTotalUang.put("`total`", String.format("'%s'", jumlah + UangModel.getTotal()));
                if (DBHelper.update("uang", paramsUang, String.format("id = '%s'", id)) == true
                        && 
                    DBHelper.update("total_uang", paramsTotalUang, String.format("id = %s", 1)) == true) 
                {
                    return true;
                }  
                return false;
            }else if(status == 3){
                paramsUang.put("`status_id`", String.format("'%s'", 3));
                if (DBHelper.update("uang", paramsUang, String.format("id = '%s'", id)) == true)
                {
                    return true;
                }  
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return false;
    }
    
    
    
}
