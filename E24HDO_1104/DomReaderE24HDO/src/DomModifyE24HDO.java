import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DomModifyE24HDO{

    Document document;
    Element rootElement;

    public DomModifyE24HDO(String name) throws ParserConfigurationException, IOException, SAXException{
        File xmlFile = new File(name);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        rootElement = document.getDocumentElement();
    }

    public void printRootElement() throws Exception{
        if(rootElement == null){
            throw new Exception("Nincs root element ://");
        }
        System.out.println("root element: " + rootElement.getNodeName());
    }

    public void printLowKeyNodeHierarchia() throws Exception{
        if(rootElement == null){
            throw new Exception("Nincs root element ://");
        }

        NodeList ora = document.getElementsByTagName("ora");
        for(int i = 0; i < ora.getLength(); i++){
            Element element = (Element) document.getElementsByTagName("ora").item(i);
            System.out.println("ID: " + element.getAttribute("id"));
            System.out.println("Típus: " + element.getAttribute("tipus"));
            System.out.println("Időpont: " + element.getElementsByTagName("idopont").item(0).getTextContent());
            System.out.println("Helyszín: " + element.getElementsByTagName("helyszin").item(0).getTextContent());
            if(element.getElementsByTagName("oktato") != null){
                System.out.println("oktató: " + element.getElementsByTagName("oktato").item(0).getTextContent());
            }
            System.out.println("Szak: " + element.getElementsByTagName("szak").item(0).getTextContent());
            System.out.println("");
        }
    }

    public void printTextChild(Node reqNode) throws Exception{
        if(reqNode == null){
            throw new Exception("Nincs node element ://");
        }
        NodeList nodeList = reqNode.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            String nodeName = node.getNodeName();
            if(!nodeName.startsWith("#")){
                System.out.println(nodeName+": " + node.getTextContent());
            }
        }
    }

    public void modifyLowKeyV1(String nev, int eletkor){
        Element element = (Element) document.getElementsByTagName("vendeg").item(0);
        element.getElementsByTagName("nev").item(0).setTextContent(nev);
        element.getElementsByTagName("eletkor").item(0).setTextContent(eletkor + "");
    }

    public void modifyEGY_3(){
        Element element = (Element) document.getElementsByTagName("gyakornok").item(0);
        element.setAttribute("e_gy", "e3");
    }

    public void deleteAllOrders() throws TransformerException{
        NodeList nodeList = document.getElementsByTagName("rendeles");
        for(int i = nodeList.getLength() - 1; i >= 0; i--){
            Node node = nodeList.item(i);
            node.getParentNode().removeChild(node);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(System.out);
        transformer.transform(domSource, streamResult);
    }
}
