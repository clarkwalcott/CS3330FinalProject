/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;

import javafx.fxml.FXMLLoader;

/**
 *
 * @author Clark
 */

public interface EmailViewerInterface {
    FXMLLoader openNewStage(String stageFXMLName);
    void viewEmail(int messageNumber);
}
