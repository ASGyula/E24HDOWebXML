package hu.e24hdo.domparse;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

public class E24HDODomRead extends E24HDODom{

    public E24HDODomRead(String fileName){
        super(fileName);
    }

    public HashMap<String, Element> getOrders(){
        HashMap<String, Element> hashMap = new HashMap<String, Element>();
        NodeList nodeList = getDocument().getElementsByTagName("Fogyasztas");
        if(nodeList.getLength() == 0){
            Colors.printWithColor(Colors.RED, "Nincs ilyen tag!");
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                hashMap.put(i+"", element);
            }
        }

        return hashMap;
    }

    public void printManufactures(){
        Colors.printWithColorln(Colors.CYAN, "Gyártók");
        int index = 1;
        HashMap hashMap = getElementList("Gyarto", "GyartoID");
        for(Object key : hashMap.keySet()){
            Element element = (Element) hashMap.get(key);
            String name = element.getElementsByTagName("Cegnev").item(0).getTextContent();
            String country = element.getElementsByTagName("Orszag").item(0).getTextContent();
            String alapitasEve = element.getElementsByTagName("AlapitasEve").item(0).getTextContent();
            Colors.printWithColor(Colors.PURPLE, index + "");
            Colors.printWithColor(Colors.DEFAULT, ") ");
            Colors.printWithColor(Colors.GREEN, name);
            Colors.printWithColor(Colors.DEFAULT, " (");
            Colors.printWithColor(Colors.BLUE, alapitasEve);
            Colors.printWithColor(Colors.DEFAULT, "), ");
            Colors.printWithColorln(Colors.YELLOW, country);
            index++;
        }
    }

    public void printDrinks(){
        Colors.printWithColorln(Colors.CYAN, "Italok");
        int index = 1;
        HashMap<String, Element> hashMap = getElementList("Ital", "ItalID");
        HashMap<String, Element> manufactures = getElementList("Gyarto", "GyartoID");
        for(Object key : hashMap.keySet()){
            Element element = hashMap.get(key);
            String manufactureRef = element.getAttribute("GyartoRef");
            String name = element.getElementsByTagName("Nev").item(0).getTextContent();
            String caffeineMg = element.getElementsByTagName("KoffeinMg").item(0).getTextContent();
            String sizeInMl = element.getElementsByTagName("KiszerelesMl").item(0).getTextContent();
            String flavorNote = joinMultipleTags("Izjegy", element);
            String colors = joinMultipleTags("Szin", element);

            Colors.printWithColor(Colors.PURPLE, index + "");
            Colors.printWithColor(Colors.DEFAULT, ") ");
            Colors.printWithColor(Colors.GREEN, name);
            Colors.printWithColor(Colors.DEFAULT, " (");
            Colors.printWithColor(Colors.BLUE, sizeInMl);
            Colors.printWithColor(Colors.DEFAULT, "ml)\n\tKoffein: ");
            Colors.printWithColor(Colors.BLUE, caffeineMg);
            Colors.printWithColor(Colors.DEFAULT, "mg\n\tÍzjegyek: ");
            Colors.printWithColor(Colors.YELLOW, flavorNote);
            Colors.printWithColor(Colors.DEFAULT, "\n\tSzínek: ");
            Colors.printWithColor(Colors.YELLOW, colors);
            Colors.printWithColor(Colors.DEFAULT, "\n\tGyártó: ");
            Colors.printWithColorln(Colors.YELLOW, manufactures.getOrDefault(manufactureRef, null).getElementsByTagName("Cegnev").item(0).getTextContent().toString());

            index++;
        }
    }

    public void printLaboratories(){
        Colors.printWithColorln(Colors.CYAN, "Labor Központok");
        int index = 1;
        HashMap<String, Element> hashMap = getElementList("LaborKozpont", "KozpontID");
        for(Object key : hashMap.keySet()){
            Element element = hashMap.get(key);
            String id = key.toString();
            String city = element.getElementsByTagName("Varos").item(0).getTextContent();
            String address = element.getElementsByTagName("Cim").item(0).getTextContent();
            String coolerCapacity = element.getElementsByTagName("Hutokapacitas").item(0).getTextContent();

            Colors.printWithColor(Colors.PURPLE, index + "");
            Colors.printWithColor(Colors.DEFAULT, ") ");
            Colors.printWithColorln(Colors.GREEN, id);
            Colors.printWithColor(Colors.DEFAULT, "\tCím: ");
            Colors.printWithColor(Colors.YELLOW, city);
            Colors.printWithColor(Colors.DEFAULT, ", ");
            Colors.printWithColor(Colors.YELLOW, address);
            Colors.printWithColor(Colors.DEFAULT, "\n\tHűtő kapacitás: ");
            Colors.printWithColorln(Colors.BLUE, coolerCapacity);

            index++;
        }
    }

    public void printMembers(){
        Colors.printWithColorln(Colors.CYAN, "Tagok");
        int index = 1;
        HashMap<String, Element> hashMap = getElementList("Tag", "TagID");
        HashMap<String, Element> laboratories = getElementList("LaborKozpont", "KozpontID");
        for(Object key : hashMap.keySet()){
            Element element = hashMap.get(key);
            String laboratoryRef = element.getAttribute("KozpontRef");
            Element nameElement = (Element) element.getElementsByTagName("Nev").item(0);
            String lastName = nameElement.getElementsByTagName("Vezeteknev").item(0).getTextContent();
            String firstName = nameElement.getElementsByTagName("Keresztnev").item(0).getTextContent();
            String email = element.getElementsByTagName("Email").item(0).getTextContent();
            String birthday = element.getElementsByTagName("SzuletesiDatum").item(0).getTextContent();

            Colors.printWithColor(Colors.PURPLE, index + "");
            Colors.printWithColor(Colors.DEFAULT, ") ");
            Colors.printWithColor(Colors.GREEN, lastName + " " + firstName);
            Colors.printWithColor(Colors.DEFAULT, " (");
            Colors.printWithColor(Colors.BLUE, birthday);
            Colors.printWithColorln(Colors.DEFAULT, ")");
            Colors.printWithColor(Colors.DEFAULT, "\tElérhetőség: ");
            Colors.printWithColorln(Colors.YELLOW, email);
            Colors.printWithColor(Colors.DEFAULT, "\tKözpont: ");
            Colors.printWithColorln(Colors.YELLOW, laboratories.getOrDefault(laboratoryRef, null).getElementsByTagName("Varos").item(0).getTextContent());

            index++;
        }
    }

    public void printVIPCards(){
        Colors.printWithColorln(Colors.CYAN, "VIP Kártyák");
        int index = 1;
        HashMap<String, Element> hashMap = getElementList("VIPKartya", "KartyaID");
        HashMap<String, Element> members = getElementList("Tag", "TagID");
        for(Object key : hashMap.keySet()){
            Element element = hashMap.get(key);
            String id = key.toString();
            String tagRef = element.getAttribute("TagRef");
            String serialNumber = element.getElementsByTagName("Sorozatszam").item(0).getTextContent();
            String level = element.getElementsByTagName("Szint").item(0).getTextContent();
            String cat = element.getElementsByTagName("KiallitasDatuma").item(0).getTextContent();

            Colors.printWithColor(Colors.PURPLE, index + "");
            Colors.printWithColor(Colors.DEFAULT, ") ");
            Colors.printWithColor(Colors.GREEN, id);
            Colors.printWithColor(Colors.DEFAULT, " (");
            Colors.printWithColor(Colors.YELLOW, members.getOrDefault(tagRef, null).getElementsByTagName("Nev").item(0).getTextContent().replaceAll("\n", " ").replaceAll("  ", "").trim());
            Colors.printWithColor(Colors.DEFAULT, ")\n\tSorozatszám: ");
            Colors.printWithColor(Colors.YELLOW, serialNumber);
            Colors.printWithColor(Colors.DEFAULT, "\n\tSzint: ");
            Colors.printWithColor(Colors.YELLOW, level);
            Colors.printWithColor(Colors.DEFAULT, "\n\tKiállítva: ");
            Colors.printWithColorln(Colors.BLUE, cat);

            index++;
        }
    }

    public void printOrders(){
        Colors.printWithColorln(Colors.CYAN, "Fogyasztások");
        int index = 1;
        HashMap<String, Element> hashMap = getOrders();
        HashMap<String, Element> drinks = getElementList("Ital", "ItalID");
        HashMap<String, Element> members = getElementList("Tag", "TagID");
        for(Object key : hashMap.keySet()){
            Element element = hashMap.get(key);
            String tagRef = element.getAttribute("TagRef");
            String drinkRef = element.getAttribute("ItalRef");
            String cat = element.getElementsByTagName("FogyasztasDatuma").item(0).getTextContent();
            String quantity = element.getElementsByTagName("MennyisegDb").item(0).getTextContent();
            String rating = element.getElementsByTagName("Ertekeles").item(0).getTextContent();
            String ritualCombo = element.getElementsByTagName("RitualisKombo").item(0).getTextContent();

            Colors.printWithColor(Colors.PURPLE, index + "");
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

            index++;
        }
    }
}
