/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.controller;

import app.hibernate.model.Customer;
import app.hibernate.model.Deposit;
import app.hibernate.model.Employee;
import app.hibernate.model.HibernateUtil;
import app.hibernate.model.InterestDetail;
import app.hibernate.model.SavingsBook;
import app.hibernate.model.WindowController;
import app.hibernate.model.Withdraw;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * FXML Controller class
 *
 * @author nhatminh266
 */
public class CreateWithdrawController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane pane;
    private WindowController window = new WindowController();
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXTextField txtContent;
    @FXML
    private JFXComboBox<Customer> cbCustomer;
    @FXML
    private JFXDatePicker dueDate;
    @FXML
    private JFXComboBox<SavingsBook> cbSavingsBook;
    @FXML
    private JFXComboBox<Employee> cbEmployee;
    @FXML
    private TableView<InterestDetail> tbInterestDetail;
    @FXML
    private TableColumn<InterestDetail, String> colInterestDetailID;
    @FXML
    private TableColumn<InterestDetail, SavingsBook> colSavingsBook;
    @FXML
    private TableColumn<InterestDetail, Double> colInterest;
    @FXML
    private TableColumn<InterestDetail, Double> colBalance;
    @FXML
    private TableColumn<Withdraw, Double> colAmount;
    @FXML
    private TableColumn<Withdraw, String> colContent;
    @FXML
    private TableColumn<Withdraw, Employee> colEmployee;
    @FXML
    private TableColumn<Withdraw, Customer> colCustomer;
    @FXML
    private JFXRadioButton rdoWithdrawTerm;
    @FXML
    private ToggleGroup withdraw;
    @FXML
    private JFXRadioButton rdoWithdrawNotTerm;
    @FXML
    private JFXTextField txtWithdraw;
    @FXML
    private JFXDatePicker withdrawDate;
    @FXML
    private JFXButton btnSaveWithdraw;
    @FXML
    private JFXButton btnDeleteWithdraw;
    @FXML
    private JFXButton btnResetWithdraw;
    @FXML
    private JFXTextField keyWordWithdraw;
    @FXML
    private TableView<Withdraw> tbWithdraw;
    @FXML
    private TableColumn<Withdraw, String> colWithdrawID;
    @FXML
    private TableColumn<Withdraw, SavingsBook> colSavingsBookWithdraw;
    @FXML
    private TableColumn<Withdraw, Date> colWithdrawDate;
    private final static String NOT_TERM = "ST00";
    private final static String[] TERM = {"ST01", "ST03", "ST06", "ST09", "ST12"};
    private LocalDate dueDay;
    private static int month = 1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtWithdraw.textProperty().addListener((ov, oldValue, newValue) -> {
            txtWithdraw.setText(newValue.toUpperCase());
        });
        withdrawDate.setValue(LocalDate.now());
        btnSaveWithdraw.setDisable(true);
        btnDeleteWithdraw.setDisable(true);
        selectRdoWithdrawNotTerm();
        
        
        cbSavingsBook.valueProperty().addListener(e ->{
            Session session = HibernateUtil.getSession();
            String id = cbSavingsBook.getSelectionModel().getSelectedItem().getId();
            Transaction trans = session.beginTransaction();
            SavingsBook savingsBook = (SavingsBook) session.get(SavingsBook.class, id);
            Date dueDate = savingsBook.getDueDate();
            dueDay = Instant
               .ofEpochMilli(dueDate.getTime()) // get the millis value to build the Instant
               .atZone(ZoneId.systemDefault()) // convert to JVM default timezone
               .toLocalDate();  // convert to LocalDate
            this.dueDate.setValue(dueDay);
            
            //getSumBalance of savingsBook from openDate to now..
           
            List<InterestDetail> list = getInterestDetailSavingsBook(id);
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
            double sum = 0;
            if(!list.isEmpty()) {
            for(InterestDetail in : list) {
                double balance = in.getBalance();
                double interest = in.getInterest();
                if(savingsTypeID.equals(NOT_TERM)) {
                //Tiền lãi không kỳ hạn = (Số dư * Lãi suất) / 365 * Số ngày
                    interest = balance * interestRate / 365 * numDays;  
                }
                else{
                //Tiền lãi co kỳ hạn = Số tiền gửi x Lãi suất (%năm)/12 * số tháng gửi
                    
                    if(toDay.compareTo(dueDay) != 0) {
                       // neu rut tien khong phai ngay dao han thi mat lai xuat
                        interest = 0;
                    }
                    else interest = balance * interestRate / 12 * term;
                }
                sum = balance + interest;  
            }
            }
        
        this.txtAmount.setText(Double.toString(Math.round(sum)));
        trans.commit();
        HibernateUtil.closeSession();
        });
        

        loadCbCustomer();
        listenerKeyWordWithdraw();
        listenerRdoWithdrawTerm();
        listenerRdoWithdrawNotTerm();
        
        loadCbEmployee();
        loadWithdraw(null, NOT_TERM);
        loadInterestDetail();
        getRowTbWithdraw();
        getRowTbInterestDetail();
        
        
    }
    
    private ObservableList<InterestDetail> getInterestDetailSavingsBook(String id) {
        Session session = HibernateUtil.getSession();
        List<InterestDetail> in = null;
        try {
             Criteria criteria = session.createCriteria(InterestDetail.class)
                    .createCriteria("savingsBook")
                    .add(Restrictions.eq("id", id));
            in = criteria.list();
        }
        catch(HibernateException ex) {
            
        }
        finally{
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(in);
    }
    private ObservableList<SavingsBook> getSavingsBookById(String id) {
        Session session = HibernateUtil.getSession();
        List<SavingsBook> sb = null;
        try {
             Criteria criteria = session.createCriteria(SavingsBook.class)
                    //.createCriteria("id")
                    .add(Restrictions.eq("id", id));
            sb = criteria.list();
        }
        catch(HibernateException ex) {
            
        }
        finally{
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(sb);
    }
    private void listenerKeyWordWithdraw() {
        keyWordWithdraw.textProperty().addListener(e -> {
            tbWithdraw.getItems().clear();
            
            tbWithdraw.setItems(getWithdraw(keyWordWithdraw.getText()));
        
        });
    }
    private void getRowTbWithdraw() {
        tbWithdraw.setRowFactory(e -> {
            TableRow<Withdraw> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    Withdraw withdraw = row.getItem();
                    txtWithdraw.setText(withdraw.getId());
                    txtAmount.setText(Double.toString(withdraw.getAmount()));
                    
                    String date = withdraw.getWithdrawDate().toString();
                    withdrawDate.setValue(LocalDate.parse(date));
                    txtContent.setText(withdraw.getContent());
                    cbCustomer.setValue(withdraw.getCustomer());
                    cbSavingsBook.setValue(withdraw.getSavingsBook());
                    cbEmployee.setValue(withdraw.getEmployee());
                    btnDeleteWithdraw.setDisable(false);
                }
            
            });
            return row;
        
        });
        
    }
    private void getRowTbInterestDetail() {
        tbInterestDetail.setRowFactory(e -> {
            TableRow<InterestDetail> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 2 && !row.isEmpty()) {
                    InterestDetail interestDetail = row.getItem();
                    int id = interestDetail.getId();
                    String savingsBookId = interestDetail.getSavingsBook().getId();
                    List<SavingsBook> list = getSavingsBookById(savingsBookId);
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete ID = "+ id + " ?",
                            ButtonType.YES, ButtonType.NO);
                    confirm.setHeaderText(null);
                    confirm.showAndWait();
                    if(confirm.getResult() == ButtonType.YES) {
                        if(interestDetail.getSumBalance() == 0) {
                        Session session = HibernateUtil.getSession();
                        Transaction trans = session.beginTransaction();
                        InterestDetail in = (InterestDetail) session
                                .get(InterestDetail.class, id);
                        if(in != null) {
                            session.delete(in);
                            trans.commit();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Delete Success!");
                            alert.show();
                            loadInterestDetail(); 
                        }
                        //updateStatusSavingsBook();
                        Session sess = HibernateUtil.getSession();
                        Transaction tr = sess.beginTransaction();
                            for(SavingsBook sb : list) {
                               sb.setStatus(false);
                               sess.update(sb);
                               tr.commit();
                            }
                        HibernateUtil.closeSession();
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Delete Fail!");
                            alert.show();
                        }
                    }
                }
            
            });
            return row;
        
        });
        
    }
    
    private void selectRdoWithdrawNotTerm() {
        if(rdoWithdrawNotTerm.isSelected()) {
            btnSaveWithdraw.setDisable(false);
            cbSavingsBook.getItems().clear();
            loadSavingsBookNotTerm();
            
        }
    }
    private void loadCbCustomer() {
       getCustomer().forEach(c -> {
           cbCustomer.getItems().add(c);
       });
    }
    private void loadSavingsBookNotTerm() {
        getSavingsBook(null, NOT_TERM).forEach((sb) -> {
            cbSavingsBook.getItems().add(sb);
        });
    }
   private ObservableList<SavingsBook> getSavingsBook(String[] arr, String id) {
        Session session = HibernateUtil.getSession();
        List<SavingsBook> sb = null;
        Criteria criteria = null;
        try {
            
            //Select * from SavingsBook where savingsType = id;
            if(arr == null) {
                 criteria = session.createCriteria(SavingsBook.class)
                    .createCriteria("savingsType")
                    .add(Restrictions.eq("id", id));
            }
            if(id == null) {
                 criteria = session.createCriteria(SavingsBook.class)
                    .createCriteria("savingsType")
                    .add(Restrictions.in("id", arr));
            }
            sb = criteria.list();          
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(sb);
        
    }
    private ObservableList<Customer> getCustomer() {
        Session session = HibernateUtil.getSession();
        List<Customer> c = null;
        try {
            Criteria criteria = session.createCriteria(Customer.class);
            c = criteria.list();
        }
        catch(HibernateException ex) {
            
        }
        finally{
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(c);
    }
    private void listenerRdoWithdrawTerm() {
        rdoWithdrawTerm.selectedProperty().addListener(e -> {
            if(rdoWithdrawTerm.isSelected()) {    
                btnSaveWithdraw.setDisable(false);
                cbSavingsBook.getItems().clear();
                loadSavingsBookTerm();
                loadWithdraw(TERM, null);
            }
        
        });
    }
    private void loadSavingsBookTerm() {
        getSavingsBook(TERM, null).forEach((sb) -> {
            cbSavingsBook.getItems().add(sb);
        });
    }
    /*private ObservableList<SavingsBook> getSavingsBook(String one, 
            String three, String six) {
        Session session = HibernateUtil.getSession();
        List<SavingsBook> sb = null;
        try {
            
            //Select * from SavingsBook where savingsType = {ST01, ST03, ST06};
            Criteria criteria = session.createCriteria(SavingsBook.class)
                    .createCriteria("savingsType")
                    .add(Restrictions.in("id", new String[] {one, three, six}));  
            sb = criteria.list();          
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(sb);
        
    }*/
    private void listenerRdoWithdrawNotTerm() {
        rdoWithdrawNotTerm.selectedProperty().addListener(e -> {
            if(rdoWithdrawNotTerm.isSelected()) {
                btnSaveWithdraw.setDisable(false);
                cbSavingsBook.getItems().clear();
                loadSavingsBookNotTerm();
                loadWithdraw(null, NOT_TERM);
            }
        
        });
    }
    @FXML
    private void closeStage(MouseEvent event) {
        window.closeStage(pane);
    }
    private void loadCbEmployee() {
        getEmployee().forEach(c -> {
           cbEmployee.getItems().add(c);
       });
    }
    private ObservableList<Employee> getEmployee() {
        Session session = HibernateUtil.getSession();
        List<Employee> e = null;
        try {
            Criteria criteria = session.createCriteria(Employee.class);
            e = criteria.list();
        }
        catch(HibernateException ex) {
            
        }
        finally{
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(e);
    }
    private void loadWithdraw() {
        colWithdrawID.setCellValueFactory(new PropertyValueFactory("id"));
        colSavingsBookWithdraw.setCellValueFactory(new PropertyValueFactory("savingsBook"));
        colAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        colWithdrawDate.setCellValueFactory(new PropertyValueFactory("withdrawDate"));
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colEmployee.setCellValueFactory(new PropertyValueFactory("employee"));
        colCustomer.setCellValueFactory(new PropertyValueFactory("customer"));
        tbWithdraw.setItems(getWithdraw(""));
    }
    private void loadWithdraw(String[] arr, String id) {
        colWithdrawID.setCellValueFactory(new PropertyValueFactory("id"));
        colSavingsBookWithdraw.setCellValueFactory(new PropertyValueFactory("savingsBook"));
        colAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        colWithdrawDate.setCellValueFactory(new PropertyValueFactory("withdrawDate"));
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colEmployee.setCellValueFactory(new PropertyValueFactory("employee"));
        colCustomer.setCellValueFactory(new PropertyValueFactory("customer"));
        if(arr == null) 
            tbWithdraw.setItems(getWithdraw(null, id));
        else
            tbWithdraw.setItems(getWithdraw(arr, null));
    }
    private void loadInterestDetail() {
        colInterestDetailID.setCellValueFactory(new PropertyValueFactory("id"));
        colSavingsBook.setCellValueFactory(new PropertyValueFactory("savingsBook"));
        colInterest.setCellValueFactory(new PropertyValueFactory("interest"));
        colBalance.setCellValueFactory(new PropertyValueFactory("balance"));
        tbInterestDetail.setItems(getInterestDetail());
    }
    private ObservableList<InterestDetail> getInterestDetail() {
        Session session = HibernateUtil.getSession();
        List<InterestDetail> i = null;
        try {
            Criteria criteria = session.createCriteria(InterestDetail.class);
            i = criteria.list();         
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(i);
    }
    
    private ObservableList<Withdraw> getWithdraw(String key) {
        Session session = HibernateUtil.getSession();
        List<Withdraw> w = null;
        try {
            
            Criteria criteria = session.createCriteria(Withdraw.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion id = Restrictions.ilike("id", format);
                Criterion sv = Restrictions.ilike("savingsBook", format);
                criteria.add(Restrictions.or(id, sv));
            }
            w = criteria.list();
            
                    
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(w);
    }
    private ObservableList<Withdraw> getWithdraw(String[] arr, String id) {
        Session session = HibernateUtil.getSession();
        List<Withdraw> w = null;
        Criteria criteria = null;
        try {
            if(arr == null) {
                 criteria = session.createCriteria(Withdraw.class)
                    .createCriteria("savingsBook")
                    .createCriteria("savingsType")
                    .add(Restrictions.eq("id", id));
            }
            if(id == null) {
                 criteria = session.createCriteria(Withdraw.class)
                    .createCriteria("savingsBook")
                    .createCriteria("savingsType")
                    .add(Restrictions.in("id", arr));
            }
            
            w = criteria.list();
            
                    
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(w);
    }

    @FXML
    private void minimize(MouseEvent event) {
        window.minimizeStage(pane);
    }

    @FXML
    private void saveWithdraw(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtWithdraw.getText().isEmpty()) {
            alert.setContentText("Please Enter Withdraw ID");
            alert.show();
            return;
        }
        
        
        if(cbCustomer.getValue() == null) {
            alert.setContentText("Please Enter Customer Name");
            alert.show();
            return;
        }
        if(cbSavingsBook.getValue() == null) {
            alert.setContentText("Please Enter SavingsBook ID");
            alert.show();
            return;
        }
        if(withdrawDate.getValue() == null) {
            alert.setContentText("Please Enter Withdraw Date");
            alert.show();
            return;
        }
        if(cbEmployee.getValue() == null) {
            alert.setContentText("Please Enter Employee");
            alert.show();
            return;
        }
        if(rdoWithdrawNotTerm.isSelected()) {
            addWithdrawNotTerm();
            
            tbInterestDetail.getItems().clear();
            loadInterestDetail();
        }
        else {
            addWithdrawTerm();
            tbInterestDetail.getItems().clear();
            loadInterestDetail();
        }
    }

    private void addWithdrawNotTerm() {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Withdraw withdraw = null;
        Session session = HibernateUtil.getSession();
        try {
            
            withdraw = (Withdraw) session.get(Deposit.class, 
                    txtWithdraw.getText());
            trans = session.beginTransaction();
            
            if(!txtAmount.getText().equals("0.0")) {
            if(withdraw == null) {
                // Insert record
                withdraw = new Withdraw();
                withdraw.setId(txtWithdraw.getText());
                withdraw.setAmount(Double.parseDouble(txtAmount.getText()));
                withdraw.setWithdrawDate(Date.from(withdrawDate.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                withdraw.setContent(txtContent.getText());
                withdraw.setCustomer(cbCustomer.getSelectionModel().getSelectedItem());
                withdraw.setEmployee(cbEmployee.getSelectionModel().getSelectedItem());
                withdraw.setSavingsBook(cbSavingsBook.getSelectionModel().getSelectedItem());
                session.save(withdraw);
                trans.commit();
                    
                
                alert.setContentText("Withdraw Success!");
                alert.setHeaderText(null);
                alert.show();
                updateInterestDetail();
                loadInterestDetail();
                updateSavingsBook();
            }
            else {
                alert.setContentText("Withdraw ID has existed!");
                alert.setHeaderText(null);
                alert.show();
            }
            }
            
            else{
                alert.setContentText("This savings book out of money!");
                alert.setHeaderText(null);
                alert.show();
            }
            
        }catch(HibernateException | NullPointerException e)
        {
            alert.setContentText("Add Fail!");
            alert.setHeaderText(null);
            alert.show();
            trans.rollback();
            
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            HibernateUtil.closeSession();
            loadWithdraw(null, NOT_TERM);
        }
    }
    
    private void updateInterestDetail() {
        
        String id = cbSavingsBook.getSelectionModel().getSelectedItem().getId();
        List<InterestDetail> list = getInterestDetailSavingsBook(id);
        Session session = HibernateUtil.getSession();
        Transaction trans = session.beginTransaction();
        for(InterestDetail in : list) {
            in.setInterest(0);
            in.setBalance(0);
            in.setSumBalance(0);
            session.update(in);
        }
        
        trans.commit();    
        HibernateUtil.closeSession();
    }
    private void updateSavingsBook() {
        String id = cbSavingsBook.getSelectionModel().getSelectedItem().getId();
        List<SavingsBook> list = getSavingsBookById(id);
        Session session = HibernateUtil.getSession();
        Transaction trans = session.beginTransaction();
        for(SavingsBook sb : list) {
            sb.setCloseDate(Date.from(withdrawDate.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
            session.update(sb);
        }
        
        trans.commit();    
        HibernateUtil.closeSession();
    }
    private void addWithdrawTerm() {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);;
        Session session = HibernateUtil.getSession();
        LocalDate dueDate = this.dueDate.getValue();
        LocalDate toDay = LocalDate.now();
        try {
            Withdraw withdraw = (Withdraw) session.get(Withdraw.class, 
                    txtWithdraw.getText());
            trans = session.beginTransaction();
            if(withdraw == null) {
                // Insert record
                withdraw = new Withdraw();
                withdraw.setId(txtWithdraw.getText());
                withdraw.setAmount(Double.parseDouble(txtAmount.getText()));
                withdraw.setWithdrawDate(Date.from(withdrawDate.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                withdraw.setContent(txtContent.getText());
                withdraw.setCustomer(cbCustomer.getSelectionModel().getSelectedItem());
                withdraw.setEmployee(cbEmployee.getSelectionModel().getSelectedItem());
                withdraw.setSavingsBook(cbSavingsBook.getSelectionModel().getSelectedItem());
                session.save(withdraw);
                trans.commit();
           
                alert.setContentText("Withdraw Success!");
                alert.setHeaderText(null);
                alert.show();
                updateInterestDetail();
                loadInterestDetail();
                updateSavingsBook();
            }
    
        }catch(HibernateException | NullPointerException e)
        {
            
            alert.setContentText("Add Fail!");
            alert.setContentText(null);
            alert.show();
            trans.rollback();
            
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            HibernateUtil.closeSession();
            
            loadWithdraw(TERM, null);
        }
    }
    @FXML
    private void deleteWithdraw(ActionEvent event) {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        Session session = HibernateUtil.getSession();
        try {
            Withdraw withdraw = (Withdraw) session.get(Withdraw.class, 
                    txtWithdraw.getText());
            trans = session.beginTransaction();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Do you want to delete " + withdraw.getId()+ " ?", 
                    ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(withdraw != null) {
                session.delete(withdraw);
                trans.commit();
                alert.setContentText("Delete Success!");
                alert.show();
            }  
            }
        }catch(HibernateException ex) {
            alert.setContentText("Delete Fail");
            alert.show();
            trans.rollback();
            
        }
        finally {
            
            HibernateUtil.closeSession();
            loadWithdraw();
        }
    }

    @FXML
    private void resetWithdraw(ActionEvent event) {
        txtWithdraw.setText("");
        txtAmount.setText("");
        txtContent.setText("");
        cbCustomer.setValue(null);
        cbEmployee.setValue(null);
        cbSavingsBook.setValue(null);
        withdrawDate.setValue(LocalDate.now());
        dueDate.setValue(null);
    }

    @FXML
    private void showReport(ActionEvent event) {
        showChoiceDialog();
    }
    public void showChoiceDialog() {
        List<Integer> months = new ArrayList<>();
        for(int i = 1; i < 13; i++) {
            months.add(i); // add 1-12 month
        }
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(12, months);
        dialog.setTitle("Choose a month");
        dialog.setContentText("Choose your month: ");
        dialog.setHeaderText(null);
        Optional<Integer> result = dialog.showAndWait();
        if(result.isPresent()) {
            month = result.get();
            showReport();
        }
    }
    public void showReport() {
        File reportFile = new File("src/report/reportWithdraw.jasper");
        java.util.Map hash = new HashMap();
        hash.put("pMonth", month);
        Connection conn = null;
        JasperPrint jp = null;
        try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/savings_book_management?" +
                                 "user=root&password=123456");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        try {
            //JasperReport jasperReport = JasperCompileManager.compileReport(reportFile.getAbsolutePath());
            jp = JasperFillManager.fillReport(reportFile.getAbsolutePath(),hash, conn);
        
        } catch (JRException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        JasperViewer jv = new JasperViewer(jp, false);
        jv.viewReport(jp,false);
        
    }
}
