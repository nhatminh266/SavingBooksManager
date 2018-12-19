/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.controller;

import app.hibernate.model.HibernateUtil;
import app.hibernate.model.InterestDetail;
import app.hibernate.model.SavingBooksManager;
import app.hibernate.model.SavingsBook;
import app.hibernate.model.WindowController;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * FXML Controller class
 *
 * @author nhatminh266
 */
public class MainController implements Initializable {


    private WindowController window = new WindowController();
    @FXML
    private StackPane pane;
    @FXML
    private JFXTextField keySearch;
    @FXML
    private Text txtSavingsBookId;
    @FXML
    private Text txtSavingsType;
    @FXML
    private Text txtCustomer;
    @FXML
    private Text txtSum;
    @FXML
    private Text txtOpenDate;
    @FXML
    private Text txtDueDate;
    @FXML
    private Text txtCloseDate;
    @FXML
    private Text txtStatus;
    @FXML
    private Text txtBalance;
    @FXML
    private Text txtAmount;
    @FXML
    private Text txtInterest;
    @FXML
    private GridPane gridPane;
    private String id = null;
    private final String NOT_TERM = "ST00";
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        keySearch.textProperty().addListener((ov, oldValue, newValue) -> {
            keySearch.setText(newValue.toUpperCase());
        });
        
        keySearch.textProperty().addListener(e -> {
            id = keySearch.getText();
            updateInterestToNow();
            List<SavingsBook> list = this.getSavingsBook(id);
            List<InterestDetail> listDetail = this.getInterestDetail(id);
            //Session session = HibernateUtil.getSession();
            if(!list.isEmpty()) {
                gridPane.setVisible(true);
            for(SavingsBook sb : list) {
                if(sb != null) {
                txtSavingsBookId.setText(new StringBuilder("ID:   ")
                        .append(sb.getId()).toString());
                txtAmount.setText(new StringBuilder("Amount:   ")
                        .append(sb.getAmount()).toString() + "   VND");
                txtOpenDate.setText(new StringBuilder("Open Date:   ")
                        .append(sb.getOpenDate()).toString());
                txtCloseDate.setText(new StringBuilder("Close Date:   ")
                        .append(sb.getCloseDate()).toString());
                txtDueDate.setText(new StringBuilder("Due Date:   ")
                        .append(sb.getDueDate()).toString());
                txtCustomer.setText(new StringBuilder("Customer Name:   ")
                        .append(sb.getCustomer().getName()).toString());
                
                txtSavingsType.setText(new StringBuilder("Savings Type:   ")
                        .append(sb.getSavingsType().getName()).toString());
                txtStatus.setText(new StringBuilder("Status:   ")
                        .append(sb.isStatus()).toString());
                }
                
                
            }
            }
            else {
                gridPane.setVisible(false);
            }
            if(!listDetail.isEmpty()) {
            for(InterestDetail in : listDetail) {
                if(in != null) {
                   txtInterest.setText(new StringBuilder("Interest:   ")
                        .append(in.getInterest()).toString() + "   VND");
                txtBalance.setText(new StringBuilder("Balance:   ")
                        .append(in.getBalance()).toString() + "   VND");
                txtSum.setText(new StringBuilder("Sum Balance:   ")
                        .append(in.getSumBalance()).toString() + "   VND");
                }
                
            }
            }
            
        
        });
        
            
        
    }  
    private void updateInterestToNow() {
        try{
        List<InterestDetail> list = this.getInterestDetail(id);
        Session session = HibernateUtil.getSession();
        Transaction trans = session.beginTransaction();
        SavingsBook savingsBook = (SavingsBook) session.get(SavingsBook.class, id);
        int term = savingsBook.getSavingsType().getTerm();
        String savingsTypeID = savingsBook.getSavingsType().getId();
        double interestRate = savingsBook.getSavingsType().getInterestRate();
        Date open = savingsBook.getOpenDate();
        LocalDate openDay = Instant
               .ofEpochMilli(open.getTime()) // get the millis value to build the Instant
               .atZone(ZoneId.systemDefault()) // convert to JVM default timezone
               .toLocalDate();  // convert to LocalDate
        LocalDate toDay = LocalDate.now();
        int numDays = (int) DAYS.between(openDay, toDay);
        if(numDays == 0)
            numDays = 1;
        
        if(!list.isEmpty()) {
            for(InterestDetail in : list) {
                double balance = in.getBalance();
                double interest = in.getInterest();
                double sumBalance = in.getSumBalance();
                if(savingsTypeID.equals(NOT_TERM)) {
                //Tiền lãi không kỳ hạn = (Số dư * Lãi suất) / 365 * Số ngày
                    interest = balance * interestRate / 365 * numDays;
                    in.setBalance(balance);
                    in.setInterest(Math.round(interest));
                    in.setSumBalance(Math.round(balance + interest));
                    session.update(in);
                }
            }
            trans.commit();
            
        }
        }catch(NullPointerException e) {
           
        }
        finally {
            HibernateUtil.closeSession();
        }
    }

    private ObservableList<SavingsBook> getSavingsBook(String id) {
        Session session = HibernateUtil.getSession();
        List<SavingsBook> sb = null;
        
        try {
             //Select * from SavingsBook where id = id;
            Criteria criteria = session.createCriteria(SavingsBook.class)
                    .add(Restrictions.eq("id", id));
            sb = criteria.list();          
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(sb);
        
    }
    private ObservableList<InterestDetail> getInterestDetail(String id) {
        Session session = HibernateUtil.getSession();
        List<InterestDetail> in = null;
        
        try {
             //Select * from SavingsBook where id = id;
            Criteria criteria = session.createCriteria(InterestDetail.class)
                    .createCriteria("savingsBook")
                    .add(Restrictions.eq("id", id));
            in = criteria.list();          
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(in);
        
    }
    @FXML
    private void loadAddSavingsType(ActionEvent event) {
        window.loadWindow("/app/hibernate/view/addSavingsType.fxml");
    }
    @FXML
    private void loadAddSavingsBook(ActionEvent event) {
        window.loadWindow("/app/hibernate/view/addSavingBooks.fxml");
        //window.closeStage(pane);
    }

    @FXML
    private void loadDeposit(ActionEvent event) {
        window.loadWindow("/app/hibernate/view/createDeposit.fxml");
    }

    @FXML
    private void loadWithdraw(ActionEvent event) {
        window.loadWindow("/app/hibernate/view/createWithdraw.fxml");
    }


    @FXML
    private void loadView(ActionEvent event) {
        CreateDepositController deposit = new CreateDepositController();
        CreateWithdrawController withdraw = new CreateWithdrawController();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                "Press YES to show Deposit Report.\nPress NO to show Withdraw Report!", 
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait();
        if(confirm.getResult() == ButtonType.YES) {
            deposit.showChoiceDialog();
        }
        else withdraw.showChoiceDialog();
        
    }

    @FXML
    private void exitPlatform(MouseEvent event) {
        window.showAlert();
        
    }

    @FXML
    private void minimize(MouseEvent event) {
        window.minimizeStage(pane);
    }
    
    
}
