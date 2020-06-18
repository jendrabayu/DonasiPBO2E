package controllers.admin;

import com.jfoenix.controls.JFXComboBox;
import helpers.DBHelper;
import helpers.Dialog;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import models.StatusModel;

public class MakananController implements Initializable {

    @FXML
    private AnchorPane mainPane;
 
    //tabel dengan status_id 1
    @FXML
    private TableView<Makanan> table;

    @FXML
    private TableColumn<Makanan, Number> colNo;

    @FXML
    private TableColumn<Makanan, String> colDate;

    @FXML
    private TableColumn<Makanan, String> colNama;

    @FXML
    private TableColumn<Makanan, String> colEmail;

    @FXML
    private TableColumn<Makanan, String> colTelp;

    @FXML
    private TableColumn<Makanan, String> colAlamat;

    @FXML
    private TableColumn<Makanan, String> colMakanan;

    @FXML
    private TableColumn<Makanan, Number> colJumlah;

    @FXML
    private TableColumn<Makanan, String> colExp;

    @FXML
    private TableColumn<Makanan, String> colKet;
    
    //tabel dengan status_id 2 & 3
    @FXML
    private TableView<Makanan> table2;

    @FXML
    private TableColumn<Makanan, Number> colNo2;

    @FXML
    private TableColumn<Makanan, String> colTgl2;

    @FXML
    private TableColumn<Makanan, String> colNama2;

    @FXML
    private TableColumn<Makanan, String> colEmail2;

    @FXML
    private TableColumn<Makanan, String> colTelp2;

    @FXML
    private TableColumn<Makanan, String> colAlamat2;

    @FXML
    private TableColumn<Makanan, String> colMakanan2;

    @FXML
    private TableColumn<Makanan, Number> colJumlah2;

    @FXML
    private TableColumn<Makanan, String> colStatus2;
    
    @FXML
    private JFXComboBox<Status> statusComboBox;
    
    ObservableList<Makanan> makanan = FXCollections.observableArrayList();
    ObservableList<Makanan> makanan2 = FXCollections.observableArrayList();
    ObservableList<Status> status = FXCollections.observableArrayList();
    private static int selectedStatusId;
    private static int id = 0;
    
    @FXML
    void index(ActionEvent event) {
        changeFxml("Makanan");
    }
    
    @FXML
    void edit(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Makanan m = table.getItems().get(selectedIndex);
            MakananController.id = m.getId().getValue();
            changeFxml("EditStatus");
        }else{
            Dialog.alertWarning("Klik row yang ingin diedit!");
        } 
    }

    @FXML
    void update(ActionEvent event) {
        if (MakananController.id > 0) {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("status_id", String.format("'%s'", MakananController.selectedStatusId));
            if (DBHelper.update("makanan", params, String.format("id = '%s'", MakananController.id))) {
                Dialog.alertSuccess("Data berhasil diupdate");
                changeFxml("Makanan");
            }else{
                Dialog.alertError("Data gagal diupdate!");
            }
        }
    }
    
    
    private void iniTable(){
        ResultSet rs = DBHelper.query(
         "SELECT DATE(makanan.created_at) as date, "
                 + "users.*, "
                 + "makanan.* "
                 + "FROM makanan JOIN users ON makanan.user_id = users.id "
                 + "WHERE makanan.status_id = '1' "
                 + "GROUP BY makanan.id "
                 + "ORDER BY makanan.created_at DESC "        
        );
        
        try {
            int no = 1;
            while (rs.next()) {     
                makanan.add(new Makanan(
                    no++, 
                    rs.getInt("makanan.id"), 
                    rs.getInt("makanan.jumlah_awal"), 
                    rs.getInt("makanan.jumlah"), 
                    rs.getString("date"), 
                    rs.getString("users.nama"), 
                    rs.getString("users.email"), 
                    rs.getString("users.no_telp"), 
                    rs.getString("users.alamat"), 
                    rs.getString("makanan.nama"), 
                    rs.getString("makanan.expired_date"), 
                    rs.getString("makanan.keterangan") 
                ));
               
            }
            
            try {
                table.setItems(makanan);
                colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
                colDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
                colNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
                colEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
                colTelp.setCellValueFactory(cellData -> cellData.getValue().getTelepon());
                colAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
                colMakanan.setCellValueFactory(cellData -> cellData.getValue().getMakanan());
                colJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
                colExp.setCellValueFactory(cellData -> cellData.getValue().getExpdate());
                colKet.setCellValueFactory(cellData -> cellData.getValue().getKeterangan());
            } catch (Exception e) {
                System.err.println(e);
            }
            
            
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    private void initTable2(){
        
        ResultSet rs= DBHelper.query(
            "SELECT DATE(makanan.created_at) as date, "
                + "users.*, "
                + "makanan.*, "
                + "status.*"
                + "FROM makanan JOIN users ON makanan.user_id = users.id "
                + "JOIN status ON makanan.status_id = status.id "
                + "WHERE makanan.status_id <> '1' "
                + "GROUP BY makanan.id "
                + "ORDER BY makanan.created_at DESC "        
        );
        
        try {
             
            int no = 1;
            while (rs.next()) {   
                makanan2.add(new Makanan(
                    no++, 
                    rs.getInt("makanan.jumlah_awal"), 
                    rs.getString("date"), 
                    rs.getString("users.nama"), 
                    rs.getString("users.email"),
                    rs.getString("users.no_telp"),
                    rs.getString("users.alamat"), 
                    rs.getString("makanan.nama"), 
                    rs.getString("status.nama")));
                 System.out.println( rs.getInt("makanan.jumlah_awal"));
            }
            
            try {
                table2.setItems(makanan2);
                colNo2.setCellValueFactory(cellData -> cellData.getValue().getNo());
                colTgl2.setCellValueFactory(cellData -> cellData.getValue().getDate());
                colNama2.setCellValueFactory(cellData -> cellData.getValue().getNama());
                colEmail2.setCellValueFactory(cellData -> cellData.getValue().getEmail());
                colTelp2.setCellValueFactory(cellData -> cellData.getValue().getTelepon());
                colAlamat2.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
                colMakanan2.setCellValueFactory(cellData -> cellData.getValue().getMakanan());
                colJumlah2.setCellValueFactory(cellData -> cellData.getValue().getJumlah_awal());
                colStatus2.setCellValueFactory(cellData -> cellData.getValue().getStatus());
            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
 
    private void initStatus(){
        ArrayList<StatusModel> statuss = StatusModel.getAll();
        for(StatusModel s : statuss){
            status.add(new Status(s.getId(), s.getStatus()));
        }
        
        try {
            statusComboBox.setItems(status);
            statusComboBox.setConverter(new StringConverter<Status>(){
            @Override
            public String toString(Status object) {
               return object.getNama();
            }

            @Override
            public Status fromString(String string) {
                 return statusComboBox.getItems().stream().filter(ap -> 
                     ap.getNama().equals(string)).findFirst().orElse(null);
            }

        });
           
        statusComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
               selectedStatusId = newval.getId();
            }
        });
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniTable();
        initTable2();
        initStatus(); 
    }    
    
    
    
    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/makanan/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(500));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (IOException e) {
            System.err.println(e);
        }
    }

}


