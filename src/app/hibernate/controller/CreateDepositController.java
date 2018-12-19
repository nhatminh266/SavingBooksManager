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
import app.hibernate.model.SavingsType;
import app.hibernate.model.WindowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Map;

/**
 * FXML Controller class
 *
 * @author nhatminh266
 */
public class CreateDepositController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private WindowController window = new WindowController();
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXRadioButton rdoDepositTerm;
    @FXML
    private ToggleGroup deposit;
    @FXML
    private JFXRadioButton rdoDepositNotTerm;
    @FXML
    private JFXTextField txtDeposit;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXTextField txtContent;
    @FXML
    private JFXComboBox<Customer> cbCustomer;
    @FXML
    private JFXDatePicker depositDate;
    @FXML
    private JFXDatePicker dueDate;
    @FXML
    private JFXComboBox<SavingsBook> cbSavingsBook;
    @FXML
    private JFXComboBox<Employee> cbEmployee;
    @FXML
    private JFXButton btnSaveDeposit;
    @FXML
    private JFXButton btnDeleteDeposit;
    @FXML
    private JFXButton btnResetDeposit;
    @FXML
    private JFXTextField keyWordDeposit;
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
    private TableView<Deposit> tbDeposit;
    @FXML
    private TableColumn<Deposit, String> colDepositID;
    @FXML
    private TableColumn<Deposit, SavingsBook> colSavingsBookDeposit;
    @FXML
    private TableColumn<Deposit, Double> colAmount;
    @FXML
    private TableColumn<Deposit, Date> colDepositDate;
    @FXML
    private TableColumn<Deposit, String> colContent;
    @FXML
    private TableColumn<Deposit, Employee> colEmployee;
    @FXML
    private TableColumn<Deposit, Customer> colCustomer;
    @FXML
    private Label lbAmount;
    private static int month = 1;
    private final static String NOT_TERM = "ST00";
    private final static String[] TERM = {"ST01", "ST03", "ST06", "ST09", "ST12"};
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*Set id toUpperCase*/
        txtDeposit.textProperty().addListener((ov, oldValue, newValue) -> {
             txtDeposit.setText(newValue.toUpperCase());
        });
        cbSavingsBook.valueProperty().addListener(e ->{
            Session session = HibernateUtil.getSession();
            String id = cbSavingsBook.getSelectionModel().getSelectedItem().getId();
            
            Transaction trans = session.beginTransaction();
            SavingsBook savingsBook = (SavingsBook) session.get(SavingsBook.class, id);
            Date dueDate = savingsBook.getDueDate();
            LocalDate dueDay = Instant
               .ofEpochMilli(dueDate.getTime()) // get the millis value to build the Instant
               .atZone(ZoneId.systemDefault()) // convert to JVM default timezone
               .toLocalDate();  // convert to LocalDate
            this.dueDate.setValue(dueDay);
            trans.commit();
            HibernateUtil.closeSession();
            
        
        });
        
        depositDate.setValue(LocalDate.now());
        btnSaveDeposit.setDisable(true);
        btnDeleteDeposit.setDisable(true);
        
        
        selectRdoDepositNotTerm();
        loadCbCustomer();
        
        listenerRdoDepositTerm();
        listenerRdoDepositNotTerm();
        
        loadCbEmployee();
        //loadDeposit();
        loadDeposit(null, NOT_TERM);
        loadInterestDetail();
        getRowTbDeposit();
        // TODO
        txtAmount.setText("500");
        lbAmount.setText("500000");
        listenerAmount();
        listenerKeyWordDeposit();
    } 
    
    private void selectRdoDepositNotTerm() {
        if(rdoDepositNotTerm.isSelected()) {
            btnSaveDeposit.setDisable(false);
            cbSavingsBook.getItems().clear();
            loadSavingsBookNotTerm();
            
        }
    }

    private void listenerRdoDepositTerm() {
        rdoDepositTerm.selectedProperty().addListener(e -> {
            if(rdoDepositTerm.isSelected()) {
                btnSaveDeposit.setDisable(false);
            cbSavingsBook.getItems().clear();
            loadSavingsBookTerm();
            loadDeposit(TERM, null);
            }
        });
    }
    private void listenerKeyWordDeposit() {
        keyWordDeposit.textProperty().addListener(e -> {
            tbDeposit.getItems().clear();
            tbDeposit.setItems(getDeposit(keyWordDeposit.getText()));
        });
    }
    private void listenerRdoDepositNotTerm() {
        rdoDepositNotTerm.selectedProperty().addListener(e -> {
        
            if(rdoDepositNotTerm.isSelected()) {
                btnSaveDeposit.setDisable(false);
            cbSavingsBook.getItems().clear();
            loadSavingsBookNotTerm();
            loadDeposit(null, NOT_TERM);
        }
        });
    }
    private void listenerAmount() {
        txtAmount.textProperty().addListener(e -> {
            lbAmount.setText(
                    new StringBuilder(txtAmount.getText()).append("000").toString());
        });
    }
    private void getRowTbDeposit() {
        tbDeposit.setRowFactory(e -> {
            TableRow<Deposit> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    Deposit deposit = row.getItem();
                    txtDeposit.setText(deposit.getId());
                    lbAmount.setText(Integer.toString(deposit.getAmount()));
                    String date = deposit.getDepositDate().toString();
                    depositDate.setValue(LocalDate.parse(date));
                    txtContent.setText(deposit.getContent());
                    cbCustomer.setValue(deposit.getCustomer());
                    cbSavingsBook.setValue(deposit.getSavingsBook());
                    cbEmployee.setValue(deposit.getEmployee());
                    btnDeleteDeposit.setDisable(false);
                    
                }
            
            });
            return row;
        
        });
        
    }
    private void loadCbCustomer() {
        getCustomer().forEach((c) -> { 
            cbCustomer.getItems().add(c);
        });
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
    private void loadSavingsBookTerm() {
        getSavingsBook(TERM, null).forEach((sb) -> {
            cbSavingsBook.getItems().add(sb);
        });
    }
    private void loadSavingsBookNotTerm() {
        
        getSavingsBook(null, NOT_TERM).forEach((sb) -> {
            cbSavingsBook.getItems().add(sb);
        });
    }
    private void loadDeposit(String[] arr, String id) {
        colDepositID.setCellValueFactory(new PropertyValueFactory("id"));
        colSavingsBookDeposit.setCellValueFactory(new PropertyValueFactory("savingsBook"));
        colAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        colDepositDate.setCellValueFactory(new PropertyValueFactory("depositDate"));
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colEmployee.setCellValueFactory(new PropertyValueFactory("employee"));
        colCustomer.setCellValueFactory(new PropertyValueFactory("customer"));
        if(arr == null) 
           tbDeposit.setItems(getDeposit(null, id));
        else 
            tbDeposit.setItems(getDeposit(arr, null));
    }
    
    private void loadDeposit() {
        colDepositID.setCellValueFactory(new PropertyValueFactory("id"));
        colSavingsBookDeposit.setCellValueFactory(new PropertyValueFactory("savingsBook"));
        colAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        colDepositDate.setCellValueFactory(new PropertyValueFactory("depositDate"));
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colEmployee.setCellValueFactory(new PropertyValueFactory("employee"));
        colCustomer.setCellValueFactory(new PropertyValueFactory("customer"));
        tbDeposit.setItems(getDeposit(""));
        
    }
    private ObservableList<Deposit> getDeposit(String[] arr, String id) {
        Session session = HibernateUtil.getSession();
        List<Deposit> de = null;
        Criteria criteria = null;
        try {
            if(arr == null) {
                 criteria = session.createCriteria(Deposit.class)
                    .createCriteria("savingsBook")
                    .createCriteria("savingsType")
                    .add(Restrictions.eq("id", id));
            }
            if(id == null) {
                 criteria = session.createCriteria(Deposit.class)
                    .createCriteria("savingsBook")
                    .createCriteria("savingsType")
                    .add(Restrictions.in("id", arr));
            }
            de = criteria.list();
        }catch(HibernateException e) {
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(de);
        
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
    private ObservableList<Deposit> getDeposit(String key) {
        Session session = HibernateUtil.getSession();
        List<Deposit> c = null;
        try {
            
            Criteria criteria = session.createCriteria(Deposit.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion kh = Restrictions.ilike("customer", format);
                Criterion id = Restrictions.ilike("id", format);
                Criterion idCard = Restrictions.ilike("depositDate", format);
                criteria.add(Restrictions.or(kh, id, idCard));
            }
            c = criteria.list();
            
                    
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(c);
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
        
    }
*/
    private void loadCbEmployee() {
        getEmployee().forEach((em) -> {
            cbEmployee.getItems().add(em);
        });
    }
    private ObservableList<Employee> getEmployee() {
        Session session = HibernateUtil.getSession();
        List<Employee> em = null;
        try {
            Criteria criteria = session.createCriteria(Employee.class);
            em = criteria.list();
        }
        catch(HibernateException ex) {
            
        }
        finally{
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(em);
    }
    @FXML
    private void closeStage(MouseEvent event) {
        window.closeStage(pane);
    }

    @FXML
    private void saveDeposit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtDeposit.getText().isEmpty()) {
            alert.setContentText("Please Enter Deposit ID");
            alert.show();
            return;
        }
        if(txtAmount.getText().isEmpty()) {
            alert.setContentText("Please Enter Amount");
            alert.show();
            return;
        }
        if(Double.parseDouble(lbAmount.getText()) < 500000) {
            alert.setContentText("Please Enter Amount >= 500000!");
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
        if(depositDate.getValue() == null) {
            alert.setContentText("Please Enter Deposit Date");
            alert.show();
            return;
        }
        if(cbEmployee.getValue() == null) {
            alert.setContentText("Please Enter Employee");
            alert.show();
            return;
        }
        if(rdoDepositNotTerm.isSelected()) {
            addDepositNotTerm();
            
            tbInterestDetail.getItems().clear();
            loadInterestDetail();
        }
        else {
            addDepositTerm();
            tbInterestDetail.getItems().clear();
            loadInterestDetail();
        }
    }

    private void addDepositNotTerm() {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Session session = HibernateUtil.getSession();
        try {
            Deposit deposit = (Deposit) session.get(Deposit.class, 
                    txtDeposit.getText());
            trans = session.beginTransaction();
            if(deposit == null) {
                // Insert record
                deposit = new Deposit();
                deposit.setId(txtDeposit.getText());
                deposit.setAmount(Integer.parseInt(lbAmount.getText()));
                deposit.setDepositDate(Date.from(depositDate.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                deposit.setContent(txtContent.getText());
                deposit.setCustomer(cbCustomer.getSelectionModel().getSelectedItem());
                deposit.setEmployee(cbEmployee.getSelectionModel().getSelectedItem());
                deposit.setSavingsBook(cbSavingsBook.getSelectionModel().getSelectedItem());
                session.save(deposit);
                trans.commit();
                
                alert.setContentText("Deposit Success!");
                alert.setHeaderText(null);
                alert.show();
                updateBalance();
            }
            else {
                alert.setContentText("Deposit ID has existed!");
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
            loadDeposit(null, NOT_TERM);
        }
    }
    private void addDepositTerm() {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);;
        Session session = HibernateUtil.getSession();
        LocalDate dueDate = this.dueDate.getValue();
        LocalDate toDay = LocalDate.now();
        try {

            Deposit de = (Deposit) session.get(Deposit.class, 
                    txtDeposit.getText());
            trans = session.beginTransaction();
            // Neu ngay hom nay la ngay dao han thi moi dc gui tiep
            if(toDay.compareTo(dueDate) == 0) {
            if(de == null) {
                // Insert record
                de = new Deposit();
                de.setId(txtDeposit.getText());
                de.setAmount(Integer.parseInt(lbAmount.getText()));
                de.setDepositDate(Date.from(depositDate.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                de.setContent(txtContent.getText());
                de.setCustomer(cbCustomer.getSelectionModel().getSelectedItem());
                de.setEmployee(cbEmployee.getSelectionModel().getSelectedItem());
                de.setSavingsBook(cbSavingsBook.getSelectionModel().getSelectedItem());
                session.save(de);
                trans.commit();
           
                
                alert.setContentText("Deposit Success!");
                alert.setHeaderText(null);
                alert.show();
                updateBalance();
            }
            
            }
            if(toDay.compareTo(dueDate) != 0) {
               alert.setContentText("Can not deposit into this savings book!");
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
            
            loadDeposit(TERM, null);
        }
        
    }
    private void updateBalance() {
        // Get list InterestDetail theo ma so tiet kiem
        // UPdate balance cua list do
        String id = cbSavingsBook.getSelectionModel().getSelectedItem().getId();
        List<InterestDetail> list = getInterestDetailSavingsBook(id);
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
        int numDays = (int) DAYS.between(openDay, LocalDate.now());
        if(numDays == 0)
            numDays = 1;
        if(!list.isEmpty()) {
        for(InterestDetail in : list) {
            double balance = in.getBalance();
            double interest = in.getInterest();
            double sum = 0;
            balance += Double.parseDouble(lbAmount.getText());
            if(savingsTypeID.equals(NOT_TERM)) {
                //Công thức tính lãi cho loại tiết kiệm không kỳ hạn
                //Tiền lãi = (Số dư * Lãi suất) / 365 * Số ngày
                interest = balance * interestRate / 365 * numDays;  
            }
            else{
                //Công thức tính lãi cho loại tiết kiệm co kỳ hạn
                //Tiền lãi = Số tiền gửi x Lãi suất (%năm)/12 * số tháng gửi
                interest += balance * interestRate / 12 * term;
            }
            sum = balance + interest;
            in.setBalance(balance);
            in.setInterest(Math.round(interest * 100) / 100);
            in.setSumBalance(sum);
            session.update(in);
        }
        }
        trans.commit();
        HibernateUtil.closeSession();
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
    
    @FXML
    private void deleteDeposit(ActionEvent event) {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        Session session = HibernateUtil.getSession();
        try {
            Deposit deposit = (Deposit) session.get(Deposit.class, 
                    txtDeposit.getText());
            trans = session.beginTransaction();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Do you want to delete " + deposit.getId()+ " ?", 
                    ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(deposit != null) {
                session.delete(deposit);
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
            loadDeposit();
        }
    }

    @FXML
    private void resetDeposit(ActionEvent event) {
        txtDeposit.setText("");
        txtAmount.setText("500");
        lbAmount.setText("500000");
        txtContent.setText("");
        cbCustomer.setValue(null);
        cbEmployee.setValue(null);
        cbSavingsBook.setValue(null);
        depositDate.setValue(LocalDate.now());
        dueDate.setValue(null);
    }

    @FXML
    private void minimize(MouseEvent event) {
        window.minimizeStage(pane);
    }

    @FXML
    private void showReport(ActionEvent event) {
        showChoiceDialog();
        
        /*
        List<Deposit> dataList = this.getDeposit("");
        Session session = HibernateUtil.getSession();
        Transaction trans = session.beginTransaction();
        String printFile = null;
        

        
        File reportFile = new File("src/report/reportDeposit.jasper");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        java.util.Map params = new HashMap();
        //params.put("ReportTitle", dataSource);
        try {
            printFile = JasperFillManager.fillReportToFile(
                    reportFile.getAbsolutePath(), params, dataSource);
            if(printFile != null) {
                JasperPrintManager.printReport(printFile, true);
            }
        } catch (JRException ex) {
            System.out.println(ex.getLocalizedMessage());
            trans.rollback();
                    
        }
        finally {
            HibernateUtil.closeSession();
        }*/
        
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
        File reportFile = new File("src/report/reportDeposit.jasper");
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
