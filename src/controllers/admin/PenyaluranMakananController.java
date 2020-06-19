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
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import models.MakananModel;
import models.PenerimaModel;

public class PenyaluranMakananController implements Initializable{
    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label dateTodayLabel;
    
    private static int id;

    @FXML
    private TableView<PenyaluranMakanan> table;

    @FXML
    private TableColumn<PenyaluranMakanan, Number> colNo;

    @FXML
    private TableColumn<PenyaluranMakanan, String> colNama;

    @FXML
    private TableColumn<PenyaluranMakanan, String> colEmail;

    @FXML
    private TableColumn<PenyaluranMakanan, String> colTelp;

    @FXML
    private TableColumn<PenyaluranMakanan, Number> colJmlOrg;

    @FXML
    private TableColumn<PenyaluranMakanan, String> colAlamat;

    @FXML
    private TableColumn<PenyaluranMakanan, String> colmakanan;

    @FXML
    private TableColumn<PenyaluranMakanan, Number> colJumlah;

    @FXML
    private TableColumn<PenyaluranMakanan, String> colExpired;
    
    
    private ObservableList<PenyaluranMakanan> data = FXCollections.observableArrayList();
    
    @FXML
    private JFXComboBox<MakananModel> comboBoxMakanan;

    @FXML
    private JFXComboBox<PenerimaModel> comboBoxPenerima;

    @FXML
    private JFXTextField jumlahPorsi;
    
    private ObservableList<PenerimaModel> dataPenerima;
    private ObservableList<MakananModel> dataMakanan;
    
