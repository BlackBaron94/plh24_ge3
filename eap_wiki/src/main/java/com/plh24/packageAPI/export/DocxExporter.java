package com.plh24.packageAPI.export;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Simple DOCX export utility (Apache POI XWPF).
 */
/** 
 * Εδώ ορίζω το class <b>DocxExporter</b> στο API [διεπαφή υπηρεσίας].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public final class DocxExporter {

    /**

     * Εδώ αρχικοποιώ το <b>DocxExporter</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>DocxExporter()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private DocxExporter() { }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>export()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param outFile Παράμετρος εισόδου.

     * @param title Παράμετρος εισόδου.

     * @param metaLines Παράμετρος εισόδου.

     * @param bodyText Παράμετρος εισόδου.

     */

    public static void export(Path outFile, String title, List<String> metaLines, String bodyText) throws IOException {
        try (XWPFDocument doc = new XWPFDocument()) {

            // Title
            XWPFParagraph pTitle = doc.createParagraph();
            XWPFRun rTitle = pTitle.createRun();
            rTitle.setBold(true);
            rTitle.setFontFamily("Arial");
            rTitle.setFontSize(16);
            rTitle.setText(title == null ? "" : title);

            // Meta
            for (String line : metaLines) {
                XWPFParagraph p = doc.createParagraph();
                XWPFRun r = p.createRun();
                r.setFontFamily("Arial");
                r.setFontSize(11);
                r.setText(line == null ? "" : line);
            }

            doc.createParagraph(); // blank

            // Body
            for (String para : bodyText.split("\n\n")) {
                XWPFParagraph p = doc.createParagraph();
                XWPFRun r = p.createRun();
                r.setFontFamily("Arial");
                r.setFontSize(11);
                r.setText(para == null ? "" : para);
            }

            try (FileOutputStream out = new FileOutputStream(outFile.toFile())) {
                doc.write(out);
            }
        }
    }
}
