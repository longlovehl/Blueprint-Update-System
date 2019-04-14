
package bx_bd;

import Example.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class deletetablezd {
    public void deletetable() throws ParserConfigurationException, SAXException, IOException
    {
       
        
        try { 
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
        Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
        
        Statement st4 = con.createStatement(); 
        st4.executeUpdate("delete from zd");
        
        con.close(); 
        } catch (Exception e) 
        { 
        System.err.println("Got an exception! "); 
        System.err.println(e.getMessage()); 
        }
    }
}
