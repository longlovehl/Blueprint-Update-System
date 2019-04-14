
package bx_bd;

import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class read_ArtifactTemplate {
    public  void Ar() throws ParserConfigurationException, SAXException, IOException
    {
        String a[]=new String[100];
        String b[]=new String[3];
        ResultSet rs = null;
        try { 
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
        Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
        Statement st = con.createStatement(); 
        //st.executeUpdate("insert into za(list) values('"+b+"')");
        rs = st.executeQuery("select * from za");
        int j=0;
        while (rs.next())
      {
        a[j] = rs.getString("list");
        j++;
      }
        
        con.close(); 
        } catch (Exception e) 
        { 
        System.err.println("Got an exception! "); 
        System.err.println(e.getMessage()); 
        }
        
        int k=0;
        for (int i=0; i < 6; i++)
        {
            b[k] = a[i];
            k++;
            if (k==3)
            {
                try { 
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
                Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
                Statement st = con.createStatement();
                st.executeUpdate("insert into artifacttemplate(id,type,reference) values('"+b[0]+"', '"+b[1]+"','"+b[2]+"')");
                con.close(); 
                    }catch (Exception e) 
                    { 
                     System.err.println("Got an exception! "); 
                     System.err.println(e.getMessage()); 
                    }
                k=0;
            }
        }
        
        //String list = rs.getString("list");
        //System.out.format("%s\n", list);
    }
}
