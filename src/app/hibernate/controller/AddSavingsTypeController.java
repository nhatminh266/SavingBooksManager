/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.controller;

import app.hibernate.model.Branch;
import app.hibernate.model.HibernateUtil;
import app.hibernate.model.Position;
import app.hibernate.model.SavingsType;
import app.hibernate.model.WindowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
public class AddSavingsTypeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private WindowController window = new WindowController();
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXComboBox<String> cbTerm;
    @FXML
    private JFXTextField txtSavingTypeId;
    @FXML
    private JFXComboBox<String> cbSavingType;
    @FXML
    private JFXTextField keyWordSavingType;
    @FXML
    private JFXComboBox<String> cbInterestRate;
    @FXML
    private TableColumn<SavingsType, String> colSavingsTypeID;
    @FXML
    private TableColumn<SavingsType, String> colSavingsType;
    @FXML
    private TableColumn<SavingsType, Integer> colTerm;
    @FXML
    private TableColumn<SavingsType, Double> colInterestRate;
    @FXML
    private JFXTextField txtPositionID;
    @FXML
    private JFXComboBox<String> cbPosition;
    @FXML
    private JFXTextField keyWordPosition;
    @FXML
    private TableColumn<Position, String> colPositionID;
    @FXML
    private TableColumn<Position, String> colPosition;
    @FXML
    private JFXTextField txtBranchID;
    @FXML
    private JFXComboBox<String> cbBranch;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXTextField keyWordBranch;
    @FXML
    private TableColumn<Branch, String> colBranchID;
    @FXML
    private TableColumn<Branch, String> colBranchName;
    @FXML
    private TableColumn<Branch, String> colMobile;
    @FXML
    private TableColumn<Branch, String> colAddress;
    
    @FXML
    private JFXButton btnSaveSavingType;
    @FXML
    private JFXButton btnDeleteSavingType;
    @FXML
    private TableView<SavingsType> tbSavingType;
    
    @FXML
    private JFXButton btnSavePosition;
    @FXML
    private JFXButton btnDeletePosition;
    @FXML
    private JFXButton btnSaveBranch;
    @FXML
    private JFXButton btnDeleteBranch;
    @FXML
    private TableView<Position> tbPosition;
    @FXML
    private TableView<Branch> tbBranch;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Set id toUpperCase*/
        txtBranchID.textProperty().addListener((ov, oldValue, newValue) ->{
            txtBranchID.setText(newValue.toUpperCase());
        });
        txtPositionID.textProperty().addListener((ov, oldValue, newValue) ->{
            txtPositionID.setText(newValue.toUpperCase());
        });
        txtSavingTypeId.textProperty().addListener((ov, oldValue, newValue) ->{
            txtSavingTypeId.setText(newValue.toUpperCase());
        });
        
        initCbTerm();
        initInterestRate();
        initSavingsType();
        initPosition();
        initBranch();
        btnDeleteSavingType.setDisable(true);
        btnDeletePosition.setDisable(true);
        btnDeleteBranch.setDisable(true);
        /*SavingsType*/
        loadSavingsType();
        listenerKeyWordSavingType();
        getRowTbSavingType();
        /*Branch*/
        loadBranch();
        listenerKeyWordBranch();
        getRowTbBranch();
        
        /*Position*/
        loadPosition();
        listenerKeyWordPosition();
        getRowTbPosition();
        
    } 
    
    private void initCbTerm() {
        
        cbTerm.setEditable(true);
        cbTerm.setValue("0");
        cbTerm.getItems().addAll("0", "1", "3", "6", "9", "12");
    }
    private void initInterestRate() {
        cbInterestRate.setEditable(true);
        cbInterestRate.setValue("2");
        cbInterestRate.getItems().addAll("2", "5", "6", "7", "8", "9");
    }
    private void initSavingsType() {
        cbSavingType.setEditable(true);
        cbSavingType.setValue("ST not term, deposit and withdraw anytime");
        cbSavingType.getItems().addAll("ST not term, deposit and withdraw anytime", 
                "ST term 1 month, deposit and withdraw on due date",
                "ST term 3 month, deposit and withdraw on due date",
                "ST term 6 month, deposit and withdraw on due date",
                "ST term 9 month, deposit and withdraw on due date",
                "ST term 12 month, deposit and withdraw on due date");
    }
    private void initPosition() {
        cbPosition.setEditable(true);
        cbPosition.setValue("Teller");
        cbPosition.getItems().addAll("Teller",
                "Credit Approval Officer",
                "Financial Analyst",
                "Internal Audit Officer",
                "Operations Officer",
                "Sales Executive",
                "Branch Manager");
    }
    private void initBranch() {
        cbBranch.setEditable(true);
        cbBranch.setValue("Xuyen Moc Town");
        cbBranch.getItems().addAll("Xuyen Moc Town",
                "Chau Duc Town",
                "Tan Thanh Town",
                "Long Dien Town",
                "Ba Ria Town",
                "Vung Tau Town");
    }

    private void loadSavingsType() {
        colSavingsTypeID.setCellValueFactory(new PropertyValueFactory("id"));
        colSavingsType.setCellValueFactory(new PropertyValueFactory("name"));
        colTerm.setCellValueFactory(new PropertyValueFactory("term"));
        colInterestRate.setCellValueFactory(new PropertyValueFactory("interestRate"));
        tbSavingType.setItems(getSavingsType(""));
        
    }
    private void loadBranch() {
        colBranchID.setCellValueFactory(new PropertyValueFactory("id"));
        colBranchName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        tbBranch.setItems(getBranch(""));
    }
    private void loadPosition() {
        colPositionID.setCellValueFactory(new PropertyValueFactory("id"));
        colPosition.setCellValueFactory(new PropertyValueFactory("name"));
        tbPosition.setItems(getPosition(""));
    }
    private ObservableList<Position> getPosition(String key) {
        Session session = HibernateUtil.getSession();
        List<Position> p = null;
        try {
            Criteria criteria = session.createCriteria(Position.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion id = Restrictions.ilike("id", format);
                Criterion name = Restrictions.ilike("name", format);
                criteria.add(Restrictions.or(id, name));
            }
            p = criteria.list();
        }catch(HibernateException e){
            
        }
        finally{
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(p);
    }
    private ObservableList<SavingsType> getSavingsType(String key) {
        Session session = HibernateUtil.getSession();
        List<SavingsType> st = null;
        try {
            Criteria criteria = session.createCriteria(SavingsType.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion id = Restrictions.ilike("id", format);
                Criterion name = Restrictions.ilike("name", format);
                criteria.add(Restrictions.or(id, name));
            }
            st = criteria.list();
        }catch(HibernateException e) {
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(st);
    }
    private ObservableList<Branch> getBranch(String key) {
        Session session = HibernateUtil.getSession();
        List<Branch> b = null;
        try {
            Criteria criteria = session.createCriteria(Branch.class);
            if(!key.isEmpty()) {
                String format = String.format("%%%s%%", key);
                Criterion id = Restrictions.ilike("id", format);
                Criterion name = Restrictions.ilike("name", format);
                criteria.add(Restrictions.or(id, name));
            }
            b = criteria.list();
        }catch(HibernateException e) {
            
        }
        finally {
            HibernateUtil.closeSession();
        }
        return FXCollections.observableArrayList(b);
    }
    private void listenerKeyWordSavingType() {
        keyWordSavingType.textProperty().addListener(e -> {
            tbSavingType.getItems().clear();
            tbSavingType.setItems(getSavingsType(keyWordSavingType.getText()));
        
        });
    }
      
    private void listenerKeyWordPosition() {
        keyWordPosition.textProperty().addListener(e -> {
            tbPosition.getItems().clear();
            tbPosition.setItems(getPosition(keyWordPosition.getText()));
        
        });
    }
    private void listenerKeyWordBranch() {
        keyWordBranch.textProperty().addListener(e -> {
            tbBranch.getItems().clear();
            tbBranch.setItems(getBranch(keyWordBranch.getText()));
        
        });
    }
    private void getRowTbSavingType() {
        tbSavingType.setRowFactory(e -> {
            TableRow<SavingsType> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    SavingsType st = row.getItem();
                    txtSavingTypeId.setText(st.getId());
                    cbSavingType.setValue(st.getName());
                    cbTerm.setValue(Integer.toString(st.getTerm()));
                    cbInterestRate.setValue(Double.toString(st.getInterestRate()));
                    btnDeleteSavingType.setDisable(false);
                }
            
            });
            return row;
        
        });
    }
    
    private void getRowTbBranch() {
        tbBranch.setRowFactory(e -> {
            TableRow<Branch> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    Branch b = row.getItem();
                    txtBranchID.setText(b.getId());
                    cbBranch.setValue(b.getName());
                    txtMobile.setText(b.getMobile());
                    txtAddress.setText(b.getAddress());
                    btnDeleteBranch.setDisable(false);
                }
            
            });
            return row;
        
        });
    }
    private void getRowTbPosition() {
        tbPosition.setRowFactory(e -> {
            TableRow<Position> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent ev) -> {
                if(ev.getClickCount() == 1 && !row.isEmpty()) {
                    Position b = row.getItem();
                    txtPositionID.setText(b.getId());
                    cbPosition.setValue(b.getName());
                    btnDeletePosition.setDisable(false);
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
    private void saveSavingsType(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtSavingTypeId.getText().isEmpty()) {
            alert.setContentText("Please Enter SavingType Id");
            alert.show();
            return;
        }
        if(cbSavingType.getValue().isEmpty()) {
            alert.setContentText("Please Enter SavingType");
            alert.show();
            return;
        }
        if(cbTerm.getValue() == null) {
            alert.setContentText("Please Enter Term");
            alert.show();
            return;
        }
        if(cbInterestRate.getValue() == null) {
            alert.setContentText("Please Enter InterestRate");
            alert.show();
            return;
        }
        addSavingsType();
    }

    private void addSavingsType() {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            SavingsType st = (SavingsType) session.get(SavingsType.class, 
                    txtSavingTypeId.getText());
            trans = session.beginTransaction();
            if(st == null) {
                st = new SavingsType();
                st.setId(txtSavingTypeId.getText()); 
            }
            st.setName(cbSavingType.getSelectionModel().getSelectedItem());
            st.setTerm(Integer.parseInt(cbTerm.getSelectionModel().getSelectedItem()));
            st.setInterestRate(Double.parseDouble(cbInterestRate.
                    getSelectionModel().getSelectedItem()));
            session.save(st);
            trans.commit();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Saved Success!");
            alert.setHeaderText(null);
        }catch(HibernateException | NullPointerException e) {
            alert.setContentText("Add Fail!");
            trans.rollback();
            System.out.println(e.toString());
        }
        finally{
            
            HibernateUtil.closeSession();
            alert.show();
            loadSavingsType();
        }
    }

    @FXML
    private void deleteSavingsType(ActionEvent event) {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            SavingsType st = (SavingsType) session.get(SavingsType.class, 
                    txtSavingTypeId.getText());
            trans = session.beginTransaction();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you want to delete " + st.getId() + " ?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(st != null) {
                session.delete(st);
                trans.commit();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Delete Success!");
                alert.setHeaderText(null);
                alert.show();
            }
            }
        }catch(HibernateException | NullPointerException e) {
            alert.setContentText("Delete Fail!");
            alert.show();
            trans.rollback();
            System.out.println(e.toString());
        }
        finally{
            
            HibernateUtil.closeSession();
            loadSavingsType();
        }
    }


    @FXML
    private void savePosition(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtPositionID.getText().isEmpty()) {
            alert.setContentText("Please Enter Position Id");
            alert.show();
            return;
        }
        if(cbPosition.getValue() == null) {
            alert.setContentText("Please Enter Position Name!");
            alert.show();
            return;
        }
        addPosition();
    }


    private void addPosition() {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            
            trans = session.beginTransaction();
            Position p = (Position) session.get(Position.class, txtPositionID.getText());
            if(p == null) {
                p = new Position();
                p.setId(txtPositionID.getText());
            }
            p.setName(cbPosition.getSelectionModel().getSelectedItem());
            session.save(p);
            trans.commit();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Saved Success!");
            alert.setHeaderText(null);
        }catch(HibernateException | NullPointerException e) {
            alert.setContentText("Add Fail!");
            trans.rollback();
            System.out.println(e.toString());
        }
        finally{
            
            HibernateUtil.getSession();
            alert.show();
            loadPosition();
        }
    }
    @FXML
    private void deletePosition(ActionEvent event) {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            Position p = (Position) session.get(Position.class, 
                    txtPositionID.getText());
            trans = session.beginTransaction();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you want to delete " + p.getId() + " ?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(p != null) {
                session.delete(p);
                trans.commit();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Delete Success!");
                alert.setHeaderText(null);
            }
            }
        }catch(HibernateException | NullPointerException e) {
            alert.setContentText("Delete Fail!");
            alert.show();
            trans.rollback();
            System.out.println(e.toString());
        }
        finally{
            alert.show();
            HibernateUtil.closeSession();
            loadPosition();
        }
    }


    @FXML
    private void saveBranch(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(txtBranchID.getText().isEmpty()) {
            alert.setContentText("Please Enter Branch ID!");
            alert.show();
            return;
        }
        if(cbBranch.getValue() == null) {
            alert.setContentText("Please Choose Branch Name");
            alert.show();
            return;
        }
        addBranch();
    }

    private void addBranch() {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            
            trans = session.beginTransaction();
            Branch br = (Branch) session.get(Branch.class, txtBranchID.getText());
            if(br == null) {
                br = new Branch();
                br.setId(txtBranchID.getText());
            }
            br.setName(cbBranch.getSelectionModel().getSelectedItem());
            br.setAddress(txtAddress.getText());
            br.setMobile(txtMobile.getText());
            session.save(br);
            trans.commit();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Saved Success!");
            alert.setHeaderText(null);
        }catch(HibernateException | NullPointerException e) {
            alert.setContentText("Add Fail!");
            alert.show();
            trans.rollback();
            System.out.println(e.toString());
        }
        finally{
            
            HibernateUtil.getSession();
            alert.show();
            loadBranch();
        }
    }

    @FXML
    private void deleteBranch(ActionEvent event) {
        Transaction trans = null;
        Alert alert = null;
        Session session = HibernateUtil.getSession();
        try {
            Branch br = (Branch) session.get(Branch.class, 
                    txtBranchID.getText());
            trans = session.beginTransaction();
             Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                     "Do you want to delete " + br.getId() + " ?",
                     ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES) {
            if(br != null) {
                session.delete(br);
                trans.commit();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Delete Success!");
                alert.setHeaderText(null);
            }
            }
        }catch(HibernateException | NullPointerException e) {
            alert.setContentText("Delete Fail!");
            alert.show();
            trans.rollback();
            System.out.println(e.toString());
        }
        finally{
            alert.show();
            HibernateUtil.closeSession();
            loadBranch();
        }
    }

    @FXML
    private void resetSavingsType(ActionEvent event) {
        txtSavingTypeId.setText("");
        cbSavingType.setValue(null);
        cbTerm.setValue(null);
        cbInterestRate.setValue(null);
    }

    @FXML
    private void resetPosition(ActionEvent event) {
        txtPositionID.setText("");
        cbPosition.setValue(null);
    }

    @FXML
    private void resetBranch(ActionEvent event) {
        txtBranchID.setText("");
        cbBranch.setValue(null);
        txtMobile.setText("");
        txtAddress.setText("");
    }

    @FXML
    private void minimize(MouseEvent event) {
        window.minimizeStage(pane);
    }
    
}
