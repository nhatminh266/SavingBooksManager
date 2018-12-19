/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.controller;

import app.hibernate.model.HibernateUtil;
import app.hibernate.model.WindowController;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.hibernate.SessionFactory;

/**
 * FXML Controller class
 *
 * @author nhatminh266
 */
public class AlertController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView imgAlert;
    @FXML
    private Label lbContent;

    private WindowController window = new WindowController();
    @FXML
    private JFXButton noButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noButton.requestFocus();
        // TODO
    }

    @FXML
    private void closeStage(MouseEvent event) {
        window.closeStage(pane);
    }

    @FXML
    private void btnYes(ActionEvent event) {				
	HibernateUtil.closeSessionFactory();
        Platform.exit();
        
    }

    @FXML
    private void btnNo(ActionEvent event) {
        window.closeStage(pane);
    }

    
    
}
