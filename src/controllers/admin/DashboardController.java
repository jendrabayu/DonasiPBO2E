package controllers.admin;

import helpers.DBHelper;
import helpers.MyHelper;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.UangModel;
import models.DonaturModel;
import models.PenerimaModel;
        

public class DashboardController implements Initializable {
    
    @FXML
    private Label jumlahDonatur;

    @FXML
    private Label jumlahPenerima;
    
        @FXML
    private Label jumlahMakanan;
    
    @FXML
    private Label jumlahUang;
    
    @FXML
    private Label uangBelumDiproses;

    @FXML
    private Label makananBelumDiproses;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        uangSedangDirposes();
        makananSedangDirposes();
        jumlahMakanan();
        try {
            this.jumlahDonatur.setText(Integer.toString(DonaturModel.getAll().size()));
            this.jumlahPenerima.setText(Integer.toString(PenerimaModel.getAll().size()));
            this.jumlahUang.setText(MyHelper.rupiahFormat(Long.toString(UangModel.getTotal())));
            
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    private void uangSedangDirposes(){
        
        ResultSet rs = DBHelper.query(""
                + "SELECT COUNT(id) as jumlah "
                + "FROM uang "
                + "WHERE status_id = '1' ");
        try {
            while (rs.next()) {                
                uangBelumDiproses.setText(rs.getString("jumlah"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        
    }
    
    private void makananSedangDirposes(){
        
        ResultSet rs = DBHelper.query(""
                + "SELECT COUNT(id) as jumlah "
                + "FROM makanan "
                + "WHERE status_id = '1' ");
        try {
            while (rs.next()) {                
                makananBelumDiproses.setText(rs.getString("jumlah"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        
    }
    
      private void jumlahMakanan(){
        
        ResultSet rs = DBHelper.query(""
                + "SELECT SUM(jumlah) as jumlah "
                + "FROM makanan "
                + "WHERE jumlah > '0' ");
        try {
            while (rs.next()) {                
                jumlahMakanan.setText(rs.getString("jumlah"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        
    }
}
