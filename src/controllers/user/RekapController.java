package controllers.user;


import helpers.DBHelper;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RekapController implements Initializable {

    
    @FXML
    private TableView<RekapPenyaluranMakanan> mTable;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, Number> mNo;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mDate;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mNama;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, Number> mJumlahOrrg;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mAlamat;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, String> mMakanan;

    @FXML
    private TableColumn<RekapPenyaluranMakanan, Number> mJumlah;

    @FXML
    private TableView<RekapPenyaluranUang> uTable;

    @FXML
    private TableColumn<RekapPenyaluranUang, Number> uNo;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> uDate;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> uNama;

    @FXML
    private TableColumn<RekapPenyaluranUang, Number> uJumlahOrg;

    @FXML
    private TableColumn<RekapPenyaluranUang, String> uAlamat;

    @FXML
    private TableColumn<RekapPenyaluranUang, Number> uJumlah;
    
       
    private ObservableList<RekapPenyaluranMakanan> mData = FXCollections.observableArrayList();
        
    private ObservableList<RekapPenyaluranUang> uData = FXCollections.observableArrayList();



    
    private void initMTable(){
        ResultSet rs = DBHelper.query(""
                + "SELECT DATE(penyaluran_uang.created_at) as date, "
                + "penyaluran_uang.*, "
                + "penerima.* "
                + "FROM penyaluran_uang JOIN penerima ON penyaluran_uang.penerima_id = penerima.id "
                + "ORDER BY penyaluran_uang.created_at DESC");
        
        try {
            int no = 1;
//int no, int jumlahOrang, int jumlah, String date, String nama, String alamat
            while (rs.next()) {                
              uData.add(new RekapPenyaluranUang(
                      no++, 
                      rs.getInt("penerima.jumlah_orang"), 
                      rs.getInt("penyaluran_uang.jumlah"), 
                      rs.getString("date"), 
                      rs.getString("penerima.nama"), 
                      rs.getString("penerima.alamat")));
              
            }
            
            uTable.setItems(uData);
            uNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
            uDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
            uNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
            uAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
            uJumlahOrg.setCellValueFactory(cellData -> cellData.getValue().getJumlahOrang());
            uJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     private void initUTable(){
          ResultSet rs = DBHelper.query(""
                + "SELECT DATE(penyaluran_makanan.created_at) as date, "
                + "penyaluran_makanan.*, "
                + "makanan.*, "
                + "penerima.* "
                + "FROM penyaluran_makanan JOIN makanan ON penyaluran_makanan.makanan_id = makanan.id "
                + "JOIN penerima ON penyaluran_makanan.penerima_id = penerima.id "
                + "ORDER BY penyaluran_makanan.created_at DESC");
        
        try {
            int no = 1;

            while (rs.next()) {                
                mData.add(new RekapPenyaluranMakanan(
                        no++, 
                        rs.getInt("penerima.jumlah_orang"), 
                        rs.getInt("penyaluran_makanan.jumlah"), 
                        rs.getString("date"), 
                        rs.getString("penerima.nama"),
                        rs.getString("penerima.alamat"), 
                        rs.getString("makanan.nama")));
                
                System.out.println( rs.getString("makanan.nama"));
            }
            
            mTable.setItems(mData);
            mNo.setCellValueFactory(cellData -> cellData.getValue().getNo());
            mDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
            mNama.setCellValueFactory(cellData -> cellData.getValue().getNama());
            mAlamat.setCellValueFactory(cellData -> cellData.getValue().getAlamat());
            mJumlahOrrg.setCellValueFactory(cellData -> cellData.getValue().getJumlahOrang());
            mMakanan.setCellValueFactory(cellData -> cellData.getValue().getMakanan());
            mJumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initMTable();
        initUTable();

    }
    
}



class RekapPenyaluranMakanan{
    IntegerProperty no, jumlahOrang, jumlah;
    StringProperty date, nama, alamat, makanan;

    public RekapPenyaluranMakanan(int no, int jumlahOrang, int jumlah, String date, String nama, String alamat, String makanan) {
        this.no = new SimpleIntegerProperty(no);
        this.jumlahOrang = new SimpleIntegerProperty(jumlahOrang);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.date = new SimpleStringProperty(date);
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
        this.makanan = new SimpleStringProperty(makanan);
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

    public StringProperty getAlamat() {
        return alamat;
    }

    public StringProperty getMakanan() {
        return makanan;
    }
    
    
    
}

class RekapPenyaluranUang{
    IntegerProperty no, jumlahOrang, jumlah;
    StringProperty date, nama, alamat;

    public RekapPenyaluranUang(int no, int jumlahOrang, int jumlah, String date, String nama, String alamat) {
        this.no = new SimpleIntegerProperty(no);
        this.jumlahOrang = new SimpleIntegerProperty(jumlahOrang);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.date = new SimpleStringProperty(date);
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
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

    public StringProperty getAlamat() {
        return alamat;
    }
    
    
    
    
}

