package helpers;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import models.UserModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Session {
    
    public static int createSession(UserModel user){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("session");
            doc.appendChild(rootElement);

            Element id = doc.createElement("id");
            rootElement.appendChild(id);
            id.setTextContent(Integer.toString(user.getId()));
            
            Element nama = doc.createElement("nama");
            rootElement.appendChild(nama);
            nama.setTextContent(user.getNama());

            Element email = doc.createElement("email");
            rootElement.appendChild(email);
            email.setTextContent(user.getEmail());

            Element no_telp = doc.createElement("no_telp");
            rootElement.appendChild(no_telp);
            no_telp.setTextContent(user.getNo_telp());

            Element alamat = doc.createElement("alamat");
            rootElement.appendChild(alamat);
            alamat.setTextContent(user.getAlamat());

            Element password = doc.createElement("password");
            rootElement.appendChild(password);
            password.setTextContent(user.getPassword());

            Element roles = doc.createElement("role");
            rootElement.appendChild(roles);
            roles.setTextContent(String.valueOf(user.getRole()));
            
            Element created_at = doc.createElement("created_at");
            rootElement.appendChild(created_at);
            created_at.setTextContent(user.getCreated_at());
            
            Element updated_at = doc.createElement("updated_at");
            rootElement.appendChild(updated_at);
            updated_at.setTextContent(user.getUpdated_at());

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "/session.xml"));
            t.transform(source, result);
            
            return 1;
        } catch (ParserConfigurationException | TransformerException e) {
            return 0;
        }
    }

      
    public static int sessionDestroy(){  
        File data = new File(System.getProperty("user.dir") + "/session.xml");
        if (data.exists()) {
            data.delete();
            return 1;
        }
        return 0;
    }
      
      
    public static int cekSession(){
        
        File data = new File(System.getProperty("user.dir") + "/session.xml");
        if (data.exists()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = (Document) dBuilder.parse(data);
                
                int id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
                String nama = doc.getElementsByTagName("nama").item(0).getTextContent();
                String email = doc.getElementsByTagName("email").item(0).getTextContent();
                String no_telp = doc.getElementsByTagName("no_telp").item(0).getTextContent();
                String alamat = doc.getElementsByTagName("alamat").item(0).getTextContent();
                String password = doc.getElementsByTagName("password").item(0).getTextContent();
                int role = Integer.parseInt(doc.getElementsByTagName("role").item(0).getTextContent());
                String created_at = doc.getElementsByTagName("created_at").item(0).getTextContent();
                String updated_at = doc.getElementsByTagName("updated_at").item(0).getTextContent();                   
                
                UserModel.setId(id);
                UserModel.setNama(nama);
                UserModel.setEmail(email);
                UserModel.setNo_telp(no_telp);
                UserModel.setAlamat(alamat);
                UserModel.setPassword(password);
                UserModel.setRole(role);
                UserModel.setCreated_at(created_at);
                UserModel.setUpdated_at(updated_at);
                
                return 1;           
                
            } catch (IOException | ParserConfigurationException | SAXException e) {
                return 0; 
            }
     
        }                         
        return 0;
    }
}
