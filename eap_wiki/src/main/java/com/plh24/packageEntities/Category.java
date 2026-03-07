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


/**
 * Η κλάση Category αναπαριστά μια κατηγορία άρθρων
 * στη Βάση Δεδομένων.
 * Πολλά άρθρα μπορούν να ανήκουν στην ίδια κατηγορία ManyToOne 
 *
 * @author Αντώνιος Πιστέλας
 */

@Entity//η κλάση είναι JPA Entity και θα αντιστοιχηθεί σε πίνακα DB


@Table(name = "category")//Ονομα του πίνακας στη MySQL είναι "category"

@NamedQueries({
    
    /**
     * Επιστρoφή όλων των κατηγορίων από τη Βάση Δεδομένων.
     */
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    
    
    /**
     * Αναζητηση κατηγορίας με βάση το ID της.
     */
    @NamedQuery(name = "Category.findByCategoryId", query = "SELECT c FROM Category c WHERE c.categoryId = :categoryId"),
    
    
    /**
     * Αναζήτηση κατηγορίας με βάση το όνομα.
     */
    @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")
})


public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     * Δημιουργείται αυτόματα από τη Βάση Δεδομένων.
     */
    @Id//Primary key του πίνακα category
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-increment
    @Column(name = "CATEGORY_ID")//Στη DB η στήλη λέγεται CATEGORY_ID
    private Integer categoryId;

     /**
     * 
     * Μοναδικό όνομα κατηγορίας το οποίο δε πρέπει να είναι null.
     */
    @Column(name = "CATEGORy_NAME", nullable = false, unique = true)//Όνομα κατηγορίας: not null και unique
    private String name;


    
    
    // Κατασκευαστες
    
    
    /**
     * Προεπιλεγμένος constructor
     * Απαιτείται από το JPA framework 
     */
    
    public Category() { //default constractor για JPA
    }

    
    /** 
     * Constructor δημιουργία νέας κατηγορίας 
     * 
     * @param name όνομα της κατηγορίας 
     */
    public Category(String name) {
        this.name = name;
    }

    
    // Getters & Setters
    

    
    /**
     * Επιστροφή του μοναδικό ID της κατηγορίας.
     * @return categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Επιστρέφει όνομα της κατηγορίας.
     * @return name κατηγορίας
     */
    public String getName() {
        return name;
    }
    
    
    // Equals, HashCode & ToString

    
    /**
     * Υπολογίζει hash code με βάση το ID της κατηγορίας.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return (categoryId != null ? categoryId.hashCode() : 0);
    }

    
     /**
     * Ελέγχει με βάση το categoryId αν δύο αντικείμενα Category είναι ίσα
     * @param object αντικείμενο προς σύγκριση
     * @return true αν τα αντικείμενα είναι ίσα
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Category)) return false;
        Category other = (Category) object;
        return (this.categoryId != null || other.categoryId == null)
                && (this.categoryId == null || this.categoryId.equals(other.categoryId));
    }

    
    /**
     * Επιστρέφει το όνομα της κατηγορίας ως String.
     * @return name κατηγορίας
     */
    @Override
    public String toString() {
        return name; // Εμφανίζεται στο JComboBox
    }
}
