package packageController;

import java.util.List;

/**
 * Row shown in Search results table.
 *
 * Category is many-to-many, so we keep a List<String> categories and the GUI
 * renders it as: "Cat1, Cat2, Cat3".
 */
public record SearchResultRow(
        String title,
        long pageId,
        String source,
        List<String> categories
) { }
