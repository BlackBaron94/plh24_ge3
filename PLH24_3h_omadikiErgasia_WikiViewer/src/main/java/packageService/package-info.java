/**
 * 
 * packageService (Business Logic) — καλείται από Controller, χρησιμοποιεί Repository + API
 * packageService (Business Logic / Services)
 *
 * Υλοποιεί τους κανόνες λειτουργίας της εφαρμογής (business logic).
 * Συνδυάζει DB (Repositories) και Wikipedia API (API Client) σύμφωνα με πολιτικές.
 *
 * Παραδείγματα:
 * - SearchService: αναζήτηση με πολιτική DB-first
 * - SyncService: background refresh / έλεγχος νεότερης έκδοσης
 * - StatsService: στατιστικά από DB
 * - ValidationService: έλεγχοι εισόδου
 *
 * Πολιτικές (στο Service):
 * - DB-first: πρώτα αναζήτηση στη DB, αν δεν βρεθεί τότε API.
 * - Background refresh: αν βρεθεί στη DB, γίνεται έλεγχος στο API για νεότερη έκδοση.
 * - Update policy: ενημερώνονται μόνο τα fetched fields του Article.
 *   Τα τοπικά δεδομένα (σχόλια/βαθμολογία) δεν διαγράφονται (ArticleNote/Metadata).
 *
 * Κανόνες:
 * - Καλείται από Controllers.
 * - Καλεί Repositories (DB) και API Client (Wikipedia).
 * - Δεν περιέχει Swing/UI κώδικα.
 */

package packageService;
