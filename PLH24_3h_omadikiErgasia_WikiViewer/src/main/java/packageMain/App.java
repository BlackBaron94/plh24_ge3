
package packageMain;

import javax.swing.SwingUtilities;
import packageController.WikiController;
import packageController.WikiControllerImpl;
import packageGUI.MainFrame;

public final class App {

    private App() { }

    /**
     * Κεντρικό entry point (σημείο εκκίνησης).
     *
     * @param args ορίσματα (arguments) εκκίνησης
     */
    public static void main(String[] args) {
        // Εκκίνηση GUI (Graphical User Interface) στο EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            WikiController controller = new WikiControllerImpl();
            new MainFrame(controller).setVisible(true);
        });
    }
}
