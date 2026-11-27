package hu.e24hdo.domparse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class E24HDODom{
    private final String fileName;
    private final Document document;
    private final Element rootElement;

// Konstruktor: beolvassa és normalizálja a megadott XML fájlt
    public E24HDODom(String fileName){
        this.fileName = fileName;
        File file = new File(fileName);
        try{
            this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            this.document.getDocumentElement().normalize();
            this.rootElement = document.getDocumentElement();
        }catch(IOException | SAXException | ParserConfigurationException e){
            throw new RuntimeException(e);
        }
    }

// Visszaadja a memóriába betöltött fájlnevet
    public String getFileName(){
        return fileName;
    }

// Visszaadja a memóriába betöltött DOM Document objektumot
    public Document getDocument(){
        return this.document;
    }

// Kiírja a megnyitott fájl nevét és a gyökérelem azonosítóját
    public void printRootElement(){
        IO.print("Megnyitott fájl: ");
        Colors.printWithColor(Colors.CYAN, fileName);
        IO.print(" (gyökér elem: ");
        Colors.printWithColor(Colors.GREEN, rootElement.getNodeName());
        IO.println(")");
        printPrettyLine();
    }

// Kawaii uwu 💅🏿
    public void printPrettyLine(){
        IO.println("----------------------------");
    }

// Visszaadja a gyökérelemet
    public Element getRootElement(){
        return rootElement;
    }

// Adott tagű elemek kigyűjtése és indexelése az ID attribútum alapján HashMap-be
    public HashMap<String, Element> getElementList(String tag, String elementID){
        HashMap<String, Element> hashMap = new HashMap<String, Element>();
        NodeList nodeList = getDocument().getElementsByTagName(tag);
        if(nodeList.getLength() == 0){
            Colors.printWithColor(Colors.RED, "Nincs ilyen tag!");
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                String id = element.getAttribute(elementID);
                hashMap.put(id, element);
            }
        }

        return hashMap;
    }

// Többértékű gyerekelemek (pl. Izjegy, Szin) szöveges tartalmának összefűzése vesszővel
    public String joinMultipleTags(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < nodeList.getLength(); i++){
            if(i > 0) stringBuilder.append(", ");
            stringBuilder.append(nodeList.item(i).getTextContent().trim());
        }
        return stringBuilder.toString();
    }

// A kapott String tömb elemeinek szöveges tartalmának összefűzése vesszővel
    public String joinMultipleText(String[] stringArray){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < stringArray.length; i++){
            if(i > 0) stringBuilder.append(", ");
            stringBuilder.append(stringArray[i]);
        }
        return stringBuilder.toString();
    }

// Új element hozzáfűzése a szülőhöz
    public void appendChild(Element parent, String tagName, String text){
        Element child = getDocument().createElement(tagName);
        child.setTextContent(text);
        parent.appendChild(child);
    }
}
