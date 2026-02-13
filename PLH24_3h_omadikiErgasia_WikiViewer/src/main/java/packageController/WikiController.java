package packageController;

import java.util.List;

/**
 * WikiController (interface) [διεπαφή ελεγκτή] – το «συμβόλαιο» ανάμεσα σε GUI και Controller.
 *
 * Κανόνας: το packageGUI καλεί ΜΟΝΟ αυτό, και ο Controller αναλαμβάνει:
 * - DB-first search (και προαιρετικά API refresh)
 * - CRUD σε saved articles & metadata (rating/comments/categories)
 * - Stats snapshot για το Stats tab
 */
/** 
 * Εδώ ορίζω το interface <b>WikiController</b> στον Controller [ελεγκτή] (λογική εφαρμογής).
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public interface WikiController {

    // ===============================
    // SEARCH
    // ===============================

    /**
     * Αναζήτηση άρθρων.
     * @param query λέξεις-κλειδιά.
     * @param mode DB_ONLY ή DB_API.
     * @param categoryFilter λίστα checked κατηγοριών (OR semantics). Αν κενό/"Όλες", δεν φιλτράρει.
     */
    List<SearchResultRow> search(String query, SearchMode mode, List<String> categoryFilter);

    // ===============================
    // SAVED
    // ===============================

    /**
     * Επιστρέφει τη λίστα των αποθηκευμένων άρθρων (για το Saved tab).
     */
    List<SavedArticleRow> listSaved(List<String> categoryFilter);

    /**
     * Αποθηκεύει άρθρο στα Saved.
     */
    void saveArticle(long pageId, String title, String source, List<String> categories);

    /**
     * Ενημερώνει metadata (rating/comments/categories) για αποθηκευμένο άρθρο.
     */
    void updateSavedMetadata(long pageId, int rating0to5, String comments, List<String> categories);

    /**
     * Διαγράφει αποθηκευμένο άρθρο.
     */
    void deleteSaved(long pageId);

    /**
     * Καθαρίζει ΟΛΑ τα saved άρθρα.
     */
    void clearAllSaved();

    // ===============================
    // DETAILS
    // ===============================

    /**
     * Φορτώνει πλήρη στοιχεία άρθρου για Article Details (και Load Article).
     */
    ArticleDetails getDetails(long pageId);

    // ===============================
    // STATS
    // ===============================

    /**
     * Statistics snapshot for Stats tab.
     */
    StatsSnapshot getStats();

    /**
     * Alias method used by the GUI (older name).
     *
     * ==TO DO : Στο επόμενο στάδιο, κράτα μόνο getStats(). Τότε στο GUI αντικατέστησε:
     *           controller.loadStats() -> controller.getStats()
     */
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>loadStats()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @return Επιστρέφω αποτέλεσμα.
     */
    default StatsSnapshot loadStats() {
        return getStats();
    }
}
