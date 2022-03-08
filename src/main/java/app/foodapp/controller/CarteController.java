package app.foodapp.controller;

import app.foodapp.JSONReader;
import app.foodapp.model.EndPoints;
import app.foodapp.model.Favourites;
import app.foodapp.model.Receipt;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
public class CarteController implements Initializable{

    @FXML private TextFlow ReceiptDisc;
    @FXML private Label ReceiptLikes;
    @FXML private Label ReceiptScore;
    @FXML private Label ReceiptName;
    @FXML private ImageView ReceiptImg;
    @FXML private ImageView Like;

    private long id;
    private String name;
    private long likes;
    private double score;
    private String image;
    private FoodAppController ParentController;
    private JSONObject receiptInformation;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set(Receipt receipt){
        this.receiptInformation = (JSONObject) receipt.getInformation();
        this.id = receipt.getId();
        this.name = (String) this.receiptInformation.get("title");
        this.likes = (long) this.receiptInformation.get("aggregateLikes");
        this.score =((double) this.receiptInformation.get("spoonacularScore"))/10;
        this.image = (String) this.receiptInformation.get("image");

        update();
    }

    public void setFavourite(Object object){
        JSONObject receipt = (JSONObject) object;
        this.id = (long) receipt.get("id");

        this.receiptInformation  = (JSONObject) receipt.get("information");
        this.name = (String) this.receiptInformation.get("title");
        this.likes = (long) this.receiptInformation.get("aggregateLikes");
        this.score =((double) this.receiptInformation.get("spoonacularScore"))/10;
        this.image = (String) this.receiptInformation.get("image");

        updateFavourites();
    }

    private  void updateFavourites() {
        this.ReceiptScore.setText(this.score + "/10");
        this.ReceiptLikes.setText(format(this.likes) + " Like");
        this.ReceiptName.setText(this.name);
        Image image = new Image(this.image);
        Image redHeart = new Image(String.valueOf(getClass().getResource("/data/images/heart.png")));
        this.Like.setImage(redHeart);
        if(!image.isError())
            this.ReceiptImg.setImage(image);
    }

    private void update(){
        this.ReceiptScore.setText(this.score + "/10");
        this.ReceiptLikes.setText(format(this.likes) + " Like");
        this.ReceiptName.setText(this.name);
        Image image = new Image(this.image);
        if(!image.isError())
            this.ReceiptImg.setImage(image);
        JSONObject receipts =  (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
        assert receipts != null;
        if(receipts.containsKey(""+this.id)){
            Like.setImage(new Image("/data/images/heart.png"));
        }else{
            Like.setImage(new Image("/data/images/like.png"));
        }
    }

    private String format(long number){

        if(number < 1000)
            return ""+number;
        else if(number < 10000){
            NumberFormat nf2 = NumberFormat.getInstance(new Locale("sk", "SK"));
            return nf2.format(number);
        }else if(number < 1000000){
            number = number / 1000;
            NumberFormat nf2 = NumberFormat.getInstance(new Locale("sk", "SK"));
            String likes = nf2.format(number);
            return likes+ " K";
        }else if(number < 1000000000){
            number = number / 1000000;
            NumberFormat nf2 = NumberFormat.getInstance(new Locale("sk", "SK"));
            String likes = nf2.format(number);
            return likes+ " M";
        }
        return ""+number;
    }

    @FXML
    private void addOrRemoveFavorite(){
        Image redHeart = new Image("/data/images/heart.png");
        Receipt receipt = new Receipt(this.id);
        if(Like.getImage().getUrl().equals(redHeart.getUrl())){
            Like.setImage(new Image(String.valueOf(getClass().getResource("/data/images/like.png"))));
            Favourites.removeFromFavourites(receipt);
        }else{
            Like.setImage(redHeart);
            Favourites.addToFavourites(receipt);
        }
    }

    @FXML
    private void getReceipt(){

        try {
            ReceiptController receiptController = (ReceiptController) this.ParentController.load("/app/foodapp/view/receipt.fxml");
            receiptController.setReceiptInformation(this.receiptInformation);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setParentController(FoodAppController foodAppController) {
        this.ParentController = foodAppController;
    }
}
