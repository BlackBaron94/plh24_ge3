package packageAPI.export;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Simple DOCX export utility (Apache POI XWPF).
 */
public final class DocxExporter {

    private DocxExporter() { }

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
