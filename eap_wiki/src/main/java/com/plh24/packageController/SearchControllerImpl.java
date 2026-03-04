/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageController;

import com.plh24.packageAPI.WikiApiClient;
import com.plh24.packageEntities.Article;
import com.plh24.packageEntities.Category;
import com.plh24.packageEntities.SearchLog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.plh24.packageUtils.Utilities.stripSnippetHTMLTags;

/**
 * Υλοποίηση [implementation] του {@link com.plh24.packageController.SearchController}.
 * <p>
 * Συντονίζει:
 * <ul>
 *   <li>Καταγραφή όρων αναζήτησης στη ΒΔ (SearchLog).</li>
 *   <li>Αναζήτηση αποθηκευμένων τίτλων στη ΒΔ (Article).</li>
 *   <li>Αναζήτηση στην Wikipedia μέσω {@link com.plh24.packageAPI.WikiApiClient}.</li>
 *   <li>Αποθήκευση άρθρου στη ΒΔ με επιλεγμένη {@link com.plh24.packageEntities.Category}.</li>
 * </ul>
 * 
 * <p>
 * Χρησιμοποιεί JPA (EntityManager/EntityManagerFactory) για DB operations.
 * </p>
 *
 * υλοποίηση controller layer (DB + API) και parsing αποτελεσμάτων.
 */
public class SearchControllerImpl implements SearchController {   
    
 /**
 * Επιστρέφει το κοινό {@link jakarta.persistence.EntityManagerFactory} της εφαρμογής.
 * <p>
 * Χρησιμοποιεί το {@link com.plh24.packageUtils.Utilities#getEMF()} ώστε ο controller
 * να δημιουργεί {@link jakarta.persistence.EntityManager} για DB operations.
 * </p>
 *
 * @return το EntityManagerFactory (ή null αν δεν είναι διαθέσιμο σε design-time).
 */
    private static synchronized EntityManagerFactory getEMF() {
        return com.plh24.packageUtils.Utilities.getEMF();
    }

    
 /**
 * Client για επικοινωνία με Wikipedia API (search + fetch article).
 *
 */
    private final WikiApiClient api = new WikiApiClient();
    
    
    
/**
 * Εκτελεί την ενιαία αναζήτηση (ΒΔ + Wikipedia) για ένα keyword.
 * <p>
 * Ροή:
 * <ul>
 *   <li>Καταγράφει το keyword στη ΒΔ μέσω {@link #logSearchEvent(String)}.</li>
 *   <li>Αναζητά τίτλους αποθηκευμένων άρθρων στη ΒΔ μέσω {@link #searchSavedArticleTitles(String)}.</li>
 *   <li>Κάνει κλήση στο Wikipedia Search API μέσω {@link com.plh24.packageAPI.WikiApiClient#searchWikipedia(String)}.</li>
 *   <li>Κάνει parsing των JSON αποτελεσμάτων σε λίστα {@link com.plh24.packageController.SearchController.WikiHit}.</li>
 * </ul>
 * 
 * <p>
 * Σε περίπτωση αποτυχίας επικοινωνίας με Wikipedia API, επιστρέφει τουλάχιστον τα αποτελέσματα της ΒΔ.
 * </p>
 * @param keyword ο όρος αναζήτησης.
 * @return {@link com.plh24.packageController.SearchController.SearchResults} με τίτλους από ΒΔ και hits από Wikipedia.
 */
    @Override
    public SearchResults runSearch(String keyword) {
        logSearchEvent(keyword);

        Set<String> dbTitles = searchSavedArticleTitles(keyword);

        String wikiJson;
        try {
            wikiJson = api.searchWikipedia(keyword);
        } catch (Exception ex) {
            // αν “πέσει” το API, δίνουμε τουλάχιστον τα DB αποτελέσματα
            return new SearchResults(dbTitles, List.of());
        }

        List<WikiHit> hits = parseWikiHits(wikiJson);
        return new SearchResults(dbTitles, hits);
    }
/**
 * Αποθηκεύει το keyword στο SearchLog για στατιστικά.
 * <p>
 * Δημιουργεί {@link com.plh24.packageEntities.SearchLog} και κάνει persist μέσα σε transaction.
 * </p>
 *
 * @param keyword ο όρος αναζήτησης προς καταγραφή.
 * @throws RuntimeException αν αποτύχει η εγγραφή (η υλοποίηση κάνει rollback και ξαναπετάει την εξαίρεση).
 */
    @Override
    public void logSearchEvent(String keyword) {
        EntityManagerFactory emf = getEMF();
        if (emf == null) return;

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new SearchLog(keyword));
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
/**
 * Αναζητά στη ΒΔ τίτλους άρθρων που περιέχουν το keyword.
 * <p>
 * Εκτελεί JPQL query με {@code LIKE} στον τίτλο (case-insensitive) και επιστρέφει
 * {@link java.util.LinkedHashSet} ώστε να διατηρείται η σειρά ταξινόμησης ({@code ORDER BY a.title}).
 * </p>
 *
 * @param keyword ο όρος αναζήτησης.
 * @return σύνολο τίτλων άρθρων από τη ΒΔ που ταιριάζουν.
 */
    @Override
    public Set<String> searchSavedArticleTitles(String keyword) {
        EntityManagerFactory emf = getEMF();
        if (emf == null) return new LinkedHashSet<>();

        EntityManager em = emf.createEntityManager();
        try {
            List<String> titles = em.createQuery(
                    "SELECT a.title FROM Article a " +
                    "WHERE UPPER(a.title) LIKE :kw ORDER BY a.title",
                    String.class
            ).setParameter("kw", "%" + keyword.toUpperCase() + "%")
             .getResultList();

            return new LinkedHashSet<>(titles);
        } finally {
            em.close();
        }
    }

