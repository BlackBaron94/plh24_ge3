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
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Η κλάση αναπαριστά μία καταγραφή αναζήτησης που πραγματοποιείται στην εφαρμογη.
 * @author Αντώνιος Πιστέλας
 */

@Entity
@Table(name = "search_log")//Αυτή η κλάση αντιστοιχεί στον πίνακα search_log
public class SearchLog implements Serializable {
    // Καλή πρακτική όταν κάνουμε implement το Serializable
    
    
    private static final long serialVersionUID = 1L;

    
 /**Primary Key 
 * Δημιουργείται αυτόματα από τη Βάση Δεδομένων με auto-increment.
 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Primary key (id) με auto-increment από τη MySQL
    @Column(name = "search_id")
    private Long search_id;

     /**
     * Ο όρος αναζήτησης που εισήγαγε ο χρήστης, δεν μπορεί να είναι null
     */
    @Column(name = "search_term",nullable = false)//κάθε log πρέπει να έχει όρο αναζήτησης
    private String searchTerm;

    /**
     * Η ημερομηνία και ώρα που έγινε η αναζήτηση.
     */
    @Column(name = "searched_at", nullable = false)//Αποθηκεύση πότε έγινε η αναζήτηση. 
                           //Το όνομα της στήλης θα είναι searched_at στη ΒΔ.
    private LocalDateTime searchedAt;

    
  /**
     * Προεπιλεγμένος constructor
     * Απαιτείται από το JPA framework 
     */
    public SearchLog() {}//Default constructor που απαιτείται από JPA

    
    /**
     * Constructor δημιουργίας νέας εγγραφής αναζήτησης.
     *
     * @param searchTerm η αναζήτηση του χρήστη
     */
    
    public SearchLog(String searchTerm) {
        this.searchTerm = searchTerm;
        this.searchedAt = LocalDateTime.now();//αυτόματα timestamp την ώρα που φτιάχνεται
    }

    // Getters
    
    /**
     * Επιστρέφει το μοναδικό αναγνωριστικό της εγγραφής αναζήτησης.
     * @return search_id τοID της εγγραφής
     */
    public Long getId() { 
        return search_id; 
    }
    
    
     /**
     * Επιστρέφει την αναζήτηση που καταγράφηκε.
     * @return searchTerm
     */
    public String getSearchTerm() {
        return searchTerm; 
    }
   
}