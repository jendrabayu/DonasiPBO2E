package controllers.user;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import helpers.DBHelper;
import helpers.Dialog;
import helpers.MyHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.MakananModel;
import models.RekeningModel;

import models.UserModel;


public class DonasiController implements Initializable {

    @FXML
    private AnchorPane mainPane;
       
    @FXML
    private HBox hboxContainer;

    @FXML
    private JFXTextField nama_makanan;

    @FXML
    private JFXDatePicker expired_date;

    @FXML
    private JFXTimePicker expired_time;

    @FXML
    private JFXTextField jumlah_makanan;

    @FXML
    private JFXTextArea keterangan;

    @FXML
    private JFXTextField nama;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField no_telp;

    @FXML
    private JFXTextArea alamat;

    
    
    //Makanan
    @FXML
    private TableView<Makanan> tabel_makanan;

    @FXML
    private TableColumn<Makanan, Number> col_mkn_no;

    @FXML
    private TableColumn<Makanan, String> col_mkn_date;

    @FXML
    private TableColumn<Makanan, String> col_mkn_nama;

    @FXML
    private TableColumn<Makanan, Number> col_mkn_jumlah;

    @FXML
    private TableColumn<Makanan, String> col_mkn_expdate;

    @FXML
    private TableColumn<Makanan, String> col_mkn_status;

    @FXML
    private TableColumn<Makanan, String> col_mkn_keterangan;
    
    private ObservableList<Makanan> listMakanan;
    
    
    //uang
    @FXML
    private TableView<Uang> tabel_uang;

    @FXML
    private TableColumn<Uang, Number> col_uang_no;

    @FXML
    private TableColumn<Uang, String> col_uang_date;

    @FXML
    private TableColumn<Uang, String> col_uang_rekening_from;

    @FXML
    private TableColumn<Uang, String> col_uang_rekening_to;

    @FXML
    private TableColumn<Uang, String> col_uang_jumlah;

    @FXML
    private TableColumn<Uang, String> col_uang_status;
    
    private ObservableList<Uang> listUang;
    
    
    private  void  initTableViewUang(){
        listUang = FXCollections.observableArrayList();
         

        ResultSet rs = DBHelper.query(String.format(
                "SELECT DATE(uang.created_at) as tanggal, "
                        + "CONCAT_WS('-', uang.nama_bank, uang.atas_nama, uang.no_rekening) as rekening_user, "
                        + "CONCAT_WS('-', rekening.nama_bank, rekening.atas_nama, rekening.no_rekening) as rekening, "
                        + "uang.jumlah as jumlah, "
                        + "status.nama as status "
                        + "FROM uang JOIN status ON uang.status_id = status.id "
                        + "JOIN rekening ON uang.rekening_id = rekening.id "
                        + "WHERE uang.user_id = '%s'"
                , UserModel.getId()));
        
        try {
            int no = 0;
            while (rs.next()) {                    
                listUang.add(new Uang(
                        1+no++, 
                        rs.getString("tanggal"), 
                        rs.getString("rekening_user"), 
                        rs.getString("rekening"), 
                        MyHelper.rupiahFormat(rs.getString("jumlah")), 
                        rs.getString("status")
                ));   
            }
            
            col_uang_no.setCellValueFactory(cellData -> cellData.getValue().getNo());
            col_uang_date.setCellValueFactory(cellData -> cellData.getValue().getCreatedAt());
            col_uang_rekening_from.setCellValueFactory(cellData -> cellData.getValue().getRekeningFrom());
            col_uang_rekening_to.setCellValueFactory(cellData -> cellData.getValue().getRekeningTo());
            col_uang_jumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
            col_uang_status.setCellValueFactory(cellData -> cellData.getValue().getStatus());
            tabel_uang.setItems(listUang);
       
        
            
        } catch (Exception e) {
            System.err.println("eror"+e);;
        }
        
    }

    
    private void initTableViewMakanan(){

        
        listMakanan = FXCollections.observableArrayList();
        
        ResultSet rs = DBHelper.query(String.format(
                "SELECT DATE(makanan.created_at) as tanggal, "
                        + "makanan.id as id, "
                        + "makanan.nama as makanan, "
                        + "makanan.jumlah as jumlah, "
                        + "makanan.expired_date as exp, "
                        + "makanan.keterangan as keterangan, "
                        + "status.nama as status "
                        + "FROM makanan JOIN status ON makanan.status_id = status.id "
                        + "WHERE makanan.user_id = '%s'"
                , UserModel.getId()));
        
        try {
            int no = 0;
            while (rs.next()) {                    
                listMakanan.add(new Makanan(
                        rs.getInt("id"), 
                        1+no++, 
                        rs.getInt("jumlah"), 
                        rs.getString("tanggal"), 
                        rs.getString("makanan"), 
                        rs.getString("exp"), 
                        rs.getString("keterangan"), 
                        rs.getString("status")
                ));   
            }
            
            col_mkn_no.setCellValueFactory(cellData -> cellData.getValue().getNo());
            col_mkn_date.setCellValueFactory(cellData -> cellData.getValue().getTanggal());
            col_mkn_nama.setCellValueFactory(cellData -> cellData.getValue().getMakanan());
            col_mkn_jumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
            col_mkn_expdate.setCellValueFactory(cellData -> cellData.getValue().getExp());
            col_mkn_status.setCellValueFactory(cellData -> cellData.getValue().getStatus());
            col_mkn_keterangan.setCellValueFactory(cellData -> cellData.getValue().getKeterangan());
            tabel_makanan.setItems(listMakanan);
            
                   
        } catch (Exception e) {
            System.err.println(e);;
        }
        
   
    }
    
    
    @FXML
    void addMakanan(ActionEvent event) {
        if (nama_makanan.getText().equals("") || 
                jumlah_makanan.getText().equals("") ) {
            Dialog.alertWarning("Semua field tidak boleh kosong kecuali keterangan");
        }else{
            
             //ubah waktu menjadi HH:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(expired_time.getValue().toString());
            String dateTime = expired_date.getValue().toString()+" "+localTime.format(formatter);
            if ( MakananModel.create(
                    nama_makanan.getText(), 
                    Integer.parseInt(jumlah_makanan.getText()), 
                    dateTime, 
                    keterangan.getText()) == 1
                    ) {
                    changeFxml("Sukses");
            }else{
                Dialog.alertError("Terjadi Kesalahan...");
            }   
        }   
    }
    
