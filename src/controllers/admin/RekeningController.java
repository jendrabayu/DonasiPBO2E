package controllers.admin;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.RekeningModel;


public class RekeningController implements Initializable {
    
    private static int id;
    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<Rekening> table;

    @FXML
    private TableColumn<Rekening, Number> colNo;

    @FXML
    private TableColumn<Rekening, String> colNamaBank;

    @FXML
    private TableColumn<Rekening, String> colAtasNama;

    @FXML
    private TableColumn<Rekening, String> colNoRekening;
    
    private ObservableList<Rekening> data = FXCollections.observableArrayList();
    
    @FXML
    private JFXTextField fieldNamaBank;

    @FXML
    private JFXTextField fieldAtasNama;

    @FXML
    private JFXTextField fieldNoRekening;

    @FXML
    void index(ActionEvent event) {
        RekeningController.id = 0;
        changeFxml("Rekening");
    }

    @FXML
    void add(ActionEvent event) {
        changeFxml("TambahRekening");
    }
    
    @FXML
    void edit(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Rekening rekening = table.getItems().get(selectedIndex);
            RekeningController.id = rekening.getId().getValue();
            changeFxml("EditRekening");
        }else{
            Dialog.alertWarning("Klik row rekening yang ingin diedit!");
        } 
    }

    @FXML
    void delete(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Rekening rekening = table.getItems().get(selectedIndex);
            RekeningController.id = rekening.getId().getValue();
            //Konfirmasi
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus Rekening");
            alert.setContentText("Apakah anda yakin?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if (RekeningModel.delete(RekeningController.id)) {
                    Dialog.alertSuccess("Data Berhasil Dihapus");
                    initTable();
                }else{
                    Dialog.alertError("Data Gagal Dihapus");
                }  
            }
        }else{
            Dialog.alertWarning("Klik row yang ingin dihapus!");
        } 
    }
    
    @FXML
    void store(ActionEvent event) {
        if (fieldNamaBank.getText().equals("") || fieldAtasNama.getText().equals("") || fieldNoRekening.getText().equals("")) {
            Dialog.alertWarning("Semua field tidak boleh kosong!");
        }else{
            if(RekeningModel.store(fieldNamaBank.getText(), fieldAtasNama.getText(), fieldNoRekening.getText())) {
                Dialog.alertSuccess("Data Berhasil Ditambahkan");
                changeFxml("Rekening");
            }else{
                Dialog.alertError("Data Gagal Ditambahkan");
            }
        }
    }
    
    
    @FXML
    void update(ActionEvent event) {
        if (fieldNamaBank.getText().equals("") || fieldAtasNama.getText().equals("") || fieldNoRekening.getText().equals("")) {
            Dialog.alertWarning("Semua field tidak boleh kosong!");
        }else{
            if(RekeningModel.update(RekeningController.id, fieldNamaBank.getText(), fieldAtasNama.getText(), fieldNoRekening.getText())) {
            Dialog.alertSuccess("Data Berhasil Diupdate");
            changeFxml("Rekening");
            }else{
                Dialog.alertError("Data Gagal Diupdate");
            }
        }
    }

    private void initTable(){
        data.clear();
        ArrayList<RekeningModel> rekenings = RekeningModel.getAll();
        int no = 0; 
        for (RekeningModel rm : rekenings){
            data.add(new Rekening(
                    1+no++, 
                    rm.getId(), 
                    rm.getNamaBank(), 
                    rm.getAtasNama(),
                    rm.getNoRekening()
                    )
                );
        }
        
        try {
            table.setItems(data);
            colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
            colNamaBank.setCellValueFactory(cellData -> cellData.getValue().getNamaBank());
            colAtasNama.setCellValueFactory(cellData -> cellData.getValue().getAtasNama());
            colNoRekening.setCellValueFactory(cellData -> cellData.getValue().getNoRekening());
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        initTable();
        
        if (RekeningController.id > 0) {
            try {
                ArrayList<RekeningModel> rekenings = RekeningModel.get(RekeningController.id);
                for(RekeningModel rekening : rekenings){
                    fieldNamaBank.setText(rekening.getNamaBank());
                    fieldAtasNama.setText(rekening.getAtasNama());
                    fieldNoRekening.setText(rekening.getNoRekening());
                }
                
                fieldNoRekening.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d{0,25}")) {
                            fieldNoRekening.setText(oldValue);
                        }
                    }
                });
            } catch (Exception e) {
                //e.printStackTrace();
            }
        } 
    }

    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/rekening/"+file+".fxml"));
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

class Rekening{
    
    IntegerProperty no, id;
    StringProperty namaBank, atasNama ,noRekening;

    public Rekening(int no, int id, String namaBank, String atasNama, String noRekening) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.namaBank = new SimpleStringProperty(namaBank);
        this.atasNama = new SimpleStringProperty(atasNama);
        this.noRekening = new SimpleStringProperty(noRekening);
    }

    public IntegerProperty getNo() {
        return no;
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getNamaBank() {
        return namaBank;
    }

    public StringProperty getAtasNama() {
        return atasNama;
    }

    public StringProperty getNoRekening() {
        return noRekening;
    }
}
