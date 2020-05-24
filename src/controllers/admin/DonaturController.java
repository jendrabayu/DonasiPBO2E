package controllers.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import models.DonaturModel;


public class DonaturController implements Initializable {
    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<DataDonatur> table;

    @FXML
    private TableColumn<DataDonatur, Number> colNo;

    @FXML
    private TableColumn<DataDonatur, String> colNama;

    @FXML
    private TableColumn<DataDonatur, String> colEmail;

    @FXML
    private TableColumn<DataDonatur, String> colNoTelp;

    @FXML
    private TableColumn<DataDonatur, String> colAlamat;

    @FXML
    private Label result_count;
    
    private ObservableList<DataDonatur> dataDonatur = FXCollections.observableArrayList();

    @FXML
    void searchDonatur(KeyEvent event) {
        initTable(searchField.getText());
    }

    private void initTable(String keyword){

        dataDonatur.clear();
        ArrayList<DonaturModel> donaturs = DonaturModel.getAll(keyword);
        
        int no = 0;
        
        for(DonaturModel donatur : donaturs){
            dataDonatur.add(new DataDonatur(
                    1+no++, 
                    donatur.getId(), 
                    donatur.getNama(), 
                    donatur.getEmail(), 
                    donatur.getNo_telp(), 
                    donatur.getAlamat()) 
            );
        }
        
          
        Label notfoundLabel = new Label("Data Donatur Tidak Ditemukan");
        notfoundLabel.setFont(Font.font(20));
      
        
        if(dataDonatur.size() == 0) {
            table.setPlaceholder(notfoundLabel);
        
        }
        
        table.setItems(dataDonatur);
        result_count.setText(String.format("%s hasil", dataDonatur.size()));
        colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
        colNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        colNoTelp.setCellValueFactory(cellData -> cellData.getValue().getNo_telp());
        colAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
   
    }
    
     private void initTable(){

        dataDonatur.clear();
        ArrayList<DonaturModel> donaturs = DonaturModel.getAll();
        
        int no = 0;
        
        for(DonaturModel donatur : donaturs){
            dataDonatur.add(new DataDonatur(
                    1+no++, 
                    donatur.getId(), 
                    donatur.getNama(), 
                    donatur.getEmail(), 
                    donatur.getNo_telp(), 
                    donatur.getAlamat()) 
            );
        }
        
          
        Label notfoundLabel = new Label("Data Donatur Tidak Ditemukan");
        notfoundLabel.setFont(Font.font(20));
      
        
        if(dataDonatur.size() == 0) {
            table.setPlaceholder(notfoundLabel);
        
        }
        
        table.setItems(dataDonatur);
        result_count.setText(String.format("%s hasil", dataDonatur.size()));
        colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
        colNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        colNoTelp.setCellValueFactory(cellData -> cellData.getValue().getNo_telp());
        colAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
   
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
    }    

    
}


class DataDonatur{
    
    IntegerProperty no, id;
    StringProperty nama, email, no_telp, alamat;

    public DataDonatur(int no, int id, String nama, String email, String no_telp, String alamat) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.no_telp = new SimpleStringProperty(no_telp);
        this.alamat = new SimpleStringProperty(alamat);
    }  

    public IntegerProperty getNo() {
        return no;
    }

    public void setNo(IntegerProperty no) {
        this.no = no;
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public StringProperty getNama() {
        return nama;
    }

    public void setNama(StringProperty nama) {
        this.nama = nama;
    }

    public StringProperty getEmail() {
        return email;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }

    public StringProperty getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(StringProperty no_telp) {
        this.no_telp = no_telp;
    }

    public StringProperty getAlamat() {
        return alamat;
    }

    public void setAlamat(StringProperty alamat) {
        this.alamat = alamat;
    }
}





