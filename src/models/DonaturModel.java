package models;

import helpers.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DonaturModel extends Person{
    
    private static final String TABLE = "users";
    
    public DonaturModel(){
        
    }

    public DonaturModel(int id, String nama, String email, String no_telp, String alamat) {
        super(id, nama, email, no_telp, alamat);
    }
   
    public static ArrayList<DonaturModel> getAll(String keyword){
        ResultSet rs = DBHelper.selectAll(TABLE,"role = '2' AND nama LIKE '%"+keyword+"%'");
        ArrayList<DonaturModel> result = new ArrayList<DonaturModel>();
        try {
            while (rs.next()){
                DonaturModel donatur = new DonaturModel();
                donatur.setId(rs.getInt("id"));
                donatur.setNama(rs.getString("nama"));
                donatur.setEmail(rs.getString("email"));
                donatur.setNo_telp(rs.getString("no_telp"));
                donatur.setAlamat(rs.getString("alamat"));                                                 
                result.add(donatur);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    
    public static ArrayList<DonaturModel> getAll(){
        ResultSet rs = DBHelper.selectAll(TABLE,"role = '2'");
        ArrayList<DonaturModel> result = new ArrayList<DonaturModel>();
        try {
            while (rs.next()){
                DonaturModel donatur = new DonaturModel();
                donatur.setId(rs.getInt("id"));
                donatur.setNama(rs.getString("nama"));
                donatur.setEmail(rs.getString("email"));
                donatur.setNo_telp(rs.getString("no_telp"));
                donatur.setAlamat(rs.getString("alamat"));                                                 
                result.add(donatur);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
