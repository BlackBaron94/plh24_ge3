/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageUtils;
import com.plh24.packageEntities.Article;
import com.plh24.packageEntities.Category;
import com.plh24.packageAPI.WikiApiClient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;
/**
 * Κλάση με διάφορες χρήσιμες μεθόδους που χρησιμοποιούνται σε όλη την εφαρμογή.
 * @author Γιώργος Τσολακίδης
 * @author Παναγιώτης Σοφιανόπουλος
 */
public class Utilities {
    /**
    * Αποθηκεύει άρθρο ελέγχοντας αν το rating είναι μηδενικό ή τα comments
    * είναι άδειο String, τότε το αποθηκεύει με null σε αυτά τα πεδία.
    * Ελέγχει αν το άρθρο υπάρχει ήδη έτσι ώστε να το ενημερώσει ή να το
    * δημιουργήσει, όταν δεν υπάρχει.
    * @param title: Μοναδικός τίτλος άρθρου με τον οποίο αναζητείται το 
    * άρθρο στη Wikipedia.
    * @param rating: Βαθμολογία ενδιαφέροντος άρθρου. Αν λάβει 0 αποθηκεύει
    * null.
    * @param category: Αντικείμενο Pojo της κατηγορίας.
    * @param comments: Σχόλια χρήστη για το άρθρο. Αν λάβει κενό String ""
    * αποθηκεύει null.
    */
    public static void saveArticle(String title, Integer rating, Category category, String comments){
        
        // Έλεγχος αν το rating είναι 0, αποθηκεύει null
        if (rating == 0){
            rating = null;
        }
        // Έλεγχος αν τα comments είναι κενό String "", αποθηκεύει null
        if (comments.equals("")){
            comments = null;
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        // Try-catch block για περίπτωση αποτυχίας εγγραφής σε ΒΔ
        try {
            em.getTransaction().begin();
            // Δημιουργία NamedQuery αναζήτησης άρθρου με τίτλο
            Query findArticleByTitle = em.createNamedQuery("Article.findByTitle");
            // Περνάει ως παράμετρο αναζήτησης του Named Query τον τίτλο
            findArticleByTitle.setParameter("title", title);
            // Αρχικοποιεί τη μεταβλητή article
            Article article;
            // Try-catch block για περίπτωση αποτυχίας εύρεσης άρθρου
            try {
                /* Αποπειράται να λάβει το άρθρο από τη ΒΔ. Αν δεν το βρει,
                * κάνει handle με το catch NoResultException
                */
                article = (Article) findArticleByTitle.getSingleResult();
                // Θέτει τα νέα δεδομένα στο άρθρο
                article.setCategory(category);
                article.setComments(comments);
                article.setRating(rating);
                
            } catch (NoResultException nre) {
                /* Αν δεν βρέθηκε το άρθρο, η μεταβλητή article είναι νέο
                * άρθρο με τα ίδια πεδία
                */
                article = new Article(title, rating, category, comments);
            }
            // Αποθήκευση στη ΒΔ και commit
            em.persist(article);
            em.getTransaction().commit();
        } catch (Exception e) {
            // Αν για κάποιο λόγο αποτύχει η εγγραφή στη Β.Δ. κάνει rollback
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Κλείνει πάντα τη σύνδεση για αποφυγή προβλημάτων
            em.close();
            emf.close();
        }
    }
    
    /**
    * Μέθοδος που παίρνει το άρθρο από τη ΒΔ ή αλλιώς επιστρέφει null.
    * @param title: Μοναδικός τίτλος άρθρου για ανεύρεσή του.
    * @return Article: POJO του άρθρου που βρέθηκε ή null αν δεν υπάρχει στη ΒΔ.
    */
    public static Article getArticle(String title){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        // Try-catch block για αποτυχία επικοινωνίας με ΒΔ
        try {
            em.getTransaction().begin();
            // Ετοιμασία NamedQuery με παράμετρο το άρθρο
            Query findArticleByTitle = em.createNamedQuery("Article.findByTitle");
            findArticleByTitle.setParameter("title", title);
            // Αρχικοποίηση μεταβλητής άρθρου
            Article article;
            // Try-catch block για περίπτωση μη εύρεσής του
            try {
                article = (Article) findArticleByTitle.getSingleResult();
                // Αν το βρει θα το επιστρέψει αμέσως μετά το finally
                return article;
            } catch (NoResultException nre) {
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Κλείσιμο της σύνδεσης όπως και νά'χει.
            em.close();
            emf.close();
        }
        // Εάν δεν έχει βρει το άρθρο, επιστρέφει null εδώ.
        return null;
    }
    
    /**
     * Μέθοδος που παίρνει τις κατηγορίες της Β.Δ. και επιστρέφει Λίστα με 
     * POJOs τύπου Category.
     * @return List<Category>: Λίστα με αντικείμενα POJOs τύπου Category.
     */
    public static List<Category> getCategories() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        try {
            // Χρησιμοποιεί το NamedQuery εύρεσης όλων των κατηγοριών
            Query findAllCategories = em.createNamedQuery("Category.findAll");
            List<Category> categoryList = findAllCategories.getResultList();
            return categoryList;
        } catch (Exception e) {
            e.printStackTrace();
            // Σε περίπτωση εξαίρεσης, επιστρέφει κενή λίστα.
            return new ArrayList<>();
        } finally {
            em.close();
            emf.close();
        }
    }
    
    /**
    * Μέθοδος που αφαιρεί τα HTML Tags που εμφανίζονται στο snippet (opening & 
    * closing tags για span με class searchmatch μέσα τους)
    * @param snippet: Snippet string που επιστρέφει η αναζήτηση με keyword που 
    * περιέχει τα HTML Tags στο σημείο που βρέθηκε το keyword.
    * @return String: String του snippet χωρίς τα HTML tags.
    */
    public static String stripSnippetHTMLTags(String snippet) {
        // Pattern seeker για τα tags
        Pattern openingTagPattern = Pattern.compile("<span class=\"searchmatch\">");
        Pattern closingTagPattern = Pattern.compile("</span>");
        // Matcher που βρίσκει το pattern και το αντικαθιστά με κενό String
        Matcher openingTagMatcher = openingTagPattern.matcher(snippet);
        // Ανανέωση του snippet
        snippet = openingTagMatcher.replaceAll("");
        // Το ίδιο για το closing tag
        Matcher closingTagMatcher = closingTagPattern.matcher(snippet);
        snippet = closingTagMatcher.replaceAll("");
        return snippet;
    }
    
    /**
     * DEPRECATED TODO remove if dimitris doesn't wanna use this
     * @param jsonString 
     */
    public static void parseArticleFetch(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        JSONObject query = obj.getJSONObject("query");
        JSONArray pages = query.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (!page.has("extract")) continue;
            String cleanText = page.getString("extract");
            System.out.println(cleanText);
        }
    }
    
