package app.foodapp.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

public class FoodAppController implements Initializable {

    @FXML private VBox menuRight;
    @FXML private AnchorPane Scene;
    @FXML private AnchorPane MainScene;

    private LinkedList<AnchorPane> FIFO = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        MainScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Crimson+Pro");
        try{
            this.load("/app/foodapp/view/home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuOnOff(){
        if(menuRight.isVisible()){
            menuRight.setVisible(false);
            menuRight.setManaged(false);
        }

        else{
            menuRight.setVisible(true);
            menuRight.setManaged(true);
        }
    }

    @FXML
    private void goHome(){
        try{
            this.load("/app/foodapp/view/home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML private void home(Event e){

    }

    @FXML
    private void goSearchByIngredients(){
        if(Objects.equals(this.Scene.getChildren().get(0).idProperty().get(), "SearchPane"))
            return;
        try{
            SearchController searchController =(SearchController) this.load("/app/foodapp/view/search.fxml");
            searchController.setParentController(this);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Initializable load(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        AnchorPane Pane = loader.load();
        if(this.Scene.getChildren().get(0) != null)
            FIFO.addFirst((AnchorPane) this.Scene.getChildren().get(0));
        this.Scene.getChildren().clear();
        this.Scene.getChildren().add(Pane);
        AnchorPane.setTopAnchor(Pane, 0d);
        AnchorPane.setBottomAnchor(Pane, 0d);
        AnchorPane.setLeftAnchor(Pane, 0d);
        AnchorPane.setRightAnchor(Pane, 0d);
        return loader.getController();
    }

    @FXML
    private void favourite(){
        //if(Objects.equals(this.Scene.getChildren().get(0).idProperty().get(), "favouritesPane"))
            //return;
        try{
            FavouritesController favouritesController =(FavouritesController) this.load("/app/foodapp/view/favourites.fxml");
            favouritesController.setParentController(this);
            favouritesController.displayFavourites();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void goBack(){
        if(this.FIFO.size() <= 0)
            return;
        this.Scene.getChildren().clear();
        this.Scene.getChildren().add(this.FIFO.getFirst());
        this.FIFO.removeFirst();
    }
}
