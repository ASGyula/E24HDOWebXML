package hu.e24hdo.domparse;

public class Colors{

    public static String DEFAULT = "\u001B[0m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";
    public static String YELLOW = "\u001B[33m";
    public static String BLUE = "\u001B[34m";
    public static String PURPLE = "\u001B[35m";
    public static String CYAN = "\u001B[36m";

    public static void printWithColor(String color, String text){
        IO.print(color + text + DEFAULT);
    }
    public static void printWithColorln(String color, String text){
        IO.println(color + text + DEFAULT);
    }
}
