package controllers.admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import helpers.DBHelper;
import helpers.Dialog;
import helpers.MyHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import models.PenerimaModel;
import models.RekeningModel;
import models.UangModel;


public class PenyaluranUangController implements Initializable {
    
    private static long totalUang;
    
    private static int id;
    
    
    
    
    @FXML
    private Label dateTodayLabel;

    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private JFXComboBox<PenerimaModel> penerimaComboBox;

    @FXML
    private JFXTextField jumlahUangField;
    
    @FXML
    private TableView<PenyaluranUang> table;

    @FXML
    private TableColumn<PenyaluranUang, Number> colNo;

    @FXML
    private TableColumn<PenyaluranUang, String> colNama;

    @FXML
    private TableColumn<PenyaluranUang, String> colEmail;

    @FXML
    private TableColumn<PenyaluranUang, String> colTelp;

    @FXML
    private TableColumn<PenyaluranUang, String> colAlamat;

    @FXML
    private TableColumn<PenyaluranUang, String> colJumlah;
    
    private ObservableList<PenyaluranUang> penyaluranUang = FXCollections.observableArrayList();

    
    
    @FXML
    private Label totalUangLabel;

    @FXML
    void handleJumlahUang(KeyEvent event) {
        long sisa = PenyaluranUangController.totalUang - 70101;
        System.out.println(sisa);
//        totalUangLabel.setText(MyHelper.rupiahFormat(Long.toString(sisa)));
    }


    
    ObservableList<PenerimaModel> dataPenerima;

    @FXML
    void add(ActionEvent event) {
        changeFxml("TambahPenyaluranUang");
    }

    
    @FXML
    void index(ActionEvent event) {
    changeFxml("PenyaluranUang");
    }

    @FXML
    void store(ActionEvent event) {
         boolean comboBoxEmpty = penerimaComboBox.getValue() == null ;
        
        if (comboBoxEmpty) {
              Dialog.alertWarning("Anda harus memilih penerima!");
        }else{
            if (jumlahUangField.getText().equals("")) {
                Dialog.alertWarning("Anda harus memasukkan nominal uang!");
            }else{
                int id = penerimaComboBox.getValue().getId();
                long jumlahUang = Long.parseLong(jumlahUangField.getText());
                if (jumlahUang > UangModel.getTotal()) {
                    Dialog.alertWarning("Nominal yang anda masukkan melebihi batas!");
                }else{
                    Map<String, String> params = new LinkedHashMap<>();
                    params.put("penerima_id", String.format("'%s'", id));
                    params.put("jumlah", String.format("'%s'", jumlahUang));
                    if(DBHelper.insert("penyaluran_uang", params)){
                        Map<String, String> params2 = new LinkedHashMap<>();
                        params2.put("total", String.format("'%s'", UangModel.getTotal()-jumlahUang)); 
                        DBHelper.update("total_uang", params2, String.format("id = '%s'", 1));
                        Dialog.alertSuccess("Anda berhasil menyalurkan uang hari ini");
                        changeFxml("PenyaluranUang");
                    }else{
                        Dialog.alertError("Penyauran Uang Gagal");
                        changeFxml("PenyaluranUang");
                    }
                }
                
            }
        }
    }
    
    private void initTable()
    {
        penyaluranUang.clear();
       
        ResultSet rs = DBHelper.query(""
                + "SELECT "
                + "penerima.nama as nama, "
                + "penerima.email as email, "
                + "penerima.no_telp as telp, "
                + "penerima.alamat as alamat, "
                + "penyaluran_uang.jumlah as jumlah, "
                + "penyaluran_uang.id as id "
                + "FROM penyaluran_uang JOIN penerima ON penyaluran_uang.penerima_id = penerima.id "
                + "WHERE DATE(penyaluran_uang.created_at) = CURRENT_DATE()");
        try {
           
            int no = 1;
            while (rs.next()) {                
                penyaluranUang.add(new PenyaluranUang(
                        no++, 
                        rs.getInt("id"), 
                        rs.getString("nama"), 
                        rs.getString("email"), 
                        rs.getString("telp"), 
                        rs.getString("alamat"), 
                        rs.getString("jumlah")
                ));
            }
            
            try {
               
                dateTodayLabel.setText(String.format("Penyaluran uang hari ini (%s)", MyHelper.getDateNow()));
                table.setItems(penyaluranUang);
                colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
                colNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
                colEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
                colTelp.setCellValueFactory(cellData -> cellData.getValue().getTelp());
                colAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
                colJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }    


    @FXML
    void delete(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            PenyaluranUang pu = table.getItems().get(selectedIndex);
            PenyaluranUangController.id = pu.getId().getValue();
            long jumlahUang = Long.parseLong(pu.getJumlah().getValue());
            //Konfirmasi
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus Data");
            alert.setContentText("Apakah anda yakin?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if (DBHelper.delete("penyaluran_uang", String.valueOf(PenyaluranUangController.id))) {
                    Map<String, String> params2 = new LinkedHashMap<>();
                    params2.put("total", String.format("'%s'", UangModel.getTotal()+jumlahUang)); 
                    DBHelper.update("total_uang", params2, String.format("id = '%s'", 1));
                    Dialog.alertSuccess("Data berhasil dihapus");
                    initTable();
                }else{
                    Dialog.alertError("Data gagal dihapus");
                    initTable();
                }  
            }
        }else{
            Dialog.alertWarning("Klik row yang ingin dihapus!");
        } 
    }
    


   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        initTable();
        try {
            dataPenerima =  FXCollections.observableArrayList(PenerimaModel.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(PenyaluranUangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            totalUangLabel.setText(MyHelper.rupiahFormat(Long.toString(UangModel.getTotal())));
 
            //Pilihan Penerima Uang
            penerimaComboBox.setItems(dataPenerima);
            penerimaComboBox.setConverter(new StringConverter<PenerimaModel>() {
                @Override
                public String toString(PenerimaModel object) {
                   return String.format("%s - %s - %s - %s", 
                           object.getId(),
                           object.getNama(),
                           object.getEmail(),
                           object.getNo_telp());
                }

                @Override
                public PenerimaModel fromString(String string) {
                    return penerimaComboBox.getItems().stream().filter(ap -> 
                     ap.getNama().equals(string)).findFirst().orElse(null);
                }
            });
            
            
            
            jumlahUangField.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   if (!newValue.matches("\\d{0,12}")) {
                        jumlahUangField.setText(oldValue);
                    }
                }
                
            });
  
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    
    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/uang/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(600));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
}

class PenyaluranUang{
    
    IntegerProperty no, id;
    StringProperty nama, email, telp, alamat, jumlah;

    public PenyaluranUang(int no, int id, String nama, String email, String telp, String alamat, String jumlah) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.telp = new SimpleStringProperty(telp);
        this.alamat = new SimpleStringProperty(alamat);
        this.jumlah = new SimpleStringProperty(jumlah);
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

    public StringProperty getTelp() {
        return telp;
    }

    public void setTelp(StringProperty telp) {
        this.telp = telp;
    }

    public StringProperty getAlamat() {
        return alamat;
    }

    public void setAlamat(StringProperty alamat) {
        this.alamat = alamat;
    }

    public StringProperty getJumlah() {
        return jumlah;
    }

    public void setJumlah(StringProperty jumlah) {
        this.jumlah = jumlah;
    }
    
    
}


