/**
 * WikipediaClient
 *
 * Ρόλος:
 * - Κάνει HTTP κλήσεις προς το Wikipedia API και επιστρέφει αποτελέσματα (DTOs).
 *
 * Παρέχει (ενδεικτικά):
 * - search(query): λίστα αποτελεσμάτων (id, title, snippet)
 * - getDetails(pageId): λεπτομέρειες άρθρου (content/extract, revision info)
 *
 * Κανόνες:
 * - Δεν μιλάει με DB (αυτό είναι του packageRepository/packageService).
 * - Δεν κάνει Swing/UI ενημερώσεις.
 * - Δεν περιέχει business rules (π.χ. DB-first). Αυτό είναι του Controller/Service.
 */
/**
 * packageAPI
 *
 * Client για Wikipedia API.
 * Περιλαμβάνει:
 * - WikipediaClient (HTTP requests)
 * - DTOs/Response models για JSON parsing
 * - JSON mapping (π.χ. Gson/Jackson helpers)
 *
 * Κανόνες:
 * - Καμία πρόσβαση στη DB (αυτό είναι του repository/service).
 * - Καμία UI λογική.
 */

package com.plh24.packageAPI;
