import hu.e24hdo.domparse.E24HDODomRead;

void main(){

    E24HDODomRead e24HDODomRead = new E24HDODomRead("E24HDO_XML.xml");
    e24HDODomRead.printRootElement();
//    e24HDODomRead.printElements("Gyarto", "Gyártó");
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

}
