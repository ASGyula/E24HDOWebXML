void main(){
    try{
        DOMReadE24HDO domReadE24HDO = new DOMReadE24HDO("E24HDO_XML.xml");
        domReadE24HDO.printRootElement();
        domReadE24HDO.printLowKeyNodeHierarchia();
    }catch(Exception exception){
        System.out.println("HIBA");
        exception.printStackTrace();
    }

    try{
        DOMParseE24HDO domReadE24HDO = new DOMParseE24HDO("E24HDO_orarend.xml");
        domReadE24HDO.printRootElement();
        domReadE24HDO.printLowKeyNodeHierarchia();
    }catch(Exception exception){
        System.out.println("HIBA");
        exception.printStackTrace();
    }

    try{
        DomModifyE24HDO domModifyE24HDO = new DomModifyE24HDO("E24HDO_XML.xml");
        domModifyE24HDO.modifyLowKeyV1("SADF Karcsi", 41);
        domModifyE24HDO.modifyEGY_3();
        domModifyE24HDO.deleteAllOrders();
    }catch(Exception exception){
        System.out.println("HIBA");
        exception.printStackTrace();
    }

    try{
        DomQueryE24HDO domQueryE24HDO = new DomQueryE24HDO("E24HDO_XML.xml");
        domQueryE24HDO.searchAllSheffWithKnowledge();
        domQueryE24HDO.searchRestaurantWithFiveStars();
        domQueryE24HDO.searchIntersWithAfternoonShift();
    }catch(Exception exception){
        System.out.println("HIBA");
        exception.printStackTrace();
    }

    try{
        OrarendModifyE24HDO orarendModifyE24HDO = new OrarendModifyE24HDO("E24HDO_orarend.xml");
        orarendModifyE24HDO.modifyLowKeyWebtech1ToWebt("WebTech1");
        orarendModifyE24HDO.modifyLowKeyWebtech1Location("V. előadó");
        orarendModifyE24HDO.modifyLowKeyWebtech1Type("gyakorlat");
    }catch(Exception exception){
        System.out.println("HIBA");
        exception.printStackTrace();
    }

    try{
        OrarendQueryE24HDO orarendQueryE24HDO = new OrarendQueryE24HDO("E24HDO_orarend.xml");
        orarendQueryE24HDO.queryCourseNames();
        orarendQueryE24HDO.queryLocation();
        orarendQueryE24HDO.queryType();
    }catch(Exception exception){
        System.out.println("HIBA");
        exception.printStackTrace();
    }

}
