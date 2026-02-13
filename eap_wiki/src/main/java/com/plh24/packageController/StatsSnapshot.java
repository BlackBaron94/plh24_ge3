package com.plh24.packageController;

import java.util.List;

/**
 * Συγκεντρωτικά στατιστικά για το Stats tab.
 */
/**
 * Εδώ υλοποιώ τη μέθοδο <b>StatsSnapshot()</b>.
 * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
 * @param keywordStats Παράμετρος εισόδου.
 * @param categoryStats Παράμετρος εισόδου.
 * @return Επιστρέφω αποτέλεσμα.
 */
/** 
 * Εδώ ορίζω το record <b>StatsSnapshot</b> στον Controller [ελεγκτή] (λογική εφαρμογής).
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public record StatsSnapshot(
        List<KeywordStatRow> keywordStats,
        List<CategoryStatRow> categoryStats
) {}
