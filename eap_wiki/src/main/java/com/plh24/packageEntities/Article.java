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
import jakarta.persistence.*;
import java.time.LocalDateTime;




/**
 * Η κλάση Article δείχνει ένα άρθρο στη ΒΔ
 * Σύνδεση με Category ManyToOne.
 * @author Αντώνης Πιστέλας
 */


@Entity
@Table(name = "article")

@NamedQueries({
    @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title")
})

public class Article {

    
    //Πεδία
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")//Στη DB η στήλη λέγεται ARTICLE_ID
    private Long article_id;  //primary key auto generated

    @Column(nullable = false, unique = true)
    private String title; //Δε μπορεί να είναι null

    @Column(nullable = true)
    private Integer rating;

    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt; //Ημερομηνία δημιουργίας

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category; //Πρέπει να υπάρχει κατηγορία

    @Column(name = "comments", nullable = true)
    private String comments;
    // Κατασκευαστες
    
    
    public Article() { //default constractor για JPA
    }
    
    public Article(String title, Integer rating, Category category, String comments) {
        this.title = title;
        this.rating = rating;
        this.category = category;
        this.savedAt = LocalDateTime.now();//η τρέχουσα ώρα και ημερομηνία
        this.comments = comments;
    }
    public Article(String title, Category category, String comments) {
        this.title = title;
        this.rating = null;
        this.category = category;
        this.savedAt = LocalDateTime.now();
        this.comments = comments; // μπορεί να είναι null
    }
   
    // Getters
    public Long getId() {
        return article_id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getRating(){
        return rating;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        return category != null ? category.getName() : "";
    }
    
    public String getComments() {
        return comments;
    }

    
    
    // Setters
    public void setRating(Integer rating) {
        this.rating = rating;
}

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

  
    // Equals, hashCode, toString
    @Override
    public int hashCode() {
        return (article_id != null ? article_id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Article)) return false;
        Article other = (Article) object;
        return (this.article_id != null || other.article_id == null) && (this.article_id == null || this.article_id.equals(other.article_id));
    }

    @Override
    public String toString() {
        return title;
    }
}
