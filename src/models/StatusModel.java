/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class StatusModel {
    
    private int id;
    private String status;

    public StatusModel(int id, String status) {
        this.id = id;
        this.status = status;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static ArrayList<StatusModel> getAll(){
        ArrayList<StatusModel> result = new ArrayList<>();
        
        ResultSet rs = DBHelper.selectAll("status");
        try {
            while (rs.next()) {                
                result.add(new StatusModel(rs.getInt("id"), rs.getString("nama")));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return result;
    }
    
    
}
