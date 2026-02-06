/**
 * packageGUI
 *
 * Swing GUI της εφαρμογής:
 * - MainFrame (JFrame), Panels (JPanel), Dialogs (JDialog)
 * - Event handlers και UI models (π.χ. TableModel)
 *
 * Κανόνες:
 * - Δεν περιέχει DB/API λογική. Καλεί μόνο Controllers.
 * - Ό,τι μπορεί να καθυστερήσει τρέχει σε background (SwingWorker) για να μην παγώνει το EDT.
 * - UI update μόνο στο EDT (π.χ. SwingWorker#done).
 */

package packageGUI;
