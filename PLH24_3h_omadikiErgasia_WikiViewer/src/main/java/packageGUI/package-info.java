/**
 * packageGUI (Border / UI) — επικοινωνεί μόνο με Controller
 *
 *
 * Swing GUI της εφαρμογής:
 * - MainFrame (JFrame), Panels (JPanel), Dialogs (JDialog)
 * - Event handlers (ActionListener), UI components, UI models (π.χ. TableModel)
 *
 * Ρόλος:
 * - Παίρνει ενέργειες χρήστη (click/enter) και καλεί Controllers.
 * - Παρουσιάζει αποτελέσματα (πίνακες, preview, dialogs).
 *
 * Κανόνες:
 * - Δεν περιέχει SQL/DB λογική.
 * - Δεν περιέχει HTTP/API λογική.
 * - Καλεί ΜΟΝΟ Controllers.
 * - Ό,τι μπορεί να καθυστερήσει εκτελείται σε background (SwingWorker)
 *   για να μην παγώνει το EDT.
 * - UI ενημέρωση μόνο στο EDT (π.χ. SwingWorker#done).
 */

package packageGUI;
