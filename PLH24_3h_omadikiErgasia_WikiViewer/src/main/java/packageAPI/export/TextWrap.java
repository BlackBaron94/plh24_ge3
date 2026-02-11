package packageAPI.export;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tiny text wrapping helper for PDFBox.
 */
final class TextWrap {

    private TextWrap() { }

    static List<String> wrap(String text, PDType1Font font, float fontSize, float maxWidth) throws IOException {
        List<String> out = new ArrayList<>();
        if (text == null || text.isBlank()) {
            out.add("");
            return out;
        }

        String[] words = text.replace("\n", " ").split("\s+");
        StringBuilder line = new StringBuilder();

        for (String w : words) {
            String candidate = line.isEmpty() ? w : (line + " " + w);
            float width = font.getStringWidth(candidate) / 1000f * fontSize;

            if (width <= maxWidth) {
                line.setLength(0);
                line.append(candidate);
            } else {
                if (!line.isEmpty()) out.add(line.toString());
                line.setLength(0);
                line.append(w);
            }
        }
        if (!line.isEmpty()) out.add(line.toString());
        return out;
    }
}
