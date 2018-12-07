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
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Clark
 */
public class LoginMenuController extends AbstractModel implements Initializable {

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passField;

    @FXML
    private Button signInButton;
    
    private Pair<String, String> usernamePassword = new Pair<>("", "");
        
    @FXML
    public void signIn(ActionEvent event) throws Exception { //might throw exception

        usernamePassword = new Pair<>(userField.getText(), passField.getText());
        Platform.runLater(() -> {
            firePropertyChange("usernamePassword", "", usernamePassword);
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

        userField.textProperty().addListener((observable, oldValue, newValue) -> {
            signInButton.setDisable(newValue.trim().isEmpty());
        });
        
    }    
    
}
