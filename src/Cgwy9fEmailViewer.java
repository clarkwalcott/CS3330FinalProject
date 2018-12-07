/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Clark
 *
 * Icon made by Vectors Market from
 * "https://www.flaticon.com/authors/vectors-market"
 * 
 */
public class Cgwy9fEmailViewer extends Application {
    
    private final String appName = "Email Viewer";
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = (Parent)loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setTitle(appName);
        Image icon = new Image(getClass().getResourceAsStream("email.png"));
        stage.getIcons().add(icon);
        
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(e -> {
            MainMenuController menuController = loader.getController();
            if(menuController.searcher != null){
                menuController.searcher.disconnectFromEmailServer();
            }
            Platform.exit();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
