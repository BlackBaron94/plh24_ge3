package packageEntities;

/**
 * Association entity (many-to-many): Article <-> Category
 *
 * ==TO DO : Map to DB join table ARTICLE_CATEGORY==
 * Suggested columns:
 * - page_id (FK -> ARTICLE.page_id)
 * - category_id (FK -> CATEGORY.category_id)
 * PK: (page_id, category_id)
 */
/** 
 * Εδώ ορίζω το class <b>ArticleCategory</b> στο Model/Entities [οντότητες].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public class ArticleCategory {

    private long pageId;
    private long categoryId;

    /**

     * Εδώ αρχικοποιώ το <b>ArticleCategory</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     * @param pageId Παράμετρος αρχικοποίησης.

     * @param categoryId Παράμετρος αρχικοποίησης.

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>ArticleCategory()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @param categoryId Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public ArticleCategory(long pageId, long categoryId) {
        this.pageId = pageId;
        this.categoryId = categoryId;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getPageId()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public long getPageId() { return pageId; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>getCategoryId()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @return Επιστρέφω αποτέλεσμα.
     */
    public long getCategoryId() { return categoryId; }
}
