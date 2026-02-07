/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dimitris
 */

package packageMain;

import javax.swing.SwingUtilities;
import packageGUI.MainFrame;

public final class App {

    private App() { }

    /**
     * Κεντρικό entry point (σημείο εκκίνησης).
     * Αν περάσεις argument "info", ανοίγει το DevPackageInfoApp.
     * Αλλιώς ανοίγει το MainFrame (GUI).
     *
     * @param args ορίσματα (arguments) εκκίνησης
     */
    public static void main(String[] args) {
        //Για να δείς συνοπτικά τι περιέχουν τα Packages ΠΡΟΣΩΡΙΝΌ
        SwingUtilities.invokeLater(() -> new DevPackageInfoApp().showUI());

        // Κανονική εκκίνηση GUI (Graphical User Interface) στο EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
