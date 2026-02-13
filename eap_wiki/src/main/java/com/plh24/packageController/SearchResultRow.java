package com.plh24.packageController;

import java.util.List;

/**
 * Row shown in Search results table.
 *
 * Category is many-to-many, so we keep a List<String> categories and the GUI
 * renders it as: "Cat1, Cat2, Cat3".
 */
/**
 * Εδώ υλοποιώ τη μέθοδο <b>SearchResultRow()</b>.
 * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
 * @param title Παράμετρος εισόδου.
 * @param pageId Παράμετρος εισόδου.
 * @param source Παράμετρος εισόδου.
 * @param categories Παράμετρος εισόδου.
 * @return Επιστρέφω αποτέλεσμα.
 */
/** 
 * Εδώ ορίζω το record <b>SearchResultRow</b> στον Controller [ελεγκτή] (λογική εφαρμογής).
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public record SearchResultRow(
        String title,
        long pageId,
        String source,
        List<String> categories
) { }
