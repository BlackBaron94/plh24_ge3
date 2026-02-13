package com.plh24.packageAPI.export;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tiny text wrapping helper for PDFBox.
 */
/** 
 * Εδώ ορίζω το class <b>TextWrap</b> στο API [διεπαφή υπηρεσίας].
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
final class TextWrap {

    /**

     * Εδώ αρχικοποιώ το <b>TextWrap</b>.

     * Φροντίζω να στήσω την αρχική κατάσταση του αντικειμένου (fields, defaults, listeners κ.λπ.).

     */

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>TextWrap()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @return Επιστρέφω αποτέλεσμα.

     */

    private TextWrap() { }

    /**

     * Εδώ υλοποιώ τη μέθοδο <b>wrap()</b>.

     * Τη χρησιμοποιώ για να εκτελέσω τη συγκεκριμένη λειτουργία με ασφαλή τρόπο.

     * @param text Παράμετρος εισόδου.

     * @param font Παράμετρος εισόδου.

     * @param fontSize Παράμετρος εισόδου.

     * @param maxWidth Παράμετρος εισόδου.

     * @return Επιστρέφω αποτέλεσμα.

     */

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
