import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class OrarendQueryE24HDO{
    Document document;
    Element rootElement;

    public OrarendQueryE24HDO(String name) throws ParserConfigurationException, IOException, SAXException{
        File xmlFile = new File(name);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        rootElement = document.getDocumentElement();
    }

    public void queryCourseNames(){
        Element element = (Element) document.getElementsByTagName("ora").item(0);
        System.out.println(element.getElementsByTagName("targy").item(0).getTextContent());
    }

    public void queryLocation(){
        Element element = (Element) document.getElementsByTagName("ora").item(0);
        System.out.println(element.getElementsByTagName("helyszin").item(0).getTextContent());
    }

    public void queryType(){
        Element element = (Element) document.getElementsByTagName("ora").item(0);
        System.out.println(element.getAttribute("tipus"));
    }
}