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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.time.LocalDateTime;




/**
 * Η κλάση Article δείχνει ένα άρθρο στη Βάση Δεδομένων
 * Σύνδεση με Category ManyToOne.
 * @author Αντώνιος Πιστέλας
 */


@Entity
@Table(name = "article")

@NamedQueries({
    @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title")
})

public class Article {


    /**
     *
     * Primary Key
     * Δημιουργείται αυτόματα στη Βάση Δεδομένων.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")//Στη DB η στήλη λέγεται ARTICLE_ID
    private Long article_id;  //primary key auto generated

    /**
     * Τίτλος άρθρου.
     * μοναδικός,δεν μπορεί να είναι null.
     */
    @Column(nullable = false, unique = true)
    private String title; //Δε μπορεί να είναι null


    /**
     * Βαθμολογια άρθρου.
     * Μπορεί να είναι null.
     */

    @Column(nullable = true)
    private Integer rating;


    /**
     * Ημερομηνία και ώρα αποθηκευσης του άρθρου
     */
    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt; //Ημερομηνία δημιουργίας

    /**
     * Κατηγορία σπου ανήκει το άρθρο.
     * Σχέση ManyToOne με Category
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category; //Πρέπει να υπάρχει κατηγορία


    /**
     * Σχόλια για το άρθρο
     */
    @Column(name = "comments", nullable = true)
    private String comments;
    // Κατασκευαστες


    public Article() { //default constractor για JPA
    }

    /**
     * Constructor δημιουργίας άρθρου με βαθμολογία.
     *
     *
     * @param title τίτλος άρθρου
     * @param rating βαθμολογία άρθρου
     * @param category κατηγορία άρθρου
     * @param comments σχόλια άρθρου
     */
    public Article(String title, int rating, Category category, String comments) {
        this.title = title;
        this.rating = rating;
        this.category = category;
        this.savedAt = LocalDateTime.now();//η τρέχουσα ώρα και ημερομηνία
        this.comments = comments;
    }

      /**
     * Constructor δημιουργίας άρθρου χωρίς βαθμολογία.
     *
     *
     * @param title τίτλος άρθρου
     * @param category κατηγορία άρθρου
     * @param comments σχόλια άρθρου
     */

    public Article(String title, Category category, String comments) {
        this.title = title;
        this.rating = null;
        this.category = category;
        this.savedAt = LocalDateTime.now();
        this.comments = comments; // μπορεί να είναι null
    }

    // Getters


    /**
     * @return μοναδικό ID άρθρου
     */
    public Long getId() {
        return article_id;
    }


     /**
     * @return title τίτλο άρθρου.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return rating βαθμολογία άρθρου
     */
    public Integer getRating(){
        return rating;
    }

     /**
     * @return savedAt ημερομηνία αποθήκευσης του άθρου.
     */
    public LocalDateTime getSavedAt() {
        return savedAt;
    }


    /**
     * @return category κατηγορία του άρθρου
     */
    public Category getCategory() {
        return category;
    }


    /**
     *
     * @return category όνομα κατηγορίας, κενό string αν δεν υπάρχει
     */
    public String getCategoryName() {
        return category != null ? category.getName() : "";
    }

    /**
     * @return comments σχόλια άρθρου
     */
    public String getComments() {
        return comments;
    }



    // Setters


    /**
     * βαζει βαθμολογία στο άρθρο
     * @param rating  βαθμολογία του άθρου
     */
    public void setRating(Integer rating) {
        this.rating = rating;
}


    /**
     *
     * @param category νέα κατηγορία
     */
    public void setCategory(Category category) {
        this.category = category;
    }


    /**

     * @param comments νέα σχόλια
     */
    public void setComments(String comments) {
        this.comments = comments;
    }


    // Equals, hashCode, toString



    /**
     * @return hash code υπολοσγισμός hash code βάση το ID του άρθρου.
     */

    @Override
    public int hashCode() {
        return (article_id != null ? article_id.hashCode() : 0);
    }


    /**
     * Ελέγχει αν δύο άρθρα είναι ίσα βάση του ID.
     * @param object αντικείμενο προς σύγκριση
     * @return true αν είναι το ίδιο άρθρο
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Article)) return false;
        Article other = (Article) object;
        return (this.article_id != null || other.article_id == null) && (this.article_id == null || this.article_id.equals(other.article_id));
    }


 /**
     * @return τίτλος άρθρου ως String
     */
    @Override
    public String toString() {
        return title;
    }
}
