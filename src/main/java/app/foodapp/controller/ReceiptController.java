package app.foodapp.controller;

import app.foodapp.JSONReader;
import app.foodapp.model.EndPoints;
import app.foodapp.model.Favourites;
import app.foodapp.model.Receipt;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ReceiptController  implements Initializable {

    @FXML private ImageView ReceiptImg;
    @FXML private Label ReceiptTilte;
    @FXML private Label ReceiptAuthor;
    @FXML private Label ReceiptTime;
    @FXML private AnchorPane ReceiptPane;
    @FXML private HBox Stars;
    @FXML private HBox InfoHbox;
    @FXML private ImageView Like;
    @FXML private Label Score;
    @FXML private Label Likes;
    @FXML private Label Summary;
    @FXML private ImageView NutriScore;
    @FXML private VBox IngredientsBox;
    @FXML private VBox EquipmentBox;
    @FXML private VBox StepsBox;
    @FXML private VBox LeftVBox;

    private JSONObject receiptInformation;
    private Receipt receipt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ReceiptImg.fitHeightProperty().bind(InfoHbox.heightProperty());
        this.Summary.setTextAlignment(TextAlignment.JUSTIFY);
        this.LeftVBox.minWidthProperty().bind(this.ReceiptPane.widthProperty().multiply((double) 1/3));
        this.StepsBox.minWidthProperty().bind(this.ReceiptPane.widthProperty().multiply((double) 2/3));

    }

    @FXML
    private void addOrRemoveFavorite(){
        JSONObject FavoriteReceipts =  (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
        Image redHeart = new Image(String.valueOf(getClass().getResource("/data/images/heart.png")));
        Image whiteHeart = new Image(String.valueOf(getClass().getResource("/data/images/like.png")));
        assert FavoriteReceipts != null;
        if(FavoriteReceipts.containsKey(""+this.receipt.getId())){
            Like.setImage(whiteHeart);
            Favourites.removeFromFavourites(this.receipt);
        }else{
            Like.setImage(redHeart);
            Favourites.addToFavourites(this.receipt);
        }
    }

    public void setReceiptInformation(JSONObject receiptInformation) {
        this.receiptInformation = receiptInformation;
        this.receipt = new Receipt((long)this.receiptInformation.get("id"));
        updateReceiptInformation();
        updateReceipt();
        updateHeart();
    }

    private void updateHeart() {
        JSONObject FavoriteReceipts =  (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);
        Image redHeart = new Image(String.valueOf(getClass().getResource("/data/images/heart.png")));
        Image whiteHeart = new Image(String.valueOf(getClass().getResource("/data/images/like.png")));
        assert FavoriteReceipts != null;
        if(FavoriteReceipts.containsKey(""+this.receipt.getId())){
            Like.setImage(redHeart);
        }else{
            Like.setImage(whiteHeart);
        }
    }

    private void updateReceipt() {
        DecimalFormat format = new DecimalFormat("0.#");
        String summary =this.receipt.getSummary();
        this.Summary.setText(summary.replaceAll("\\<.*?\\>", ""));

        JSONArray Ingredinets = (JSONArray) receipt.getIngredients();
        for(Object ingredinet : Ingredinets){
            Label label = new Label();
            label.setFont(Font.font(18));
            label.setPadding(new Insets(5,20,5,20));
            JSONObject amount = (JSONObject)((JSONObject) ((JSONObject)ingredinet).get("amount")).get("metric");
            String text = "- "+  format.format(amount.get("value")) +" "+ amount.get("unit") +" "+ ((JSONObject)ingredinet).get("name");
            label.setText(text);
            this.IngredientsBox.getChildren().add(label);
        }
        JSONArray Equipments = (JSONArray)receipt.getEquipments();
        if(Equipments.size() == 0)
            this.EquipmentBox.getChildren().clear();
        for(Object equipment : Equipments){
            Label label = new Label();
            label.setFont(Font.font(18));
            label.setPadding(new Insets(5,20,5,20));
            String text = "- "+  ((JSONObject)equipment).get("name");
            label.setText(text);
            this.EquipmentBox.getChildren().add(label);
        }

        for(Object object : ((JSONArray)receipt.getInstructions())){
            String name = (String)((JSONObject)object).get("name");
            Label nameLabel = makeLabel();
            nameLabel.setFont(Font.font("calibri", FontWeight.BOLD,20));
            JSONArray Instructions = (JSONArray)((JSONObject)object).get("steps");
            for(Object objet : Instructions){
                Label label = this.makeLabel();
                JSONObject instruction = (JSONObject) objet;
                String text = "step "+  instruction.get("number") +": "+ instruction.get("step");
                label.setText(text);
                this.StepsBox.getChildren().add(label);
            }
        }


            double size = 0;
            this.StepsBox.setMinHeight(500);

    }
    private Label makeLabel(){
        Label label = new Label();
        label.setFont(Font.font(18));
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setWrapText(true);
        label.setMaxHeight(200);
        label.wrapTextProperty().setValue(true);
        label.setPadding(new Insets(5,20,5,20));
        return label;
    }

    private void updateReceiptInformation() {
        Image image = new Image(this.receiptInformation.get("image").toString());
        if(!image.isError())
            this.ReceiptImg.setImage(image);
        System.out.println(this.receiptInformation.get("healthScore").toString());
        this.ReceiptTilte.setText(this.receiptInformation.get("title").toString());
        this.ReceiptAuthor.setText("By "+ this.receiptInformation.get("sourceName").toString());
        this.ReceiptTime.setText("Ready in "+ this.receiptInformation.get("readyInMinutes").toString() + " min");
        this.Likes.setText(this.receiptInformation.get("aggregateLikes").toString()+" Like");
        double score =(double) this.receiptInformation.get("spoonacularScore");
        this.Score.setText(""+score/10+"/10");
        double stars = score/20;
        Image star = new Image("/data/images/star.png");
        Image halfStar = new Image("/data/images/halfstar.png");
        Image noStar = new Image("/data/images/nostar.png");
        for(int i = 0; i < 5; i++){
            if(stars >= i+1){
                ((ImageView) this.Stars.getChildren().get(i)).setImage(star);
            }else if(stars > i){
                ((ImageView) this.Stars.getChildren().get(i)).setImage(halfStar);
            }else{
                ((ImageView) this.Stars.getChildren().get(i)).setImage(noStar);
            }
        }

        double nutri = (double) this.receiptInformation.get("healthScore");
        switch ((int)nutri/10){
            case 0:
                this.NutriScore.setImage(new Image("/data/images/E.png"));
                break;
            case 1:
                this.NutriScore.setImage(new Image("/data/images/D.png"));
                break;
            case 2:
                this.NutriScore.setImage(new Image("/data/images/C.png"));
                break;
            case 3:
                this.NutriScore.setImage(new Image("/data/images/B.png"));
                break;
            default:
                this.NutriScore.setImage(new Image("/data/images/A.png"));
                break;
        }


    }
}
