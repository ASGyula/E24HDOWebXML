package xpathe24hdo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class xPathE24HDO{
    Document document;
    Element rootElement;

    public xPathE24HDO(String path) throws ParserConfigurationException, IOException, SAXException{
        File file = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        this.document = documentBuilder.parse(file);
        this.document.getDocumentElement().normalize();
        this.rootElement = document.getDocumentElement();
    }

    public void xPathQuestions() throws XPathExpressionException, IOException{
        XPath xPath = XPathFactory.newInstance().newXPath();
        String e24hdo;
        boolean isNeedWriteCloseClassTag = false;
//        1) Válassza ki az összes student element, amely a class gyermekei!
//        e24hdo = "/class/hallgato";
//        2) Válassza ki azt a student elemet, amely rendelkezik "id" attribútummal és értéke "02"!
//        e24hdo = "/class/hallgato[@id = '02']";
//        3) Kiválasztja az összes student elemet, függetlenül attól, hogy hol vannak a dokumentumban!
//        e24hdo = "/*/hallgato";
//        4) Válassza ki a második student element, amely a class root element gyermeke!
//        e24hdo = "/class/hallgato[2]";
//        5) Válassza ki az utolsó student elemet, amely a class root element gyermeke!
//        e24hdo = "/class/hallgato[last()]";
//        6) Válassza ki a utolsó előtti student elemet, amely a class root element gyermeke!
//        e24hdo = "/class/hallgato[last()-1]";
//        7) Válassza ki az első két student elemet, amelyek a root element gyermekei!
//        e24hdo = "/class/hallgato[position() <= 2]";
//        9) Válassza ki az összes student elemet, amely rendelkezik legalább egy bármilyen
//        attribútummal!
//        e24hdo = "/class/*[@*]";
//        10) Válassza ki a dokumentum összes elemét!
//        e24hdo = "//*";
//        isNeedWriteCloseClassTag = true;
//        11) Válassza ki a class root element összes student elemét, amelynél a kor>20!
//        e24hdo = "/class/student[kor>20]"; // nincs ilyenünk!
//        12) Válassza ki az összes student elem összes keresztney or vezeteknev csomópontot!
        e24hdo = "//hallgato/keresztnev | //hallgato/vezeteknev";

        NodeList result = (NodeList) xPath.compile(e24hdo).evaluate(this.document, XPathConstants.NODESET);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("hallgato1.xml"));
        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        if(!isNeedWriteCloseClassTag) bufferedWriter.write("<class xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"studentE24HDO.xsd\">\n");
        for(int i = 0; i < result.getLength(); i++){
            Node node = result.item(i);
            IO.println("aktuális elem: " + node.getNodeName());
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                String prefix = element.getNodeName();
                if(prefix.equals("class")){
                    bufferedWriter.write("\t<" + prefix + ">\n");
                    continue;
                }else if(prefix.equals("keresztnev") || prefix.equals("vezeteknev")){
                    bufferedWriter.write("\t<" + prefix + ">\n");
                    bufferedWriter.write("\t\t"+ element.getTextContent() + "\n");
                    bufferedWriter.write("\t</" + prefix + ">\n");
                    continue;
                }
                String id = element.getAttribute("id");
                IO.println("id: " + id);
                bufferedWriter.write("\t<" + prefix + " id=\"" + id + "\">\n");
                bufferedWriter.write("\t\t<keresztnev>"+element.getElementsByTagName("keresztnev").item(0).getTextContent()+"</keresztnev>\n");
                bufferedWriter.write("\t\t<vezeteknev>"+element.getElementsByTagName("vezeteknev").item(0).getTextContent()+"</vezeteknev>\n");
                bufferedWriter.write("\t\t<foglalkozas>"+element.getElementsByTagName("foglalkozas").item(0).getTextContent()+"</foglalkozas>\n");
                bufferedWriter.write("\t</"+prefix+">\n");
            }
        }
        bufferedWriter.write("</class>\n");
        bufferedWriter.flush();
        bufferedWriter.close();
        IO.println();
    }

    public void modifyChild(String tag, String id, String child, String value) throws TransformerException{
        Optional<Element> modifyElement = null;
        NodeList nodeList = document.getElementsByTagName(tag);
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(element.getAttribute("id").equals(id)){
                    modifyElement = Optional.of(element);
                    break;
                }
            }
        }

        if(modifyElement.isPresent()){
            modifyElement.get().getElementsByTagName(child).item(0).setTextContent(value);
        }
        IO.println("nincs ilyen elem");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(System.out);
        transformer.transform(domSource, streamResult);
    }

}