    @FXML
    void showDonasi(ActionEvent event) {
        changeFxml("donasi/Donasi");
    }

    
    @FXML
    void showDonasiMakanan(ActionEvent event) {
        changeFxml("donasi/form_makanan");
                

    }
    
     @FXML
    void showDonasiUang(ActionEvent event) {
        changeFxml("donasi/form_uang");
    }
    
    @FXML
    void showKonfirmasiTransfer(ActionEvent event) {
        changeFxml("donasi/form_konfirmasi_transfer");
    }
    
    @FXML
    void showBeranda(ActionEvent event) {
        changeFxml("Beranda");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
        
        try {
            
            ObservableList<RekeningModel> bankList = FXCollections.observableArrayList();
            ArrayList<RekeningModel> rekenings = RekeningModel.getAll();
            ListView rekeningListView = new ListView();
            rekeningListView.setPrefSize(500, 220);
            rekeningListView.setOrientation(Orientation.VERTICAL);
            


            for(RekeningModel rekening : rekenings){
                rekeningListView.getItems().add(String.format("%s - %s", rekening.getNamaBank(), rekening.getNoRekening()));
            }
            
        

            
            
            hboxContainer.getChildren().add(rekeningListView);
        } catch (Exception e) {
        }
        
  
        initTableViewMakanan();
        
        initTableViewUang();    
       
        try {                   
            nama.setText(UserModel.getNama());
            email.setText(UserModel.getEmail());
            no_telp.setText(UserModel.getNo_telp());
            alamat.setText(UserModel.getAlamat());
        } catch (Exception e) {
            
        }
    }    
      
    private void changeFxml(String name){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/user/"+name+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            //
        }
    }
    
   
    
    
    
}

class Uang {
    
    IntegerProperty no;
    StringProperty
            createdAt,
            rekeningFrom,
            rekeningTo,
            jumlah,
            status;

    public Uang(int no, String createdAt, String rekeningFrom, String rekeningTo, String jumlah, String status) {
        this.no =  new SimpleIntegerProperty(no);
        this.createdAt =  new SimpleStringProperty(createdAt);
        this.rekeningFrom =  new SimpleStringProperty(rekeningFrom);
        this.rekeningTo =  new SimpleStringProperty(rekeningTo);
        this.jumlah =  new SimpleStringProperty(jumlah);
        this.status =  new SimpleStringProperty(status);
    }

    public IntegerProperty getNo() {
        return no;
    }

    public void setNo(IntegerProperty no) {
        this.no = no;
    }

    public StringProperty getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(StringProperty createdAt) {
        this.createdAt = createdAt;
    }

    public StringProperty getRekeningFrom() {
        return rekeningFrom;
    }

    public void setRekeningFrom(StringProperty rekeningFrom) {
        this.rekeningFrom = rekeningFrom;
    }

    public StringProperty getRekeningTo() {
        return rekeningTo;
    }

    public void setRekeningTo(StringProperty rekeningTo) {
        this.rekeningTo = rekeningTo;
    }

    public StringProperty getJumlah() {
        return jumlah;
    }

    public void setJumlah(StringProperty jumlah) {
        this.jumlah = jumlah;
    }

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }
    
    
    
}


class Makanan {
    IntegerProperty id, no, jumlah;
    StringProperty tanggal, makanan, exp, keterangan, status;

    public Makanan(int id, int no, int jumlah, String tanggal, String makanan, String exp, String keterangan, String status) {
        this.id =  new SimpleIntegerProperty(id);
        this.no = new SimpleIntegerProperty(no);
        this.jumlah = new SimpleIntegerProperty(jumlah);;
        this.tanggal = new SimpleStringProperty(tanggal);
        this.makanan = new SimpleStringProperty(makanan);
        this.exp = new SimpleStringProperty(exp);
        this.keterangan = new SimpleStringProperty(keterangan);
        this.status = new SimpleStringProperty(status);
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public IntegerProperty getNo() {
        return no;
    }

    public void setNo(IntegerProperty no) {
        this.no = no;
    }

    public IntegerProperty getJumlah() {
        return jumlah;
    }

    public void setJumlah(IntegerProperty jumlah) {
        this.jumlah = jumlah;
    }

    public StringProperty getTanggal() {
        return tanggal;
    }

    public void setTanggal(StringProperty tanggal) {
        this.tanggal = tanggal;
    }

    public StringProperty getMakanan() {
        return makanan;
    }

    public void setMakanan(StringProperty makanan) {
        this.makanan = makanan;
    }

    public StringProperty getExp() {
        return exp;
    }

    public void setExp(StringProperty exp) {
        this.exp = exp;
    }

    public StringProperty getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(StringProperty keterangan) {
        this.keterangan = keterangan;
    }

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }
    
    
    
}



