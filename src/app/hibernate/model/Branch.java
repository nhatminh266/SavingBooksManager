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
@Table (name = "chinhanh")
public class Branch {
    @Id
    @Column (name = "idChiNhanh")
    private String id;
    
    @Column (name = "tenChiNhanh")
    private String name;
    
    @Column (name = "diaChi")
    private String address;
    
    @Column (name = "dienThoai")
    private String mobile;

    public Branch() {
        
    }
    public Branch(String id, String name, String address, String mobile) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return this.name;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    
    
}
