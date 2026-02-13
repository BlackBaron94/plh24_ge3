package com.plh24.packageEntities;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: Article
 *
 * ==TO DO : Map to DB table ARTICLE==
 * Suggested columns:
 * - article_id (PK, surrogate) OR page_id (unique)
 * - title
 * - source (DB/API)
 * - full_text (CLOB)
 * - created_at / updated_at
 */
/** 
 * Εδώ ορίζω το class <b>Article</b> στο Model/Entities [οντότητες].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public class Article {

    private long pageId;                 // Wikipedia PageID
    private String title;
    private ArticleSource source;
    private String fullText;

    // many-to-many via ArticleCategory
    private final List<Category> categories = new ArrayList<>();

    /**

     * Εδώ αρχικοποιώ το <b>Article</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     * @param pageId Παράμετρος αρχικοποίησης.

     * @param title Παράμετρος αρχικοποίησης.

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>Article()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param pageId Παράμετρος εισόδου.

     * @param title Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public Article(long pageId, String title) {
        this.pageId = pageId;
        this.title = title;
        this.source = ArticleSource.DB;
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

     * Εδώ υλοποιώ τη μέθοδο <b>getTitle()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getTitle() { return title; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setTitle()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param title Παράμετρος εισόδου.
     */
    public void setTitle(String title) { this.title = title; }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getSource()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public ArticleSource getSource() { return source; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setSource()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param source Παράμετρος εισόδου.
     */
    public void setSource(ArticleSource source) { this.source = source; }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getFullText()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public String getFullText() { return fullText; }
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>setFullText()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param fullText Παράμετρος εισόδου.
     */
    public void setFullText(String fullText) { this.fullText = fullText; }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>getCategories()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    public List<Category> getCategories() { return categories; }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>addCategory()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param c Παράμετρος εισόδου.

     */

    public void addCategory(Category c) {
        if (c != null && !categories.contains(c)) categories.add(c);
    }
}
