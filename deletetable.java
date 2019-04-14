
package bx_bd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class deletetable {
    public void deletetable() throws ParserConfigurationException, SAXException, IOException
    {
       
        
        try { 
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
        Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
        Statement st1 = con.createStatement(); 
        st1.executeUpdate("delete from za");
        Statement st2 = con.createStatement(); 
        st2.executeUpdate("delete from zb");
        Statement st3 = con.createStatement(); 
        st3.executeUpdate("delete from zc");
        Statement st4 = con.createStatement(); 
        st4.executeUpdate("delete from zd");
        Statement st5 = con.createStatement(); 
        st5.executeUpdate("delete from artifacttemplate");
        Statement st6 = con.createStatement(); 
        st6.executeUpdate("delete from definitions");
        Statement st7 = con.createStatement(); 
        st7.executeUpdate("delete from nodetemplate");
        Statement st8 = con.createStatement(); 
        st8.executeUpdate("delete from relationshiptemplate");
        Statement st9 = con.createStatement(); 
        st9.executeUpdate("delete from servicetemplate");
        Statement st10 = con.createStatement(); 
        st10.executeUpdate("delete from viewnodetemplate");
        con.close(); 
        } catch (Exception e) 
        { 
        System.err.println("Got an exception! "); 
        System.err.println(e.getMessage()); 
        }
    }
}
