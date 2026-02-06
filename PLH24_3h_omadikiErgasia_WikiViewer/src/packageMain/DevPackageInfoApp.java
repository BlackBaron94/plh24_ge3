package packageMain;


    
import javax.swing.*;
import java.awt.*;

public class DevPackageInfoApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PackageInfoFrame().setVisible(true));
    }

    // Μικρό παράθυρο που δείχνει τα "info" των packages
    private static class PackageInfoFrame extends JFrame {

        public PackageInfoFrame() {
            setTitle("PLH24_3h_omadikiErgasia_WikiViewer - Package Info");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            JTextArea area = new JTextArea(70, 50);
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            //---
            area.setFont(new Font("Arial", Font.PLAIN, 18));   // ↑ μέγεθος γραμματοσειράς
            area.setMargin(new Insets(12, 12, 12, 12));        // ↑ εσωτερικά περιθώρια (padding)
            //--
            area.setText(buildText());

            add(new JScrollPane(area), BorderLayout.CENTER);
            setPreferredSize(new Dimension(950, 850));
            pack();
            setLocationRelativeTo(null);
        }

        private String buildText() {
            return """
                   Πληροφορίες Packages - Δομή Πακέτων [packages]
Για περισσότερες λεπτομέρειες ΔΕΙΤΕ τους Σχολιασμούς στα package-info.java\n 
                   1) packageGUI
                      - Swing UI: JFrame/JPanel/JDialog, handlers, UI models (TableModel)
                      - Κανόνας: όχι DB/API λογική. 
                      - Ό,τι αργεί τρέχει σε SwingWorker.(Νήματα Threats)

                   2) packageController
                      - Controllers: ροές, συντονισμός DB-first + fallback API
                      - Background refresh (αν βρεθεί στη DB) + μήνυμα "νεότερη έκδοση"
                      - Update μόνο fetched fields (Article). Τα σχόλια/βαθμολογία δεν χάνονται.

                   3) packageEntities
                      - POJOs: Article, Category, ArticleNote/Metadata, SearchResult
                      - Κανόνας: χωρίς Swing/SQL/HTTP.

                   4) packageRepository
                      - DB access (DAO/Repository): SQL/CRUD, DataSource, mapping σε Entities
                      - Κανόνας: όχι UI, όχι API.

                   5) packageAPI
                      - WikipediaClient, DTOs/Responses, JSON parsing
                      - Κανόνας: όχι DB, όχι UI.
                      - Ό,τι αργεί τρέχει σε SwingWorker.(Νήματα Threats)                
                      - Δές και λεπτομερή Σχόλια της class WikipediaClient
                   

                   6) packageService
                      - Business logic: SearchService, SyncService, StatsService, ValidationService
                      - Στόχος: επεκτασιμότητα (κανόνες αλλάζουν χωρίς αλλαγές στο GUI).

                   7) packageUtils
                      - Helpers: TextUtils (remove HTML tags), DateUtils, Constants
                   """;
        }
    }
}
