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
@Table (name = "loaitietkiem")
public class SavingsType {
    @Id
    @Column (name ="idLoaiTietKiem")
    private String id;
    
    @Column (name ="loaiTietKiem")
    private String name;
    
    @Column (name ="kyHan")
    private int term;
    
    @Column (name ="laiSuat")
    private double interestRate;

    public SavingsType() {
        
    }
    @Override
    public String toString() {
        return this.name;
    }
    public SavingsType(String id, String name, int term, double interestRate) {
        this.id = id;
        this.name = name;
        this.term = term;
        this.interestRate = interestRate;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the term
     */
    public int getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(int term) {
        this.term = term;
    }

    /**
     * @return the interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
            
}
