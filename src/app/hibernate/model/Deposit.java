/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
/**
 *
 * @author nhatminh266
 */
@Entity
@Table( name = "phieugoitien")
public class Deposit implements Serializable {
    @Id
    @Column(name = "idPhieuGoi")
    private String id;
    
    @Column(name = "tienGoi")
    private int amount;
    
    @Column(name = "ngayGoi")
    @Temporal(TemporalType.DATE)
    private Date depositDate;
    
    @Column(name = "noiDung")
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "nhanvien_idNhanVien")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "sotietkiem_idSoTietKiem")
    private SavingsBook savingsBook;

    @ManyToOne
    @JoinColumn(name = "khachhang_idKhachHang")
    private Customer customer;
    public Deposit() {
        
    }
    public Deposit(String id, int amount, Date depositDate, String content, Employee employee, SavingsBook savingsBook, Customer customer) {
        this.id = id;
        this.amount = amount;
        this.depositDate = depositDate;
        this.content = content;
        this.employee = employee;
        this.savingsBook = savingsBook;
        this.customer = customer;
    }
    
    

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the depositDate
     */
    public Date getDepositDate() {
        return depositDate;
    }

    /**
     * @param depositDate the depositDate to set
     */
    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return the savingsBook
     */
    public SavingsBook getSavingsBook() {
        return savingsBook;
    }

    /**
     * @param savingsBook the savingsBook to set
     */
    public void setSavingsBook(SavingsBook savingsBook) {
        this.savingsBook = savingsBook;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
