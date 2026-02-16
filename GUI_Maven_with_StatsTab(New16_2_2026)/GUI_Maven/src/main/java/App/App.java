package App;

import javax.swing.SwingUtilities;
import packageGUI.MainForm;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm f = new MainForm();
            f.setVisible(true);
        });
    }
}
