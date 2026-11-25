package hu.e24hdo.domparse;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;

public class E24HDODomModify extends E24HDODom{

    public E24HDODomModify(String fileName){
        super(fileName);
    }

    public void updateCoolerCapacity(String laboratoryID, String newCapacity){
        NodeList nodelist = getDocument().getElementsByTagName("LaborKozpont");
        for(int i = 0; i < nodelist.getLength(); i++){
           Node node = nodelist.item(i);
           if(node.getNodeType() == Node.ELEMENT_NODE){
               Element element = (Element) nodelist.item(i);
               if(element.getAttribute("KozpontID").equals(laboratoryID)){
                   Node capacityNode = element.getElementsByTagName("Hutokapacitas").item(0);
                   String oldCapacity = capacityNode.getTextContent();
                   capacityNode.setTextContent(newCapacity);
                   Colors.printWithColorln(Colors.CYAN, "Hűtőkapacitás módosítása");
                   Colors.printWithColor(Colors.GREEN, laboratoryID);
                   Colors.printWithColor(Colors.DEFAULT, " [");
                   Colors.printWithColor(Colors.BLUE, oldCapacity);
                   Colors.printWithColor(Colors.DEFAULT, " -> ");
                   Colors.printWithColor(Colors.BLUE, newCapacity);
                   Colors.printWithColorln(Colors.DEFAULT, "]");
               }
           }
        }
    }

    public void upgradeVIPCard(String cardID, String newLevel){
        NodeList nodelist = getDocument().getElementsByTagName("VIPKartya");
        for(int i = 0; i < nodelist.getLength(); i++){
            Node node = nodelist.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) nodelist.item(i);
                if(element.getAttribute("KartyaID").equals(cardID)){
                    Node capacityNode = element.getElementsByTagName("Szint").item(0);
                    Node serialNode = element.getElementsByTagName("Sorozatszam").item(0);
                    String level = capacityNode.getTextContent();
                    String serialNumber = serialNode.getTextContent();
                    capacityNode.setTextContent(newLevel);
                    Colors.printWithColorln(Colors.CYAN, "VIP Kártya módosítása");
                    Colors.printWithColor(Colors.GREEN, cardID);
                    Colors.printWithColor(Colors.DEFAULT, " (");
                    Colors.printWithColor(Colors.BLUE, serialNumber);
                    Colors.printWithColor(Colors.DEFAULT, ") most már nem ");
                    Colors.printWithColor(Colors.YELLOW, level);
                    Colors.printWithColor(Colors.DEFAULT, ", hanem ");
                    Colors.printWithColor(Colors.YELLOW, newLevel);
                    Colors.printWithColorln(Colors.DEFAULT, " szintű");
                }
            }
        }
    }

    public void addNewDrink(String id, String manufactureRef, String name, String caffeine, String size, String[] flavors, String[] colors){
        Element drinksRoot = (Element) getDocument().getElementsByTagName("Italok").item(0);

        Element newDrink = getDocument().createElement("Ital");
        newDrink.setAttribute("ItalID", id);
        newDrink.setAttribute("GyartoRef", manufactureRef);

        appendChild(newDrink, "Nev", name);
        appendChild(newDrink, "KoffeinMg", caffeine);
        appendChild(newDrink, "KiszerelesMl", size);

        for(String flavor : flavors){
            appendChild(newDrink, "Izjegy", flavor);
        }
        for(String color : colors){
            appendChild(newDrink, "Szin", color);
        }

        drinksRoot.appendChild(newDrink);

        Colors.printWithColorln(Colors.CYAN, "Ital beszúrása");
        Colors.printWithColor(Colors.GREEN, name);
        Colors.printWithColor(Colors.DEFAULT, " (");
        Colors.printWithColor(Colors.BLUE, id);
        Colors.printWithColorln(Colors.DEFAULT, ") hozzáadva.");
        Colors.printWithColor(Colors.DEFAULT, "\tKoffein: ");
        Colors.printWithColor(Colors.BLUE, caffeine);
        Colors.printWithColorln(Colors.DEFAULT, "mg");
        Colors.printWithColor(Colors.DEFAULT, "\tKiszerelés: ");
        Colors.printWithColor(Colors.BLUE, size);
        Colors.printWithColorln(Colors.DEFAULT, "ml");
        Colors.printWithColor(Colors.DEFAULT, "\tÍzek: ");
        Colors.printWithColorln(Colors.YELLOW, joinMultipleText(flavors));
        Colors.printWithColor(Colors.DEFAULT, "\tSzínek: ");
        Colors.printWithColorln(Colors.YELLOW, joinMultipleText(colors));
    }

    public void removeLastOrder(){
        Element ordersRoot = (Element) getDocument().getElementsByTagName("Fogyasztasok").item(0);
        NodeList orders = ordersRoot.getElementsByTagName("Fogyasztas");
        if(orders.getLength() == 0){
            Colors.printWithColorln(Colors.RED, "Nincs 1 rendelés sem");
            return;
        }

        Node lastOrder = orders.item(orders.getLength() - 1);
        Element element = (Element) lastOrder;
        String tagRef = element.getAttribute("TagRef");
        String drinkRef = element.getAttribute("ItalRef");
        String cat = element.getElementsByTagName("FogyasztasDatuma").item(0).getTextContent();
        String quantity = element.getElementsByTagName("MennyisegDb").item(0).getTextContent();
        String rating = element.getElementsByTagName("Ertekeles").item(0).getTextContent();
        String ritualCombo = element.getElementsByTagName("RitualisKombo").item(0).getTextContent();

        HashMap<String, Element> drinks = getElementList("Ital", "ItalID");
        HashMap<String, Element> members = getElementList("Tag", "TagID");

        Colors.printWithColorln(Colors.CYAN, "Utolsó rendelés törölve ");
        Colors.printWithColor(Colors.PURPLE, orders.getLength() + "");
        Colors.printWithColor(Colors.DEFAULT, ") ");
        Colors.printWithColor(Colors.GREEN, drinks.getOrDefault(drinkRef, null).getElementsByTagName("Nev").item(0).getTextContent());
        Colors.printWithColor(Colors.DEFAULT, " (");
        Colors.printWithColor(Colors.BLUE, quantity);
        Colors.printWithColor(Colors.DEFAULT, "x)\n\tVendég: ");
        Colors.printWithColor(Colors.YELLOW, members.getOrDefault(tagRef, null).getElementsByTagName("Nev").item(0).getTextContent().replaceAll("\n", " ").replaceAll("  ", "").trim());
        Colors.printWithColor(Colors.DEFAULT, "\n\tÉrtékelés: ");
        Colors.printWithColor(Colors.BLUE, rating);
        Colors.printWithColor(Colors.DEFAULT, " csillag\n\tFogyasztva: ");
        Colors.printWithColor(Colors.BLUE, cat);
        Colors.printWithColor(Colors.DEFAULT, "\n\tRituális kombó: ");
        Colors.printWithColorln(Colors.YELLOW, ritualCombo);
        ordersRoot.removeChild(lastOrder);
    }

    public void saveToFile(String outputFileName){
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");

            DOMSource domSource = new DOMSource(getDocument());
            StreamResult fileResult = new StreamResult(new File(outputFileName));
            transformer.transform(domSource, fileResult);
            Colors.printWithColorln(Colors.CYAN, "Mentés");
            Colors.printWithColor(Colors.DEFAULT, "A fájlt sikeresen kimentettük ide: ");
            Colors.printWithColorln(Colors.GREEN, outputFileName);
        }catch(TransformerException e){
            throw new RuntimeException(e);
        }
    }
}
