package app.foodapp.model;



import app.foodapp.JSONReader;
import org.json.simple.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {


    public static void main(String[] args){
        // parsing file "JSONExample.json"
        Receipt receipt = new Receipt(2010);
        System.out.println();
        System.out.println("this is the receipt number 2010");
        receipt.getIngredients();
        System.out.println(Receipt.searchByIngredients(List.of("apples", "flour"), 2,0));
    }
}
