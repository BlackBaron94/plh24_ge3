    

///ΓΙΑ΄ΕΛΕΓΧΟ ΛΕΙΤΟΥΡΓΙΑΣ 

public class Main {    
    
    
public static void main(String[] args) {
    System.setProperty("file.encoding", "UTF-8"); //για να βλέπω ελλήνικα
    ArticleService service = new ArticleService();

   
    System.out.println("Έλεγχος βάσης...");
    service.printAllArticles();

    // Αποθήκευση ενός άρθρου )
    WikipediaManager manager = new WikipediaManager();
    manager.fetchAndSave("Πόλεμος", "Ιστορία");

    //Προβολή μετά την αποθήκευση 
    System.out.println("\nΝέα λίστα:");
    service.printAllArticles();
}
}