/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.File;
import java.io.IOException;
import javafx.scene.Node;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import models.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ACER
 */
public class Session {
    
    public static int buatSessionXML(String id,String nama,String email, String telepon, String alamat, String foto,
            String password, String role){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("session");
            doc.appendChild(rootElement);

            Element id_user = doc.createElement("id");
            rootElement.appendChild(id_user);
            id_user.setTextContent(id);
            
            Element nama_user = doc.createElement("nama");
            rootElement.appendChild(nama_user);
            nama_user.setTextContent(nama);
            
            
            
            Element email_user = doc.createElement("email");
            rootElement.appendChild(email_user);
            email_user.setTextContent(email);

            Element telepon_user = doc.createElement("telepon");
            rootElement.appendChild(telepon_user);
            telepon_user.setTextContent(telepon);

            Element alamat_user = doc.createElement("alamat");
            rootElement.appendChild(alamat_user);
            alamat_user.setTextContent(alamat);

            Element foto_user = doc.createElement("foto");
            rootElement.appendChild(foto_user);
            foto_user.setTextContent(foto);

            Element password_user = doc.createElement("password");
            rootElement.appendChild(password_user);
            password_user.setTextContent(password);

            Element role_user = doc.createElement("role");
            rootElement.appendChild(role_user);
            role_user.setTextContent(role);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "/session.xml"));
            transformer.transform(source, result);
            
            return 1;

        } catch (ParserConfigurationException | TransformerException e) {
             System.out.println(e);
        }
        
        return 0;
  }
      
      
    public static int hapusSession(){  
        File data = new File(System.getProperty("user.dir") + "/session.xml");
        if (data.exists()) {
            data.delete();
            return 1;
        }
        return 0;
    }
      
      
    public static int CekSessionData(){
        
        File data = new File(System.getProperty("user.dir") + "/session.xml");
        if (data.exists()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = (Document) dBuilder.parse(data);
                
                String id = doc.getElementsByTagName("id").item(0).getTextContent();
                String nama = doc.getElementsByTagName("nama").item(0).getTextContent();
                String email = doc.getElementsByTagName("email").item(0).getTextContent();
                String telepon = doc.getElementsByTagName("telepon").item(0).getTextContent();
                String alamat = doc.getElementsByTagName("alamat").item(0).getTextContent();
                String foto = doc.getElementsByTagName("foto").item(0).getTextContent();
                String password = doc.getElementsByTagName("password").item(0).getTextContent();
                String role = doc.getElementsByTagName("role").item(0).getTextContent();
                
                User.setId(id);
                User.setNama(nama);
                User.setEmail(email);
                User.setTelepon(telepon);
                User.setAlamat(alamat);
                User.setFoto(foto);
                User.setPassword(password);
                User.setRole(role); 
                return 1;
                
                
            } catch (IOException | ParserConfigurationException | SAXException e) {
                JOptionPane.showMessageDialog(null, e);
                
            }
            
        }else {
            return 0;
        }                      
        
        return 0;
    }
    
    public static int updateSessionData(String nama, String telepon, String alamat){
        File data = new File(System.getProperty("user.dir") + "/session.xml");
        if (data.exists()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = (Document) dBuilder.parse(data);
          
                
                
                doc.getElementsByTagName("nama").item(0);
                doc.getElementsByTagName("telepon").item(0).setTextContent(telepon);
                doc.getElementsByTagName("alamat").item(0).setTextContent(alamat);

                User.setNama(nama);
                User.setTelepon(telepon);
                User.setAlamat(alamat);
             
                return 1;
                
                
            } catch (IOException | ParserConfigurationException | SAXException e) {
                JOptionPane.showMessageDialog(null, e);
                
            }
            
        }else {
            return 0;
        }             
        
        return 0;
    }

      
}
