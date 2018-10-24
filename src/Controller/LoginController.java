/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
    private MaterialDesignIconView closeIcon;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label lbResult;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeStage(MouseEvent event) {
        Platform.exit();
        
    }

    @FXML
    private void checkLogin(ActionEvent event) throws Exception {
        String user = userName.getText();
        String pass = password.getText();
        String userAccess = "admin";
        String passAccess = "123";
        if(userAccess.equals(user) && passAccess.equals(pass)) {
           closeStage();
           loadMain();   
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
                lbResult.setStyle("-fx-background-color: #212121; -fx-text-fill: white");
            }
        }
    }
    private void closeStage() {
        Stage stage = (Stage) pane.getScene().getWindow(); // get stage login
        stage.close(); // close stage login
    }
    
    private void loadMain() throws IOException {
        try{
        Stage home = new Stage(StageStyle.DECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
        home.setTitle("Saving Books Manager");
        home.getIcons().add(new Image("/Image/main.jpg"));
        home.setScene(new Scene(root, 800, 600));
        home.show();
        }
        catch(IOException e){
            System.out.println("File not found " + e.getLocalizedMessage());
        }
    }
    
}
