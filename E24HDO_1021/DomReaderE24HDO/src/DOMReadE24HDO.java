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

public class DOMReadE24HDO{

    Document document;
    Element rootElement;

    public DOMReadE24HDO(String name) throws ParserConfigurationException, IOException, SAXException{
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

        NodeList etteremsz = document.getElementsByTagName("etterem");
        for(int i = 0; i < etteremsz.getLength(); i++){
            Element element = (Element) document.getElementsByTagName("etterem").item(i);
            System.out.println("ID: " + element.getAttribute("ekod"));
            System.out.println("Név: " + element.getElementsByTagName("nev").item(0).getTextContent());
            String address = element.getElementsByTagName("varos").item(0).getTextContent() + ", " + element.getElementsByTagName("utca").item(0).getTextContent() + " " + element.getElementsByTagName("hazszam").item(0).getTextContent();
            System.out.println("Cím: " + address);
            System.out.println("Házszám: " + element.getElementsByTagName("hazszam"));
            System.out.println("Csillag: " + element.getElementsByTagName("csillag").item(0).getTextContent());
            System.out.println("");
        }

        System.out.println("-----------------\n");
        NodeList foszakacssz = document.getElementsByTagName("foszakacs");
        for(int i = 0; i < foszakacssz.getLength(); i++){
            Element element = (Element) document.getElementsByTagName("foszakacs").item(i);
            System.out.println("ID: " + element.getAttribute("fkod"));
            System.out.println("e_f: " + element.getAttribute("e_f"));
            System.out.println("Név: " + element.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Életkor: " + element.getElementsByTagName("eletkor").item(0).getTextContent());
            NodeList vegzettsges = element.getElementsByTagName("vegzettseg");
            for(int i2 = 0; i2<vegzettsges.getLength(); i2++){
                System.out.println("Végzettség: " + vegzettsges.item(i2).getTextContent());
            }
            System.out.println("");
        }

        System.out.println("-----------------\n");
        NodeList szaakcsaz = document.getElementsByTagName("szakacs");
        for(int i = 0; i < szaakcsaz.getLength(); i++){
            Element element = (Element) document.getElementsByTagName("szakacs").item(i);
            System.out.println("ID: " + element.getAttribute("szkod"));
            System.out.println("e_sz: " + element.getAttribute("e_sz"));
            System.out.println("Név: " + element.getElementsByTagName("nev").item(0).getTextContent());
            NodeList vegzettsges = element.getElementsByTagName("vegzettseg");
            for(int i2 = 0; i2<vegzettsges.getLength(); i2++){
                System.out.println("Végzettség: " + vegzettsges.item(i2).getTextContent());
            }
            System.out.println("");
        }

        System.out.println("-----------------\n");
        NodeList gyakornokok = document.getElementsByTagName("gyakornok");
        for(int i = 0; i < gyakornokok.getLength(); i++){
            Element element = (Element) document.getElementsByTagName("gyakornok").item(i);
            System.out.println("ID: " + element.getAttribute("gykod"));
            System.out.println("e_gy: " + element.getAttribute("e_gy"));
            System.out.println("Név: " + element.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Gyakorlat kezdete: " + element.getElementsByTagName("kezdete").item(0).getTextContent());
            System.out.println("Gyakorlat időtartam: " + element.getElementsByTagName("idotartam").item(0).getTextContent());
            System.out.println("műszak: " + element.getElementsByTagName("muszak").item(0).getTextContent());
            System.out.println("");
        }


        System.out.println("-----------------\n");
        NodeList vendekedk = document.getElementsByTagName("vendeg");
        for(int i = 0; i < vendekedk.getLength(); i++){
            Element element = (Element) document.getElementsByTagName("vendeg").item(i);
            System.out.println("ID: " + element.getAttribute("vkod"));
            System.out.println("Név: " + element.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Életkor: " + element.getElementsByTagName("eletkor").item(0).getTextContent());
            String address = element.getElementsByTagName("varos").item(0).getTextContent() + ", " + element.getElementsByTagName("utca").item(0).getTextContent() + " " + element.getElementsByTagName("hazszam").item(0).getTextContent();
            System.out.println("Cím: " + address);
            System.out.println("");
        }

        System.out.println("-----------------\n");
        NodeList rendelesek = document.getElementsByTagName("rendeles");
        for(int i = 0; i < rendelesek.getLength(); i++){
            Element element = (Element) document.getElementsByTagName("rendeles").item(i);
            System.out.println("evv: " + element.getAttribute("e_v_v"));
            System.out.println("eve: " + element.getAttribute("e_v_e"));
            System.out.println("Összeg: " + element.getElementsByTagName("osszeg").item(0).getTextContent());
            System.out.println("Étel: " + element.getElementsByTagName("etel").item(0).getTextContent());
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
