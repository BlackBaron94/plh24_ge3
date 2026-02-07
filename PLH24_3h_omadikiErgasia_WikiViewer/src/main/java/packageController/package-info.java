/**
 * packageController
 *
 * Controllers που συνδέουν GUI με Services/Repositories/API.
 * Εφαρμόζουν τους κανόνες ροής της εφαρμογής.
 *
 * Πολιτικές:
 * - DB-first: πρώτα αναζήτηση στη DB, αν δεν βρεθεί τότε αναζήτηση στο API.
 * - Background refresh: αν βρεθεί στη DB, γίνεται έλεγχος στο API για νεότερη έκδοση.
 * - Update policy: ενημερώνονται μόνο τα fetched fields του Article.
 *   Τα τοπικά δεδομένα (σχόλια/βαθμολογία) δεν διαγράφονται (ArticleNote/Metadata).
 */

package packageController;
