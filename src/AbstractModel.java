/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.util.Pair;

/**
 *
 * @author Clark
 * 
 * abstract means you cannot create an instance of this class
 */
public abstract class AbstractModel {
    protected final String host = "imap.mail.yahoo.com";
    protected final String port = "993";
    protected Pair<String,String> usernamePassword = new Pair<>("","");
    protected PropertyChangeSupport propertyChangeSupport;

    public AbstractModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
        System.out.println("AbstractModel Constructed");
    }
    
    // register event listener for property
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    // deregister event listener for property
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    // notify any listeners the property has changed
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
