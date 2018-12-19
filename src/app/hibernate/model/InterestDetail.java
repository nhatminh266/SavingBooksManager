/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.model;
import javax.persistence.*;
/**
 *
 * @author nhatminh266
 */
@Entity
@Table(name = "chitietlai")
public class InterestDetail {
    @Id
    @Column(name = "idChiTietLai")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "tienLai")
    private double interest;
    
    @Column(name = "soDu")
    private double balance;
    
    @Column (name = "tongTien")
    private double sumBalance;
    
    @ManyToOne
    @JoinColumn(name = "sotietkiem_idSoTietKiem")
    private SavingsBook savingsBook;

    public InterestDetail() {
        
    }
    public InterestDetail(int id, double interest, double balance, SavingsBook savingsBook, double sumBalance) {
        this.id = id;
        this.interest = interest;
        this.balance = balance;
        this.savingsBook = savingsBook;
        this.sumBalance = sumBalance;
    }
       
    

    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the interest
     */
    public double getInterest() {
        return interest;
    }

    /**
     * @param interest the interest to set
     */
    public void setInterest(double interest) {
        this.interest = interest;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
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
     * @return the sumBalance
     */
    public double getSumBalance() {
        return sumBalance;
    }

    /**
     * @param sumBalance the sumBalance to set
     */
    public void setSumBalance(double sumBalance) {
        this.sumBalance = sumBalance;
    }

    
}
