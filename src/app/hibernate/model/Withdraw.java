/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.model;
import java.util.Date;
import javax.persistence.*;
/**
 *
 * @author nhatminh266
 */
@Entity
@Table (name = "phieuruttien")
public class Withdraw {
    @Id
    @Column (name = "idPhieuRut")
    private String id;
    
    @Column (name = "tienRut")
    private double amount;
    
    @Column (name = "ngayRut")
    @Temporal(TemporalType.DATE)
    private Date withdrawDate;
    
    @Column (name = "noiDung")
    private String content;
    
    @ManyToOne
    @JoinColumn (name = "sotietkiem_idSoTietKiem")
    private SavingsBook savingsBook;
    
    @ManyToOne
    @JoinColumn (name = "nhanvien_idNhanVien")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn (name = "khachhang_idKhachHang")
    private Customer customer;

    public Withdraw(String id, double amount, Date withdrawDate, String content, 
            SavingsBook savingsBook, Employee employee, Customer customer) {
        this.id = id;
        this.amount = amount;
        this.withdrawDate = withdrawDate;
        this.content = content;
        this.savingsBook = savingsBook;
        this.employee = employee;
        this.customer = customer;
    }
    public Withdraw() {
        
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
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the withdrawDate
     */
    public Date getWithdrawDate() {
        return withdrawDate;
    }

    /**
     * @param withdrawDate the withdrawDate to set
     */
    public void setWithdrawDate(Date withdrawDate) {
        this.withdrawDate = withdrawDate;
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
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
