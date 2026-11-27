package hu.e24hdo.domparse;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class E24HDODomQuery extends E24HDODom{
    public E24HDODomQuery(String fileName){
        super(fileName);
    }

    public void queryHighCaffeineDrinks(int minCaffeine){
        Colors.printWithColorln(Colors.CYAN, "160mg-nál több koffeint tartalmazó italok ");
        NodeList drinks = getDocument().getElementsByTagName("Ital");
        HashMap<String, Element> manufactures = getElementList("Gyarto", "GyartoID");

        int found = 0;
        for(int i = 0; i < drinks.getLength(); i++){
            Node node = drinks.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                int caffeine = Integer.parseInt(element.getElementsByTagName("KoffeinMg").item(0).getTextContent());

                if(caffeine >= minCaffeine){
                    found++;
                    String manufactureRef = element.getAttribute("GyartoRef");
                    String name = element.getElementsByTagName("Nev").item(0).getTextContent();
                    String caffeineMg = element.getElementsByTagName("KoffeinMg").item(0).getTextContent();
                    String sizeInMl = element.getElementsByTagName("KiszerelesMl").item(0).getTextContent();
                    String flavorNote = joinMultipleTags("Izjegy", element);
                    String colors = joinMultipleTags("Szin", element);

                    Colors.printWithColor(Colors.PURPLE, found + "");
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
                }
            }
        }
    }

    public void queryMembersByVIPLevel(String targetLevel){
        NodeList cardList = getDocument().getElementsByTagName("VIPKartya");
        List<Node> cards = IntStream.range(0, cardList.getLength()).mapToObj(cardList::item).filter(node -> node.getNodeType() == Node.ELEMENT_NODE).map(node -> (Element) node).filter(element -> {
            return element.getElementsByTagName("Szint").item(0).getTextContent().equalsIgnoreCase(targetLevel);
        }).collect(Collectors.toList());
        HashMap<String, Element> members =  getElementList("Tag", "TagID");
        Colors.printWithColorln(Colors.CYAN, targetLevel + " szinttel rendelkező VIP tagok (" + cards.size() + ")");

        for(int i = 0; i < cards.size(); i++){
            Element element = (Element) cards.get(i);
            String memberID = element.getAttribute("TagID").toString();
            String tagRef = element.getAttribute("TagRef");
            String serialNumber = element.getElementsByTagName("Sorozatszam").item(0).getTextContent();
            String level = element.getElementsByTagName("Szint").item(0).getTextContent();
            String cat = element.getElementsByTagName("KiallitasDatuma").item(0).getTextContent();

            Colors.printWithColor(Colors.PURPLE, (i+1) + "");
            Colors.printWithColor(Colors.GREEN, element.getAttribute("KartyaID").toString());
            Colors.printWithColor(Colors.DEFAULT, ") ");
            Colors.printWithColor(Colors.YELLOW, memberID);
            Colors.printWithColor(Colors.DEFAULT, " (");
            Colors.printWithColor(Colors.GREEN, members.getOrDefault(tagRef, null).getElementsByTagName("Nev").item(0).getTextContent().replaceAll("\n", " ").replaceAll("  ", "").trim());
            Colors.printWithColor(Colors.DEFAULT, ")\n\tSorozatszám: ");
            Colors.printWithColor(Colors.YELLOW, serialNumber);
            Colors.printWithColor(Colors.DEFAULT, "\n\tSzint: ");
            Colors.printWithColor(Colors.YELLOW, level);
            Colors.printWithColor(Colors.DEFAULT, "\n\tKiállítva: ");
            Colors.printWithColorln(Colors.BLUE, cat);
        }
    }

    public void queryMembersOrderAndCaffeineStats(String targetMemberID){
        NodeList orderList = getDocument().getElementsByTagName("Fogyasztas");
        List<Node> orders = IntStream.range(0, orderList.getLength()).mapToObj(orderList::item).filter(node -> node.getNodeType() == Node.ELEMENT_NODE).map(node -> (Element) node).filter(element -> {
            return element.getAttribute("TagRef").equalsIgnoreCase(targetMemberID);
        }).collect(Collectors.toList());
        HashMap<String, Element> members =  getElementList("Tag", "TagID");
        HashMap<String, Element> drinks = getElementList("Ital", "ItalID");
        Colors.printWithColorln(Colors.CYAN, members.getOrDefault(targetMemberID, null).getElementsByTagName("Nev").item(0).getTextContent().replaceAll("\n", " ").replaceAll("  ", "").trim() + " tag fogyasztásai & bevitt kalóriái (" + orders.size() + "x)");

        int allCaffeine = 0;
        for(int i = 0; i < orders.size(); i++){
            Element element = (Element) orders.get(i);
            String drinkRef = element.getAttribute("ItalRef");
            String quantity = element.getElementsByTagName("MennyisegDb").item(0).getTextContent();
            int currentCaffeine = Integer.parseInt(drinks.getOrDefault(drinkRef, null).getElementsByTagName("KoffeinMg").item(0).getTextContent())* Integer.parseInt(quantity);
            allCaffeine += currentCaffeine;
            Colors.printWithColor(Colors.PURPLE, (i+1) + "");
            Colors.printWithColor(Colors.DEFAULT, ") ");
            Colors.printWithColor(Colors.GREEN, drinks.getOrDefault(drinkRef, null).getElementsByTagName("Nev").item(0).getTextContent());
            Colors.printWithColor(Colors.DEFAULT, " (");
            Colors.printWithColor(Colors.BLUE, quantity);
            Colors.printWithColor(Colors.DEFAULT, "x) - ");
            Colors.printWithColor(Colors.BLUE, currentCaffeine + "");
            Colors.printWithColorln(Colors.DEFAULT, "mg koffein");
        }
        Colors.printWithColor(Colors.DEFAULT, "Összes koffein (mg): ");
        Colors.printWithColorln(Colors.BLUE, allCaffeine + "");
    }

    public void queryLaboratoryMemberCount(String targetBaseID){
        NodeList laboratoryList = getDocument().getElementsByTagName("LaborKozpont");
        List<Node> laboratory = IntStream.range(0, laboratoryList.getLength()).mapToObj(laboratoryList::item).filter(node -> node.getNodeType() == Node.ELEMENT_NODE).map(node -> (Element) node).filter(element -> {
            return element.getAttribute("KozpontID").equalsIgnoreCase(targetBaseID);
        }).collect(Collectors.toList());
        HashMap<String, Element> members =  getElementList("Tag", "TagID");
        if(laboratory.isEmpty()){
            Colors.printWithColorln(Colors.CYAN, targetBaseID + " telephelyhez regisztrált tagok");
            Colors.printWithColorln(Colors.RED, "Nem létezik ez a Labor Központunk, de ne csüggedj! Ha elég aláírás gyűlik össze, megépítjük!");
            return;
        }

        Element element = (Element) laboratory.get(0);
        Colors.printWithColorln(Colors.CYAN, element.getElementsByTagName("Varos").item(0).getTextContent() + " telephelyhez regisztrált tagok");
        int membersCount = 0;
        for(var member:members.values()){
            if(member.getAttribute("KozpontRef").toString().equalsIgnoreCase(targetBaseID)){
                membersCount++;
            }
        }

        Colors.printWithColor(Colors.PURPLE,  targetBaseID+ "");
        Colors.printWithColor(Colors.DEFAULT, ") ");
        Colors.printWithColor(Colors.GREEN, element.getElementsByTagName("Cim").item(0).getTextContent().trim());
        Colors.printWithColor(Colors.DEFAULT, " - ");
        Colors.printWithColor(Colors.BLUE, membersCount + "");
        Colors.printWithColorln(Colors.DEFAULT, " regisztrált tag");


    }

}
