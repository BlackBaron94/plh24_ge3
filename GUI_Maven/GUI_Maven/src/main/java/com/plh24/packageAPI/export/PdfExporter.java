package com.plh24.packageAPI.export;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Simple PDF export utility (no fancy layout).
 *
 * ==TO DO : Improve layout / Unicode fonts==
 * - For Greek text you may need a Unicode TrueType font (PDType0Font.load)
 *   and use that font instead of PDType1Font.HELVETICA.
 */
/** 
 * Εδώ ορίζω το class <b>PdfExporter</b> στο API [διεπαφή υπηρεσίας].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public final class PdfExporter {

    /**

     * Εδώ αρχικοποιώ το <b>PdfExporter</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>PdfExporter()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private PdfExporter() { }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>export()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param outFile Παράμετρος εισόδου.

     * @param title Παράμετρος εισόδου.

     * @param metaLines Παράμετρος εισόδου.

     * @param bodyText Παράμετρος εισόδου.

     */

    public static void export(Path outFile, String title, List<String> metaLines, String bodyText) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float width = page.getMediaBox().getWidth() - 2 * margin;

            float fontSizeTitle = 16;
            float fontSize = 11;
            float leading = 1.35f * fontSize;

            PDType1Font font = PDType1Font.HELVETICA;
            PDType1Font fontBold = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            float y = yStart;

            // Title
            cs.beginText();
            cs.setFont(fontBold, fontSizeTitle);
            cs.newLineAtOffset(margin, y);
            cs.showText(safe(title));
            cs.endText();
            y -= (fontSizeTitle * 1.8f);

            // Meta lines
            cs.setFont(font, fontSize);
            for (String line : metaLines) {
                y = writeWrappedLine(doc, cs, page, margin, y, width, leading, font, fontSize, line);
            }
            y -= leading;

            // Body
            for (String para : bodyText.split("\n\n")) {
                y = writeWrappedLine(doc, cs, page, margin, y, width, leading, font, fontSize, para);
                y -= leading;
            }

            cs.close();
            doc.save(outFile.toFile());
        }
    }

    // Very simple wrapping; adds new pages if needed.
    /**
     * Εδώ υλοποιώ τη μέθοδο <b>writeWrappedLine()</b>.
     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.
     * @param doc Παράμετρος εισόδου.
     * @param cs Παράμετρος εισόδου.
     * @param page Παράμετρος εισόδου.
     * @param x Παράμετρος εισόδου.
     * @param y Παράμετρος εισόδου.
     * @param width Παράμετρος εισόδου.
     * @param leading Παράμετρος εισόδου.
     * @param font Παράμετρος εισόδου.
     * @param fontSize Παράμετρος εισόδου.
     * @param text Παράμετρος εισόδου.
     * @return Επιστρέφω αποτέλεσμα.
     */
    private static float writeWrappedLine(PDDocument doc, PDPageContentStream cs, PDPage page,
                                         float x, float y, float width, float leading,
                                         PDType1Font font, float fontSize, String text) throws IOException {

        List<String> lines = TextWrap.wrap(text, font, fontSize, width);

        for (String line : lines) {
            if (y < 60) {
                // new page
                cs.close();
                PDPage newPage = new PDPage(PDRectangle.A4);
                doc.addPage(newPage);
                page = newPage;
                y = page.getMediaBox().getHeight() - 50;
                cs = new PDPageContentStream(doc, page);
            }

            cs.beginText();
            cs.setFont(font, fontSize);
            cs.newLineAtOffset(x, y);
            cs.showText(safe(line));
            cs.endText();
            y -= leading;
        }
        return y;
    }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>safe()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param s Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private static String safe(String s) {
        if (s == null) return "";
        // PDFBox Type1 fonts are limited; keep ASCII-ish. Real fix: use Unicode font.
        return s.replace("\t", " ").replace("\r", " ");
    }
}