    private void initComboboxPenerima(){
        
        try {
            dataPenerima =  FXCollections.observableArrayList(PenerimaModel.getAll());
            System.out.println(dataPenerima);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        try {
            comboBoxPenerima.setItems(dataPenerima);
            comboBoxPenerima.setConverter(new StringConverter<PenerimaModel>() {
                @Override
                public String toString(PenerimaModel object) {
                   return String.format("%s (Jumlah Orang: %s)", 
                           object.getNama(),
                           object.getJumlahOrang());
                }

                @Override
                public PenerimaModel fromString(String string) {
                    return comboBoxPenerima.getItems().stream().filter(ap -> 
                     ap.getNama().equals(string)).findFirst().orElse(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComboBoxMakanan(){
        dataMakanan =  FXCollections.observableArrayList(MakananModel.getAllAvailable());

        try {
            comboBoxMakanan.setItems(dataMakanan);
            comboBoxMakanan.setConverter(new StringConverter<MakananModel>() {
                @Override
                public String toString(MakananModel object) {
                   return String.format("%s (Tersedia: %s, Exp: %s)", 
                           object.getNama(),
                           object.getJumlah(),
                           object.getExpired_date()
                       );
                }

                @Override
                public MakananModel fromString(String string) {
                    return comboBoxMakanan.getItems().stream().filter(ap -> 
                     ap.getNama().equals(string)).findFirst().orElse(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void index(ActionEvent event) {
        changeFxml("PenyaluranMakanan");
    }

    @FXML
    void store(ActionEvent event) {
        boolean comboBoxPenerimaEmpty = comboBoxPenerima.getValue() == null ;
        boolean comboBoxMakananEmpty = comboBoxMakanan.getValue() == null;
        boolean jumlahEmpty = jumlahPorsi.getText() == null;
        
        if (comboBoxMakananEmpty || comboBoxPenerimaEmpty || jumlahEmpty) {
            Dialog.alertWarning("Semua field tidak boleh kosong");
        }else {
            int idMakanan = comboBoxMakanan.getValue().getId();
            int jumlah = Integer.parseInt(jumlahPorsi.getText());
            int idPenerima = comboBoxPenerima.getValue().getId();

            if (jumlah>comboBoxMakanan.getValue().getJumlah()) {
                Dialog.alertWarning("Jumlah atau porsi melebihi batas");
            }else{
                //insert database
                Map<String, String> params = new LinkedHashMap<>();
                params.put("`penerima_id`", String.format("'%s'",idPenerima));
                params.put("`makanan_id`", String.format("'%s'", idMakanan));
                params.put("`jumlah`", String.format("'%s'", jumlah));
             
                
                //update jumlah
                Map<String, String> params2 = new LinkedHashMap<String, String>();
                params2.put("`jumlah`", String.format("'%s'", comboBoxMakanan.getValue().getJumlah() - jumlah));  
                DBHelper.update("makanan", params, String.format("id = %s", idMakanan));
                
                if (DBHelper.insert("penyaluran_makanan", params) && DBHelper.update("makanan", params2, String.format("id = %s", idMakanan))) {
                    Dialog.alertSuccess("Berhasil menambahkan data baru");
                    changeFxml("PenyaluranMakanan");
                }else{
                    Dialog.alertError("Gagal menambahkan data baru");
                    changeFxml("PenyaluranMakanan");
                }
            }
        }

    }
    

    @FXML
    void add(ActionEvent event) {
        changeFxml("TambahPenyaluranMakanan");
    }

    @FXML
    void delete(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            PenyaluranMakanan pm = table.getItems().get(selectedIndex);
            PenyaluranMakananController.id= pm.getId().getValue();
            
            //jumlahmaanan
            int jumlah = pm.getJumlah().getValue();

            
//            ambil jumlah makanan
            ResultSet rs = DBHelper.selectAll("makanan", String.format("id = '%s'",pm.getIdMakanan().getValue() ));
            int jumlahMakanan = 0;
            try {
                while (rs.next()) {                    
                    jumlahMakanan = rs.getInt("jumlah");
                }
            } catch (Exception e) {
                
            }
            
            //Konfirmasi
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus Data");
            alert.setContentText("Apakah anda yakin?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if (DBHelper.delete("penyaluran_makanan", String.valueOf(PenyaluranMakananController.id))) {
                    Map<String, String> params2 = new LinkedHashMap<>();
                    params2.put("jumlah", String.format("'%s'", jumlahMakanan + jumlah) ); 
                    DBHelper.update("makanan", params2, String.format("id = '%s'", pm.getIdMakanan().getValue()));
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
    
    
    private void initTable(){
        //join penyaluran_makanan + makanan + penerima
        data.clear();
        ResultSet rs = DBHelper.query(""
                + "SELECT * FROM penyaluran_makanan "
                + "JOIN penerima ON penyaluran_makanan.penerima_id = penerima.id "
                + "JOIN makanan ON penyaluran_makanan.makanan_id = makanan.id "
                + "WHERE DATE(penyaluran_makanan.created_at) = CURRENT_DATE() "
                + "GROUP BY penyaluran_makanan.id ");
        
        try {
          
            int no = 1;
            while (rs.next()) {                
                data.add(new PenyaluranMakanan(
                        rs.getInt("penyaluran_makanan.id"), 
                        rs.getInt("makanan.id"), 
                        no++, 
                        rs.getInt("penerima.jumlah_orang"), 
                        rs.getInt("penyaluran_makanan.jumlah"), 
                        rs.getString("penerima.nama"), 
                        rs.getString("penerima.email"), 
                        rs.getString("penerima.no_telp"), 
                        rs.getString("penerima.alamat"), 
                        rs.getString("makanan.nama"), 
                        rs.getString("makanan.expired_date")));
            }
            try {
                table.setItems(data);
                colNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
                colNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
                colEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
                colTelp.setCellValueFactory(cellData -> cellData.getValue().getTelp());
                colJmlOrg.setCellValueFactory(cellData -> cellData.getValue().getJumlahOrang());
                colAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
                colmakanan.setCellValueFactory(cellData -> cellData.getValue().getMakanan());
                colJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
                colExpired.setCellValueFactory(cellData -> cellData.getValue().getTglExpired());
                
            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        initComboboxPenerima();
        initComboBoxMakanan();
        initTable();
        
        try {
              dateTodayLabel.setText(String.format("Penyaluran makanan hari Ini (%s)", MyHelper.getDateNow()));
        } catch (Exception e) {
        }
        
        
        try {
             jumlahPorsi.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   if (!newValue.matches("\\d{0,12}")) {
                        jumlahPorsi.setText(oldValue);
                    }
                }
                
            });
        } catch (Exception e) {
            System.err.println(e);
        }
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
          e.printStackTrace();
        }
    }
     
     
    @FXML
    void print(ActionEvent event) {
        
    }
       
}

class PenyaluranMakanan{
    
    IntegerProperty id, no, jumlahOrang, jumlah, idMakanan;
    StringProperty nama, email, telp, alamat, makanan, tglExpired;

    public PenyaluranMakanan(int id, int idMakanan, int no, int jumlahOrang, int jumlah, String nama, String email, String telp, String alamat, String makanan, String tglExpired) {
        this.id =  new SimpleIntegerProperty(id);
        this.idMakanan =  new SimpleIntegerProperty(idMakanan);
        this.no =  new SimpleIntegerProperty(no);
        this.jumlahOrang =  new SimpleIntegerProperty(jumlahOrang);
        this.jumlah =  new SimpleIntegerProperty(jumlah);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.telp = new SimpleStringProperty(telp);
        this.alamat = new SimpleStringProperty(alamat);
        this.makanan = new SimpleStringProperty(makanan);
        this.tglExpired = new SimpleStringProperty(tglExpired);
    }

    public void setIdMakanan(IntegerProperty idMakanan) {
        this.idMakanan = idMakanan;
    }

    public IntegerProperty getIdMakanan() {
        return idMakanan;
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

    public StringProperty getTglExpired() {
        return tglExpired;
    }
    
    
    
}
