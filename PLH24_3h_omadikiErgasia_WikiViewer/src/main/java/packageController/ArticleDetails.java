package packageController;

import java.util.List;

/**
 * Full article details for "View Details" / "Load Article" dialogs.
 */
/**
 * Εδώ υλοποιώ τη μέθοδο <b>ArticleDetails()</b>.
 * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
 * @param title Παράμετρος εισόδου.
 * @param pageId Παράμετρος εισόδου.
 * @param source Παράμετρος εισόδου.
 * @param categories Παράμετρος εισόδου.
 * @param fullText Παράμετρος εισόδου.
 * @param rating Παράμετρος εισόδου.
 * @param text Παράμετρος εισόδου.
 * @return Επιστρέφω αποτέλεσμα.
 */
/** 
 * Εδώ ορίζω το record <b>ArticleDetails</b> στον Controller [ελεγκτή] (λογική εφαρμογής).
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public record ArticleDetails(
        String title,
        long pageId,
        String source,
        List<String> categories,
        String fullText,
        int rating,      // 0..5
        String comments  // free text
) { }
