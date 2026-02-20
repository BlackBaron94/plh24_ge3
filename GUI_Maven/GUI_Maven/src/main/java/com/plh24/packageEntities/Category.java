/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity//η κλάση είναι JPA Entity και θα αντιστοιχηθεί σε πίνακα DB


@Table(name = "category")//Ονομα του πίνακας στη MySQL είναι "category"

@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByCategoryId", query = "SELECT c FROM Category c WHERE c.categoryId = :categoryId"),
    @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")
})


public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id//Primary key του πίνακα category
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-increment
    
    
    @Column(name = "CATEGORY_ID")//Στη DB η στήλη λέγεται CATEGORY_ID
    private Integer categoryId;

    @Column(name = "CATEGORy_NAME", nullable = false, unique = true)//Όνομα κατηγορίας: not null και unique
    private String name;


    
    
    // Κατασκευαστες
    public Category() {
        // Empty, Default constructor for JPA
    }

    // Δημιουργία νέας κατηγορίας με όνομα (για DB insert)
    public Category(String name) {
        this.name = name;
    }

    
    // Getters & Setters
    

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
    
    
    // Equals, HashCode & ToString

    @Override
    public int hashCode() {
        return (categoryId != null ? categoryId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Category)) return false;
        Category other = (Category) object;
        return (this.categoryId != null || other.categoryId == null)
                && (this.categoryId == null || this.categoryId.equals(other.categoryId));
    }

    @Override
    public String toString() {
        return name; // Εμφανίζεται στο JComboBox
    }
}
