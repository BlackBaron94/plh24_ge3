package packageAPI.dto;

/**
 * DTO για αποτέλεσμα αναζήτησης από το Wikipedia API (search endpoint).
 *
 * Χρήση στη ροή DB-first + refresh:
 * - WikipediaClient.search(query) επιστρέφει List<SearchResultDto>.
 * - Το Service κάνει mapping σε Entity (π.χ. SearchResult / Article-lite) και το GUI τα εμφανίζει.
 *
 * Σημείωση για revisionId & timestamp:
 * - Τα revisionId και revisionTimestamp ΔΕΝ περιλαμβάνονται συνήθως στα αποτελέσματα search.
 * - Τα παίρνουμε από το details endpoint μέσω ArticleDetailsDto (WikipediaClient.getDetails(pageId)).
 *   Αυτό ταιριάζει ιδανικά με τη λογική "DB-first + refresh":
 *   (1) δείχνω γρήγορα αποτελέσματα, 
 *   (2) όταν χρειαστεί λεπτομέρειες/refresh, φέρνω revision info.
 *
 * Σημείωση για snippet:
 * - Το snippet συχνά περιέχει HTML (π.χ. <span class="searchmatch">).
 * - Καθάρισμα/απογύμνωση HTML γίνεται σε packageUtils.TextUtils 
 *   (π.χ. stripHtml, decodeEntities).
 */
/** 
 * Εδώ ορίζω το class <b>SearchResultDto</b> στο API [διεπαφή υπηρεσίας].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, 
 * ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public class SearchResultDto {

    private long pageId;     // query.search[i].pageid
    private String title;    // query.search[i].title
    private String snippet;  // query.search[i].snippet (συχνά με HTML)
    private int wordCount;   // query.search[i].wordcount (προαιρετικό)

    /**

     * Εδώ αρχικοποιώ το <b>SearchResultDto</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>SearchResultDto()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public SearchResultDto() {}

    /**

     * Εδώ αρχικοποιώ το <b>SearchResultDto</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     * @param pageId Παράμετρος αρχικοποίησης.

     * @param title Παράμετρος αρχικοποίησης.

     * @param snippet Παράμετρος αρχικοποίησης.

     * @param wordCount Παράμετρος αρχικοποίησης.

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>SearchResultDto()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @param title Παράμετρος εισόδου.

     * @param snippet Παράμετρος εισόδου.

     * @param wordCount Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public SearchResultDto(long pageId, String title, String snippet, int wordCount) {
        this.pageId = pageId;
        this.title = title;
        this.snippet = snippet;
        this.wordCount = wordCount;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getPageId()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public long getPageId() {
        return pageId;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>setPageId()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     */

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getTitle()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getTitle() {
        return title;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>setTitle()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param title Παράμετρος εισόδου.

     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getSnippet()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getSnippet() {
        return snippet;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>setSnippet()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param snippet Παράμετρος εισόδου.

     */

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getWordCount()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public int getWordCount() {
        return wordCount;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>setWordCount()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param wordCount Παράμετρος εισόδου.

     */

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>toString()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    @Override
    public String toString() {
        return "SearchResultDto{" +
                "pageId=" + pageId +
                ", title='" + title + '\'' +
                '}';
    }
}
