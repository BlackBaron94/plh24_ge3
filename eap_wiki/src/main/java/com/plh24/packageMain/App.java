
package com.plh24.packageMain;

import javax.swing.SwingUtilities;
import com.plh24.packageController.Controller;
import com.plh24.packageGUI.MainFrame;

/** 

 * Εδώ ορίζω το class <b>App</b> στην εκκίνηση εφαρμογής.

 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, 
 * ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>

 */

public final class App {

    /**

     * Εδώ αρχικοποιώ το <b>App</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>App()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private App() { }

    /**
     * Κεντρικό entry point (σημείο εκκίνησης).
     *
     * @param args ορίσματα (arguments) εκκίνησης
     */
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>main()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param args Παράμετρος εισόδου.
     */
    public static void main(String[] args) {
        // Εκκίνηση GUI (Graphical User Interface) στο EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            Controller.WikiController controller = new Controller.WikiControllerImpl();
            new MainFrame(controller).setVisible(true);
        });
    }
}
