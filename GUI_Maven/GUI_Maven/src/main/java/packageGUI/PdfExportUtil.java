package packageGUI;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.swing.JTable;
import java.io.File;
import java.io.IOException;

/**
 * Small helper for exporting the Statistics tab tables to a PDF file.
 * Keeps formatting intentionally simple (monospace-like text layout).
 */
final class PdfExportUtil {

    private PdfExportUtil() {}

    public static void exportTablesToPdf(File out, JTable topKeywords, JTable savedByCategory) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            float margin = 40;
            float y = page.getMediaBox().getHeight() - margin;
            float x = margin;
            float leading = 14;

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 14);
                cs.newLineAtOffset(x, y);
                cs.showText("Statistics & Keywords");
                cs.endText();
            }

            y -= 2 * leading;
            y = writeTable(doc, page, y, "Top Keywords (αναζητήσεις χρήστη)", topKeywords);
            y -= leading;
            writeTable(doc, page, y, "Saved Articles ανά Κατηγορία", savedByCategory);

            doc.save(out);
        }
    }

    private static float writeTable(PDDocument doc, PDPage page, float startY, String title, JTable tbl) throws IOException {
        float margin = 40;
        float pageHeight = page.getMediaBox().getHeight();
        float pageWidth = page.getMediaBox().getWidth();
        float y = startY;
        float x = margin;
        float leading = 12;

        PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);

        // title
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
        cs.newLineAtOffset(x, y);
        cs.showText(title);
        cs.endText();
        y -= leading;

        // header
        String header = buildRow(tbl, -1);
        cs.beginText();
        cs.setFont(PDType1Font.COURIER_BOLD, 10);
        cs.newLineAtOffset(x, y);
        cs.showText(truncate(header, pageWidth - 2 * margin));
        cs.endText();
        y -= leading;

        // rows
        cs.setFont(PDType1Font.COURIER, 10);
        for (int r = 0; r < tbl.getRowCount(); r++) {
            if (y < margin + leading * 3) {
                cs.close();
                page = new PDPage(PDRectangle.A4);
                doc.addPage(page);
                y = pageHeight - margin;
                cs = new PDPageContentStream(doc, page);
            }

            String row = buildRow(tbl, r);
            cs.beginText();
            cs.newLineAtOffset(x, y);
            cs.showText(truncate(row, pageWidth - 2 * margin));
            cs.endText();
            y -= leading;
        }

        cs.close();
        return y;
    }

    private static String buildRow(JTable t, int row) {
        // fixed-ish spacing: col0 padded, col1.. appended.
        StringBuilder sb = new StringBuilder();
        for (int c = 0; c < t.getColumnCount(); c++) {
            String v;
            if (row < 0) v = String.valueOf(t.getColumnName(c));
            else {
                Object o = t.getValueAt(row, c);
                v = o == null ? "" : String.valueOf(o);
            }
            if (c == 0) {
                sb.append(padRight(v, 30));
            } else {
                sb.append("  ");
                sb.append(v);
            }
        }
        return sb.toString();
    }

    private static String padRight(String s, int n) {
        if (s == null) s = "";
        if (s.length() >= n) return s.substring(0, n);
        return s + " ".repeat(n - s.length());
    }

    private static String truncate(String s, float maxWidth) {
        // crude: assume ~6px/char at font size 10 in Courier -> 5.5-6.
        int maxChars = (int) (maxWidth / 6.0);
        if (s.length() <= maxChars) return s;
        return s.substring(0, Math.max(0, maxChars - 1)) + "…";
    }
}
