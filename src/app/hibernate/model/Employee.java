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
@Table(name = "nhanvien")
public class Employee {
    @Id
    @Column (name = "idNhanVien")
    private String id;
    
    @Column (name = "hoTen")
    private String name;
    
    
    @Column (name = "cmnd")
    private String idCard;
    
    @Column (name = "diaChi")
    private String address;
    
    @Column (name = "dienThoai")
    private String mobile;
    
    @ManyToOne
    @JoinColumn(name = "chucvu_idChucVu" )
    private Position position;
    
    @ManyToOne
    @JoinColumn(name = "chinhanh_idChiNhanh" )
    private Branch branch;
    

    /**
     * @return the id
     */
    public Employee() {
        
    }
    public Employee(String id, String name, String idCard, String address, 
            String mobile, Position position, Branch branch) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.address = address;
        this.mobile = mobile;
        this.position = position;
        this.branch = branch;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name;
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

    /**
     * @return the idPosition
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position
     
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the idBranch
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * @param branch
     
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
