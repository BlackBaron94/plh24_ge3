package com.plh24.packageEntities;

/**
 * Entity: ArticleMetadata (rating + comments)
 *
 * ==TO DO : Map to DB table ARTICLE_METADATA==
 * Suggested columns:
 * - page_id (PK/FK to ARTICLE.page_id)
 * - rating (0..5)
 * - comments (CLOB)
 * - updated_at
 *
 * Note: local metadata must NOT be deleted when Article is refreshed from API.
 */
/** 
 * Εδώ ορίζω το class <b>ArticleMetadata</b> στο Model/Entities [οντότητες].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public class ArticleMetadata {

    private long pageId;
    private int rating;       // 0..5
    private String comments;  // multi-line text

    /**

     * Εδώ αρχικοποιώ το <b>ArticleMetadata</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     * @param pageId Παράμετρος αρχικοποίησης.

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>ArticleMetadata()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public ArticleMetadata(long pageId) {
        this.pageId = pageId;
        this.rating = 0;
        this.comments = "";
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getPageId()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public long getPageId() { return pageId; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setPageId()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param pageId Παράμετρος εισόδου.
     */
    public void setPageId(long pageId) { this.pageId = pageId; }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getRating()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public int getRating() { return rating; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setRating()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param rating Παράμετρος εισόδου.
     */
    public void setRating(int rating) { this.rating = Math.max(0, Math.min(5, rating)); }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getComments()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getComments() { return comments; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setComments()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param comments Παράμετρος εισόδου.
     */
    public void setComments(String comments) { this.comments = (comments == null) ? "" : comments; }
}
