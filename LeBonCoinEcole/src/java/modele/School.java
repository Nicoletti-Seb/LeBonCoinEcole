/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class School implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String name;
    
    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Address address;
    
    @OneToMany(mappedBy = "school")
    private List<Student> students;
    
    public School() {
        
    }
    
    public School(String name, Address address) {
        this.name = name;
        this.address = address;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Student> getStudents() {
        return students;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof School)) {
            return false;
        }
        School other = (School) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.address.toString() + ")";
    }
}
