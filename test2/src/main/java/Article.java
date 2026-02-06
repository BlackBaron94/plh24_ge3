

import jakarta.persistence.*;// Για να μπορουμε να χρησιμοποιούμε τα
                //annotations του JPA
import java.time.LocalDateTime;//για αποθήκευση ημερομηνίας και ώρας



@Entity//Αυτή η κλάση είναι table στη ΒΔ τη φτιάχνει το Hibernate   
@Table(name = "article")//όνομα του πίνακα στη ΒΔ



public class Article {

   
    @Id //το επόμενο θα είναι primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//το id θα
            //δημιουργήται μόνο του με αυξηση για να έχουμε μοναδικότητα, το 
            //id για τις σχεσεις στη ΒΔ

    
    private Long id;//στα id βάζουμε Long

   
    @Column(name = "page_id", nullable = false, unique = true)//το id της
            //ΒΔ μας είναι ιδιότητα,πεδίο, δεν μπορεί να είναι κενη και είναι
            //μοναδική
    
    private int pageId;

    
    @Column(nullable = false)//η στήλη δεν μπορει να είναι κενή
    private String title;

   
    @Column(length = 3000)//η στήλη της περίληψης να είναι 3000 χαρακτήρες
    private String snippet;

  
    @Column(nullable = false)//δεν αφήνουμε το δικαίωμα να μη αξιολογήσει
    private int rating;
    

    @Column(name = "saved_at", nullable = false)//ημερομηνία και ώρα που
            //αποθηκεύτηκε το άθρο σωζεται  στη στήλη saved_at και δε
            //μπορεί να είναι κενή
    private LocalDateTime savedAt;

   
    @ManyToOne(optional = false)//κάθε άθρο πρέπει να έχει μία κατηγορία 
            //και σχέση πολλα αθρά σε μία κατηγορία
    @JoinColumn(name = "category_id")//στο πίνακα article θα υπάρχει ξένο key
            //που θα δείχνει στο πίνακα category
    private Category category;

   
   
    public Article() {//Υποχρεωτικός empty contstractor για JPA 
    }

    
    public Article(int pageId, String title, String snippet, int rating, Category category) { //constractor
        this.pageId = pageId;
        this.title = title;
        this.snippet = snippet;
        this.rating = rating;
        this.category = category;
        this.savedAt = LocalDateTime.now();//Θέτουμε τη ημερομηνία και ώρα
                                //αποθήκευσης
    }

   
    
    
    public Long getId() {//για να μας δίνει το Ιd της ΒΔ
        return id;
    }

   
    public int getPageId() {//για να μας δίνει το PageΙd της Wikipdia
        return pageId;
    }

   
    
    public String getTitle() { //για να μας δίνει το τίτλο
        return title;
    }

    
    
    public String getSnippet() {//για να μας δίνει τη περίληψη
        return snippet;
    }

   
    public int getRating() {//για να μας δίνει τη βαθμολογία
        return rating;
    }

    
    
    public LocalDateTime getSavedAt() {//για να μας δίνει την ημερομηνία και 
                    //ώρα αποθήκευσης
        return savedAt;
    }

   
    public Category getCategory() { //για να μας δίνει τη κατηγορία
        return category;
    }


    public void setRating(int rating) {//θετουμε την αξιολόγηση, για 
                //να αλλάξουμε την αξιολόγηση
        this.rating = rating;
    }

 
    public void setCategory(Category category) {//θέτουμε την κατηγορία, αν χρειαστεί 
                //να αλλάξουμε ένα αθρο από μια κατηγορία σε άλλη. ΑΝ ΔΕ ΤΟ ΧΡΕΙΑΣΤΟΥΜΕ ΘΑ ΤΟ ΣΒΗΣΟΥΜΕ
        this.category = category;
    }
}
