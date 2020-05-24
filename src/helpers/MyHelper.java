
package helpers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


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
    
    public static String rupiahFormat(String money){
        
        double harga = Double.parseDouble(money);
 
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
 
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        
 
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String x = kursIndonesia.format(harga);
       
        
        return x;
    }

    
    
    

    public static String getDateNow(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

}
