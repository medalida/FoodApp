package app.foodapp.model;

import app.foodapp.JSONReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Receipt {
    private final long id;

    public Receipt(long id){
        this.id =id;
    }

    public long getId(){
        return this.id;
    }

    public String getSummary() {
            String url = "https://api.spoonacular.com/recipes/"+this.id+"/summary";
            url = JSONReader.formatUrl(url,null);
            Object data = JSONReader.getDataByUrl(url);
            if(data != null){
                return (String)((JSONObject) Objects.requireNonNull(JSONReader.getDataByUrl(url))).get("summary");
            }else{
                JSONObject favouritesFile = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
                assert favouritesFile != null;
                return (String) ((JSONObject)(favouritesFile.get(""+this.id))).get("summary");
            }

    }

        public Object getIngredients() {
        String url = "https://api.spoonacular.com/recipes/"+this.id+"/ingredientWidget.json";
            url = JSONReader.formatUrl(url,null);
            Object data = JSONReader.getDataByUrl(url);
            if(data != null){
                return ((JSONObject)Objects.requireNonNull(JSONReader.getDataByUrl(url))).get("ingredients");
            }else{
                JSONObject favouritesFile = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
                if(favouritesFile == null)
                    return null;
                return ((JSONObject)favouritesFile.get(""+this.id)).get("ingredients");
            }
        }

    public Object getEquipments() {
        String url = "https://api.spoonacular.com/recipes/"+this.id+"/equipmentWidget.json";
        url = JSONReader.formatUrl(url,null);
        Object data = JSONReader.getDataByUrl(url);
        if(data != null){
            return ((JSONObject)Objects.requireNonNull(JSONReader.getDataByUrl(url))).get("equipment");
        }else{
            JSONObject favouritesFile = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
            if(favouritesFile == null)
                return null;
            return ((JSONObject)favouritesFile.get(""+this.id)).get("equipment");
        }
    }

    public Object getInstructions() {
        String url = "https://api.spoonacular.com/recipes/"+this.id+"/analyzedInstructions";
        url = JSONReader.formatUrl(url,null);
        Object data = JSONReader.getDataByUrl(url);
        if(data != null){
            return  Objects.requireNonNull(JSONReader.getDataByUrl(url));
        }else{
            JSONObject favouritesFile = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
            if(favouritesFile == null)
                return null;
            return ((JSONObject)favouritesFile.get(""+this.id)).get("instructions");
        }
    }

    public Object getInformation() {
        String url = "https://api.spoonacular.com/recipes/"+this.id+"/information";
        url = JSONReader.formatUrl(url, Map.of("includeNutrition", List.of("false")));
        Object data = JSONReader.getDataByUrl(url);
        if(data != null){
            return Objects.requireNonNull(JSONReader.getDataByUrl(url));
        }else{
            JSONObject favouritesFile = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
            if(favouritesFile == null)
                return null;
            return ((JSONObject)favouritesFile.get(""+this.id)).get("information");
        }
    }

    public static ArrayList<Receipt> searchByIngredients(List<String> Ingredients, int number, int offset){

        String url = "https://api.spoonacular.com/recipes/findByIngredients";
        url = JSONReader.formatUrl(url, Map.of("ingredients", Ingredients, "number", List.of(""+number),"offset",List.of(""+offset),"sort", List.of("popularity")));
        JSONArray data = (JSONArray) JSONReader.getDataByUrl(url);
        if(data == null)
            return null;
        ArrayList<Receipt> receipts = new ArrayList<>();
        Receipt receipt;
        JSONObject receiptObj;
        long id;
        for(Object obj : data){
            receiptObj = (JSONObject) obj;
            id = (long) receiptObj.get("id");
            receipt = new Receipt(id);
            receipts.add(receipt);
        }
        return receipts;
    }


}

