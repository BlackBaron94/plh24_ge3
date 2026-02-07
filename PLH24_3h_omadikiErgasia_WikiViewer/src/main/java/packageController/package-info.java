/**
 * packageController (Control) — μιλάει με GUI και Service (όχι DB/HTTP)
 * packageController (Control)
 *
 * Controllers που συνδέουν το GUI με τα Services και συντονίζουν τη ροή.
 *
 * Ρόλος:
 * - Δέχεται αιτήματα από το GUI (π.χ. search(query), viewDetails(id), saveArticle(...)).
 * - Καλεί τα Services και επιστρέφει αποτελέσματα προς εμφάνιση στο GUI.
 * - Εφαρμόζει/ενεργοποιεί πολιτικές ροής σε επίπεδο orchestration
 *   (π.χ. ποια service μέθοδος θα κληθεί για κάθε ενέργεια).
 *
 * Κανόνες:
 * - Δεν κάνει SQL/DB (αυτό είναι Repository).
 * - Δεν κάνει HTTP/JSON (αυτό είναι API Client).
 * - Δεν μιλάει απευθείας με Repository ή API Client.
 * - Μιλάει με Services και με Entities/DTOs ως δεδομένα μεταφοράς.
 */

package packageController;
