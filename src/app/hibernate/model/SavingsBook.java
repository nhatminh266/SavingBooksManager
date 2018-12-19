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
@Table (name = "sotietkiem")
public class SavingsBook {
    @Id
    @Column(name = "idSoTietKiem")
    private String id;
    
    @Column(name = "tienGoc")
    private int amount;
    
    @Column(name = "ngayMoSo")
    @Temporal(TemporalType.DATE)
    private Date openDate;
    
    @Column(name ="ngayDaoHan", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @Column(name ="ngayTraLai")
    @Temporal(TemporalType.DATE)
    private Date interestPaid;
    
    @Column(name ="ngayTatToan")
    @Temporal(TemporalType.DATE)
    private Date closeDate;
    
    @Column(name ="tinhTrang")
    private boolean status;
    
    
    @ManyToOne
    @JoinColumn(name ="khachhang_idKhachHang")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name ="loaitietkiem_idLoaiTietKiem")
    private SavingsType savingsType;
    
    @ManyToOne
    @JoinColumn(name ="nhanvien_idNhanVien")
    private Employee employee;

    @Override
    public String toString() {
        return this.id;
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
     * @return the openDate
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the interestPaid
     */
    public Date getInterestPaid() {
        return interestPaid;
    }

    /**
     * @param interestPaid the interestPaid to set
     */
    public void setInterestPaid(Date interestPaid) {
        this.interestPaid = interestPaid;
    }

    /**
     * @return the closeDate
     */
    public Date getCloseDate() {
        return closeDate;
    }

    /**
     * @param closeDate the closeDate to set
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
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

    /**
     * @return the savingsType
     */
    public SavingsType getSavingsType() {
        return savingsType;
    }

    /**
     * @param savingsType the savingsType to set
     */
    public void setSavingsType(SavingsType savingsType) {
        this.savingsType = savingsType;
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
}
