package View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.Authenticator;
import java.util.*;


/**
 * Created by User on 07-Jun-18.
 */
public class sendEmail {

    final String senderEmailID = "mevhanet@gmail.com";
    final String senderPassword = "arnon2018";
    final String emailSMTPserver = "smtp.gmail.com";

    public void handleSubmitButtonAction(ActionEvent event)
    {
        final String emailServerPort = "465";
        String receiverEmailID = Main.user.getEmail();
        String emailSubject = "Welcome to Mevhanet";
        String emailBody = "Welcome to Mevhanet! \n\t " +
                "Thank you for creating a Mevhanet account. \n " +
                "Username: " + Main.user.getUser_name() + "\n Password: " + Main.user.getPassword() + "\n";

        Properties props = new Properties();
        props.put("mail.smtp.user",senderEmailID);
        props.put("mail.smtp.host",emailSMTPserver);
        props.put("mail.smtp.port",emailServerPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port",emailServerPort);
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback","false");
        SecurityManager security = System.getSecurityManager();
        try
        {
            SMTPAuthenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(emailBody);
            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(senderEmailID));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(receiverEmailID));
            Transport.send(msg);
            System.out.println("Message send Successfully");
            Parent register_page = FXMLLoader.load(getClass().getResource("myView.fxml"));
            Scene register_scene = new Scene (register_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("Mevhanet");
            app_stage.setScene(register_scene);
            app_stage.show();
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
        }
    }


    public class SMTPAuthenticator extends javax.mail.Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(senderEmailID, senderPassword);
        }
    }

    @FXML
    protected void handleHomePage(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("myView.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Mevhanet");
        app_stage.setScene(register_scene);
        app_stage.show();
    }
}
