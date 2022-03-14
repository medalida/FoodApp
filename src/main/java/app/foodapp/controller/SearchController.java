package app.foodapp.controller;

import app.foodapp.model.Receipt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SearchController  implements Initializable {

    @FXML private FlowPane CartesPane;
    @FXML private FlowPane IngredientsFlowPane;
    @FXML private TextField SearchField;
    @FXML private Button More;
    @FXML private VBox MessageBox;
    @FXML private TextField NumberField;
    private HashMap<String, Button> Ingredients = new HashMap<>();
    private FoodAppController ParentController;
    private int offset = 0;
    private ArrayList<String> IngredientsArray = null;
    private int numberOfReceipt = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.More.setVisible(false);
    }
    @FXML
    private void addIngredient(){
        String Ingredient = this.SearchField.getText();
        this.SearchField.clear();
        if(Ingredient.equals("") || this.Ingredients.containsKey(Ingredient.toLowerCase()))
            return;
        this.Ingredients.put(Ingredient.toLowerCase(), addIngredientButton(Ingredient));
        System.out.println(this.Ingredients.get(Ingredient.toLowerCase()));
        this.IngredientsFlowPane.getChildren().add(this.Ingredients.get(Ingredient.toLowerCase()));
    }
    private Button addIngredientButton(String Ingredient){

        ImageView DeleteIcon = new ImageView(new Image(String.valueOf(getClass().getResource("/data/images/delete.png"))));
        DeleteIcon.setFitHeight(12);
        DeleteIcon.setFitWidth(12);
        Button Iconbutton = new Button();
        Iconbutton.setStyle("-fx-background-color: #C39AE5");
        Iconbutton.setMinSize(12,12);
        Iconbutton.setMinSize(12,12);
        Button button = new Button(Ingredient);
        button.setContentDisplay(ContentDisplay.RIGHT);
        button.setMaxHeight(25);
        button.setMinHeight(25);
        button.setPadding(new Insets(0,0,0,10));
        button.setTextFill(Color.valueOf("#7c0072"));
        button.setFont(Font.font(14));
        button.setStyle("-fx-background-color: #C39AE5");
        Iconbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            this.IngredientsFlowPane.getChildren().remove(button);
            this.Ingredients.remove(Ingredient.toLowerCase());
        });
        Iconbutton.setGraphic(DeleteIcon);
        button.setGraphic(Iconbutton);
        return button;
    }
    @FXML
    private void search(){
        this.More.setVisible(true);
        this.CartesPane.getChildren().clear();
        this.IngredientsArray = new ArrayList<>(this.Ingredients.keySet());
        String text = this.NumberField.getText();
        if(text.matches("[0-9]*")){
            if(!text.equals("")){
                int number = Integer.parseInt(text);
                if(number > 10){
                    this.numberOfReceipt = 10;
                    this.NumberField.setText("10");
                }else{
                    this.numberOfReceipt = number;
                }
            }else{
                this.numberOfReceipt = 5;
            }

        }else{
            this.numberOfReceipt = 5;
            this.NumberField.setText("5");
        }
        ArrayList<Receipt> receipts= Receipt.searchByIngredients(this.IngredientsArray, this.numberOfReceipt,0);

        if(receipts == null){
            showNoInternet();
            return;
        }
        if(receipts.size()==0){
            showNoReceiptFound();
        }
        this.offset = receipts.size();

        load(receipts);

    }
    private void load(ArrayList<Receipt> receipts){
        for(Receipt receipt : receipts){
            try{
                this.MessageBox.getChildren().clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/foodapp/view/carte.fxml"));
                AnchorPane pane = loader.load();
                CarteController controller = loader.getController();
                controller.setParentController(this.ParentController);
                controller.set(receipt);
                this.CartesPane.getChildren().add(pane);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showNoReceiptFound() {
        this.More.setVisible(false);
        this.MessageBox.getChildren().clear();
        Label label = new Label("No receipt found");
        label.setFont(Font.font(32));
        label.setTextFill(Color.valueOf("#92278F"));
        this.MessageBox.getChildren().add(label);
    }

    private void showNoInternet() {
        this.More.setVisible(false);
        this.MessageBox.getChildren().clear();
        Label label = new Label("No internet :(");
        label.setFont(Font.font(32));
        label.setTextFill(Color.valueOf("#92278F"));
        this.MessageBox.getChildren().add(label);
    }

    @FXML
    private void loadMore(){
        this.MessageBox.getChildren().clear();
        ArrayList<Receipt> receipts= Receipt.searchByIngredients(this.IngredientsArray, this.numberOfReceipt,this.offset);
        if(receipts == null){
            showNoInternet();
            return;
        }
        System.out.println(receipts.size() + " " + this.offset);
        if(receipts.size()==0){
            this.More.setVisible(false);
            return;
        }
        this.offset += receipts.size();
        this.load(receipts);
    }

    public void setParentController(FoodAppController foodAppController) {
        this.ParentController = foodAppController;
    }
}