    /**
     * ΝΕΟ: Αποθήκευση άρθρου με κατηγορία που επιλέγει ο χρήστης.
     */
    /**
 * Αποθηκεύει νέο άρθρο με επιλεγμένη κατηγορία, μόνο αν δεν υπάρχει ήδη στη ΒΔ.
 * <p>
 * Βήματα:
 * <ul>
 *   <li>Ελέγχει ότι το {@code category} δεν είναι null.</li>
 *   <li>Κάνει {@code COUNT} για να διαπιστώσει αν υπάρχει ήδη άρθρο με τον ίδιο τίτλο.</li>
 *   <li>Αν δεν υπάρχει, ανοίγει transaction και κάνει persist νέο {@link com.plh24.packageEntities.Article}.</li>
 *   <li>Αν το {@code category} είναι detached, το κάνει managed με {@code em.merge(category)}.</li>
 * </ul>
 * 
 *
 * @param title ο μοναδικός τίτλος άρθρου.
 * @param category η κατηγορία αποθήκευσης (απαραίτητη).
 * @return {@code true} αν αποθηκεύτηκε νέο άρθρο, {@code false} αν υπήρχε ήδη.
 * @throws IllegalArgumentException αν {@code category} είναι null.
 * @throws RuntimeException αν αποτύχει η αποθήκευση (η υλοποίηση κάνει rollback και ξαναπετάει την εξαίρεση).
 */
    @Override
    public boolean saveDefaultArticleIfNotExists(String title, Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }

        EntityManagerFactory emf = getEMF();
        if (emf == null) return false;

        EntityManager em = emf.createEntityManager();
        try {
            Long cnt = em.createQuery(
                    "SELECT COUNT(a) FROM Article a WHERE a.title = :t",
                    Long.class
            ).setParameter("t", title)
             .getSingleResult();

            if (cnt != null && cnt > 0) return false;

            em.getTransaction().begin();

            // Αν το Category από το JComboBox είναι detached, το κάνουμε managed:
            Category managedCat = em.contains(category) ? category : em.merge(category);

            Article a = new Article(title, managedCat, null); // rating=null, comments=null
            em.persist(a);

            em.getTransaction().commit();
            return true;

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

 /**
 * Κάνει parsing του JSON που επιστρέφει το Wikipedia Search API σε λίστα {@link WikiHit}.
 * <p>
 * Διαβάζει το μονοπάτι {@code query.search[]} και για κάθε στοιχείο εξάγει:
 * {@code title}, {@code wordcount}, {@code snippet}. Το snippet καθαρίζεται από HTML tags
 * μέσω {@link com.plh24.packageUtils.Utilities#stripSnippetHTMLTags(String)}.
 * </p>
 *
 * @param wikiJson raw JSON από {@link com.plh24.packageAPI.WikiApiClient#searchWikipedia(String)}.
 * @return λίστα με {@link WikiHit} έτοιμα για εμφάνιση στο UI.
 * @throws org.json.JSONException αν το JSON δεν έχει την αναμενόμενη δομή.
 */
    
    private static List<WikiHit> parseWikiHits(String wikiJson) {
        JSONObject obj = new JSONObject(wikiJson);
        JSONObject query = obj.getJSONObject("query");
        JSONArray searchResults = query.getJSONArray("search");

        java.util.ArrayList<WikiHit> list = new java.util.ArrayList<>();
        for (int i = 0; i < searchResults.length(); i++) {
            JSONObject item = searchResults.getJSONObject(i);
            String title = item.getString("title");
            int wordCount = item.getInt("wordcount");
            String snippet = stripSnippetHTMLTags(item.getString("snippet"));
            list.add(new WikiHit(title, wordCount, snippet));
        }
        return list;
    }
}