    /**
     * Μέθοδος που αναζητά στη Wikipedia το πλήρες άρθρο με τον τίτλο που 
     * δίνεται. Λαμβάνει και καθαρίζει το JSON που επιστρέφει η αναζήτηση του 
     * πλήρους άρθρου της Wikipedia για βελτιστοποιημένη οπτικά προβολή.
     * @param title: Τίτλος του άρθρου με βάση τον οποίο αναζητείται στη 
     * Wikipedia.
     * @return String: Βελτιστοποιημένο οπτικά άρθρο για προβολή.
     * @throws IOException: Αποτυχία επικοινωνίας με το API.
     * @throws JSONException: Αποτυχία parsing του JSON.
     */
    public static String fetchArticleCleanText(String title) throws IOException, JSONException {
        // Δημιουργία WikiApiClient για κλήση API
        WikiApiClient api = new WikiApiClient();
        // Αρχικοποίηση του String που θα επιστραφεί
        String cleanText = "";
        // Απόπειρα εύρεσης άρθρου στο API
        String jsonResponse = api.fetchArticle(title);
        /* Parsing του JSON που έχει τη μορφή:
        * query: pages: [{extract:".."}, {extract:".."}]
        */
        JSONObject obj = new JSONObject(jsonResponse);
        JSONObject query = obj.getJSONObject("query");
        JSONArray pages = query.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (!page.has("extract")) continue;
            // Προσθέτει στο cleanText το νέο extract
            cleanText += page.getString("extract");
        }
        return cleanText;
    }
    
    /**
     * Δημιουργεί τις προεπιλεγμένες κατηγορίες στη Β.Δ.
     */
    public static void initializeCategories() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        // Λίστα με τα ονόματα κατηγοριών για εύκολη τροποποίηση
        List<String> listOfNames = Arrays.asList(
            "Ιστορία",
            "Φιλοσοφία",
            "Λογοτεχνία",
            "Επιστήμες",
            "Πολιτική",
            "Τέχνη",
            "Γεωγραφία",
            "Τεχνολογία"
        );
        // Try-catch block για τυχόν προβλήματα επικοινωνίας με Β.Δ.
        try {
            em.getTransaction().begin();
            // Διατρέχει τη λίστα ονομάτων και δημιουργεί τη νέα κατηγορία
            for (String name : listOfNames) {
                Category c = new Category(name);
                em.persist(c);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Κλείνει πάντα τη σύνδεση
            em.close();
            emf.close();
        }
    }
    
    /**
     * Ελέγχει αν υπάρχουν αποθηκευμένες κατηγορίες στη Β.Δ.
     * @return boolean: True αν βρέθηκε έστω μία κατηγορία, αλλιώς false.
     */
    public static boolean categoriesExist() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        // Try-catch block για τη σύνδεση με τη Β.Δ.
        try {
            // Κάνει count για ελαφρύ query
            Query countQuery = em.createQuery("SELECT COUNT(c) FROM Category c");
            // Typecast σε Long για να μη γυρίσει object
            Long count = (Long) countQuery.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            emf.close();
        }
    }
    
    
    /**
     * Μέθοδος που δέχεται ένα ComboBox και το γεμίζει με λίστα κατηγοριών.
     * Δέχεται boolean για το αν θα προσθέσει στο ComboBox null επιλογή (π.χ.
     * ViewPanel) ή όχι (ViewByCategoryPanel)
     * @param comboBox: Το ComboBox που θα λάβει τα αντικείμενα τύπου Category
     * @param addNull: Boolean για την προσθήκη null στο ComboBox
     */
    public static void updateCategoriesComboBox(JComboBox comboBox, boolean addNull){
        List<Category> categoriesList = getCategories();
        if (addNull){
            comboBox.addItem(null);
        }
        for (Category c : categoriesList) {
            comboBox.addItem(c);
        }
    }
}
