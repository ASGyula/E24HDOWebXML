import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOMParseE24HDO{

    Document document;
    Element rootElement;

    public DOMParseE24HDO(String name) throws ParserConfigurationException, IOException, SAXException{
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

    public void printNodeHierarchia() throws Exception{
        if(rootElement == null){
            throw new Exception("Nincs root element ://");
        }
        NodeList nodeList = rootElement.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            String nodeName = node.getNodeName();
            if(!nodeName.startsWith("#")){
                System.out.println("current element: " + nodeName);
                if(node.hasAttributes()){
                    for(int attributesCount = 0; attributesCount < node.getAttributes().getLength(); attributesCount++){
                        System.out.println("\t" + (node.getAttributes().item(attributesCount).getNodeName().contains("kod")?"id":node.getAttributes().item(attributesCount).getNodeName()) + ": " +node.getAttributes().item(attributesCount).getNodeValue());
                    }
                }
                if(node.getChildNodes().getLength() > 0){
                    printTextChild(node);
                }
            }
        }
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
}
