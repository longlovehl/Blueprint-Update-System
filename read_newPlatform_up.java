package bx_bd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class read_newPlatform_up {
    public void up() throws ParserConfigurationException, SAXException, IOException
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
              //stt.executeUpdate("update viewnodetemplate set maxinstances = b[1], mininstances = b[2], typ = b[3], provider = b[4], instancetype = b[5], baseimage = b[6], packages = b[7], cid = [b8] , cidtype = b[9] where id = b[0]");
              stt.executeUpdate("update viewnodetemplate set maxinstances = '"+b[1]+"', mininstances = '"+b[2]+"', typ = '"+b[3]+"', provider = '"+b[4]+"', instancetype = '"+b[5]+"', baseimage = '"+b[6]+"', packages = '"+b[7]+"', cid = '"+b[8]+"', cidtype = '"+b[9]+"' where id = '"+b[0]+"'");
        con.close(); 
        } catch (Exception e) 
        { 
        System.err.println("Got an exception! "); 
        System.err.println(e.getMessage()); 
        }
    }
}
