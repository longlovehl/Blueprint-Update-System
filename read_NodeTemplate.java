
package bx_bd;

import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class read_NodeTemplate {
    public void No() throws ParserConfigurationException, SAXException, IOException
    {
        
        String b[]=new String[8];
        ResultSet rs = null;
        for (int x=0; x < 8; x++)
              {
              b[x] = "";}
        try { 
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
        Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
        Statement st = con.createStatement(); 
        rs = st.executeQuery("select * from zc");
        
        int count = 0;
        int limit = 0;       
        int l = 0;

        boolean k = false;
        while (rs.next())
      {
         if (("ns2:Capability".equals(rs.getString(2)) && count ==3)) 
         {
                l=2;
         }
         if ("ns2:DeploymentArtifact".equals(rs.getString(2)) && count ==3)
         {
                l=4;
         }
           
          b[l]=rs.getString(1);
          //System.out.println(b[l]);
          //For os
          if (k==true && count == 7) 
          {
              l=11;
              k = false;
          }
          
          if ("os".equals(rs.getString(1)))
                  {
                      limit=0;
                      k = true;
                  } else if ("software".equals(rs.getString(1)))
                  {
                      limit = 2;
                      l = 7;
                  }
          count++;
          l++;
         
          if (count == limit)
          {
              Class.forName("com.mysql.jdbc.Driver");
              String url2 = "jdbc:mysql://localhost:3306/migrate_bx"; 
              Connection conn = DriverManager.getConnection(url2,"root","hoang@long"); 
              Statement stt = conn.createStatement();
              stt.executeUpdate("insert into nodetemplate(id,maxinstances,mininstances,typ,provider,instancetype,baseimage,packages,rid1,rid1type,rid2,rid2type,cid,cidtype,daname,xmlns,artifactref,artifacttype) values('"+b[0]+"','"+b[1]+"','"+b[2]+"','"+b[3]+"','"+b[4]+"','"+b[5]+"','"+b[6]+"','"+b[7]+"','"+b[8]+"','"+b[9]+"','"+b[10]+"','"+b[11]+"','"+b[12]+"','"+b[13]+"','"+b[14]+"','"+b[15]+"','"+b[16]+"','"+b[17]+"')");                    

              /*for (int z = 0; z < b.length; z++ )
              {
                  //System.out.println("Attr name : " + b[1]+ "; Value = " + b[2]);
                  System.out.println(z);
                  System.out.println(b[z]);
              }*/
              
              for (int x=0; x < 8; x++)
              {
              b[x] = null;}
              l=0;
              limit=0;
              count=0;
          }
      }
        con.close(); 
        } catch (Exception e) 
        { 
        System.err.println("Got an exception! "); 
        System.err.println(e.getMessage()); 
        }
        //String list = rs.getString("list");
        //System.out.format("%s\n", list);
    }
} 