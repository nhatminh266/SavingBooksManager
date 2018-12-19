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
@Table (name ="khachhang")
public class Customer {
    @Id
    @Column (name ="idKhachHang")
    private String id;
    
    @Column (name ="hoTen")
    private String name;
    
    @Column (name ="cmnd")
    private String idCard;
    
    @Column (name ="ngayCap")
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    
    @Column (name ="noiCap")
    private String issueBy;
    
    @Column (name ="diaChi")
    private String address;
    
    @Column (name ="dienThoai")
    private String mobile;

    public Customer() {
        
    }
    public Customer(String id, String name, String cmnd, 
            Date issueDate, String issueBy, String address, String mobile) {
        this.id = id;
        this.name = name;
        this.idCard = cmnd;
        this.issueDate = issueDate;
        this.issueBy = issueBy;
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
     * @return the idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard the idCard to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return the issueDate
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * @return the issueBy
     */
    public String getIssueBy() {
        return issueBy;
    }

    /**
     * @param issueBy the issueBy to set
     */
    public void setIssueBy(String issueBy) {
        this.issueBy = issueBy;
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
