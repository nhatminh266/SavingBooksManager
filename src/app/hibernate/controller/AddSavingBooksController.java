/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.controller;

import app.hibernate.model.Branch;
import app.hibernate.model.Customer;
import app.hibernate.model.Employee;
import app.hibernate.model.HibernateUtil;
import app.hibernate.model.InterestDetail;
import app.hibernate.model.Position;
import app.hibernate.model.SavingsBook;
import app.hibernate.model.SavingsType;
import app.hibernate.model.WindowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * FXML Controller class
 *
 * @author nhatminh266
 */
public class AddSavingBooksController implements Initializable {

    private WindowController window = new WindowController();
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField txtSavingsBookID;
    @FXML
    private JFXComboBox<SavingsType> cbSavingsType;
    @FXML
    private JFXComboBox<Employee> cbEmployee;
    @FXML
    private JFXComboBox<Customer> cbCustomer;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXDatePicker openDate;
    @FXML
    private JFXDatePicker dueDate;
    @FXML
    private JFXTextField txtCustomerID;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtIdCard;
    @FXML
    private JFXTextField txtIssueBy;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField keyWordCustomer;
    @FXML
    private TableColumn<Customer, String> colCustomerID;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableColumn<Customer, String> colCardID;
    @FXML
    private TableColumn<Customer, Date> colIssueDate;
    @FXML
    private TableColumn<Customer, String> colIssueBy;
    @FXML
    private TableColumn<Customer, String> colMobile;
    @FXML
    private TableColumn<Customer, String> colAddress;
    @FXML
    private JFXDatePicker issueDate;
    
    
    @FXML
    private TableView<Customer> tbCustomer;
    @FXML
    private JFXButton btnSaveCustomer;
    
    @FXML
    private JFXButton btnDeleteCustomer;
    @FXML
    private JFXButton btnResetCustomer;
    @FXML
    private JFXTextField txtEmployeeID;
    @FXML
    private JFXTextField txtEmployeeName;
    @FXML
    private JFXComboBox<Position> cbPosition;
    @FXML
    private JFXTextField txtEmployeeAddress;
    @FXML
    private JFXTextField txtEmployeeMobile;
    @FXML
    private JFXComboBox<Branch> cbBranch;
    @FXML
    private JFXTextField keyWordEmployee;
    @FXML
    private JFXTextField txtCardID;
    @FXML
    private TableColumn<Employee, String> colEmployeeID;
    @FXML
    private TableColumn<Employee, String> colEmployeeName;
    @FXML
    private TableColumn<Employee, String> colEmployeeMobile;
    @FXML
    private TableColumn<Employee, Position> colPosition;
    @FXML
    private TableColumn<Employee, Branch> colBranch;
    @FXML
    private TableColumn<Employee, String> colEmployeeAddress;
    @FXML
    private JFXButton btnDeleteEmployee;
    @FXML
    private TableColumn<Employee, String> colCardIDEmployee;
    @FXML
    private TableView<Employee> tbEmployee;
    @FXML
    private JFXTextField keyWordSavingBook;
    @FXML
    private TableColumn<SavingsBook, String> colSavingsBookID;
    @FXML
    private TableColumn<SavingsBook, Customer> colSavingsBookCustomer;
    @FXML
    private TableColumn<SavingsBook, Date> colOpenDate;
    @FXML
    private TableColumn<SavingsBook, Date> colDueDate;
    @FXML
    private TableColumn<SavingsBook, SavingsType> colSavingsType;
    @FXML
    private TableColumn<SavingsBook, Double> colAmount;
    @FXML
    private TableColumn<SavingsBook, Boolean> colStatus;
    @FXML
    private JFXButton btnDeleteSavingsBook;
    @FXML
    private TableView<SavingsBook> tbSavingsBook;
    @FXML
    private TableColumn<SavingsBook, Employee> colEmployee;
    @FXML
    private Label lbAmount;
    private final String NOT_TERM = "ST00";

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Set id toUpperCase*/
        txtEmployeeID.textProperty().addListener((ov, oldValue, newValue) -> {
             txtEmployeeID.setText(newValue.toUpperCase());
        });
           
        txtCustomerID.textProperty().addListener((ov, oldValue, newValue) ->{
            txtCustomerID.setText(newValue.toUpperCase());
        });
        txtSavingsBookID.textProperty().addListener((ov, oldValue, newValue) ->{
            txtSavingsBookID.setText(newValue.toUpperCase());
        });
        
