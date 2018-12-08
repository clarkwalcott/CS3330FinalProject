/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Clark
 */
public class LoginMenuController extends AbstractModel implements Initializable {

    private EmailSearcher searcher = new EmailSearcher();
    private Pair<String, String> usernamePassword = new Pair<>("", "");

    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passField;
    @FXML
    private Text errorField;

    @FXML
    private Button signInButton;
            
    @FXML
    private void signIn(ActionEvent event) throws Exception { //might throw exception

        usernamePassword = new Pair<>(userField.getText(), passField.getText());
        
        if(searcher.connectToEmailServer(host, port, usernamePassword.getKey(), usernamePassword.getValue()) == null){
            userField.clear();
            passField.clear();
            errorField.setVisible(true);
            return;
        }
        setAbstractModelIsLoggedIn(true);
        
        Platform.runLater(() -> {
            firePropertyChange("EmailSearcher", "", searcher);
        });
        
        Platform.runLater(() -> {
            Stage oldStage = (Stage) signInButton.getScene().getWindow();
            oldStage.close(); 
        });
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Requests focus on the username field by default
        Platform.runLater(userField::requestFocus);
        
        // Enable/Disable login button depending on whether a username was entered.
        signInButton.setDisable(true);
        errorField.setVisible(false);
        

        userField.textProperty().addListener((observable, oldValue, newValue) -> {
            signInButton.setDisable(newValue.trim().isEmpty());
        });
        
        BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResourceAsStream("background.jpg")),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //then you set to your node
        loginAnchorPane.setBackground(new Background(myBI));
        
    }
    
}
