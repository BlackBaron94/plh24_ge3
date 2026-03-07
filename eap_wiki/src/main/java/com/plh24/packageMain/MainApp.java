/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.plh24.packageMain;
import com.plh24.packageGUI.MainFrame;

/**
 * Κλάση εφαρμογής. Από εδώ τρέχει το πρόγραμμα (βλ. nbactions.xml)
 * @author Γιώργος Τσολακίδης
 */
public class MainApp {

    /**
     * Main που καλείται όταν τρέχει το project
     * @param args: Τα arguments του command line
     */
    public static void main(String[] args) {
        // Παίρνει το Nimbus LookAndFeel για εμφάνιση τύπου Nimbus
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // Αρχικοποίηση του κύριου παραθύρου
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();

            // Κεντράρισμα στην οθόνη
            frame.setLocationRelativeTo(null);

            // Εμφάνιση
            frame.setVisible(true);
        });
    }
}
