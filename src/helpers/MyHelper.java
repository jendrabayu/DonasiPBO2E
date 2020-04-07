
package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyHelper {
    
    
    //Generate MD5
    public static String getMd5(String text){
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            md5 = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        
        return  md5;
    }
    
    //Cek Koneksi DB
    public static int checkConnection(){
        DBHandler dbh = new DBHandler();
        if (dbh.getConnection() == null) {
            Dialog.alertError("Koneksi ke database gagal!");
            return 0;
        }
        return 1;
    }
}
