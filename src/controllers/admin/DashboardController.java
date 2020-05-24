package controllers.admin;

import helpers.MyHelper;
import java.net.URL;
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
    private Label jumlahUang;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            this.jumlahDonatur.setText(Integer.toString(DonaturModel.getAll().size()));
            this.jumlahPenerima.setText(Integer.toString(PenerimaModel.getAll().size()));
            this.jumlahUang.setText(MyHelper.rupiahFormat(Long.toString(UangModel.getTotal())));
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
