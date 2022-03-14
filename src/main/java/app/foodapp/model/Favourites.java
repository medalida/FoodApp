package app.foodapp.model;

import app.foodapp.JSONReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Favourites {

    public static ArrayList<Object> getFavourites() {
        ArrayList<Object> favourites = new ArrayList<>();
        JSONObject receipts =  (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
        assert receipts != null;
        Iterator<String> keys =  receipts.keySet().iterator();

        while(keys.hasNext()) {
            String key = keys.next();
            Object object = receipts.get(key);
            favourites.add(object);
        }

        return favourites;
    }

    public static void addToFavourites(Receipt receipt) {
        try {
            JSONObject object = new JSONObject();
            JSONObject informations = (JSONObject) receipt.getInformation();
            JSONArray ingredients = (JSONArray) receipt.getIngredients();
            JSONArray instructions = (JSONArray) receipt.getInstructions();
            JSONArray equipments = (JSONArray) receipt.getEquipments();

            String summary = receipt.getSummary();
            object.put("title", informations.get("title"));
            object.put("id", receipt.getId());
            object.put("information", informations);
            object.put("ingredients", ingredients);
            object.put("instructions", instructions);
            object.put("equipments", equipments);
            object.put("summary", summary);
            JSONObject favouritesFile = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
            favouritesFile.put(Long.toString(receipt.getId()), object);
            Files.write(Paths.get("src", "main", "resources", "data", "favorites.json"), favouritesFile.toJSONString().getBytes());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void removeFromFavourites(Receipt receipt){
        try {
            JSONObject favouritesFile = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
            favouritesFile.remove(Long.toString(receipt.getId()));
            Files.write(Paths.get("src", "main", "resources", "data", "favorites.json"), favouritesFile.toJSONString().getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
