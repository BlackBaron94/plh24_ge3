/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.plh24.packageController;

import com.plh24.packageEntities.Category;
import java.util.List;
import java.util.Set;

/**
 * Συμβόλαιο [contract] για αναζήτηση άρθρων.
 * <p>
 * Ορίζει τις λειτουργίες:
 * <ul>
 *   <li>Εκτέλεση αναζήτησης με keyword (επιστρέφει αποτελέσματα από ΒΔ και Wikipedia).</li>
 *   <li>Καταγραφή όρου αναζήτησης (SearchLog).</li>
 *   <li>Αναζήτηση τίτλων αποθηκευμένων άρθρων στη ΒΔ.</li>
 *   <li>Αποθήκευση άρθρου (αν δεν υπάρχει ήδη) με επιλεγμένη κατηγορία.</li>
 * </ul>
 *
 *
 * @author Dimitrios Korolis: ορισμός interface + records για αποτελέσματα αναζήτησης.
 */


public interface SearchController {
    
 /**
 * DTO [Data Transfer Object: Αντικείμενο Μεταφοράς Δεδομένων] για ένα αποτέλεσμα από Wikipedia.
 * <p>
 * Περιλαμβάνει τον τίτλο του άρθρου, τον αριθμό λέξεων και ένα σύντομο απόσπασμα (snippet).
 * 
 *
 */
   
    record WikiHit(String title, int wordCount, String snippet) {}
    
 
    
    /**
 * DTO [Data Transfer Object: Αντικείμενο Μεταφοράς Δεδομένων] για το συνολικό αποτέλεσμα αναζήτησης.
 * <p>
 * Συνδυάζει:
 * <ul>
 *   <li>{@code dbTitles}: τίτλους αποθηκευμένων άρθρων από τη ΒΔ που ταιριάζουν στο keyword.</li>
 *   <li>{@code wikiHits}: αποτελέσματα από την Wikipedia (τίτλος/wordCount/snippet).</li>
 * </ul>
 * 
 *
 */

    record SearchResults(Set<String> dbTitles, List<WikiHit> wikiHits) {}
    
    
/**
 * Εκτελεί αναζήτηση με βάση το keyword.
 * <p>
 * Η υλοποίηση συνήθως:
 * <ul>
 *   <li>Καταγράφει το keyword στο SearchLog.</li>
 *   <li>Αναζητά τίτλους αποθηκευμένων άρθρων στη ΒΔ.</li>
 *   <li>Κάνει αναζήτηση στην Wikipedia και επιστρέφει hits.</li>
 * </ul>
 * 
 *
 * @param keyword ο όρος αναζήτησης που εισάγει ο χρήστης.
 * @return συνδυασμένο αποτέλεσμα αναζήτησης (ΒΔ + Wikipedia).
 */    
    SearchResults runSearch(String keyword);

    
    
/**
 * Καταγράφει έναν όρο αναζήτησης για στατιστική ανάλυση.
 * <p>
 * Η υλοποίηση αποθηκεύει συνήθως εγγραφή {@code SearchLog} (keyword + timestamp) στη ΒΔ.
 * 
 *
 * @param keyword ο όρος αναζήτησης που καταγράφεται.
 */    
    void logSearchEvent(String keyword);

    
/**
 * Αναζητά στη ΒΔ τίτλους αποθηκευμένων άρθρων που ταιριάζουν με το keyword.
 * <p>
 * Η αντιστοίχιση είναι συνήθως με {@code LIKE} πάνω στον τίτλο (case-insensitive) και επιστρέφει
 * μοναδικούς τίτλους σε {@link java.util.Set}.
 *
 *
 * @param keyword ο όρος αναζήτησης.
 * @return σύνολο τίτλων από αποθηκευμένα άρθρα που ταιριάζουν.
 */    
    Set<String> searchSavedArticleTitles(String keyword);
    

/**
 * Αποθηκεύει άρθρο στη ΒΔ μόνο αν δεν υπάρχει ήδη με τον ίδιο τίτλο.
 * <p>
 * Η υλοποίηση συνήθως:
 * <ul>
 *   <li>Ελέγχει ύπαρξη άρθρου με {@code title}.</li>
 *   <li>Αν δεν υπάρχει, δημιουργεί νέο {@code Article} με {@code rating=null} και {@code comments=null}.</li>
 *   <li>Συσχετίζει το άρθρο με την επιλεγμένη {@link com.plh24.packageEntities.Category}.</li>
 * </ul>
 *
 * @param title ο μοναδικός τίτλος άρθρου.
 * @param category η επιλεγμένη κατηγορία αποθήκευσης (δεν πρέπει να είναι null).
 * @return {@code true} αν έγινε νέα αποθήκευση, {@code false} αν υπήρχε ήδη.
 * @throws IllegalArgumentException αν το {@code category} είναι null (στην υλοποίηση).
 */    
    boolean saveDefaultArticleIfNotExists(String title, Category category);
}