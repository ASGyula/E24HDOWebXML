import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class OrarendModifyE24HDO{
    Document document;
    Element rootElement;

    public OrarendModifyE24HDO(String name) throws ParserConfigurationException, IOException, SAXException{
        File xmlFile = new File(name);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        rootElement = document.getDocumentElement();
    }

    public void modifyLowKeyWebtech1ToWebt(String value){
        Element element = (Element) document.getElementsByTagName("ora").item(0);
        element.getElementsByTagName("targy").item(0).setTextContent(value);
        System.out.println(element.getTextContent());
    }

    public void modifyLowKeyWebtech1Location(String value){
        Element element = (Element) document.getElementsByTagName("ora").item(0);
        element.getElementsByTagName("helyszin").item(0).setTextContent(value);
        System.out.println(element.getTextContent());
    }
    public void modifyLowKeyWebtech1Type(String value){
        Element element = (Element) document.getElementsByTagName("ora").item(0);
        element.setAttribute("tipus", value);
        System.out.println(element.getTextContent());
    }
}
