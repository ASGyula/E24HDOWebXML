import hu.e24hdo.domparse.E24HDODomModify;
import hu.e24hdo.domparse.E24HDODomRead;

void main(){

    try{
        E24HDODomRead e24HDODomRead = new E24HDODomRead("E24HDO_XML.xml");
        e24HDODomRead.printRootElement();
        e24HDODomRead.printManufactures();
        e24HDODomRead.printPrettyLine();
        e24HDODomRead.printDrinks();
        e24HDODomRead.printPrettyLine();
        e24HDODomRead.printLaboratories();
        e24HDODomRead.printPrettyLine();
        e24HDODomRead.printMembers();
        e24HDODomRead.printPrettyLine();
        e24HDODomRead.printVIPCards();
        e24HDODomRead.printPrettyLine();
        e24HDODomRead.printOrders();
    }catch(Exception e){
        e.printStackTrace();
    }

    try{
        E24HDODomModify e24HDODomModify = new E24HDODomModify("E24HDO_XML.xml");
        e24HDODomModify.printPrettyLine();
        e24HDODomModify.updateCoolerCapacity("LK01", "3");
        e24HDODomModify.printPrettyLine();
        e24HDODomModify.upgradeVIPCard("VIP02", "Arany2");
        e24HDODomModify.printPrettyLine();
        e24HDODomModify.addNewDrink("IT04", "GY01", "Monster Energy Ultra White", "150", "500", new String[]{"citrus", "cukormentes frissesség"}, new String[]{"#ffffff"});
        e24HDODomModify.printPrettyLine();
        e24HDODomModify.removeLastOrder();
        e24HDODomModify.printPrettyLine();
        e24HDODomModify.saveToFile("E24HDO_XML_modified.xml");
    }catch(Exception e){
        e.printStackTrace();
    }
}
