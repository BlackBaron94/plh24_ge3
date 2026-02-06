/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wikithing;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Arrays;
import wikithing.pojos.Category;
/**
 *
 * @author Equinox
 */
public class CategoryCreator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WikiThingPU");
        EntityManager em = emf.createEntityManager();
        Category category = new Category();
        
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
        em.getTransaction().begin();
        for (String name : listOfNames) {
            Category c = new Category();
            c.setName(name);
            em.persist(c);
        }
        em.getTransaction().commit();
        em.close();
    }
    
}
