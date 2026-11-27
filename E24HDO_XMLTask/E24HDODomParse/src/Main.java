import hu.e24hdo.domparse.Colors;
import hu.e24hdo.domparse.E24HDODomModify;
import hu.e24hdo.domparse.E24HDODomQuery;
import hu.e24hdo.domparse.E24HDODomRead;

final Scanner scanner = new Scanner(System.in);

void main(){
    selectAnOption();
}

public void selectAnOption(){
    printInstruction();
    while(true){
        String option = scanner.nextLine().trim();
        if(option.equals("1")){
            doDomRead();
        }else if(option.equals("2")){
            doDomModify();
        }else if(option.equals("3")){
            doDomQuery();
        }else if(option.equals("Q")){
            break;
        }else{
            Colors.printWithColorln(Colors.RED, "Ez nem opció!");
        }
        scanner.nextLine();
        printInstruction();
    }
}

public void printInstruction(){
    Colors.printWithColorln(Colors.CYAN, "Választás");
    Colors.printWithColor(Colors.PURPLE, "1");
    Colors.printWithColor(Colors.DEFAULT, ") ");
    Colors.printWithColorln(Colors.YELLOW, "E24HDODomRead.java");
    Colors.printWithColor(Colors.PURPLE, "2");
    Colors.printWithColor(Colors.DEFAULT, ") ");
    Colors.printWithColorln(Colors.YELLOW, "E24HDODomModify.java");
    Colors.printWithColor(Colors.PURPLE, "3");
    Colors.printWithColor(Colors.DEFAULT, ") ");
    Colors.printWithColorln(Colors.YELLOW, "E24HDOQuery.java");
}

public void doDomRead(){
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
        Colors.printWithColorln(Colors.CYAN, "Enter a visszalépéshez");
    }catch(Exception e){
        e.printStackTrace();
    }
}

public void doDomModify(){
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
        e24HDODomModify.printPrettyLine();
        Colors.printWithColorln(Colors.CYAN, "Enter a visszalépéshez");
    }catch(Exception e){
        e.printStackTrace();
    }
}

public void doDomQuery(){
    try{
        E24HDODomQuery e24HDODomQuery = new E24HDODomQuery("E24HDO_XML.xml");
        e24HDODomQuery.queryHighCaffeineDrinks(160);
        e24HDODomQuery.printPrettyLine();
        e24HDODomQuery.queryMembersByVIPLevel("Obszidián");
        e24HDODomQuery.printPrettyLine();
        e24HDODomQuery.queryMembersOrderAndCaffeineStats("T01");
        e24HDODomQuery.printPrettyLine();
        e24HDODomQuery.queryLaboratoryMemberCount("LK01");
        e24HDODomQuery.printPrettyLine();
        Colors.printWithColorln(Colors.CYAN, "Enter a visszalépéshez");
    }catch(Exception e){
        e.printStackTrace();
    }
}
