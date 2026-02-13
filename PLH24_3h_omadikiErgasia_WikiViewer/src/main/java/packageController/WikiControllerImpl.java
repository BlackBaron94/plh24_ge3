package packageController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Stub implementation with dummy data.
 *
 * ==TO DO : Replace dummy data with real data sources==
 * - search():
 *   - If mode == DB_ONLY: query DB (Repository/DAO) using keywords + categories.
 *   - If mode == DB_API: first query DB; if not found -> call Wikipedia API and store to DB.
 *   - Category filtering should ideally happen in SQL (JOIN ArticleCategory ...) or in the controller/service layer.
 *
 * - listSaved():
 *   - SELECT saved articles + categories + rating + comments from DB.
 *
 * - getDetails(pageId):
 *   - Load full article text from DB; if missing and mode allows -> fetch from API.
 *
 * - getStats():
 *   - Aggregate stats from DB: keyword search counts + category article counts.
 *   - Return sorted top-N lists.
 */
/** 
 * Εδώ ορίζω το class <b>WikiControllerImpl</b> στον Controller [ελεγκτή] (λογική εφαρμογής).
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public class WikiControllerImpl implements WikiController {

    // ---------------------------
    // Dummy in-memory "database"
    // ---------------------------
    private final List<SearchResultRow> dummySearchPool;
    private final List<SavedArticleRow> dummySaved;
    private final Map<Long, ArticleDetails> dummyDetailsById;
    private final StatsSnapshot dummyStats;

    /**

     * Εδώ αρχικοποιώ το <b>WikiControllerImpl</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>WikiControllerImpl()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public WikiControllerImpl() {
        // Dummy Search Pool
        dummySearchPool = List.of(
                new SearchResultRow("Artificial intelligence", 45678L, "DB", List.of("Υπολογιστές", "ΑΙ", "Προγραμματισμός")),
                new SearchResultRow("Machine learning", 22345L, "DB", List.of("Υπολογιστές", "ΑΙ")),
                new SearchResultRow("Deep learning", 99999L, "API", List.of("Υπολογιστές", "ΑΙ")),
                new SearchResultRow("Big data", 34567L, "DB", List.of("Υπολογιστές", "Δεδομένα")),
                new SearchResultRow("Byzantine Empire", 11223L, "API", List.of("Ιστορία"))
        );

        // Dummy Saved list
        dummySaved = new ArrayList<>(List.of(
                new SavedArticleRow("Artificial intelligence", 45678L, "2026-02-05 15:33", "DB",
                        List.of("Υπολογιστές", "ΑΙ", "Προγραμματισμός"), 4,
                        "Πολύ καλό άρθρο.\nΝα δω το section 'Applications'."),
                new SavedArticleRow("Machine learning", 22345L, "2026-02-07 10:01", "DB",
                        List.of("Υπολογιστές", "ΑΙ"), 5,
                        "Εξαιρετικό. Χρήσιμο για το project."),
                new SavedArticleRow("Big data", 34567L, "2026-02-06 17:55", "DB",
                        List.of("Υπολογιστές", "Δεδομένα"), 3,
                        "Να προσθέσω references."),
                new SavedArticleRow("Byzantine Empire", 11223L, "2026-02-04 09:30", "DB",
                        List.of("Ιστορία"), 2,
                        "Σύντομο. Θέλει καλύτερη περίληψη.")
        ));

        // Dummy full text details
        dummyDetailsById = new HashMap<>();
        for (SearchResultRow r : dummySearchPool) {
            dummyDetailsById.put(r.pageId(),
                    new ArticleDetails(
                            r.title(),
                            r.pageId(),
                            r.source(),
                            r.categories(),
                            makeLongText(r.title()),
                            0,
                            ""
                    ));
        }
        for (SavedArticleRow s : dummySaved) {
            dummyDetailsById.put(s.pageId(),
                    new ArticleDetails(
                            s.title(),
                            s.pageId(),
                            s.source(),
                            s.categories(),
                            makeLongText(s.title()),
                            s.rating(),
                            s.comments()
                    ));
        }

        // Dummy stats
        dummyStats = new StatsSnapshot(
                List.of(
                        new KeywordStatRow("Concurrency", 34),
                        new KeywordStatRow("Deep learning", 29),
                        new KeywordStatRow("Big data", 22),
                        new KeywordStatRow("Python", 18),
                        new KeywordStatRow("Machine learning", 15)
                ),
                List.of(
                        new CategoryStatRow("Χωρίς Κατηγορία", 7),
                        new CategoryStatRow("Πολιτισμός", 9),
                        new CategoryStatRow("Ιστορία", 11),
                        new CategoryStatRow("Υπολογιστές", 13)
                )
        );
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>search()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param keywords Παράμετρος εισόδου.

     * @param mode Παράμετρος εισόδου.

     * @param checkedCategories Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    @Override
    public List<SearchResultRow> search(String keywords, SearchMode mode, List<String> checkedCategories) {
        // In stub we ignore keywords/mode, but keep signature.
        return filterByCategories(dummySearchPool, checkedCategories);
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>listSaved()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param checkedCategories Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    @Override
    public List<SavedArticleRow> listSaved(List<String> checkedCategories) {
        return filterByCategoriesSaved(dummySaved, checkedCategories);
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getDetails()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    @Override
    public ArticleDetails getDetails(long pageId) {
        return dummyDetailsById.getOrDefault(pageId,
                new ArticleDetails("Unknown", pageId, "DB", List.of("Χωρίς Κατηγορία"),
                        "No text (dummy).", 0, ""));
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>saveArticle()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @param title Παράμετρος εισόδου.

     * @param source Παράμετρος εισόδου.

     * @param categories Παράμετρος εισόδου.

     */

    @Override
    public void saveArticle(long pageId, String title, String source, List<String> categories) {
        // Αν υπάρχει ήδη, δεν το διπλο-σώζω.
        boolean exists = dummySaved.stream().anyMatch(s -> s.pageId() == pageId);
        if (exists) return;

        // /**==TO DO : Εδώ θα γίνει INSERT στο DB (SavedArticle + ArticleMetadata).
        //             Παράδειγμα (ψευδοκώδικας):
        //             savedRepository.insert(pageId, title, now(), source);
        //             metadataRepository.upsert(pageId, rating=0, comments="", categories);
        //==*/

        String savedAt = java.time.LocalDateTime.now().toString();
        List<String> cats = (categories == null || categories.isEmpty())
                ? List.of("Χωρίς Κατηγορία")
                : List.copyOf(categories);

        String src = (source == null || source.isBlank()) ? "DB" : source.trim();
        dummySaved.add(new SavedArticleRow(title, pageId, savedAt, src, cats, 0, ""));
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>updateSavedMetadata()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @param rating Παράμετρος εισόδου.

     * @param comments Παράμετρος εισόδου.

     * @param categories Παράμετρος εισόδου.

     */

    @Override
    public void updateSavedMetadata(long pageId, int rating, String comments, List<String> categories) {
        // /**==TO DO : Εδώ θα γίνει UPDATE/UPSERT στο DB (rating/comments/categories) για το συγκεκριμένο pageId.
        //             Παράδειγμα (ψευδοκώδικας):
        //             metadataRepository.upsert(pageId, rating, comments, categories);
        //==*/

        // update saved row
        for (int i = 0; i < dummySaved.size(); i++) {
            SavedArticleRow s = dummySaved.get(i);
            if (s.pageId() == pageId) {
                List<String> cats = (categories == null || categories.isEmpty())
                        ? s.categories()
                        : List.copyOf(categories);
                dummySaved.set(i, new SavedArticleRow(
                        s.title(),
                        s.pageId(),
                        s.savedAt(),
                        s.source(),
                        cats,
                        clampRating(rating),
                        comments == null ? "" : comments
                ));
                break;
            }
        }

        // update details (ώστε το Article Details να δείχνει τα νέα metadata)
        ArticleDetails d = dummyDetailsById.get(pageId);
        if (d != null) {
            List<String> cats = (categories == null || categories.isEmpty())
                    ? d.categories()
                    : List.copyOf(categories);
            dummyDetailsById.put(pageId, new ArticleDetails(
                    d.title(), d.pageId(), d.source(), cats, d.fullText(),
                    clampRating(rating),
                    comments == null ? "" : comments
            ));
        }
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>deleteSaved()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     */

    @Override
    public void deleteSaved(long pageId) {
        // /**==TO DO : Εδώ θα γίνει DELETE από DB (SavedArticle + metadata) για pageId.
        //             Προσοχή: αν θέλεις soft-delete, βάλε flag deleted=1.
        //==*/
        dummySaved.removeIf(s -> s.pageId() == pageId);
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>clearAllSaved()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     */

    @Override
    public void clearAllSaved() {
        // /**==TO DO : Εδώ θα γίνει DELETE/TRUNCATE από DB για όλα τα saved (ανά χρήστη).
        //==*/
        dummySaved.clear();
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getStats()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    @Override
    public StatsSnapshot getStats() {
        return dummyStats;
    }

    // ---------------------------
    // Helpers
    // ---------------------------
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>isAllCategories()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param checkedCategories Παράμετρος εισόδου.
     * @return Επιστρέφω αποτέλεσμα.
     */
    private static boolean isAllCategories(List<String> checkedCategories) {
        if (checkedCategories == null || checkedCategories.isEmpty()) return true;
        return checkedCategories.stream().anyMatch(c -> "Όλες οι κατηγορίες".equalsIgnoreCase(c));
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>filterByCategories()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param rows Παράμετρος εισόδου.

     * @param checkedCategories Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private static List<SearchResultRow> filterByCategories(List<SearchResultRow> rows, List<String> checkedCategories) {
        if (isAllCategories(checkedCategories)) return rows;

        Set<String> checked = checkedCategories.stream()
                .filter(c -> c != null && !c.isBlank())
                .collect(Collectors.toSet());

        return rows.stream()
                // OR: article belongs to ANY checked category
                .filter(r -> r.categories() != null && r.categories().stream().anyMatch(checked::contains))
                .toList();
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>filterByCategoriesSaved()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param rows Παράμετρος εισόδου.

     * @param checkedCategories Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private static List<SavedArticleRow> filterByCategoriesSaved(List<SavedArticleRow> rows, List<String> checkedCategories) {
        if (isAllCategories(checkedCategories)) return rows;

        Set<String> checked = checkedCategories.stream()
                .filter(c -> c != null && !c.isBlank())
                .collect(Collectors.toSet());

        return rows.stream()
                .filter(r -> r.categories() != null && r.categories().stream().anyMatch(checked::contains))
                .toList();
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>makeLongText()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param title Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private static String makeLongText(String title) {
        // simple long dummy text
        String base = "=== " + title + " ===\n\n"
                + "This is dummy full text for preview / export.\n"
                + "Replace with real Wikipedia article text fetched from DB/API.\n\n";

        StringBuilder sb = new StringBuilder(base);
        for (int i = 1; i <= 60; i++) {
            sb.append("Paragraph ").append(i).append(": Lorem ipsum dolor sit amet, consectetur adipiscing elit. ")
              .append("Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
        }
        return sb.toString();
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>clampRating()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param rating Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private static int clampRating(int rating) {
        if (rating < 0) return 0;
        if (rating > 5) return 5;
        return rating;
    }
}
