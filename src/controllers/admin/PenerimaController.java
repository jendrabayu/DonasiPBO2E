package controllers.admin;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import helpers.Dialog;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
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
    private TableView<Penerima> table;

    @FXML
    private TableColumn<Penerima, Number> colNo;

    @FXML
    private TableColumn<Penerima, String> colNama;

    @FXML
    private TableColumn<Penerima, String> colEmail;

    @FXML
    private TableColumn<Penerima, String> colNoTelp;

    @FXML
    private TableColumn<Penerima, Number> colJumlahOrang;

    @FXML
    private TableColumn<Penerima, String> colAlamat;

    @FXML
    private Label result_count;
    
    @FXML
    private TextField searchField;
    
    private ObservableList<Penerima> data = FXCollections.observableArrayList();
    
    @FXML
    void index(ActionEvent event) {
        PenerimaController.id = 0;
        changeFxml("Penerima");
    }
    
    @FXML
    void add(ActionEvent event) {
        changeFxml("TambahPenerima");
    }
    
    @FXML
    void edit(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Penerima penerima = table.getItems().get(selectedIndex);
            PenerimaController.id = penerima.getId().getValue();
            changeFxml("EditPenerima");
        }else{
            Dialog.alertWarning("Mohon Klik Row Yang Ingin Diedit");
        } 
    }
    
    @FXML
    void search(KeyEvent event) {
        initTable(searchField.getText());
    }
    
    @FXML
    void delete(ActionEvent event){
        int selectedIndex = table.getSelectionModel().getSelectedIndex();    
        if(selectedIndex >= 0){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Hapus Penerima");
            alert.setContentText("Apakah anda yakin?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Penerima penerima = table.getItems().get(selectedIndex);
                if (PenerimaModel.delete(penerima.getId().getValue())) {
                    Dialog.alertSuccess("Data Berhasi Dihapus");
                    initTable(null);              
                }else{
                   Dialog.alertError("Data Gagal DIhapus");
                }
            }
        }else{
            Dialog.alertWarning("Mohon Klik Row Yang Ingin Dihapus");
        } 
    }

    @FXML
    void store(ActionEvent event) {
        if (nama.getText().equals("") ||
                email.getText().equals("")||
                no_telp.getText().equals("")||
                jumlah_orang.getText().equals("")||
                alamat.getText().equals("")) 
        {
           Dialog.alertWarning("Semua field tidak boleh kosong!");
        }else{
            if (PenerimaModel.store(nama.getText(), 
                    email.getText(), 
                    no_telp.getText(), 
                    Integer.parseInt(jumlah_orang.getText()), 
                    alamat.getText())) {
                Dialog.alertSuccess("Data Berhasil Ditambahkan");
                changeFxml("Penerima");
            }else{
                Dialog.alertError("Data Gagal Ditambahkan");
                
            }
        }
    }


    @FXML
    void update(ActionEvent event) {
        if (nama.getText().equals("") ||
              email.getText().equals("")||
              no_telp.getText().equals("")||
              jumlah_orang.getText().equals("")||
              alamat.getText().equals("")) 
          {
            Dialog.alertWarning("Semua field tidak boleh kosong!");
        }else{
            if (PenerimaModel.update(
                    PenerimaController.id, 
                    nama.getText(), 
                    email.getText(), 
                    no_telp.getText(), 
                    Integer.parseInt(jumlah_orang.getText()), 
                    alamat.getText())) {
                Dialog.alertSuccess("Data Berhasil Diupdate");
                changeFxml("Penerima");
            }else{
                Dialog.alertError("Data Gagal Diupdate!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initTable(null);
        
        if (PenerimaController.id > 0) {
            ArrayList<PenerimaModel> penerimas = PenerimaModel.get(PenerimaController.id);
            try {
                for(PenerimaModel penerima : penerimas){
                nama.setText(penerima.getNama());
                email.setText(penerima.getEmail());
                no_telp.setText(penerima.getNo_telp());
                alamat.setText(penerima.getAlamat());
                jumlah_orang.setText(Integer.toString(penerima.getJumlahOrang()));
                }
                
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
                //e.printStackTrace();
            }
        }
        
    }    

    
    public  void initTable(String keyword){
        data.clear(); 
        ArrayList<PenerimaModel> penerimas;
        
        if (keyword != null) {
            penerimas = PenerimaModel.getAll(keyword);
        }else{
            penerimas = PenerimaModel.getAll();
        }
        
        Label notfoundLabel = new Label("Data Penerima Tidak Ditemukan");
        notfoundLabel.setFont(Font.font(20));

        int no = 1;
        for(PenerimaModel penerima : penerimas){
            data.add(new Penerima(
                no++, 
                penerima.getId(), 
                penerima.getNama(), 
                penerima.getEmail(), 
                penerima.getNo_telp(), 
                penerima.getJumlahOrang(), 
                penerima.getAlamat()));
        }
        
        try {    
            if (penerimas.isEmpty()) {
                table.setPlaceholder(notfoundLabel);
            }else{
                result_count.setText(String.format("%s hasil", penerimas.size()));
                table.setItems(data);
                colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
                colNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
                colEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
                colNoTelp.setCellValueFactory(cellData -> cellData.getValue().getNo_telp());
                colJumlahOrang.setCellValueFactory(cellData -> cellData.getValue().getJumlah_orang());
                colAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
            }     
        } catch (Exception e) {
            //e.printStackTrace();
        }
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
            //e.printStackTrace();
        }
    }
    
}



class Penerima{
    IntegerProperty no, id, jumlah_orang;
    StringProperty nama, email, no_telp, alamat;
    
    public Penerima(int no, int id, String nama, String email,String no_telp, int jumlah_orang, String alamat){
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

    public IntegerProperty getId() {
        return id;
    }

    public IntegerProperty getJumlah_orang() {
        return jumlah_orang;
    }

    public StringProperty getNama() {
        return nama;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getNo_telp() {
        return no_telp;
    }

    public StringProperty getAlamat() {
        return alamat;
    }
}

