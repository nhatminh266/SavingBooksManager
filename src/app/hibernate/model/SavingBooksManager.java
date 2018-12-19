/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.model;
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
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.print.DocFlavor;
import org.hibernate.SessionFactory;



/**
 *
 * @author nhatminh266
 */
public class SavingBooksManager extends Application {
    
    private WindowController window = new WindowController();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        window.loadWindow("/app/hibernate/view/main.fxml");
       //try{
        
           
        //Parent root = FXMLLoader.load(getClass().getResource("/app/hibernate/view/login.fxml"));
        
        /*Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/resources/icon.png"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        primaryStage.show();
       }
       catch(IOException | NullPointerException e){
           System.out.println("File not found" + e.getLocalizedMessage());
       }*/
       
    }
    

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
