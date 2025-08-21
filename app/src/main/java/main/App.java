package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application 
{

    @Override
    public void start(Stage stage) throws Exception 
    {
    	// icon
    	stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
    	
        // Đặt file FXML vào
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/calculatorView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("FX Calculator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
