/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.model;
import javax.persistence.*;
import org.hibernate.annotations.GeneratorType;
/**
 *
 * @author nhatminh266
 */
@Entity
@Table (name = "chucvu")
public class Position {
    @Id
    @Column (name = "idChucVu")
    private String id;
    
    @Column (name = "chucvu")
    private String name;

    public Position() {
        
    }
    public Position(String id, String name) {
        this.name = name;
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
}
