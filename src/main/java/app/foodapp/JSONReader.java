package app.foodapp;

import app.foodapp.model.EndPoints;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class JSONReader {


        private static String getData(URL url) throws IOException {
        String inputLine;

            HttpURLConnection connection =(HttpURLConnection) url.openConnection();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while((inputLine = buffer.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            buffer.close();

            return stringBuilder.toString();
    }

    public static Object getDataByUrl(String url){
        String data;
        try {
            URL Url = new URL(url);
            data = JSONReader.getData(Url);
            return new JSONParser().parse(data);
        } catch (ParseException | NullPointerException | IOException e) {
            e.printStackTrace();
            System.out.println("No data found");

            return null;
        }
    }

    public static Object getDataByPath(String path){
        try {
            FileReader reader = new FileReader(path);
            return new JSONParser().parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatUrl(String url, Map<String, List<String>> parameters){
        StringBuilder urlBuilder = new StringBuilder(url + EndPoints.APIKEY);
        if(parameters == null)
            return urlBuilder.toString();
        for(Map.Entry<String, List<String>> parameter : parameters.entrySet()){
                if(parameter.getValue().size() > 0) {
                    urlBuilder.append("&").append(parameter.getKey()).append("=");
                    for (String value : parameter.getValue()) {
                        urlBuilder.append(value).append(",+");
                    }
                    urlBuilder.delete(urlBuilder.length()-2,urlBuilder.length());
                }
            }


        return urlBuilder.toString();
    }
}
