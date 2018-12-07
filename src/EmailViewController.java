/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Clark
 */
public class EmailViewController extends AbstractModel implements Initializable {

    EmailSearcher searcher = null;
    
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem closeItem;
    @FXML
    private MenuItem aboutItem;
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
    
}
