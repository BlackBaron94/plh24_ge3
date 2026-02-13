package packageEntities;

/**
 * Entity: Category
 *
 * ==TO DO : Map to DB table CATEGORY==
 * Suggested columns:
 * - category_id (PK)
 * - name (unique)
 */
/** 
 * Εδώ ορίζω το class <b>Category</b> στο Model/Entities [οντότητες].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public class Category {

    private long categoryId;
    private String name;

    /**

     * Εδώ αρχικοποιώ το <b>Category</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     * @param categoryId Παράμετρος αρχικοποίησης.

     * @param name Παράμετρος αρχικοποίησης.

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>Category()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param categoryId Παράμετρος εισόδου.

     * @param name Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public Category(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getCategoryId()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public long getCategoryId() { return categoryId; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setCategoryId()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param categoryId Παράμετρος εισόδου.
     */
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getName()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getName() { return name; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setName()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param name Παράμετρος εισόδου.
     */
    public void setName(String name) { this.name = name; }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>toString()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    @Override
    public String toString() { return name; }
}
