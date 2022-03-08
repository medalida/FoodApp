package app.foodapp.controller;

import app.foodapp.model.Favourites;
import app.foodapp.model.Receipt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FavouritesController implements Initializable {
    @FXML private FlowPane CartesPane;
    private FoodAppController ParentController;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void displayFavourites(){
        ArrayList<Object> favourites= Favourites.getFavourites();
        this.CartesPane.getChildren().clear();
        for(Object favourite : favourites){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/foodapp/view/carte.fxml"));
                AnchorPane pane = loader.load();
                CarteController controller = loader.getController();
                controller.setParentController(this.ParentController);
                controller.setFavourite(favourite);
                this.CartesPane.getChildren().add(pane);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setParentController(FoodAppController foodAppController) {
        this.ParentController = foodAppController;
    }
}
