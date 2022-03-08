package app.foodapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;


public class FoodApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/app/foodapp/view/foodapp.fxml")));
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/data/images/foodappIcon.png"))));
        primaryStage.setTitle("FoodApp for Windows");
        primaryStage.setScene(new Scene(root, 1000, 600, Color.rgb(232, 205, 231)));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }


}
