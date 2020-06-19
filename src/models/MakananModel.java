package models;
import helpers.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MakananModel {
    
    private static final String TABLE = "makanan";
    private String
            nama, 
            expired_date,
            keterangan,
            created_at,
            updated_at;
    private int 
            id, 
            user_id, 
            status_id,
            jumlah_awal,
            jumlah;
            

    public MakananModel(int id, int user_id, String nama,int jumlah_awal,  int jumlah, String expired_date, String keterangan, int status_id, String created_at, String updated_at) {
        this.nama = nama;
        this.expired_date = expired_date;
        this.keterangan = keterangan;
        this.status_id = status_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id = id;
        this.user_id = user_id;
        this.jumlah_awal = jumlah_awal;
        this.jumlah = jumlah;
    }
    
    public MakananModel(){
        
    }

     
    public static int create(String nama, int jumlah, String expired_date, String keterangan){
            Map<String, String> params = new LinkedHashMap<>();
            params.put("`user_id`",String.format("'%s'", UserModel.getId())); 
            params.put("`nama`", String.format("'%s'",nama));
            params.put("`jumlah_awal`", String.format("'%s'", jumlah));
            params.put("`jumlah`", String.format("'%s'", jumlah));
            params.put("`expired_date`", String.format("'%s'", expired_date));
            params.put("`keterangan`", String.format("'%s'", keterangan));         
            if (DBHelper.insert("makanan", params)) {
                return 1;
            }            
            return 0;
    }
    
    public static ArrayList<MakananModel> getAll(){
        ArrayList<MakananModel> result = new ArrayList<MakananModel>();
        ResultSet rs = DBHelper.selectAll(TABLE);
        try {
            while (rs.next()){
                MakananModel makanan = new MakananModel();
                makanan.setId(rs.getInt("id"));
                makanan.setUser_id(rs.getInt("user_id"));
                makanan.setNama(rs.getString("nama"));
                makanan.setJumlah_awal(rs.getInt("jumlah_awal"));
                makanan.setJumlah(rs.getInt("jumlah"));
                makanan.setExpired_date(rs.getString("expired_date"));
                makanan.setKeterangan(rs.getString("keterangan"));
                makanan.setStatus_id(rs.getInt("status_id"));
                makanan.setCreated_at(rs.getString("created_at"));
                makanan.setUpdated_at(rs.getString("updated_at"));
                result.add(makanan);
            }
        } catch (Exception e){
            e.printStackTrace();    
        }        
        return result; 
    }
    
    
    public static ArrayList<MakananModel> get(int id){
        ArrayList<MakananModel> result = new ArrayList<MakananModel>();
        ResultSet rs = DBHelper.selectAll(TABLE, String.format("user_id = %s", id ) );
        try {
            while (rs.next()){
                MakananModel makanan = new MakananModel();
                makanan.setId(rs.getInt("id"));
                makanan.setUser_id(rs.getInt("user_id"));
                makanan.setNama(rs.getString("nama"));
                makanan.setJumlah_awal(rs.getInt("jumlah_awal"));
                makanan.setJumlah(rs.getInt("jumlah"));
                makanan.setExpired_date(rs.getString("expired_date"));
                makanan.setKeterangan(rs.getString("keterangan"));
                makanan.setStatus_id(rs.getInt("status_id"));
                makanan.setCreated_at(rs.getString("created_at"));
                makanan.setUpdated_at(rs.getString("updated_at"));
                result.add(makanan);
            }
        } catch (Exception e){
            e.printStackTrace();    
        }        
        return result; 
    }
    
    
     public static ArrayList<MakananModel> getAllAvailable(){
        ArrayList<MakananModel> result = new ArrayList<MakananModel>();
        ResultSet rs = DBHelper.selectAll(TABLE, "jumlah > 0");
        try {
            while (rs.next()){
                MakananModel makanan = new MakananModel();
                makanan.setId(rs.getInt("id"));
                makanan.setUser_id(rs.getInt("user_id"));
                makanan.setNama(rs.getString("nama"));
                makanan.setJumlah_awal(rs.getInt("jumlah_awal"));
                makanan.setJumlah(rs.getInt("jumlah"));
                makanan.setExpired_date(rs.getString("expired_date"));
                makanan.setKeterangan(rs.getString("keterangan"));
                makanan.setStatus_id(rs.getInt("status_id"));
                makanan.setCreated_at(rs.getString("created_at"));
                makanan.setUpdated_at(rs.getString("updated_at"));
                result.add(makanan);
            }
        } catch (Exception e){
            e.printStackTrace();    
        }        
        return result; 
    }
    
    

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getJumlah_awal() {
        return jumlah_awal;
    }

    public void setJumlah_awal(int jumlah_awal) {
        this.jumlah_awal = jumlah_awal;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
   
    
    

    
    
}
