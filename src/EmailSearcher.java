/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgwy9femailviewer;
 
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.BodyPart;
 
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
 
/**
 * This program demonstrates how to search for e-mail messages which satisfy
 * a search criterion.
 * Adapted by Clark Walcott from:
 * @author www.codejava.net
 * https://www.codejava.net/java-ee/javamail/using-javamail-for-searching-e-mail-messages
 *
 */
public class EmailSearcher {
 
    Store store = null;
    Message[] foundMessages = null, grabbedMessage = null;
    String messageContent = null;
    
    public EmailSearcher(){
        
    }
    
    /*
     * @param host
     * @param port
     * @param userName
     * @param password
     */
    public Store connectToEmailServer(String host, String port, String userName, String password){
        
        Properties properties = new Properties();
        Session session = null;
        
        System.out.println("Connecting to email server...");
        
        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
 
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));
//        if(host.equals("imap.gmail.com")){
            properties.put("mail.imap.ssl.enable", "true"); // required for Gmail
//            properties.put("mail.imap.auth.mechanisms", "XOAUTH2");
//        }
        
        session = Session.getInstance(properties);
 
        try{
        // connects to the message store
            store = session.getStore("imap");
//            if(host.equals("imap.gmail.com")){
//                store.connect("imap.gmail.com", userName, oauth2_access_token);
//            }
//            else{
            store.connect(host, userName, password);
//            }
            return store;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(EmailSearcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSearcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        store = null;
        return store;
    }
    
    /*
     * @param store
     */
    public void disconnectFromEmailServer(){
        System.out.println("Disconnecting from email server...");
        try {
            store.close();
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSearcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Searches for e-mail messages containing the specified keyword in
     * Subject field.
     * @param keyword
     * @param field
     * @return 
     */
    public Message[] searchEmail(final String keyword, final String field) {
        System.out.println("Searching for \"" + keyword + "\"...");    
        try{
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // creates a search criterion
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        switch (field) {
                            case "Subject":
                                if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(message.getSubject()).find()) {
                                    return true;
                                }   break;
                            case "From":
                                if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(message.getFrom().toString()).find()){
                                    return true;
                                }   break;
                            case "":
                                if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(message.getContent().toString()).find()){
                                    return true;
                                }   break;
                            default:
                                break;
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        Logger.getLogger(EmailSearcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return false;
                }
            };
 
            // performs search through the folder
            foundMessages = folderInbox.search(searchCondition);
 
//            for (int i = 0; i < foundMessages.length; i++) {
//                Message message = foundMessages[i];
//                String subject = message.getSubject();
//                System.out.println("Found message #" + i + ": " + subject);
//            }
 
            // close inbox
            folderInbox.close(false);
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
        return foundMessages;
    }
    
        /**
     * Searches for e-mail messages containing the specified keyword in
     * Subject field.
     * @param keyword
     * @param field
     * @return 
     */
    public String getMessage(final Integer messageNumber) {
        try{
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // creates a search criterion
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    Integer messageNumberInteger = message.getMessageNumber();
                    if(messageNumberInteger.equals(messageNumber)){
                        System.out.println("Worked.");
                        return true;
                    }
                    return false;
                }
            };
            // performs search through the folder
            grabbedMessage = folderInbox.search(searchCondition);
            messageContent = getTextFromMessage(grabbedMessage[0]);

            // close inbox
            folderInbox.close(false);
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(EmailSearcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return messageContent;
    }
    
private String getTextFromMessage(Message message) throws MessagingException, IOException {
    String result = "";
    if (message.isMimeType("text/plain")) {
        result = message.getContent().toString();
    } else if (message.isMimeType("multipart/*")) {
        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        result = getTextFromMimeMultipart(mimeMultipart);
    }
    return result;
}

private String getTextFromMimeMultipart(
        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
    String result = "";
    int count = mimeMultipart.getCount();
    for (int i = 0; i < count; i++) {
        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
        if (bodyPart.isMimeType("text/plain")) {
            result = result + "\n" + bodyPart.getContent();
            break; // without break same text appears twice in my tests
        } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
        } else if (bodyPart.getContent() instanceof MimeMultipart){
            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
        }
    }
    return result;
}
 
}