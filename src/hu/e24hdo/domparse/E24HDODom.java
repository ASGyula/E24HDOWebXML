package hu.e24hdo.domparse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class E24HDODom{
    private String fileName;
    private Document document;
    private Element rootElement;

    public E24HDODom(String fileName){
        this.fileName = fileName;
        File file = new File(fileName);
        try{
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            document.getDocumentElement().normalize();
            rootElement = document.getDocumentElement();
        }catch(IOException | SAXException | ParserConfigurationException e){
            throw new RuntimeException(e);
        }
    }

    public Document getDocument(){
        return document;
    }

    public void printRootElement(){
        IO.print("Megnyitott fájl: ");
        Colors.printWithColor(Colors.CYAN, fileName);
        IO.print(" (gyökér elem: ");
        Colors.printWithColor(Colors.GREEN, rootElement.getNodeName());
        IO.println(")");
        printPrettyLine();
    }

    public void printPrettyLine(){
        IO.println("----------------------------");
    }

    public Element getRootElement(){
        return rootElement;
    }

    public String joinMultipleTags(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < nodeList.getLength(); i++){
            if(i > 0) stringBuilder.append(", ");
            stringBuilder.append(nodeList.item(i).getTextContent().trim());
        }
        return stringBuilder.toString();
    }
}
