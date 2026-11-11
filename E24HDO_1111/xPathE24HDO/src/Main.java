import xpathe24hdo.xPathE24HDO;

void main(){
    try{
        xPathE24HDO xPathE24HDO = new xPathE24HDO("studentE24HDO.xml");
        xPathE24HDO.xPathQuestions();
        xPathE24HDO.modifyChild("hallgato", "01", "keresztnev", "János");

    }catch(Exception e){
        IO.println("exception: " + e.getMessage());
    }
}
