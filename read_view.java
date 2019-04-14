package bx_bd;

import java.io.File;
import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class read_view
{
   public void Vi(String readf) throws ParserConfigurationException, SAXException, IOException
   {
      //Get Docuemnt Builder
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      
      //Build Document
      //Document document = builder.parse(new File("C:\\Users\\Long\\Desktop\\test\\New PlatformComponent 02\\New_PC_Wordpress.tosca.xml"));
      Document document = builder.parse(new File(readf));
      
      //Normalize the XML Structure; It's just too important !!
      document.getDocumentElement().normalize();
      
      //Here comes the root node
      Element root = document.getDocumentElement();
      System.out.println(root.getNodeName());
      
      //Get all employees
      NodeList nList = document.getElementsByTagName(root.getNodeName());
      System.out.println("============================");
      visitChildNodes(nList);
          
   }
   
   
//This function is called recursively
   private static void visitChildNodes(NodeList nList)
   {
      for (int temp = 0; temp < nList.getLength(); temp++)
      {
         Node node = nList.item(temp);
         
                                
         if (node.getNodeType() == Node.ELEMENT_NODE )
         {
            //System.out.println("Node Name = " + node.getNodeName());  

                  
           if ("ns2:NodeTemplate".equals(node.getNodeName()))
                           {
                                    String a=new String();
                                    a = node.getNodeName();
                                    String b=new String();
                                    NamedNodeMap nodeMap = node.getAttributes();
                                    for (int i = 0; i < nodeMap.getLength(); i++)
                                       {
                                        Node tempNode = nodeMap.item(i);
                                        b = tempNode.getNodeValue();
                                    //System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
                                    try { 
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
                                    Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
                                    Statement st = con.createStatement(); 
                                    st.executeUpdate("insert into zd(list,name) values('"+b+"','"+a+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                           }
           
           if ("property".equals(node.getNodeName()))
                           {
                                    String a=new String();
                                    a = node.getNodeName();
                                    String b=new String();
                                    b = node.getTextContent();
                                    //NamedNodeMap nodeMap = node.getAttributes();
                                    //for (int i = 0; i < nodeMap.getLength(); i++)
                                    //   {
                                    //    Node tempNode = nodeMap.item(i);
                                    //    b = tempNode.getTextContent();
                                    //System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
                                    try { 
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
                                    Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
                                    Statement st = con.createStatement(); 
                                    st.executeUpdate("insert into zd(list,name) values('"+b+"','"+a+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       //}
                           }
           if ("ns2:Requirement".equals(node.getNodeName()))
                           {
                                String a=new String();
                                    a = node.getNodeName();
                               if (node.hasAttributes()) 
                                    {
                                        
                               String b=new String();
                                    NamedNodeMap nodeMap = node.getAttributes();
                                    for (int i = 0; i < nodeMap.getLength(); i++)
                                       {
                                        Node tempNode = nodeMap.item(i);
                                        b = tempNode.getNodeValue();
                                    //System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
                                    try { 
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
                                    Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
                                    Statement st = con.createStatement(); 
                                    st.executeUpdate("insert into zd(list,name) values('"+b+"','"+a+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                           }
                           }
           
           if ("ns2:Capability".equals(node.getNodeName()))
                           {
                               String a=new String();
                                    a = node.getNodeName();
                               if (node.hasAttributes()) 
                                    {
                                            
                                    String b=new String();
                                    NamedNodeMap nodeMap = node.getAttributes();
                                    for (int i = 0; i < nodeMap.getLength(); i++)
                                       {
                                        Node tempNode = nodeMap.item(i);
                                        b = tempNode.getNodeValue();
                                    //System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
                                    try { 
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
                                    Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
                                    Statement st = con.createStatement(); 
                                    st.executeUpdate("insert into zd(list,name) values('"+b+"','"+a+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                                    }
                           }
           
            
                    
                       
        }
         if (node.hasChildNodes()) 
                {
                  //We got more childs; Let's visit them as well
                  visitChildNodes(node.getChildNodes());
                }
                                
      }
   }
}