class Makanan{
    
    IntegerProperty no, id, jumlah_awal, jumlah;
    StringProperty date, nama, email, telepon, alamat, makanan, expdate, keterangan, status;

    public Makanan(int no, int id, int jumlah_awal, int jumlah, String date, String nama, String email, String telepon, String alamat, String makanan, String expdate, String keterangan, String status) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.jumlah_awal = new SimpleIntegerProperty(jumlah_awal);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.date = new SimpleStringProperty(date);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.telepon = new SimpleStringProperty(telepon);
        this.alamat = new SimpleStringProperty(alamat);
        this.makanan = new SimpleStringProperty(makanan);
        this.expdate = new SimpleStringProperty(expdate);
        this.keterangan = new SimpleStringProperty(keterangan);
        this.status = new SimpleStringProperty(status);
    }
    
    public Makanan(int no, int id, int jumlah_awal, int jumlah, String date, String nama, String email, String telepon, String alamat, String makanan, String expdate, String keterangan) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.jumlah_awal = new SimpleIntegerProperty(jumlah_awal);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.date = new SimpleStringProperty(date);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.telepon = new SimpleStringProperty(telepon);
        this.alamat = new SimpleStringProperty(alamat);
        this.makanan = new SimpleStringProperty(makanan);
        this.expdate = new SimpleStringProperty(expdate);
        this.keterangan = new SimpleStringProperty(keterangan);
     
    }
    
    public Makanan(int no, int jumlah_awal, String date, String nama, String email, String telepon, String alamat, String makanan, String status) {
        this.no = new SimpleIntegerProperty(no);
        this.jumlah_awal = new SimpleIntegerProperty(jumlah_awal);
        this.date = new SimpleStringProperty(date);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.telepon = new SimpleStringProperty(telepon);
        this.alamat = new SimpleStringProperty(alamat);
        this.makanan = new SimpleStringProperty(makanan);
        this.status = new SimpleStringProperty(status);
    }

    public IntegerProperty getNo() {
        return no;
    }

    public IntegerProperty getId() {
        return id;
    }

    public IntegerProperty getJumlah_awal() {
        return jumlah_awal;
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

    public StringProperty getTelepon() {
        return telepon;
    }

    public StringProperty getAlamat() {
        return alamat;
    }

    public StringProperty getMakanan() {
        return makanan;
    }

    public StringProperty getExpdate() {
        return expdate;
    }

    public StringProperty getKeterangan() {
        return keterangan;
    }

    public StringProperty getStatus() {
        return status;
    }

}


class Status {
    
    int id;
    String nama;

    public Status(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

}