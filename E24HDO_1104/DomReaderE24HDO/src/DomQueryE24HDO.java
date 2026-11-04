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

public class DomQueryE24HDO{

    Document document;
    Element rootElement;

    public DomQueryE24HDO(String name) throws ParserConfigurationException, IOException, SAXException{
        File xmlFile = new File(name);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        rootElement = document.getDocumentElement();
    }

    public void searchAllSheffWithKnowledge(){
        NodeList szakacsList = document.getElementsByTagName("szakacs");

        for(int i = 0; i < szakacsList.getLength(); i++){
            Node szakacsNode = szakacsList.item(i);

            if(szakacsNode.getNodeType() == Node.ELEMENT_NODE){
                Element szakacsElem = (Element) szakacsNode;
                NodeList vegzettsegList = szakacsElem.getElementsByTagName("vegzettseg");
                boolean isHasKnowledge = false;

                for(int j = 0; j < vegzettsegList.getLength(); j++){
                    String vegzettseg = vegzettsegList.item(j).getTextContent().trim().toLowerCase();
                    if(vegzettseg.contains("szakközép")){
                        isHasKnowledge = true;
                        break;
                    }
                }

                if(isHasKnowledge){
                    String szkod = szakacsElem.getAttribute("szkod");
                    String nev = szakacsElem.getElementsByTagName("nev").item(0).getTextContent();
                    String reszleg = szakacsElem.getElementsByTagName("reszleg").item(0).getTextContent();
                    System.out.println(" - [ID: " + szkod + "] Név: " + nev + ", Részleg: " + reszleg);
                }
            }
        }
    }

    public void searchRestaurantWithFiveStars(){
        NodeList restaurantList = document.getElementsByTagName("etterem");

        for(int i = 0; i < restaurantList.getLength(); i++){
            Node item = restaurantList.item(i);

            if(item.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) item;
                Element star = (Element) element.getElementsByTagName("csillag").item(0);
                if(Integer.parseInt(star.getTextContent()) == 5){
                    System.out.println(" - [ID: " + element.getAttribute("ekod") + "] Név: " + element.getElementsByTagName("nev").item(0).getTextContent());
                }

            }
        }
    }

    public void searchIntersWithAfternoonShift(){
        NodeList internList = document.getElementsByTagName("gyakornok");

        for(int i = 0; i < internList.getLength(); i++){
            Node item = internList.item(i);

            if(item.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) item;
                Element star = (Element) element.getElementsByTagName("muszak").item(0);
                if(star.getTextContent().toString().equals("délután")){
                    System.out.println(" - [ID: " + element.getAttribute("gykod") + "] Név: " + element.getElementsByTagName("nev").item(0).getTextContent());
                }

            }
        }
    }

}
