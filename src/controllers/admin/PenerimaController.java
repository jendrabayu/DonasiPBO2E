package controllers.admin;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import helpers.Dialog;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import models.PenerimaModel;


public class PenerimaController implements Initializable {
    
    private static int id;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField nama;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField no_telp;

    @FXML
    private JFXTextField jumlah_orang;

    @FXML
    private JFXTextArea alamat;    
    
    @FXML
    private TableView<DataPenerima> table;

    @FXML
    private TableColumn<DataPenerima, Number> colNo;

    @FXML
    private TableColumn<DataPenerima, String> colNama;

    @FXML
    private TableColumn<DataPenerima, String> colEmail;

    @FXML
    private TableColumn<DataPenerima, String> colNoTelp;

    @FXML
    private TableColumn<DataPenerima, Number> colJumlahOrang;

    @FXML
    private TableColumn<DataPenerima, String> colAlamat;

    @FXML
    private Label result_count;
    
    @FXML
    private TextField searchField;
    
    private ObservableList<DataPenerima> dataPenerima = FXCollections.observableArrayList();
    
    @FXML
    void showPenerima(ActionEvent event) {
        PenerimaController.id = -1;
        changeFxml("Penerima");
    }
    
    @FXML
    void showAddPenerima(ActionEvent event) {
        PenerimaController.id = -1;
        changeFxml("TambahPenerima");
    }
    
    @FXML
    void showEditPenerima(ActionEvent event) {
        PenerimaController.id = -1;
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if(selectedIndex >= 0){
            DataPenerima penerima = table.getItems().get(selectedIndex);
            PenerimaController.id = penerima.getId().getValue();
            changeFxml("EditPenerima");
        }else{
            Dialog.alertWarning("Mohon klik penerima yang akan diedit");
        } 
    }
    
    @FXML
    void showDataDonasi(ActionEvent event) {

    }

    
    
    @FXML
    void searchPenerima(KeyEvent event) {
        try {
            initTable(searchField.getText());
        } catch (SQLException ex) {
            Logger.getLogger(PenerimaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    void deletePenerima(ActionEvent event) throws SQLException {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();    
        if(selectedIndex >= 0){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Hapus Penerima");
            alert.setContentText("Apakah anda yakin?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DataPenerima penerima = table.getItems().get(selectedIndex);
                int id = penerima.getId().getValue();
                if (PenerimaModel.delete(id)) {
                    Dialog.alertSuccess("Berhasi Dihapus");  
                    initTable(null);              
                }else{
                   Dialog.alertError("Gagal DIhapus");
                }
            }
        }else{
            Dialog.alertWarning("Mohon klik penerima yang akan dihapus");
        } 
    }

   @FXML
    void createPenerima(ActionEvent event) {
        PenerimaController.id = -1;
        if (nama.getText().equals("") ||
                email.getText().equals("")||
                no_telp.getText().equals("")||
                jumlah_orang.getText().equals("")||
                alamat.getText().equals("")) 
        {
           Dialog.alertWarning("Semua field wajib diisi!");
        }else{
            if (PenerimaModel.create(nama.getText(), 
                    email.getText(), 
                    no_telp.getText(), 
                    Integer.parseInt(jumlah_orang.getText()), 
                    alamat.getText())) {
                Dialog.alertSuccess("Perhasil Menambahkan Penerima Baru");
                PenerimaController.id = -1;
                changeFxml("Penerima");
            }else{
                Dialog.alertError("Gagal Menambahkan Penerima Baru!");
                
            }
        }
    }


    @FXML
    void updatePenerima(ActionEvent event) {
        if (nama.getText().equals("") ||
              email.getText().equals("")||
              no_telp.getText().equals("")||
              jumlah_orang.getText().equals("")||
              alamat.getText().equals("")) 
          {
            Dialog.alertWarning("Semua field wajib diisi!");
        }else{
            if (PenerimaModel.update(
                    PenerimaController.id, 
                    nama.getText(), 
                    email.getText(), 
                    no_telp.getText(), 
                    Integer.parseInt(jumlah_orang.getText()), 
                    alamat.getText())) {
                Dialog.alertSuccess("Perhasil di update");
                PenerimaController.id = -1;
                changeFxml("Penerima");
            }else{
                Dialog.alertError("Gagal diupdate!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (PenerimaController.id >= 0) {
            try {
                ArrayList<PenerimaModel> penerimas = PenerimaModel.get(PenerimaController.id);
                for(PenerimaModel penerima : penerimas){
                    nama.setText(penerima.getNama());
                    email.setText(penerima.getEmail());
                    no_telp.setText(penerima.getNo_telp());
                    alamat.setText(penerima.getAlamat());
                    jumlah_orang.setText(Integer.toString(penerima.getJumlahOrang()));
                }
            } catch (SQLException ex) {
                Logger.getLogger(PenerimaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        try {
             no_telp.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,15}")) {
                        no_telp.setText(oldValue);
                        }
                    }
                });

                jumlah_orang.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d{0,4}")) {
                            jumlah_orang.setText(oldValue);
                        }
                    }
                });   
        } catch (Exception e) {
        }
        
        
        try {
             initTable("");
        } catch (Exception e) {
        }
       
        
        
    }    

    
    public  void initTable(String keyword) throws SQLException{

        dataPenerima.clear(); 
        
        ArrayList<PenerimaModel> penerimas = 
                keyword.equals("") ? 
                PenerimaModel.getAll():
                PenerimaModel.getAll(keyword)
                ;
        
        Label notfoundLabel = new Label("Data Penerima Tidak Ditemukan");
        notfoundLabel.setFont(Font.font(20));
       
        
        if (penerimas.size() == 0) {
            table.setPlaceholder(notfoundLabel);
        }
        
        
        int no = 0;
        for(PenerimaModel penerima : penerimas){
            dataPenerima.add(new DataPenerima(
                    1+no++, 
                    penerima.getId(), 
                    penerima.getNama(), 
                    penerima.getEmail(), 
                    penerima.getNo_telp(), 
                    penerima.getJumlahOrang(), 
                    penerima.getAlamat()));
        }
        
        result_count.setText(String.format("%s hasil", penerimas.size()));
        table.setItems(dataPenerima);
        colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
        colNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        colNoTelp.setCellValueFactory(cellData -> cellData.getValue().getNo_telp());
        colJumlahOrang.setCellValueFactory(cellData -> cellData.getValue().getJumlah_orang());
        colAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
    }
    
    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/penerima/"+file+".fxml"));
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
    
}



class DataPenerima{
    IntegerProperty no, id, jumlah_orang;
    StringProperty nama, email, no_telp, alamat;
    
    public DataPenerima(int no, int id, String nama, String email, 
            String no_telp, int jumlah_orang, String alamat){
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.no_telp = new SimpleStringProperty(no_telp);
        this.jumlah_orang = new SimpleIntegerProperty(jumlah_orang);
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

