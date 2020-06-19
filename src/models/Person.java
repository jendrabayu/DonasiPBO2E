package models;

public abstract class Person {
    
    private int id;
    private String nama, email, no_telp, alamat;

    public Person(int id, String nama, String email, String no_telp, String alamat) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.no_telp = no_telp;
        this.alamat = alamat;
    }

    public Person(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
}
