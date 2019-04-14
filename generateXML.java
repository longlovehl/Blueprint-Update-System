package bx_bd;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class generateXML {
    public void save(String save) throws ParserConfigurationException, ClassNotFoundException, SQLException, TransformerException {

        // definition
        String a[]=new String [3];
        
        // servicetemplate
        String b = null ;
        
        // artifacttemplate
        String c[][] = new String[2][3];
        
        // relationshiptemplate
        String d[][] = new String[3][4];
        
        // nodetemplate 4-18
        String e[][] = new String [4][18];
        
        int count = 0;
        
        ResultSet rs = null;
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/migrate_bx"; 
        Connection con = DriverManager.getConnection(url,"root","hoang@long"); 
        Statement st = con.createStatement(); 
 
        rs = st.executeQuery("select * from definitions");
        while (rs.next())                
		
            {    
                for (int z = 0 ; z < a.length; z++)
                {
                    a[z] = rs.getString(z+1);
                    //System.out.println(a[z]);
                }
            }        
        rs.close();
        //rs = null;
        
        rs = st.executeQuery("select * from servicetemplate");
        while (rs.next())                
		
            {    
                b = rs.getString(1);
                //System.out.println(b);
            }        
        rs.close();
        //rs = null;
        
        rs = st.executeQuery("select * from artifacttemplate");
        while (rs.next())                
		
            {    
                    for (int z = 0; z < 3; z++)
                        {
                            c[count][z] = rs.getString(z+1);
                            //System.out.println(rs.getString(z+1));
                        }                             
                count++;  
            }        
        count = 0;
        rs.close();
        rs = null;
        
        rs = st.executeQuery("select * from relationshiptemplate");
        while (rs.next())                
		
            {    
                    for (int z = 0; z < 4; z++)
                        {
                            d[count][z] = rs.getString(z+1);
                            //System.out.println(d[y][z]);
                        }
                    count ++;
            }      
        count = 0;
        rs.close();
        rs = null;
        
        rs = st.executeQuery("select * from nodetemplate");
        while (rs.next())                
		
            {    
                    for (int z = 0; z < 18; z++)
                        {
                            e[count][z] = rs.getString(z+1);
                            //System.out.println(rs.getString(z+1));
                        }
                count ++;
            }        
        count = 0;
        rs.close();
        rs = null;
        
        con.close();
                

        try {
                DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docfactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                Element rootElement = doc.createElement("ns2:Definitions");
		doc.appendChild(rootElement);
                // set attribute to ns2:Definitions
		Attr attr01 = doc.createAttribute("id");
		attr01.setValue(a[0]);
                rootElement.setAttributeNode(attr01);
                
                Attr attr02 = doc.createAttribute("name");
		attr02.setValue(a[1]);
                rootElement.setAttributeNode(attr02);
                
                Attr attr03 = doc.createAttribute("xmlns:ns2");
		attr03.setValue(a[2]);
                rootElement.setAttributeNode(attr03);
                
                // ServiceTemplate
                Element element11 = doc.createElement("ns2:ServiceTemplate");
		rootElement.appendChild(element11);
		
                Attr attr11 = doc.createAttribute("id");
		attr11.setValue(b);
                element11.setAttributeNode(attr11);
                
                // Topology
                
                Element element33 = doc.createElement("ns2:Topology");
		element11.appendChild(element33);
                
                // Relationship
                // 1
                Element elementr11 = doc.createElement("ns2:RelationshipTemplate");
		element33.appendChild(elementr11);
                
                Attr attrr11 = doc.createAttribute("id");
		attrr11.setValue(d[0][0]);
                elementr11.setAttributeNode(attrr11);
                
                Attr attrr12 = doc.createAttribute("type");
		attrr12.setValue(d[0][1]);
                elementr11.setAttributeNode(attrr12);
                
                Element elementr111 = doc.createElement("ns2:SourceElement");
		elementr11.appendChild(elementr111);
                
                Attr attrr111 = doc.createAttribute("ref");
		attrr111.setValue(d[0][2]);
                elementr111.setAttributeNode(attrr111);
                                
                Element elementr112 = doc.createElement("ns2:TargetElement");
		elementr11.appendChild(elementr112);
                                
                Attr attrr112 = doc.createAttribute("ref");
		attrr112.setValue(d[0][3]);
                elementr112.setAttributeNode(attrr112);
                
                // 2
                Element elementr21 = doc.createElement("ns2:RelationshipTemplate");
		element33.appendChild(elementr21);
                
                Attr attrr21 = doc.createAttribute("id");
		attrr21.setValue(d[1][0]);
                elementr21.setAttributeNode(attrr21);
                
                Attr attrr22 = doc.createAttribute("type");
		attrr22.setValue(d[1][1]);
                elementr21.setAttributeNode(attrr22);
                
                Element elementr211 = doc.createElement("ns2:SourceElement");
		elementr21.appendChild(elementr211);
                
                Attr attrr211 = doc.createAttribute("ref");
		attrr211.setValue(d[1][2]);
                elementr211.setAttributeNode(attrr211);
                                
                Element elementr212 = doc.createElement("ns2:TargetElement");
		elementr21.appendChild(elementr212);
                                
                Attr attrr212 = doc.createAttribute("ref");
		attrr212.setValue(d[1][3]);
                elementr212.setAttributeNode(attrr212);
                
                // 3
                Element elementr31 = doc.createElement("ns2:RelationshipTemplate");
		element33.appendChild(elementr31);
                
                Attr attrr31 = doc.createAttribute("id");
		attrr31.setValue(d[2][0]);
                elementr31.setAttributeNode(attrr31);
                
                Attr attrr32 = doc.createAttribute("type");
		attrr32.setValue(d[2][1]);
                elementr31.setAttributeNode(attrr32);
                
                Element elementr311 = doc.createElement("ns2:SourceElement");
		elementr31.appendChild(elementr311);
                
                Attr attrr311 = doc.createAttribute("ref");
		attrr311.setValue(d[2][2]);
                elementr311.setAttributeNode(attrr311);
                                
                Element elementr312 = doc.createElement("ns2:TargetElement");
		elementr31.appendChild(elementr312);
                                
                Attr attrr312 = doc.createAttribute("ref");
		attrr312.setValue(d[2][3]);
                elementr312.setAttributeNode(attrr312);
                
                
                // Node Template
                // 1 WordpressVM
                
                Element elementn11 = doc.createElement("ns2:NodeTemplate");
		element33.appendChild(elementn11);
                
                Attr attrn11 = doc.createAttribute("id");
		attrn11.setValue(e[0][0]);
                elementn11.setAttributeNode(attrn11);
                
                Attr attrn12 = doc.createAttribute("type");
		attrn12.setValue(e[0][3]);
                elementn11.setAttributeNode(attrn12);
                
                Attr attrn13 = doc.createAttribute("maxInstances");
		attrn13.setValue(e[0][1]);
                elementn11.setAttributeNode(attrn13);
                
                Attr attrn14 = doc.createAttribute("minInstances");
		attrn14.setValue(e[0][2]);
                elementn11.setAttributeNode(attrn14);
                
                Element elementn111 = doc.createElement("ns2:Properties");
		elementn11.appendChild(elementn111);
                
                Element elementn1111 = doc.createElement("ns2:MappingProperties");
		elementn111.appendChild(elementn1111);
                                               
                Element elementn11111 = doc.createElement("ns2:MappingProperty");
		elementn1111.appendChild(elementn11111);
                
                Attr attrn11111 = doc.createAttribute("type");
		attrn11111.setValue(e[0][3]);
                elementn11111.setAttributeNode(attrn11111);
                                                                                
                Element contentn11 = doc.createElement("property");
		contentn11.appendChild(doc.createTextNode(e[0][4]));
		elementn11111.appendChild(contentn11);
                
                Attr attrn111111 = doc.createAttribute("name");
		attrn111111.setValue("provider");
                contentn11.setAttributeNode(attrn111111);
                
                Element contentn12 = doc.createElement("property");
		contentn12.appendChild(doc.createTextNode(e[0][5]));
		elementn11111.appendChild(contentn12);
                
                Attr attrn111112 = doc.createAttribute("name");
		attrn111112.setValue("instanceType");
                contentn12.setAttributeNode(attrn111112);
                
                Element contentn13 = doc.createElement("property");
		contentn13.appendChild(doc.createTextNode(e[0][6]));
		elementn11111.appendChild(contentn13);
                
                Attr attrn111113 = doc.createAttribute("name");
		attrn111113.setValue("baseImage");
                contentn13.setAttributeNode(attrn111113);
                
                Element contentn14 = doc.createElement("property");
		elementn11111.appendChild(contentn14);
                
                Attr attrn111114 = doc.createAttribute("name");
		attrn111114.setValue("packages");
                contentn14.setAttributeNode(attrn111114);
                
                                
                Element elementn112 = doc.createElement("ns2:Requirement");
		elementn11.appendChild(elementn112);
                
                Element elementn113 = doc.createElement("ns2:Capabilities");
		elementn11.appendChild(elementn113);
                
                Element elementn114 = doc.createElement("ns2:Policies");
		elementn11.appendChild(elementn114);
                
                
                // Node Template
                // 2 WordpressNode
                
                Element elementn21 = doc.createElement("ns2:NodeTemplate");
		element33.appendChild(elementn21);
                
                Attr attrn21 = doc.createAttribute("id");
		attrn21.setValue(e[1][0]);
                elementn21.setAttributeNode(attrn21);
                
                Attr attrn22 = doc.createAttribute("type");
		attrn22.setValue(e[1][3]);
                elementn21.setAttributeNode(attrn22);
                
                Attr attrn23 = doc.createAttribute("maxInstances");
		attrn23.setValue(e[1][1]);
                elementn21.setAttributeNode(attrn23);
                
                Attr attrn24 = doc.createAttribute("minInstances");
		attrn24.setValue(e[1][2]);
                elementn21.setAttributeNode(attrn24);
                
                Element elementn211 = doc.createElement("ns2:Properties");
		elementn21.appendChild(elementn211);
                
                Element elementn2111 = doc.createElement("ns2:MappingProperties");
		elementn211.appendChild(elementn2111);
                                                            
                Element elementn212 = doc.createElement("ns2:Requirements");
		elementn21.appendChild(elementn212);
                
                Element elementn2121 = doc.createElement("ns2:Requirement");
		elementn212.appendChild(elementn2121);
                
                Attr attrn21211 = doc.createAttribute("id");
		attrn21211.setValue(e[1][8]);
                elementn2121.setAttributeNode(attrn21211);
                
                Attr attrn21212 = doc.createAttribute("type");
		attrn21212.setValue(e[1][9]);
                elementn2121.setAttributeNode(attrn21212);
                                                                                
                Element elementn2122 = doc.createElement("ns2:Requirement");
		elementn212.appendChild(elementn2122);
                
                Attr attrn21213 = doc.createAttribute("id");
		attrn21213.setValue(e[1][10]);
                elementn2122.setAttributeNode(attrn21213);
                
                Attr attrn21214 = doc.createAttribute("type");
		attrn21214.setValue(e[1][11]);
                elementn2122.setAttributeNode(attrn21214);
                
                Element elementn213 = doc.createElement("ns2:Capabilities");
		elementn21.appendChild(elementn213);
                
                Element elementn214 = doc.createElement("ns2:Policies");
		elementn21.appendChild(elementn214);
                
                Element elementn215 = doc.createElement("ns2:DeploymentArtifacts");
		elementn21.appendChild(elementn215);
                
                Element elementn2151 = doc.createElement("ns2:DeploymentArtifact");
		elementn215.appendChild(elementn2151);
                
                Attr attrn211115 = doc.createAttribute("name");
		attrn211115.setValue(e[1][14]);
                elementn2151.setAttributeNode(attrn211115);
                
                Attr attrn211116 = doc.createAttribute("xmlns:salsa");
		attrn211116.setValue(e[1][15]);
                elementn2151.setAttributeNode(attrn211116);
                
                Attr attrn211117 = doc.createAttribute("artifactRef");
		attrn211117.setValue(e[1][16]);
                elementn2151.setAttributeNode(attrn211117);
                
                Attr attrn211118 = doc.createAttribute("artifactType");
		attrn211118.setValue(e[1][17]);
                elementn2151.setAttributeNode(attrn211118);
                
                // Node Template
                // 3 MySQLVM
                
                Element elementn31 = doc.createElement("ns2:NodeTemplate");
		element33.appendChild(elementn31);
                
                Attr attrn31 = doc.createAttribute("id");
		attrn31.setValue(e[2][0]);
                elementn31.setAttributeNode(attrn31);
               
                Attr attrn32 = doc.createAttribute("type");
		attrn32.setValue(e[2][3]);
                elementn31.setAttributeNode(attrn32);
                
                Attr attrn33 = doc.createAttribute("maxInstances");
		attrn33.setValue(e[2][1]);
                elementn31.setAttributeNode(attrn33);
                
                Attr attrn34 = doc.createAttribute("minInstances");
		attrn34.setValue(e[2][2]);
                elementn31.setAttributeNode(attrn34);
                
                Element elementn311 = doc.createElement("ns2:Properties");
		elementn31.appendChild(elementn311);
                
                Element elementn3111 = doc.createElement("ns2:MappingProperties");
		elementn311.appendChild(elementn3111);
                                               
                Element elementn31111 = doc.createElement("ns2:MappingProperty");
		elementn3111.appendChild(elementn31111);
                
                Attr attrn31111 = doc.createAttribute("type");
		attrn31111.setValue(e[2][3]);
                elementn31111.setAttributeNode(attrn31111);
                                                                                
                Element contentn31 = doc.createElement("property");
		contentn31.appendChild(doc.createTextNode(e[2][4]));
		elementn31111.appendChild(contentn31);
                
                Attr attrn311111 = doc.createAttribute("name");
		attrn311111.setValue("provider");
                contentn31.setAttributeNode(attrn311111);
                
                Element contentn32 = doc.createElement("property");
		contentn32.appendChild(doc.createTextNode(e[2][5]));
		elementn31111.appendChild(contentn32);
                
                Attr attrn311112 = doc.createAttribute("name");
		attrn311112.setValue("instanceType");
                contentn32.setAttributeNode(attrn311112);
                
                Element contentn33 = doc.createElement("property");
		contentn33.appendChild(doc.createTextNode(e[2][6]));
		elementn31111.appendChild(contentn33);
                
                Attr attrn311113 = doc.createAttribute("name");
		attrn311113.setValue("baseImage");
                contentn33.setAttributeNode(attrn311113);
                
                Element contentn34 = doc.createElement("property");
		elementn31111.appendChild(contentn34);
                
                Attr attrn311114 = doc.createAttribute("name");
		attrn311114.setValue("packages");
                contentn34.setAttributeNode(attrn311114);

                Element elementn312 = doc.createElement("ns2:Requirement");
		elementn31.appendChild(elementn312);
                
                Element elementn313 = doc.createElement("ns2:Capabilities");
		elementn31.appendChild(elementn313);
                
                Element elementn314 = doc.createElement("ns2:Policies");
		elementn31.appendChild(elementn314);
                

               
                // Node Template
                // 4 WordpressNode
                
                Element elementn41 = doc.createElement("ns2:NodeTemplate");
		element33.appendChild(elementn41);
                
                Attr attrn41 = doc.createAttribute("id");
		attrn41.setValue(e[3][0]);
                elementn41.setAttributeNode(attrn41);
                
                Attr attrn42 = doc.createAttribute("type");
		attrn42.setValue(e[3][3]);
                elementn41.setAttributeNode(attrn42);
                
                Attr attrn43 = doc.createAttribute("maxInstances");
		attrn43.setValue(e[3][1]);
                elementn41.setAttributeNode(attrn43);
                
                Attr attrn44 = doc.createAttribute("minInstances");
		attrn44.setValue(e[3][2]);
                elementn41.setAttributeNode(attrn44);
                
                Element elementn411 = doc.createElement("ns2:Properties");
		elementn41.appendChild(elementn411);
                
                Element elementn4111 = doc.createElement("ns2:MappingProperties");
		elementn411.appendChild(elementn4111);
                                                            
                Element elementn412 = doc.createElement("ns2:Requirements");
		elementn41.appendChild(elementn412);
                
                Element elementn4121 = doc.createElement("ns2:Requirement");
		elementn412.appendChild(elementn4121);
                
                Attr attrn41211 = doc.createAttribute("id");
		attrn41211.setValue(e[3][8]);
                elementn4121.setAttributeNode(attrn41211);
                
                Attr attrn41212 = doc.createAttribute("type");
		attrn41212.setValue(e[3][9]);
                elementn4121.setAttributeNode(attrn41212);
                                
                Element elementn413 = doc.createElement("ns2:Capabilities");
		elementn41.appendChild(elementn413);
                
                Element elementn4122 = doc.createElement("ns2:Capability");
		elementn413.appendChild(elementn4122);
                
                Attr attrn41213 = doc.createAttribute("id");
		attrn41213.setValue(e[3][12]);
                elementn4122.setAttributeNode(attrn41213);
                
                Attr attrn41214 = doc.createAttribute("type");
		attrn41214.setValue(e[3][13]);
                elementn4122.setAttributeNode(attrn41214);
                
                Element elementn414 = doc.createElement("ns2:Policies");
		elementn41.appendChild(elementn414);
                
                Element elementn415 = doc.createElement("ns2:DeploymentArtifacts");
		elementn41.appendChild(elementn415);
                
                Element elementn4151 = doc.createElement("ns2:DeploymentArtifact");
		elementn415.appendChild(elementn4151);
                
                Attr attrn411115 = doc.createAttribute("name");
		attrn411115.setValue(e[3][14]);
                elementn4151.setAttributeNode(attrn411115);
                
                Attr attrn411116 = doc.createAttribute("xmlns:salsa");
		attrn411116.setValue(e[3][15]);
                elementn4151.setAttributeNode(attrn411116);
                
                Attr attrn411117 = doc.createAttribute("artifactRef");
		attrn411117.setValue(e[3][16]);
                elementn4151.setAttributeNode(attrn411117);
                
                Attr attrn411118 = doc.createAttribute("artifactType");
		attrn411118.setValue(e[3][17]);
                elementn2151.setAttributeNode(attrn411118);
                
                
                //  ArtifactTemplate
                // 1               
                Element elementa11 = doc.createElement("ns2:ArtifactTemplate");
		rootElement.appendChild(elementa11);
		
                Attr attra11 = doc.createAttribute("id");
		attra11.setValue(c[0][0]);
                elementa11.setAttributeNode(attra11);
                
                Attr attra12 = doc.createAttribute("type");
		attra12.setValue(c[0][1]);
                elementa11.setAttributeNode(attra12);
                                               
                Element elementa12 = doc.createElement("ns2:Properties");
		elementa11.appendChild(elementa12);
                
                Element elementa13 = doc.createElement("ns2:ArtifactReferrences");
		elementa11.appendChild(elementa13);
                
                Element elementa14 = doc.createElement("ns2:ArtifactReferrence");
		elementa13.appendChild(elementa14);
                
                Attr attra13 = doc.createAttribute("reference");
		attra13.setValue(c[0][2]);
                elementa14.setAttributeNode(attra13);
               
                // 2               
                Element elementa21 = doc.createElement("ns2:ArtifactTemplate");
		rootElement.appendChild(elementa21);
		
                Attr attra21 = doc.createAttribute("id");
		attra21.setValue(c[1][0]);
                elementa21.setAttributeNode(attra21);
                
                Attr attra22 = doc.createAttribute("type");
		attra22.setValue(c[1][1]);
                elementa21.setAttributeNode(attra22);
                                               
                Element elementa22 = doc.createElement("ns2:Properties");
		elementa21.appendChild(elementa22);
                
                Element elementa23 = doc.createElement("ns2:ArtifactReferrences");
		elementa21.appendChild(elementa23);
                
                Element elementa24 = doc.createElement("ns2:ArtifactReferrence");
		elementa23.appendChild(elementa24);
                
                Attr attra23 = doc.createAttribute("reference");
		attra23.setValue(c[1][2]);
                elementa24.setAttributeNode(attra23);
               

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		//StreamResult result = new StreamResult(new File("C:\\\\Users\\\\Long\\\\Desktop\\\\a.XML"));
                StreamResult result = new StreamResult(new File(save));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");
                } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
                } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	  } 
        
    }

