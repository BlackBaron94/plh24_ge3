


import jakarta.persistence.*; // Για να μπορουμε να χρησιμοποιούμε τα
                //annotations του JPA


@Entity //Αυτή η κλάση είναι table στη ΒΔ τη φτίχνει το Hibernate   
@Table(name = "category") //όνομα του πίνακα στη ΒΔ


public class Category {

    
    @Id //το επόμενο θα είναι primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//το id θα
            //δημιουργήται μόνο του με αυξηση για να έχουμε μοναδικότητα
    
    private Long id;//στα id βάζουμε Long

   
    @Column(nullable = false, unique = true)// η στήλη του πίνακα στο ORM
        //είναι ιδιότητα όποτε δε μπορεί να είναι μηδέν και είναι μοναδική
    
    private String name; //όνομα της κατηγορίας

  
    public Category() { //Υποχρεωτικό empty contstractor για JPA 
    }

  
    public Category(String name) { //Constractor
        this.name = name;
    }

    
    public Long getId() { //για να μας δίνει το Ιd
        return id;
    }

    
    public String getName() { //για να μας δίνει το name
        return name;
    }
}
