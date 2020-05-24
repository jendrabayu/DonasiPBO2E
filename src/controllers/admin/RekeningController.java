package controllers.admin;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.PenerimaModel;
import models.RekeningModel;


public class RekeningController implements Initializable {
    
    private static int id;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField namaBankField;

    @FXML
    private JFXTextField noRekeningField;

    @FXML
    void createRekening(ActionEvent event) {
        RekeningController.id = -1;
        if (namaBankField.getText().equals("") || noRekeningField.getText().equals("")) {
            Dialog.alertWarning("Semua field harus diisi!");
        }else{
            if (RekeningModel.create(namaBankField.getText(), noRekeningField.getText())) {
                Dialog.alertSuccess("Berhasil Menambahkan Rekening Baru");
                changeFxml("Rekening");
            }else{
                Dialog.alertError("Terjadi Kesalahan");
            }
        }
    }

    @FXML
    void showRekening(ActionEvent event) {
        changeFxml("Rekening");
    }

    @FXML
    private TableView<DataRekening> table;

    @FXML
    private TableColumn<DataRekening, Number> colNo;

    @FXML
    private TableColumn<DataRekening, String> colNamaBank;

    @FXML
    private TableColumn<DataRekening, String> colNoRekening;

    @FXML
    private TableColumn<DataRekening, String> colCreatedAt;

    @FXML
    private TableColumn<DataRekening, String> colUpdatedAt;
    
    
    private ObservableList<DataRekening> dataRekening = FXCollections.observableArrayList();

    @FXML
    void addRekening(ActionEvent event) {
        RekeningController.id = -1;
        changeFxml("TambahRekening");
    }

    @FXML
    void deleteRekening(ActionEvent event) throws SQLException {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if(selectedIndex >= 0){
            DataRekening penerima = table.getItems().get(selectedIndex);
            RekeningController.id = penerima.getId().getValue();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus Rekening");
            alert.setContentText("Apakah anda yakin?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
               
                if (RekeningModel.delete(RekeningController.id)) {
                    Dialog.alertSuccess("Berhasil Dihapus");
                    initTable();
                }else{
                      Dialog.alertError("Terjadi Kesalahan");
                }
              
            }
        }else{
            Dialog.alertWarning("Klik row yang akan di hapus");
        } 
}

    @FXML
    void editRekening(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if(selectedIndex >= 0){
            DataRekening penerima = table.getItems().get(selectedIndex);
            RekeningController.id = penerima.getId().getValue();
            changeFxml("EditRekening");
            System.out.println(RekeningController.id);
        }else{
            Dialog.alertWarning("Klik row yang akan di edit");
        } 
      
    }
    
    @FXML
    void updateRekening(ActionEvent event) {
        if (RekeningModel.update(RekeningController.id, namaBankField.getText(), noRekeningField.getText())) {
            Dialog.alertSuccess("Rekening Berhasil Diupdate");
            changeFxml("Rekening");
        }else{
            Dialog.alertError("Terjadi Kesalahan");
        }
    }
    
    private void initTable() throws SQLException{
        dataRekening.clear();
        
        ArrayList<RekeningModel> rekenings = RekeningModel.getAll();
        
        int no = 0;
        
        for (RekeningModel rm : rekenings){
            dataRekening.add(
                new DataRekening(
                    1+no++, 
                    rm.getId(), 
                    rm.getNamaBank(), 
                    rm.getNoRekening(), 
                    rm.getCreatedAt(),
                    rm.getUpdatedAt()
                    )
                );
        }
        
        table.setItems(dataRekening);
        colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
        colNamaBank.setCellValueFactory(cellData -> cellData.getValue().getNamaBank());
        colNoRekening.setCellValueFactory(cellData -> cellData.getValue().getNoRekening());
        colCreatedAt.setCellValueFactory(cellData -> cellData.getValue().getCreatedAt());
        colUpdatedAt.setCellValueFactory(cellData -> cellData.getValue().getUpdatedAt());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (RekeningController.id >= 0) {
            System.out.println(RekeningController.id);
            try {
                 ArrayList<RekeningModel> rekenings = RekeningModel.get(RekeningController.id);
                 
                 
                 for(RekeningModel rekening : rekenings){
                     namaBankField.setText(rekening.getNamaBank());
                     noRekeningField.setText(rekening.getNoRekening());
                 }
            } catch (Exception e) {
                
            }
        }
        
        try {
            
            
            noRekeningField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,25}")) {
                        noRekeningField.setText(oldValue);
                        }
                    }
           });
        } catch (Exception e) {
        }
        
        
        
        try {
             initTable();
        } catch (Exception e) {
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
          e.printStackTrace();
        }
    }
    
}



class DataRekening{
    
    IntegerProperty no, id;
    StringProperty namaBank, noRekening, createdAt, updatedAt;

    public DataRekening(int no, int id, String namaBank, String noRekening, String createdAt, String updatedAt) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.namaBank = new SimpleStringProperty(namaBank);
        this.noRekening = new SimpleStringProperty(noRekening);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.updatedAt = new SimpleStringProperty(updatedAt);
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

    public StringProperty getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(StringProperty namaBank) {
        this.namaBank = namaBank;
    }

    public StringProperty getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(StringProperty noRekening) {
        this.noRekening = noRekening;
    }

    public StringProperty getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(StringProperty createdAt) {
        this.createdAt = createdAt;
    }

    public StringProperty getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(StringProperty updatedAt) {
        this.updatedAt = updatedAt;
    }

  
    
    
    
}
