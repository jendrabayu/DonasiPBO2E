/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import helpers.DBHelper;
import helpers.MyHelper;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class RekapController implements Initializable {

    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label uLabelTitle;

    @FXML
    private Label uLabelTotal;

    @FXML
    private TableView<RekapPenyaluranUang> uTable;

    @FXML
    private TableColumn<RekapPenyaluranUang, Number> uNo;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> uDate;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> uNama;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> uEmail;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> utelp;

    @FXML
    private TableColumn<RekapPenyaluranUang, Number> uJumlahOrang;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> uAlamat;

    @FXML
    private TableColumn<RekapPenyaluranUang, Number> uJumlah;
    
    private ObservableList<RekapPenyaluranUang> uData = FXCollections.observableArrayList();
    
    private static int uSortBy = 1;
    
    @FXML
    void uAll(ActionEvent event) {
        uSortBy = 1;
        initUTable();
    }
    
    @FXML
    void uWeek(ActionEvent event) {
        uSortBy = 2;
       initUTable();
    }


    @FXML
    void uMonth(ActionEvent event) {
        uSortBy = 3;
        initUTable();
    }


    @FXML
    void uYear(ActionEvent event) {
        uSortBy = 4;
       initUTable();
    }
    
    
    
    @FXML
    private Label mLabelTitle;

    @FXML
    private Label mLabelTotal;
    
    @FXML
    private TableView<RekapPenyaluranMakanan> mtable;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, Number> mNo;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mDate;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mNama;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mEmail;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mTelp;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, Number> mJumlahOrang;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mAlamat;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mMakanan;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, Number> mJumlah;
    
    private ObservableList<RekapPenyaluranMakanan> mData = FXCollections.observableArrayList();
    
    private static int mSortBy = 1;

    @FXML
    void mAll(ActionEvent event) {
        mSortBy = 1;
        initMTable();
    }
    
    @FXML
    void mWeek(ActionEvent event) {
        mSortBy = 2;
        initMTable();
    }

    @FXML
    void mMonth(ActionEvent event) {
        mSortBy = 3;
        initMTable();
    }

  
    @FXML
    void mYear(ActionEvent event) {
        mSortBy = 4;
        initMTable();
    }
    
    private void initMTable(){
        mData.clear();
        String sort = "";
        if (mSortBy == 1) {
            sort = " ";
            mLabelTitle.setText("Semua Data Penyaluran Donasi Makanan");
        }else if(mSortBy == 2){
            sort = " WHERE WEEK(penyaluran_makanan.created_at) = WEEK(CURRENT_DATE) ";
            mLabelTitle.setText("Data Penyaluran Donasi Makanan Minggu Ini");
        }else if (mSortBy == 3) {
            sort = " WHERE MONTH(penyaluran_makanan.created_at) = MONTH(CURRENT_DATE) ";
            mLabelTitle.setText("Data Penyaluran Donasi Makanan Bulan Ini");
        }else if (mSortBy == 4) {
            sort = " WHERE YEAR(penyaluran_makanan.created_at) = YEAR(CURRENT_DATE) ";
            mLabelTitle.setText("Data Penyaluran Donasi Makanan Tahun Ini");
        }
        
        
        ResultSet rs = DBHelper.query(""
                + "SELECT DATE(penyaluran_makanan.created_at) as date, "
                + "SUM(penyaluran_makanan.jumlah) as totalMakanan, "
                + "penerima.*, "
                + "penyaluran_makanan.*, "
                + "makanan.* "
                + "FROM penyaluran_makanan "
                + "JOIN penerima ON penyaluran_makanan.penerima_id = penerima.id "
                + "JOIN makanan ON penyaluran_makanan.makanan_id = makanan.id "
                + sort
                + "GROUP BY penyaluran_makanan.id "
                + "ORDER BY penyaluran_makanan.created_at DESC ");
        
        try {   

            int no =1 ;
            int total = 0;
            while (rs.next()) {                
                mData.add(new RekapPenyaluranMakanan(
                        rs.getInt("penyaluran_makanan.id"), 
                        no++, 
                        rs.getInt("penerima.jumlah_orang"), 
                        rs.getInt("penyaluran_makanan.jumlah"), 
                        rs.getString("date"), 
                        rs.getString("penerima.nama"), 
                        rs.getString("penerima.email"), 
                        rs.getString("penerima.no_telp"), 
                        rs.getString("penerima.alamat"), 
                        rs.getString("makanan.nama")));
                total+=rs.getInt("totalMakanan");
                
            }
              mLabelTotal.setText(String.format("Total : %s Paket/Porsi", String.valueOf(total)));
           
            
            mtable.setItems(mData);
            mNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
            mDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
            mNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
            mEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
            mTelp.setCellValueFactory(cellData -> cellData.getValue().getTelp());
            mJumlahOrang.setCellValueFactory(cellData -> cellData.getValue().getJumlahOrang());
            mAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
            mMakanan.setCellValueFactory(cellData -> cellData.getValue().getMakanan());
            mJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
           
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void initUTable(){
        uData.clear();
        String sort = "";
        if (uSortBy == 1) {
            sort = " ";
            uLabelTitle.setText("Semua Data Penyaluran Donasi Uang");
        }else if(uSortBy == 2){
            sort = " WHERE WEEK(penyaluran_uang.created_at) = WEEK(CURRENT_DATE) ";
            uLabelTitle.setText("Data Penyaluran Donasi Uang Minggu Ini");
        }else if (uSortBy == 3) {
            sort = " WHERE MONTH(penyaluran_uang.created_at) = MONTH(CURRENT_DATE) ";
            uLabelTitle.setText("Data Penyaluran Donasi Uang Bulan Ini");
        }else if (uSortBy == 4) {
            sort = " WHERE YEAR(penyaluran_uang.created_at) = YEAR(CURRENT_DATE) ";
            uLabelTitle.setText("Data Penyaluran Donasi Uang Tahun Ini");
        }
        
        
        ResultSet rs = DBHelper.query(""
                + "SELECT DATE(penyaluran_uang.created_at) as date, "
                + "SUM(penyaluran_uang.jumlah) as jumlahUang, "
                + "penerima.*, "
                + "penyaluran_uang.* "
                + "FROM penyaluran_uang "
                + "JOIN penerima ON penyaluran_uang.penerima_id = penerima.id "
                + sort
                + "GROUP BY penyaluran_uang.id "
                + "ORDER BY penyaluran_uang.created_at DESC ");
        
        try {   

            int no =1 ;
            long total = 0;
            while (rs.next()) {     
                uData.add(new RekapPenyaluranUang(
                        rs.getInt("penyaluran_uang.id"), 
                        no++, 
                        rs.getInt("penerima.jumlah_orang"), 
                        rs.getInt("penyaluran_uang.jumlah"), 
                        rs.getString("date"), 
                        rs.getString("penerima.nama"), 
                        rs.getString("penerima.email"), 
                        rs.getString("penerima.no_telp"), 
                        rs.getString("penerima.alamat") 
                     ));
                total+=rs.getLong("jumlahUang");
                
            }
            uLabelTotal.setText(String.format("Total : %s", MyHelper.rupiahFormat(String.valueOf(total))));
            
            uTable.setItems(uData);
            uNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
            uDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
            uNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
            uEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
            utelp.setCellValueFactory(cellData -> cellData.getValue().getTelp());
            uJumlahOrang.setCellValueFactory(cellData -> cellData.getValue().getJumlahOrang());
            uAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
            uJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
           
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initMTable();
        initUTable();
    }    
    
}

class RekapPenyaluranMakanan{
    
    IntegerProperty id, no, jumlahOrang, jumlah;
    StringProperty date, nama, email, telp, alamat, makanan;

    public RekapPenyaluranMakanan(int id, int no, int jumlahOrang, int jumlah, String date, String nama, String email, String telp, String alamat, String makanan) {
        this.id = new SimpleIntegerProperty(id);
        this.no = new SimpleIntegerProperty(no);
        this.jumlahOrang = new SimpleIntegerProperty(jumlahOrang);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.date = new SimpleStringProperty(date);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.telp = new SimpleStringProperty(telp);
        this.alamat = new SimpleStringProperty(alamat);
        this.makanan = new SimpleStringProperty(makanan);
    }

    public IntegerProperty getId() {
        return id;
    }

    public IntegerProperty getNo() {
        return no;
    }

    public IntegerProperty getJumlahOrang() {
        return jumlahOrang;
    }

    public IntegerProperty getJumlah() {
        return jumlah;
    }

    public StringProperty getDate() {
        return date;
    }

    public StringProperty getNama() {
        return nama;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getTelp() {
        return telp;
    }

    public StringProperty getAlamat() {
        return alamat;
    }

    public StringProperty getMakanan() {
        return makanan;
    }

}

class RekapPenyaluranUang{
    
    IntegerProperty id, no, jumlahOrang, jumlah;
    StringProperty date, nama, email, telp, alamat;

    public RekapPenyaluranUang(int id, int no, int jumlahOrang, int jumlah, String date, String nama, String email, String telp, String alamat) {
        this.id = new SimpleIntegerProperty(id);
        this.no =  new SimpleIntegerProperty(no);
        this.jumlahOrang = new SimpleIntegerProperty();
        this.jumlah =  new SimpleIntegerProperty(jumlah);
        this.date = new SimpleStringProperty(date);
        this.nama =  new SimpleStringProperty(nama);
        this.email =  new SimpleStringProperty(email);
        this.telp =  new SimpleStringProperty(telp);
        this.alamat =  new SimpleStringProperty(alamat);
    }

    public IntegerProperty getId() {
        return id;
    }

    public IntegerProperty getNo() {
        return no;
    }

    public IntegerProperty getJumlahOrang() {
        return jumlahOrang;
    }

    public IntegerProperty getJumlah() {
        return jumlah;
    }

    public StringProperty getDate() {
        return date;
    }

    public StringProperty getNama() {
        return nama;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getTelp() {
        return telp;
    }

    public StringProperty getAlamat() {
        return alamat;
    }
    
    
    
}
