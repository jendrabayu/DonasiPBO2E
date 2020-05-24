package controllers.user;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import models.MakananModel;
import models.UserModel;
import models.PenerimaModel;

public class PenerimaController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<DataPenerima> tabel_penerima;

    @FXML
    private TableColumn<DataPenerima, Number> col_no;

    @FXML
    private TableColumn<DataPenerima, String> col_nama;

    @FXML
    private TableColumn<DataPenerima, Number> col_jumlah_orang;

    @FXML
    private TableColumn<DataPenerima, String> col_alamat;
      
    private ObservableList<DataPenerima> dataPenerima = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ArrayList<PenerimaModel> penerimas = PenerimaModel.getAll();
            
            int no = 0;
            for(PenerimaModel penerima : penerimas){
                dataPenerima.add(new DataPenerima(
                        1+no++,
                        penerima.getNama(),
                        penerima.getJumlahOrang(),
                        penerima.getAlamat()));
            }
            
            
            tabel_penerima.setItems(dataPenerima);
            col_no.setCellValueFactory(cellData -> cellData.getValue().getNo());
            col_nama.setCellValueFactory(cellData -> cellData.getValue().getNama());
            col_jumlah_orang.setCellValueFactory(cellData -> cellData.getValue().getJumlah_orang());
            col_alamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
        } catch (SQLException ex) {
            Logger.getLogger(PenerimaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}


class DataPenerima{
    IntegerProperty no, jumlah_orang;
    StringProperty nama, alamat;
    
    public DataPenerima(int no, String nama, int jumlah_orang, String alamat){
        this.no = new SimpleIntegerProperty(no);  
        this.nama = new SimpleStringProperty(nama);
        this.jumlah_orang = new SimpleIntegerProperty(jumlah_orang);
        this.alamat = new SimpleStringProperty(alamat);
    }

    public IntegerProperty getNo() {
        return no;
    }

    public void setNo(IntegerProperty no) {
        this.no = no;
    }

    public IntegerProperty getJumlah_orang() {
        return jumlah_orang;
    }

    public void setJumlah_orang(IntegerProperty jumlah_orang) {
        this.jumlah_orang = jumlah_orang;
    }

    public StringProperty getNama() {
        return nama;
    }

    public void setNama(StringProperty nama) {
        this.nama = nama;
    }

    public StringProperty getAlamat() {
        return alamat;
    }

    public void setAlamat(StringProperty alamat) {
        this.alamat = alamat;
    }

    

}