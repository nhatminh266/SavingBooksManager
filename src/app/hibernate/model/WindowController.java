/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.model;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author nhatminh266
 */
public class WindowController {
    private double x;
    private double y;
    
    public void loadWindow(String location) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(location));
            
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("/resources/icon.png"));
            stage.show();
            
            root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY(); 
        });
        
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
            
        
        });
        }catch(IOException e) {
            System.out.println("File not found! " + e.getLocalizedMessage());
        }
       
    }
    public void minimizeStage(Pane pane) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setIconified(true);
    }
    public void closeStage(Pane pane) {
        Stage stage = (Stage) pane.getScene().getWindow(); // get stage 
        stage.close(); // close stage 
    }
    public void showAlert() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/app/hibernate/view/alert.fxml"));
            
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("/resources/icon.png"));
            
            root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY(); 
        });
        
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
            
        
        });
        //stage.requestFocus();
        stage.show();
        stage.requestFocus();
        
        }catch(IOException e) {
            System.out.println("File not found! " + e.getLocalizedMessage());
        } 
    /*try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/hibernate/view/dialog.fxml"));
        DialogPane root = loader.load();
        

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //alert.setTitle("Diff Viewer");
        alert.setResizable(true);
        alert.setDialogPane(root);
        alert.initModality(Modality.APPLICATION_MODAL);

        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setOnCloseRequest(event -> stage.hide());

        stage.initModality(Modality.NONE);
        alert.showAndWait();

    } catch (IOException e) {
        e.printStackTrace();
    }*/
    }
    public void exitPlatform() {
        Platform.exit();
    }
    
}
