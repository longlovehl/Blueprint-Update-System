package bx_bd;

import Example.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class read_newPlatform {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        //read_view rv = new read_view();
        //rv.Vi();
        
        String b[]=new String[10];
        ResultSet rs = null;
        for (int x=0; x < b.length; x++)
              {
              b[x] = "";}
        try { 
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
        Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
        Statement st = con.createStatement(); 
        rs = st.executeQuery("select * from zd");
        
        int count = 0;
        int l = 0;
        while (rs.next())
      {
         
          b[l]=rs.getString(1);
          l++;
         
          
      }
              Class.forName("com.mysql.jdbc.Driver");
              String url2 = "jdbc:mysql://localhost:3306/migrate_bx"; 
              Connection conn = DriverManager.getConnection(url2,"root","hoang@long"); 
              Statement stt = conn.createStatement();
              stt.executeUpdate("insert into viewnodetemplate(id,maxinstances,mininstances,typ,provider,instancetype,baseimage,packages,cid,cidtype) values('"+b[0]+"','"+b[1]+"','"+b[2]+"','"+b[3]+"','"+b[4]+"','"+b[5]+"','"+b[6]+"','"+b[7]+"','"+b[8]+"','"+b[9]+"')");                    

        con.close(); 
        } catch (Exception e) 
        { 
        System.err.println("Got an exception! "); 
        System.err.println(e.getMessage());  
        }
    }
}
