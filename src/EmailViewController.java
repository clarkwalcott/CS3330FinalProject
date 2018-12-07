/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.util.Pair;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Clark
 */
public class EmailViewController extends AbstractModel implements Initializable, PropertyChangeListener {

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

    void setMessage(int messageNumber) throws IOException, MessagingException {
        Integer messageNumberInteger = messageNumber;
        System.out.println(messageNumberInteger.toString());
        System.out.println("Loading message...");
        EmailSearcher searcher = new EmailSearcher();
        Message[] foundMessages = searcher.searchEmail(host, port, usernamePassword.getKey(), usernamePassword.getValue(), messageNumberInteger.toString(), "messageNumber");
//        for(Message message : foundMessages){
//            System.out.println(message.getSubject());
//        };
        Message foundMessage = foundMessages[0];
        emailTextArea.clear();
        emailTextArea.setText(foundMessage.getContent().toString());
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
//        System.out.println("Evt: " + evt);
//        System.out.println("Value: " + evt.getNewValue());
        switch (evt.getPropertyName()) {
            case "usernamePassword":
                usernamePassword = (Pair<String,String>) evt.getNewValue();
                System.out.println("EmailViewController usernamePassword: " + usernamePassword);
                break;
                default:
                    break;
        }
    }
    
}