        /*Set dueDate when cbSavingsType changed*/
        cbSavingsType.valueProperty().addListener((observable, oldDate, newDate) ->{
            Date date = this.getDueDate();
            LocalDate day = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dueDate.setValue(day);
        
        });
        
        btnDeleteCustomer.setDisable(true);
        issueDate.setValue(LocalDate.now());
        /*Customer*/
        loadCustomer();
        listenerKeyWordCustomer();
        getRowTbCustomer();
        
        /*Employee*/
        loadPosition();
        loadBranch();
        loadEmployee();
        listenerKeyWordEmployee();
        getRowTbEmployee();
      
        /*Savings Book*/
        txtAmount.setText("500");
        lbAmount.setText("500000");
        openDate.setValue(LocalDate.now());
        dueDate.setValue(null);
        //loadCbEmployee();
        //loadCbCustomer();
        //loadSavingsType();
        //loadSavingsBook();
        
        getRowTbSavingsBook();
        // TODO
        listenerAmount();
        listenerKeyWordSavingsBook();
    }    

    private void loadPosition() {
        getPosition().forEach((p) -> {
            cbPosition.getItems().add(p);
        });
    }
    private void loadBranch() {
        getBranch().forEach((b) -> {
            cbBranch.getItems().add(b);
        });
    }
    private void loadCbEmployee() {
        getEmployee("").forEach((e) -> {
            cbEmployee.getItems().add(e);
        });
    }
    private void loadCbCustomer() {
        getCustomer("").forEach((c) -> {
            cbCustomer.getItems().add(c);   
        });
    }
    private void loadSavingsType() {
        getSavingsType().forEach((sv) -> {
            cbSavingsType.getItems().add(sv);
        });
    }
        
    private ObservableList<SavingsType> getSavingsType() {
        Session session = HibernateUtil.getSession();
        List<SavingsType> sv = null;
        try {
            Criteria criteria = session.createCriteria(SavingsType.class);
            sv = criteria.list();        
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(sv);
    }
        
    private ObservableList<Branch> getBranch() {
        Session session = HibernateUtil.getSession();
        List<Branch> b = null;
        try {
            Criteria criteria = session.createCriteria(Branch.class);
            b = criteria.list();        
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(b);
    }
    private ObservableList<Position> getPosition() {
        Session session = HibernateUtil.getSession();
        List<Position> p = null;
        try {
            Criteria criteria = session.createCriteria(Position.class);
            p = criteria.list();        
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(p);
    }
    private void getRowTbCustomer() {
        tbCustomer.setRowFactory(e -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    Customer customer = row.getItem();
                    txtCustomerID.setText(customer.getId());
                    txtCustomerName.setText(customer.getName());
                    txtIdCard.setText(customer.getIdCard());
                    String date = customer.getIssueDate().toString();
                    issueDate.setValue(LocalDate.parse(date));
                    txtIssueBy.setText(customer.getIssueBy());
                    txtAddress.setText(customer.getAddress());
                    txtMobile.setText(customer.getMobile());
                    btnDeleteCustomer.setDisable(false);
                }
            
            });
            return row;
        
        }); 
    }
    private void getRowTbEmployee() {
         tbEmployee.setRowFactory(e -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    Employee employee = row.getItem();
                    txtEmployeeID.setText(employee.getId());
                    txtEmployeeName.setText(employee.getName());
                    txtCardID.setText(employee.getIdCard());
                    txtEmployeeAddress.setText(employee.getAddress());
                    txtEmployeeMobile.setText(employee.getMobile());
                    cbBranch.setValue(employee.getBranch());
                    cbPosition.setValue(employee.getPosition());
                    btnDeleteCustomer.setDisable(false);
                }
            
            });
            return row;
        
        });
    }
    private void getRowTbSavingsBook() {
        tbSavingsBook.setRowFactory(e -> {
            TableRow<SavingsBook> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    SavingsBook savingsBook = row.getItem();
                    txtSavingsBookID.setText(savingsBook.getId());
                    lbAmount.setText(Integer.toString(savingsBook.getAmount()));
                    String open = savingsBook.getOpenDate().toString();
                    openDate.setValue(LocalDate.parse(open));
                    String due = savingsBook.getDueDate().toString();
                    dueDate.setValue(LocalDate.parse(due));
                    cbEmployee.setValue(savingsBook.getEmployee());
                    cbCustomer.setValue(savingsBook.getCustomer());
                    cbSavingsType.setValue(savingsBook.getSavingsType());
                    btnDeleteSavingsBook.setDisable(false);
                }
            
            });
            return row;
        
        });
    }
    @FXML
    private void closeStage(MouseEvent event) {
        window.closeStage(pane);
    }

    @FXML
    private void saveCustomer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtCustomerID.getText().isEmpty()) {
            alert.setContentText("Please Enter Customer Id!");
            alert.show();
            return;
        }
        if(txtCustomerName.getText().isEmpty()) {
            alert.setContentText("Please Enter Customer Name!");
            alert.show();
            return;
        }
        if(txtIdCard.getText().isEmpty()) {
            alert.setContentText("Please Enter Id Card or Passport!");
            alert.show();
            return;
        }
        if(txtIssueBy.getText().isEmpty()) {
            alert.setContentText("Please Enter Issued By!");
            alert.show();
            return;
        }
        
        if(txtCustomerID.getText().isEmpty()) {
            alert.setContentText("Please Enter Customer Id!");
            alert.show();
            return;
        }
         if(issueDate.getValue() == null) {
            alert.setContentText("Please Enter IssueDate!");
            alert.show();
            return;
        }
       
        addCustomer();  
    }

    private void listenerAmount() {
        txtAmount.textProperty().addListener(e -> {
            lbAmount.setText(
                    new StringBuilder(txtAmount.getText()).append("000").toString());
        });
    }
    private void listenerKeyWordCustomer() {
        keyWordCustomer.textProperty().addListener(e -> {
            tbCustomer.getItems().clear();
            tbCustomer.setItems(getCustomer(keyWordCustomer.getText()));
        
        });
    }
    private void listenerKeyWordEmployee() {
        keyWordEmployee.textProperty().addListener(e -> {
            tbEmployee.getItems().clear();
            tbEmployee.setItems(getEmployee(keyWordEmployee.getText()));
        
        });
    }
    private void listenerKeyWordSavingsBook() {
        try{
        keyWordSavingBook.textProperty().addListener(e -> {
            tbSavingsBook.getItems().clear();
            tbSavingsBook.setItems(getSavingsBook(keyWordSavingBook.getText()));
        });
        }catch(NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    private void addCustomer() {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            Customer customer = (Customer) session.get(Customer.class, 
                    txtCustomerID.getText());
            trans = session.beginTransaction();
            if(customer == null) {
                // Insert record
                customer = new Customer();
                customer.setId(txtCustomerID.getText());
            }
            customer.setIdCard(txtIdCard.getText());
            customer.setName(txtCustomerName.getText());
            customer.setIssueDate(Date.from(issueDate.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
            customer.setIssueBy(txtIssueBy.getText());
            customer.setAddress(txtAddress.getText());
            customer.setMobile(txtMobile.getText());
            session.save(customer);
            trans.commit();
           
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Saved Success!");
            alert.setHeaderText(null);
            
        }catch(HibernateException e)
        {
            alert.setContentText("Add Fail!");
            trans.rollback();
            alert.show();
        }
        finally {
            HibernateUtil.closeSession();
            alert.show();
            loadCustomer();
        }
    }
    private void loadCustomer() {
        colCustomerID.setCellValueFactory(new PropertyValueFactory("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory("name"));
        colCardID.setCellValueFactory(new PropertyValueFactory("idCard"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory("issueDate"));
        colIssueBy.setCellValueFactory(new PropertyValueFactory("issueBy"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        tbCustomer.setItems(getCustomer(""));
        
    }
    private void loadEmployee() {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory("id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory("name"));
        colCardIDEmployee.setCellValueFactory(new PropertyValueFactory("idCard"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colEmployeeMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        colBranch.setCellValueFactory(new PropertyValueFactory("branch"));
        colPosition.setCellValueFactory(new PropertyValueFactory("position"));
        tbEmployee.setItems(getEmployee(""));
    }
    private void loadSavingsBook() {
        colSavingsBookID.setCellValueFactory(new PropertyValueFactory("id"));
        colSavingsBookCustomer.setCellValueFactory(new PropertyValueFactory("customer"));
        colEmployee.setCellValueFactory(new PropertyValueFactory("employee"));
        colOpenDate.setCellValueFactory(new PropertyValueFactory("openDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory("dueDate"));
        colSavingsType.setCellValueFactory(new PropertyValueFactory("savingsType"));
        colAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));
        tbSavingsBook.setItems(getSavingsBook(""));
    }
    private ObservableList<Employee> getEmployee(String key) {
        Session session = HibernateUtil.getSession();
        List<Employee> e = null;
        try {
            
            Criteria criteria = session.createCriteria(Employee.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion name = Restrictions.ilike("name", format);
                Criterion id = Restrictions.ilike("id", format);
                Criterion idCard = Restrictions.ilike("idCard", format);
                criteria.add(Restrictions.or(name, id, idCard));
            }
            e = criteria.list();
            
                    
        }catch(HibernateException ex){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(e);
    }
    private ObservableList<Customer> getCustomer(String key) {
        Session session = HibernateUtil.getSession();
        List<Customer> c = null;
        try {
            
            Criteria criteria = session.createCriteria(Customer.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion name = Restrictions.ilike("name", format);
                Criterion id = Restrictions.ilike("id", format);
                Criterion idCard = Restrictions.ilike("idCard", format);
                criteria.add(Restrictions.or(name, id, idCard));
            }
            c = criteria.list();
            
                    
        }catch(HibernateException e){
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(c);
    }
    
    private ObservableList<SavingsBook> getSavingsBook(String key) {
        Session session = HibernateUtil.getSession();
        List<SavingsBook> sb = null;
        try {
            
            Criteria criteria = session.createCriteria(SavingsBook.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion id = Restrictions.ilike("id", format);
                //Criterion customer = Restrictions.ilike("customer", format);
                criteria.add(id);
            }
            sb = criteria.list();
            
                    
        }catch(HibernateException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(sb);
    }
    @FXML
    private void deleteCustomer(ActionEvent event) {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            Customer customer = (Customer) session.get(Customer.class, 
                    txtCustomerID.getText());
            trans = session.beginTransaction();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you want to delete " + customer.getName() + " ?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(customer != null) {
                session.delete(customer);
                trans.commit();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Delete Success!");
                alert.setHeaderText(null);
                alert.show();
            }
            else
            {
                btnDeleteCustomer.setDisable(true);
                
            }
            }
        }catch(HibernateException e)
        {
            alert.setContentText("Delete Fail!");
            alert.show();
            trans.rollback();   
        }
        finally {
            HibernateUtil.closeSession();
            
            loadCustomer();
        }
    }

    @FXML
    private void resetCustomer(ActionEvent event) {
         txtCustomerID.setText("");
         txtCustomerName.setText("");
         txtIdCard.setText("");
         issueDate.setValue(null);
         txtIssueBy.setText("");
         txtAddress.setText("");
         txtMobile.setText("");
    }

    @FXML
    private void saveEmployee(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtEmployeeID.getText().isEmpty()) {
            alert.setContentText("Please Enter Employee Id!");
            alert.show();
            return;
        }
        if(txtEmployeeName.getText().isEmpty()) {
            alert.setContentText("Please Enter Employee Name!");
            alert.show();
            return;
        }
        if(txtCardID.getText().isEmpty()) {
            alert.setContentText("Please Enter Id Card or Passport!");
            alert.show();
            return;
        }
        if(cbBranch.getValue() == null) {
            alert.setContentText("Please Enter Branch Name!");
            alert.show();
            return;
        }
        
        if(cbPosition.getValue() == null) {
            alert.setContentText("Please Enter Position!");
            alert.show();
            return;
        }
        
       
        addEmployee();
    }

    private void addEmployee() {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        Session session = HibernateUtil.getSession();
        try {
            Employee employee = (Employee) session.get(Employee.class, 
                    txtEmployeeID.getText());
            trans = session.beginTransaction();
            if(employee == null) {
                employee = new Employee();
                employee.setId(txtEmployeeID.getText());
            }
            employee.setName(txtEmployeeName.getText());
            employee.setMobile(txtEmployeeMobile.getText());
            employee.setAddress(txtEmployeeAddress.getText());
            employee.setIdCard(txtCardID.getText());
            employee.setBranch(cbBranch.getSelectionModel().getSelectedItem());
            employee.setPosition(cbPosition.getSelectionModel().getSelectedItem());
            session.save(employee);
            trans.commit();
            alert.setContentText("Saved Success!");
            
        }catch(HibernateException ex) {
            alert.setContentText("Add Fail");
            trans.rollback();
            alert.show();
        }
        finally {
            alert.show();
            HibernateUtil.closeSession();
            loadEmployee();
        }
    }
    @FXML
    private void deleteEmployee(ActionEvent event) {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        Session session = HibernateUtil.getSession();
        try {
            Employee employee = (Employee) session.get(Employee.class, 
                    txtEmployeeID.getText());
            trans = session.beginTransaction();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Do you want to delete " + employee.getName() + " ?", 
                    ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(employee != null) {
                session.delete(employee);
                trans.commit();
                alert.setContentText("Delete Success!");
                alert.show();
            }  
            }
        }catch(HibernateException ex) {
            alert.setContentText("Delete Fail");
            trans.rollback();
            alert.show();
        }
        finally {
            
            HibernateUtil.closeSession();
            loadEmployee();
        }
    }

    @FXML
    private void resetEmployee(ActionEvent event) {
        txtEmployeeID.setText("");
        txtEmployeeName.setText("");
        txtEmployeeAddress.setText("");
        txtEmployeeMobile.setText("");
        txtCardID.setText("");
        cbBranch.setValue(null);
        cbPosition.setValue(null);
    }

    @FXML
    private void saveSavingsBook(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtSavingsBookID.getText().isEmpty()) {
            alert.setContentText("Please Enter SavingsBook Id!");
            alert.show();
            return;
        }
        if(txtAmount.getText().isEmpty()) {
            alert.setContentText("Please Enter Amount Name!");
            alert.show();
            return;
        }
        if(Double.parseDouble(lbAmount.getText()) < 500000) {
            alert.setContentText("Please Enter Amount >= 500000!");
            alert.show();
            return;
        }
        if(cbEmployee.getValue() == null) {
            alert.setContentText("Please Chosse Employee!");
            alert.show();
            return;
        }
        if(cbCustomer.getValue() == null) {
            alert.setContentText("Please Chosse Customer!");
            alert.show();
            return;
        }
        if(cbSavingsType.getValue() == null) {
            alert.setContentText("Please Chosse SavingsType!");
            alert.show();
            return;
        }
        if(openDate.getValue() == null) {
            alert.setContentText("Please Chosse Open Date!");
            alert.show();
            return;
        }
        if(dueDate.getValue() == null) {
            alert.setContentText("Please Chosse Due Date!");
            alert.show();
            return;
        }
        addSavingsBook();
        addInterestDetail();
  
    }
    private void addSavingsBook() {
        
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        Session session = HibernateUtil.getSession();
        try {
            SavingsBook savingsBook = (SavingsBook) session.get(SavingsBook.class, 
                    txtSavingsBookID.getText());
            trans = session.beginTransaction();
            if(savingsBook == null) {
                savingsBook = new SavingsBook();
                savingsBook.setId(txtSavingsBookID.getText());   
            }
            savingsBook.setAmount(Integer.parseInt(lbAmount.getText()));
            savingsBook.setOpenDate(Date.from(openDate.getValue().
                    atStartOfDay(ZoneId.systemDefault()).toInstant()));
            
            savingsBook.setDueDate(Date.from(dueDate.getValue().
                    atStartOfDay(ZoneId.systemDefault()).toInstant()));
            savingsBook.setCustomer(cbCustomer.getSelectionModel().getSelectedItem());
            savingsBook.setEmployee(cbEmployee.getSelectionModel().getSelectedItem());
            savingsBook.setSavingsType(cbSavingsType.getSelectionModel().getSelectedItem());
            savingsBook.setCloseDate(null);
            savingsBook.setInterestPaid(null);
            savingsBook.setStatus(true);
            session.save(savingsBook);
            trans.commit();
            alert.setContentText("Saved Success!");
        }catch(HibernateException ex) {
            alert.setContentText("Add Fail");
            trans.rollback();
            alert.show();
            System.out.println(ex.getLocalizedMessage());
        }
        finally {
            alert.show();
            HibernateUtil.closeSession();
            loadSavingsBook();
        }
    }
    
    private int getInterestDetailId() {
        int id = 0;
        List<InterestDetail> list = 
                this.getInterestDetailSavingsBook(txtSavingsBookID.getText());
        if(!list.isEmpty()) {
        for(InterestDetail in : list) {
            id = in.getId();
        }
        }
        return id;
        
    }
    private void addInterestDetail() {
        int id = getInterestDetailId();
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        double interest = this.getInterest();
        double balance = Double.parseDouble(lbAmount.getText());
        Session session = HibernateUtil.getSession();
        
        //InterestDetail interestDetail = null;
        try {       
            trans = session.beginTransaction();
            InterestDetail interestDetail = (InterestDetail) session.get(
                    InterestDetail.class,id);
            if(interestDetail == null) {
                interestDetail = new InterestDetail();
            }
            interestDetail.setInterest(interest);
            interestDetail.setBalance(balance);
            interestDetail.setSumBalance(interest + balance);
            SavingsBook saving = (SavingsBook) session.get(SavingsBook.class, 
                        txtSavingsBookID.getText());
            interestDetail.setSavingsBook(saving);
            session.save(interestDetail);    
            trans.commit();
            //alert.setContentText("Saved Success!");
            
        }catch(HibernateException ex) {
            alert.setContentText("Add InterestDetail Fail");
            trans.rollback();
            alert.show();
        }
        finally {
            //alert.show();
            HibernateUtil.closeSession();
            
        }
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
    private double getInterest() {
        double interest = 0;
        
        Session session = HibernateUtil.getSession();
        Transaction trans = session.beginTransaction();
        SavingsType savingsType = (SavingsType) session.get(SavingsType.class,
                cbSavingsType.getValue().getId());
        
        LocalDate openDay = this.openDate.getValue();
        LocalDate toDay = LocalDate.now();
        int numDays = (int) DAYS.between(openDay, toDay);
        if(numDays == 0)
            numDays = 1;
        int term = savingsType.getTerm();
        
        int deposit = Integer.parseInt(lbAmount.getText());
        double interestRate = savingsType.getInterestRate();
        String sv = cbSavingsType.getSelectionModel().getSelectedItem().getId();
        if(sv.equals(NOT_TERM)) {
            //Công thức tính lãi cho loại tiết kiệm không kỳ hạn
            //Tiền lãi = (Số dư * Lãi suất) / 365 * Số ngày
            interest = deposit * interestRate / 365 * numDays;
            
        }
        else{
            //Công thức tính lãi cho loại tiết kiệm co kỳ hạn
            //Tiền lãi = Số tiền gửi x Lãi suất (%năm)/12 * số tháng gửi
            interest = deposit * interestRate / 12 * term;
        }
        trans.commit();
        HibernateUtil.closeSession();
        return Math.round(interest);
    }
    private Date getDueDate() {
        int num = 0;
        Session session = HibernateUtil.getSession();
        Transaction trans = session.beginTransaction();
                
        SavingsType savingsType = (SavingsType) session.get(SavingsType.class, cbSavingsType.getValue().getId());
        int term = savingsType.getTerm();
        if(term != 0) {
            num = term * 30;
        }
        else num = 365;
        Date open = (Date) Date.from(openDate.getValue().
                atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar c = Calendar.getInstance();
        c.setTime(open);
        c.add(Calendar.DAY_OF_YEAR,num);
        Date due = (Date) c.getTime();
        trans.commit();
        HibernateUtil.closeSession();
        return due;
    }
    @FXML
    private void deleteSavingsBook(ActionEvent event) {
        Transaction trans = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        Session session = HibernateUtil.getSession();
        try {
            SavingsBook savingsBook = (SavingsBook) session.get(SavingsBook.class, 
                    txtSavingsBookID.getText());
            trans = session.beginTransaction();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + savingsBook.getId() + " ?", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(savingsBook != null && !savingsBook.isStatus()) {
                session.delete(savingsBook);
                trans.commit();
                alert.setContentText("Delete Success!");
                alert.show();
            }
            else {
                alert.setContentText("This savings book is active, can not delete!");
                alert.show();
            }
            }

        }catch(HibernateException ex) {
            alert.setContentText("Delete Fail");
            trans.rollback();
            alert.show();
        }
        finally {
            
            HibernateUtil.closeSession();
            loadSavingsBook();
        }
    }

    @FXML
    private void resetSavingsBook(ActionEvent event) {
        txtSavingsBookID.setText("");
        txtAmount.setText("");
        cbCustomer.setValue(null);
        cbEmployee.setValue(null);
        cbSavingsType.setValue(null);
        dueDate.setValue(null);
    }

    @FXML
    private void refeshSavingsBook(Event event) {
        tbSavingsBook.getItems().clear();
        cbCustomer.getItems().clear();
        cbEmployee.getItems().clear();
        cbSavingsType.getItems().clear();
        loadSavingsBook();
        loadCbCustomer();
        loadCbEmployee();
        loadSavingsType();
    }

    @FXML
    private void minimize(MouseEvent event) {
        window.minimizeStage(pane);
    }

   

    
    
}
