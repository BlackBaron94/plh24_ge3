package packageAPI.dto;

/**
 * DTO για λεπτομέρειες άρθρου από το Wikipedia API (details endpoint).
 *
 * Κρίσιμο για DB-first + refresh:
 * - revisionId: χρησιμοποιείται για σύγκριση “νεότερης έκδοσης”.
 *   Παράδειγμα: αν apiRevisionId > dbRevisionId => υπάρχει update.
 * - revisionTimestamp: συμπληρωματικό στοιχείο (audit/χρονοσήμανση/ενημέρωση UI).
 *
 * Update policy (όπως συμφωνήσαμε):
 * - Σε περίπτωση νεότερης έκδοσης, ενημερώνονται μόνο τα fetched fields:
 *   title/extract/revisionId/revisionTimestamp/lastFetchedAt.
 * - Τα τοπικά δεδομένα (σχόλια/βαθμολογία) ΔΕΝ διαγράφονται (είναι σε ArticleNote/Metadata).
 *
 * Σημείωση για extract:
 * - Αν χρησιμοποιήσεις explainttext=1 στο endpoint, το extract έρχεται χωρίς HTML.
 */
/** 
 * Εδώ ορίζω το class <b>ArticleDetailsDto</b> στο API [διεπαφή υπηρεσίας].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, 
 * ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public class ArticleDetailsDto {

    private long pageId;               // query.pages[pageId].pageid
    private String title;              // query.pages[pageId].title
    private String extract;            // query.pages[pageId].extract
    private long revisionId;           // query.pages[pageId].revisions[0].revid
    private String revisionTimestamp;  // query.pages[pageId].revisions[0].timestamp

    /**

     * Εδώ αρχικοποιώ το <b>ArticleDetailsDto</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>ArticleDetailsDto()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public ArticleDetailsDto() {}

    /**

     * Εδώ αρχικοποιώ το <b>ArticleDetailsDto</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     * @param pageId Παράμετρος αρχικοποίησης.

     * @param title Παράμετρος αρχικοποίησης.

     * @param extract Παράμετρος αρχικοποίησης.

     * @param revisionId Παράμετρος αρχικοποίησης.

     * @param revisionTimestamp Παράμετρος αρχικοποίησης.

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>ArticleDetailsDto()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @param title Παράμετρος εισόδου.

     * @param extract Παράμετρος εισόδου.

     * @param revisionId Παράμετρος εισόδου.

     * @param revisionTimestamp Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public ArticleDetailsDto(long pageId, String title, String extract, long revisionId, String revisionTimestamp) {
        this.pageId = pageId;
        this.title = title;
        this.extract = extract;
        this.revisionId = revisionId;
        this.revisionTimestamp = revisionTimestamp;
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

     * Εδώ υλοποιώ τη μέθοδο <b>getExtract()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getExtract() {
        return extract;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>setExtract()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param extract Παράμετρος εισόδου.

     */

    public void setExtract(String extract) {
        this.extract = extract;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getRevisionId()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public long getRevisionId() {
        return revisionId;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>setRevisionId()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param revisionId Παράμετρος εισόδου.

     */

    public void setRevisionId(long revisionId) {
        this.revisionId = revisionId;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getRevisionTimestamp()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getRevisionTimestamp() {
        return revisionTimestamp;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>setRevisionTimestamp()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param revisionTimestamp Παράμετρος εισόδου.

     */

    public void setRevisionTimestamp(String revisionTimestamp) {
        this.revisionTimestamp = revisionTimestamp;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>toString()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    @Override
    public String toString() {
        return "ArticleDetailsDto{" +
                "pageId=" + pageId +
                ", title='" + title + '\'' +
                ", revisionId=" + revisionId +
                ", revisionTimestamp='" + revisionTimestamp + '\'' +
                '}';
    }
}
