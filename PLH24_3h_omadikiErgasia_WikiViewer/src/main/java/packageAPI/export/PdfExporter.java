package packageAPI.export;

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
public final class PdfExporter {

    private PdfExporter() { }

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

    private static String safe(String s) {
        if (s == null) return "";
        // PDFBox Type1 fonts are limited; keep ASCII-ish. Real fix: use Unicode font.
        return s.replace("\t", " ").replace("\r", " ");
    }
}
