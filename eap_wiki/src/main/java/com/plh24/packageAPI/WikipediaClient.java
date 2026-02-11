package com.plh24.packageAPI;

/**
 * WikipediaClient
 *
 * Ρόλος / Σκοπός:
 * - Είναι ο “API Client” της εφαρμογής: κάνει HTTP κλήσεις προς το Wikipedia API και
 *   μετατρέπει τις απαντήσεις (συνήθως JSON) σε αντικείμενα που μπορεί να χρησιμοποιήσει
 *   η εφαρμογή (DTOs ή/και Entities).
 *
 * Γιατί υπάρχει (στόχοι σχεδίασης):
 * 1) Διαχωρισμός ευθυνών (Separation of Concerns):
 *    - Όλος ο κώδικας δικτύου/HTTP/JSON συγκεντρώνεται εδώ.
 *    - Το GUI μένει “καθαρό” (δεν ξέρει endpoints, JSON parsing κ.λπ.).
 *
 * 2) Επεκτασιμότητα:
 *    - Αν αλλάξει ο τρόπος κλήσης του API (endpoint, format), αλλάζεις μόνο εδώ
 *      και στα DTOs του packageAPI, όχι στο GUI/Controller.
 *
 * 3) Απόδοση & UX:
 *    - Οι κλήσεις API μπορεί να καθυστερούν. Ο client είναι “blocking” από τη φύση του,
 *      άρα πρέπει να καλείται σε background thread (SwingWorker / Executor), ώστε να μην
 *      παγώνει το GUI.
 *
 * 4) Ευκολία testing:
 *    - Μπορεί να αντικατασταθεί από mock/stub σε tests (π.χ. Service tests) χωρίς GUI.
 *
 * Ποιος το καλεί (και γιατί):
 * - packageGUI: ΔΕΝ το καλεί απευθείας.
 *   Γιατί; Το GUI δεν πρέπει να περιέχει HTTP/JSON λογική και δεν πρέπει να μπλοκάρει το EDT.
 *
 * - packageController: Συνήθως καλεί Services (όχι απευθείας τον client).
 *   Γιατί; Ο controller συντονίζει ροές (DB-first, background refresh), όχι HTTP λεπτομέρειες.
 *
 * - packageService: Είναι ο κύριος “χρήστης” του WikipediaClient.
 *   Γιατί; Τα Services υλοποιούν τη λειτουργικότητα (αναζήτηση/συγχρονισμός) και επιλέγουν
 *   πότε θα χρησιμοποιήσουν DB (Repository) και πότε API (WikipediaClient).
 *
 * Ενδεικτική ροή κλήσης (call chain):
 * 1) GUI: ο χρήστης γράφει query και πατάει Search
 * 2) GUI handler -> SearchController.search(query)
 * 3) SearchController -> SearchService.search(query)
 * 4) SearchService:
 *    - (policy DB-first) ρωτάει πρώτα Repository
 *    - αν δεν βρει, τότε καλεί WikipediaClient.search(query)
 * 5) Επιστροφή results -> GUI τα εμφανίζει (JTable/JList)
 * 6) Όταν ο χρήστης πατήσει View Details:
 *    - Controller/Service καλεί WikipediaClient.getDetails(pageId)
 *    - και επιστρέφει περιεχόμενο/metadata για εμφάνιση στο Details Dialog
 *
 * Τι ΔΕΝ κάνει (κανόνες):
 * - Δεν κάνει πρόσβαση στη Βάση (Repository/DAO είναι αλλού).
 * - Δεν ενημερώνει Swing components.
 * - Δεν εφαρμόζει πολιτικές DB-first / background refresh (αυτά είναι Controller/Service).
 *
 * Σημείωση για threads:
 * - Ο WikipediaClient δεν “δημιουργεί” threads. Εκτελείται στο thread που τον καλεί.
 * - Η ευθύνη για background εκτέλεση είναι του GUI (SwingWorker) ή/και του Service/Controller.
 */
public class WikipediaClient {

    // TODO: Σταθερές endpoints / config (π.χ. baseUrl, timeouts)

    // TODO: Μέθοδοι π.χ.
    // - search(String query) -> List<SearchResultDTO>
    // - getDetails(long pageId) -> ArticleDetailsDTO
    //
    // (Οι ακριβείς signatures θα οριστούν όταν κλειδώσει το μοντέλο δεδομένων.)
}
