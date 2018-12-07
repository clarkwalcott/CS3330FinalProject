/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Clark
 */
public class EmailViewController extends AbstractModel implements Initializable {

    EmailSearcher searcher = null;
    
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private TextArea emailTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    void setMessage(EmailSearcher searcher, int messageNumber) throws IOException, MessagingException {
        Integer messageNumberInteger = messageNumber;
        System.out.println(messageNumberInteger.toString());
        System.out.println("Loading message...");
        
        String foundMessage = searcher.getMessage(messageNumberInteger);
        
        emailTextArea.clear();
        BufferedReader reader = new BufferedReader(new StringReader(foundMessage));
        String tempLine = reader.readLine();
        while(tempLine != null){
            emailTextArea.appendText(tempLine + "\n");
            tempLine = reader.readLine();
        }
        reader.close();    
    }
    
    @FXML
    private void handleSave(ActionEvent event){
        Stage primaryStage = (Stage) myMenuBar.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = 
                    new ExtensionFilter(
                            "Text Files (.txt, .pdf)", "*.txt", "*.pdf");
            chooser.getExtensionFilters().add(txtFilter);
            chooser.setSelectedExtensionFilter(txtFilter);
        File file = chooser.showSaveDialog(primaryStage);
        
        try{
            if(file != null){
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(emailTextArea.getText());
                writer.close();
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    
     // Adapted from NYTimesViewer(During Class) displayAboutAlert() method.
    @FXML
    private void handleAbout(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        // Changes window icon to email.png
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("email.png"));
        stage.getIcons().add(icon);
       
        alert.setTitle("About");
        alert.setHeaderText("Email Viewer");
        alert.setContentText("This application was developed by Clark Walcott for CS3330 at the University of Missouri.");
        
        TextArea textArea = new TextArea("The JavaMail API is used to obtain messages from the user's email.  Documentation for JavaMail is available at https://javaee.github.io/javamail/FAQ.");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
            
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(expContent);
        
        alert.showAndWait();
    }
    
}
