/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.print.DocFlavor;



/**
 *
 * @author nhatminh266
 */
public class SavingBooksManager extends Application {
    
    private double x;
    private double y;
    @Override
    public void start(Stage primaryStage) throws IOException {
       try{
        Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY(); 
        });
        
        root.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() - x);
            primaryStage.setY(e.getScreenY() - y);
            
        
        });
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Saving Books Login");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
       }
       catch(IOException e){
           System.out.println("File not found" + e.getLocalizedMessage());
       }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
