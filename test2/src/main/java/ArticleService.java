

import jakarta.persistence.*;//για να κάνουμε χρήση των κλάσεων του JPA
import java.util.List;//επειδή έχουμε επιστροφή λίστας αποτελεσμάτων



public class ArticleService {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("WikiPU");
    // Δημιουργία ενος Entity Manager Factory,που διαβάζει το persistence.xml, βρίσκει 
    // το persistence unit με ονομα WikiPU και κάνει το εργαλείο που μπορεί να φτιαξει
    // Entity Manager
    
    public void saveWikipediaArticle(int pageId, String title, String snippet, int rating, String categoryName) {
    //Μέθοδος που αποθηκεύει το αθρο απο Wikipedia,rating, categoryName άπό χρήστη
    //pageId, title, snippet από API
        EntityManager em = emf.createEntityManager(); //Δημιουργία Entity Manager
        EntityTransaction t = em.getTransaction();//Παιρνουμε το transaction  για να μπορούμε να κανουμε 
                        //ενέργειες στη ΒΔ(insert,remove)

                        
                        
        try {
            t.begin();//ξεκίνημα transaction

            
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.name = :name", Category.class);
            query.setParameter("name", categoryName);
            //query με JPQL πάνω σε entities (κλάση entity:Category και c.name:πεδίο της κλάσης
            //ψαχνουμε τη κατηγορία όπου name=categoryName
            
            List<Category> results = query.getResultList();//αποτέλεσμα είναι λίστα
            Category category;//δηλωση μεταβλητής
            
            if (results.isEmpty()) {
                // Αν δεν υπάρχει κατηγορία
                category = new Category(categoryName);//θα τη φτιάξουμε
                em.persist(category);//αποθηκεύουμε τη κατηγορία που φτιάξαμε πιο πανω στη ΒΔ
            } else {//Αν η κατηγορία υπάρχει
                category = results.get(0);//παιρνουμε τη πρώτη κατηγορία απο τη λίστα
            }

            
            Article article = new Article(pageId, title, snippet, rating, category);//Δημιουργία
                //νέου αντικειμένου,Article το οποίο συνδέεται με το αντικείμενο category
            
            em.persist(article);//αποθήκευση στη ΒΔ

            t.commit();//μόνιμη αποθήκευση στη ΒΔ
            
            
            System.out.println("Το άρθρο '" + title + "' αποθηκεύτηκε επιτυχώς!");
            
            
            
        } catch (Exception e) {//αν έχουμε εξαίρεση
            if (t.isActive()) t.rollback();//αν υπάρχει ενεργό transaction
                //ότι καναμε από το t.begin και μετά ακυρωσέ το
            e.printStackTrace();//εκτυπώνει το σφάλμα
        } finally {
            em.close();//κλείσιμο του Entity Manager
        }
    }
    
    
    ///////////////////////////////////////////////////
    ///ΓΙΑ΄ΕΛΕΓΧΟ ΛΕΙΤΟΥΡΓΙΑΣ ΤΟ ΠΑΡΑΚΑΤΩ
    
    public void printAllArticles() {
    EntityManager em = emf.createEntityManager();
    try {
        // JPQL ερώτημα: "Φέρε μου όλα τα αντικείμενα Article"
        List<Article> articles = em.createQuery("SELECT a FROM Article a", Article.class).getResultList();

        if (articles.isEmpty()) {
            System.out.println("H BASI EINAI ADEIA"); //Το εγραψα με αγγλικους χαρακτηρες για να δω αν το προβλημα ειναι στα ελληνικα
        } else {
            System.out.println("--- Λίστα Άρθρων στη Βάση ---");
            for (Article a : articles) {
                System.out.println("ID: " + a.getPageId() + " | Τίτλος: " + a.getTitle());
            }
        }
    } finally {
        em.close();
    }
}
    
    
    //////////////////////////////////////////////////////////
    //// 
    
    
    
    
}