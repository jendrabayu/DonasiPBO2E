package controllers.admin;

import com.jfoenix.controls.JFXComboBox;
import helpers.DBHelper;
import helpers.Dialog;
import helpers.MyHelper;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import models.StatusModel;


import models.UangModel;


public class UangController implements Initializable {

    private static int id;
    
    private static long jumlahUang;
   
              
    ObservableList<StatusUang> status = FXCollections.observableArrayList();
    
    @FXML
    private JFXComboBox<StatusUang> statusComboBox;
    
    private static int selectedStatus;

    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private Label totalUangLabel;

    //Table dengan status_id = 1
    @FXML
    private TableView<Uang> table;

    @FXML
    private TableColumn<Uang, Number> colNo;

    @FXML
    private TableColumn<Uang, String> colDate;

    @FXML
    private TableColumn<Uang, String> colBank;

    @FXML
    private TableColumn<Uang, String> colAtasNama;

    @FXML
    private TableColumn<Uang, String> colNoRekening;

    @FXML
    private TableColumn<Uang, String> colJumlah;

    @FXML
    private TableColumn<Uang, String> colBankAdmin;

    @FXML
    private TableColumn<Uang, String> colAtasnamaAdmin;

    @FXML
    private TableColumn<Uang, String> colNoRekeningAdmin;
    
    ObservableList<Uang> uang = FXCollections.observableArrayList();
    
    //Table dengan status_id = 1 & 2
    @FXML
    private TableView<Uang> table2;

    @FXML
    private TableColumn<Uang, Number> colNo2;

    @FXML
    private TableColumn<Uang, String> colDate2;

    @FXML
    private TableColumn<Uang, String> colBank2;

    @FXML
    private TableColumn<Uang, String> colAtasNama2;

    @FXML
    private TableColumn<Uang, String> colNoRekening2;

    @FXML
    private TableColumn<Uang, String> colJumlah2;

    @FXML
    private TableColumn<Uang, String> colBankAdmin2;

    @FXML
    private TableColumn<Uang, String> colAtasnamaAdmin2;

    @FXML
    private TableColumn<Uang, String> colNoRekeningAdmin2;
    
    @FXML
    private TableColumn<Uang, String> colStatus2;
    
    ObservableList<Uang> uang2 = FXCollections.observableArrayList();
    
    @FXML
    void index(ActionEvent event) {
        changeFxml("Uang");
    }

