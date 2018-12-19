/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.controller;

import app.hibernate.model.SavingBooksManager;
import app.hibernate.model.WindowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author nhatminh266
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label lbResult;
    private WindowController window = new WindowController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userName.requestFocus();
        
    }    

    @FXML
    private void checkLogin(ActionEvent event) throws Exception {
        String user = userName.getText();
        String pass = password.getText();
        String userAccess = "admin";
        String passAccess = "123";
        if(userAccess.equals(user) && passAccess.equals(pass)) {
           window.closeStage(pane);
           window.loadWindow("/app/hibernate/view/main.fxml");
        }
        else{
            if(user.isEmpty() || pass.isEmpty()){
                Alert mess = new Alert(Alert.AlertType.ERROR);
                mess.setTitle("Login Failed");
                mess.setHeaderText(null);
                mess.setContentText("Please Enter User & Password!");
                mess.show();
            }
            else{
                lbResult.setText("Invalid UserName Or Password!");
                lbResult.setStyle("-fx-text-fill: white;" 
                        +"-fx-alignment: center;"); 
            }
        }
    }
   
    
    /*private void loadMain() throws IOException {
        try{
        Stage home = new Stage(StageStyle.DECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/app/hibernate/view/main.fxml"));
        home.setTitle("Saving Books Manager");
        home.getIcons().add(new Image("/resources/icon.png"));
        home.setScene(new Scene(root));
        home.show();
        }
        catch(IOException e){
            System.out.println("File not found " + e.getLocalizedMessage());
        }
    }*/

    @FXML
    private void close(ActionEvent event) {
        window.exitPlatform();
    }
    
}
