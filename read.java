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

public class read
{
   //public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    public void readfileXML(String readf) throws ParserConfigurationException, SAXException, IOException
   {
      //Get Docuemnt Builder
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      
      //Build Document
      //Document document = builder.parse(new File("C:\\Users\\Long\\Desktop\\test\\Wordpress_tosca.xml"));
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
      read_ArtifactTemplate a = new read_ArtifactTemplate();
      a.Ar();
      read_RelationshipTemplate b = new read_RelationshipTemplate();
      b.Rl();
      read_NodeTemplate c = new read_NodeTemplate();
      c.No();
      
          
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
//--------------------------------------------------------------------------------------------------------------             
            if ("ns2:Definitions".equals(node.getNodeName()))
                           {
                               if (node.hasAttributes()) 
                                    {
                                    String a1=new String();
                                    String a2=new String();
                                    String a3=new String();
                                    // get attributes names and values
                                    NamedNodeMap nodeMap = node.getAttributes();
                                    for (int i = 0; i < nodeMap.getLength(); i++)
                                    {
                                    Node tempNode = nodeMap.item(i);
                                                                      
                                    if ("id".equals(tempNode.getNodeName()))
                                        {
                                            a1 = tempNode.getNodeValue();
                                            System.out.println("Attr name : " +a1+ "");
                                        } else
                                    if ("name".equals(tempNode.getNodeName()))
                                        {
                                            a2 = tempNode.getNodeValue();
                                        } else
                                    if ("xmlns:ns2".equals(tempNode.getNodeName()))
                                        {
                                            a3 = tempNode.getNodeValue();
                                        } 
                                    }
                                    try { 
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
                                    Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
                                    Statement st = con.createStatement(); 
                                    st.executeUpdate("insert into definitions(id,name,xmlns) values('"+a1+"','"+a2+"','"+a3+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }
                                    }
                           }
//----------------------------------------------------------------------------------------------------------------------------------             
           if ("ns2:ArtifactTemplate".equals(node.getNodeName()))
                           {
                                            String b=new String();
                                                                                // get attributes names and values
                                    NamedNodeMap nodeMap = node.getAttributes();
                                    for (int i = 0; i < nodeMap.getLength(); i++)
                                       {
                                        Node tempNode = nodeMap.item(i);
                                        b = tempNode.getNodeValue();
                                    try { 
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
                                    Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
                                    Statement st = con.createStatement(); 
                                    st.executeUpdate("insert into za(list) values('"+b+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }
                                        } 
                                    }
                            
           if ("ns2:ArtifactReference".equals(node.getNodeName()))
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
                                    st.executeUpdate("insert into za(list) values('"+b+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }
                                         
                                        } 
                                    
                                }
//-----------------------------------------------------------------------------------------------------------------------           
           if ("ns2:RelationshipTemplate".equals(node.getNodeName()))
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
                                    st.executeUpdate("insert into zb(list) values('"+b+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                           }
            if ("ns2:SourceElement".equals(node.getNodeName()))
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
                                    st.executeUpdate("insert into zb(list) values('"+b+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                           }
            
            if ("ns2:TargetElement".equals(node.getNodeName()))
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
                                    st.executeUpdate("insert into zb(list) values('"+b+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                           }                    
//---------------------------------------------------------------------------------------------------------         
           if ("ns2:ServiceTemplate".equals(node.getNodeName()))
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
                                    st.executeUpdate("insert into servicetemplate(id) values('"+b+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                           }
//---------------------------------------------------------------------------------------------------------------           
           
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
                                    st.executeUpdate("insert into zc(list,name) values('"+b+"','"+a+"')");
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
                                    st.executeUpdate("insert into zc(list,name) values('"+b+"','"+a+"')");
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
                                    st.executeUpdate("insert into zc(list,name) values('"+b+"','"+a+"')");
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
                                    st.executeUpdate("insert into zc(list,name) values('"+b+"','"+a+"')");
                                    con.close(); 
                                    } catch (Exception e) 
                                    { 
                                    System.err.println("Got an exception! "); 
                                    System.err.println(e.getMessage()); 
                                    }   
                                       }
                                    }
                           }
           
            if ("ns2:DeploymentArtifact".equals(node.getNodeName()))
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
                                    st.executeUpdate("insert into zc(list,name) values('"+b+"','"+a+"')");
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



