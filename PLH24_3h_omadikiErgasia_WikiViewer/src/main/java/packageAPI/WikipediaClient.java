

/**
 * 
 *  packageAPI (Wikipedia API Client) — καλείται από Service, κάνει HTTP/JSON
 * Σημαντικό: για καθαρό design, προτείνω ο client να επιστρέφει DTOs και το mapping σε Entities να γίνεται στο Service. 
 * (Αν δεν θέλω DTOs, το γυρνάω σε “επιστρέφει Entities”.)
 * 
 *
 * packageAPI (Wikipedia API Client)
 *
 * Ρόλος:
 * - Κάνει HTTP κλήσεις προς το Wikipedia API.
 * - Παίρνει απαντήσεις (συνήθως JSON) και τις μετατρέπει σε DTOs (ή/και απλά objects μεταφοράς).
 *
 * Γιατί υπάρχει:
 * - Διαχωρισμός ευθυνών: όλος ο κώδικας δικτύου/HTTP/JSON εδώ.
 * - Το GUI/Controller/Service δεν ασχολούνται με endpoints και JSON parsing.
 *
 * Ποιος το καλεί:
 * - Καλείται από packageService (όχι από GUI/Controller).
 *
 * Τι ΔΕΝ κάνει:
 * - Δεν κάνει πρόσβαση στη DB (αυτό είναι packageRepository).
 * - Δεν ενημερώνει Swing components.
 * - Δεν εφαρμόζει πολιτικές DB-first/refresh (αυτά είναι στο Service).
 *
 * Σημείωση για threads:
 * - Ο client είναι blocking και εκτελείται στο thread που τον καλεί.
 * - Το GUI τρέχει τα requests σε background (SwingWorker) ώστε να μη μπλοκάρει το EDT.
 */
package packageAPI;
public class WikipediaClient {
    // TODO: baseUrl, timeouts, endpoints
    // TODO: search(String query) -> List<SearchResultDto>
    // TODO: getDetails(long pageId) -> ArticleDetailsDto
}