    @FXML
    void update(ActionEvent event) {
        
        if (UangController.id > 0 && selectedStatus > 0) {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("status_id", String.format("'%s'", selectedStatus));   
            if (DBHelper.update("uang", params, String.format("id = '%s'", UangController.id))) {
                if (selectedStatus == 3) {
                    Map<String, String> params2 = new LinkedHashMap<>();
                    params2.put("total", String.format("'%s'", UangModel.getTotal()+jumlahUang)); 
                    DBHelper.update("total_uang", params2, String.format("id = '%s'", 1));
                }
                
                Dialog.alertSuccess("Status uang berhasil di update");
                jumlahUang = 0;
                selectedStatus = 0;
                changeFxml("Uang");         
            }else{
                Dialog.alertError("Status uang gagal di update");
            }
        }
    }

    
    @FXML
    void edit(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Uang uang = table.getItems().get(selectedIndex);
            UangController.id = uang.getId().getValue();
            UangController.jumlahUang = Long.parseLong(uang.getJumlah().getValue());
            changeFxml("EditStatus");
        }else{
            Dialog.alertWarning("Mohon klik row yang dipilih");
        } 
    }
    

    private void initStatus(){
        ArrayList<StatusModel> statuss = StatusModel.getAll();
        for(StatusModel s : statuss){
            status.add(new StatusUang(s.getId(), s.getStatus()));
        }
        
        try {
            statusComboBox.setItems(status);
            statusComboBox.setConverter(new StringConverter<StatusUang>(){
            @Override
            public String toString(StatusUang object) {
               return object.getNama();
            }

            @Override
            public StatusUang fromString(String string) {
                 return statusComboBox.getItems().stream().filter(ap -> 
                     ap.getNama().equals(string)).findFirst().orElse(null);
            }

        });
           
        statusComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
               selectedStatus = newval.getId();
            }
        });
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    private void initTable(){
        ResultSet rs = DBHelper.query(""
                + "SELECT "
                + "DATE(uang.created_at) as date, "
                + "uang.id as id, "
                + "uang.nama_bank as bank, "
                + "uang.atas_nama as atasNama, "
                + "uang.no_rekening as norek, "
                + "uang.jumlah as jumlah, "
                + "rekening.nama_bank as bankAdmin, "
                + "rekening.atas_nama as atasNamaAdmin, "
                + "rekening.no_rekening as noRekAdmin "
                + "FROM uang join rekening on uang.rekening_id = rekening.id "
                + "WHERE status_id = '1' "
                + "ORDER BY uang.created_at DESC");
         
        try {
            int no = 1;
            while (rs.next()) {                
                uang.add(new Uang(
                    no++, 
                    rs.getInt("id"), 
                    rs.getString("date"), 
                    rs.getString("bank"), 
                    rs.getString("atasNama"), 
                    rs.getString("norek"), 
                    rs.getString("jumlah"), 
                    rs.getString("bankAdmin"), 
                    rs.getString("atasNamaAdmin"), 
                    rs.getString("noRekAdmin")
                ));
            }
            
            try {
                
                table.setItems(uang);
                colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
                colDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
                colBank.setCellValueFactory(cellData -> cellData.getValue().getBank());
                colAtasNama.setCellValueFactory(cellData -> cellData.getValue().getAtasNama());
                colNoRekening.setCellValueFactory(cellData -> cellData.getValue().getNoRekening());
                colJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
                colBankAdmin.setCellValueFactory(cellData -> cellData.getValue().getBankAdmin());
                colAtasnamaAdmin.setCellValueFactory(cellData -> cellData.getValue().getAtasNamaAdmin());
                colNoRekeningAdmin.setCellValueFactory(cellData -> cellData.getValue().getNoRekeningAdmin());
        
                
            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    
    private void initTable2(){
         ResultSet rs = DBHelper.query(""
                + "SELECT "
                + "DATE(uang.created_at) as date, "
                + "uang.id as id, "
                + "uang.nama_bank as bank, "
                + "uang.atas_nama as atasNama, "
                + "uang.no_rekening as norek, "
                + "uang.jumlah as jumlah, "
                + "rekening.nama_bank as bankAdmin, "
                + "rekening.atas_nama as atasNamaAdmin, "
                + "rekening.no_rekening as noRekAdmin, "
                + "status.nama as status "
                + "FROM uang JOIN rekening ON uang.rekening_id = rekening.id "
                + "JOIN status on uang.status_id = status.id "
                + "WHERE status_id <> '1' "
                + "ORDER BY uang.created_at DESC");
         
        try {
            int no = 1;
            while (rs.next()) {                
                uang2.add(new Uang(
                    no++, 
                    rs.getInt("id"), 
                    rs.getString("date"), 
                    rs.getString("bank"), 
                    rs.getString("atasNama"), 
                    rs.getString("norek"), 
                    rs.getString("jumlah"), 
                    rs.getString("bankAdmin"), 
                    rs.getString("atasNamaAdmin"), 
                    rs.getString("noRekAdmin"),
                    rs.getString("status")
                ));
            }
            
            try {
                
                table2.setItems(uang2);
                colNo2.setCellValueFactory(cellData -> cellData.getValue().getNo());
                colDate2.setCellValueFactory(cellData -> cellData.getValue().getDate());
                colBank2.setCellValueFactory(cellData -> cellData.getValue().getBank());
                colAtasNama2.setCellValueFactory(cellData -> cellData.getValue().getAtasNama());
                colNoRekening2.setCellValueFactory(cellData -> cellData.getValue().getNoRekening());
                colJumlah2.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
                colBankAdmin2.setCellValueFactory(cellData -> cellData.getValue().getBankAdmin());
                colAtasnamaAdmin2.setCellValueFactory(cellData -> cellData.getValue().getAtasNamaAdmin());
                colNoRekeningAdmin2.setCellValueFactory(cellData -> cellData.getValue().getNoRekeningAdmin());
                colStatus2.setCellValueFactory(cellData -> cellData.getValue().getStatus());
        
                
            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

   
    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/uang/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(500));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (IOException e) {
          e.printStackTrace();
        }
    }
      
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        initTable2();
        initStatus();
        
        try {
            totalUangLabel.setText(MyHelper.rupiahFormat(Long.toString(UangModel.getTotal())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}


class Uang{
    
    IntegerProperty no, id;
    StringProperty date, bank, atasNama, noRekening, jumlah;
    StringProperty bankAdmin, atasNamaAdmin, noRekeningAdmin;
    StringProperty status;

    public Uang(int no, int id, String date, String bank, String atasNama, String noRekening, String jumlah, String bankAdmin, String atasNamaAdmin, String noRekeningAdmin, String status) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.bank = new SimpleStringProperty(bank);
        this.atasNama = new SimpleStringProperty(atasNama);
        this.noRekening = new SimpleStringProperty(noRekening);
        this.jumlah = new SimpleStringProperty(jumlah);
        this.bankAdmin = new SimpleStringProperty(bankAdmin);
        this.atasNamaAdmin = new SimpleStringProperty(atasNamaAdmin);
        this.noRekeningAdmin = new SimpleStringProperty(noRekeningAdmin);
        this.status = new SimpleStringProperty(status);
    }
    
    public Uang(int no, int id, String date, String bank, String atasNama, String noRekening, String jumlah, String bankAdmin, String atasNamaAdmin, String noRekeningAdmin) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.bank = new SimpleStringProperty(bank);
        this.atasNama = new SimpleStringProperty(atasNama);
        this.noRekening = new SimpleStringProperty(noRekening);
        this.jumlah = new SimpleStringProperty(jumlah);
        this.bankAdmin = new SimpleStringProperty(bankAdmin);
        this.atasNamaAdmin = new SimpleStringProperty(atasNamaAdmin);
        this.noRekeningAdmin = new SimpleStringProperty(noRekeningAdmin);
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

    public StringProperty getDate() {
        return date;
    }

    public void setDate(StringProperty date) {
        this.date = date;
    }

    public StringProperty getBank() {
        return bank;
    }

    public void setBank(StringProperty bank) {
        this.bank = bank;
    }

    public StringProperty getAtasNama() {
        return atasNama;
    }

    public void setAtasNama(StringProperty atasNama) {
        this.atasNama = atasNama;
    }

    public StringProperty getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(StringProperty noRekening) {
        this.noRekening = noRekening;
    }

    public StringProperty getJumlah() {
        return jumlah;
    }

    public void setJumlah(StringProperty jumlah) {
        this.jumlah = jumlah;
    }

    public StringProperty getBankAdmin() {
        return bankAdmin;
    }

    public void setBankAdmin(StringProperty bankAdmin) {
        this.bankAdmin = bankAdmin;
    }

    public StringProperty getAtasNamaAdmin() {
        return atasNamaAdmin;
    }

    public void setAtasNamaAdmin(StringProperty atasNamaAdmin) {
        this.atasNamaAdmin = atasNamaAdmin;
    }

    public StringProperty getNoRekeningAdmin() {
        return noRekeningAdmin;
    }

    public void setNoRekeningAdmin(StringProperty noRekeningAdmin) {
        this.noRekeningAdmin = noRekeningAdmin;
    }

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }
    
    
    
}



class StatusUang {
    
    int id;
    String nama;

    public StatusUang(int id, String nama) {
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