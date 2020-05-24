/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.admin.UangController;
import helpers.DBHelper;
import helpers.Dialog;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ACER
 */
public class UangModel {
    
    
    
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

    
    public static int updateStatus(String status, int id, long total){
        Map<String, String> params = new LinkedHashMap<String, String>();   
        Map<String, String> params2 = new LinkedHashMap<String, String>();

        if (status.equals("SELESAI") || status.equals("GAGAL")) {
            params.put("`status`", String.format("'%s'", status));
            params2.put("`total`", String.format("'%s'", total));
            
            if (status.equals("GAGAL")) {
                if (DBHelper.update("uang", params, String.format("id = '%s'", id))) {
                    return 1;
                }   
                return 0;
            }
            
            if (status.equals("SELESAI")) {
                if (DBHelper.update("uang", params, String.format("id = '%s'", id)) == true
                        && 
                    DBHelper.update("total_uang", params2, String.format("id = %s", 1)) == true) {
                    return 1;
                }  
                return 0;
            }

        }
        
        return 0;
    }
    
    
    
}
