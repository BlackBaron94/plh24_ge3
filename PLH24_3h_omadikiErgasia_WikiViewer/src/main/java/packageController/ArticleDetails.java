package packageController;

import java.util.List;

/**
 * Full article details for "View Details" / "Load Article" dialogs.
 */
public record ArticleDetails(
        String title,
        long pageId,
        String source,
        List<String> categories,
        String fullText,
        int rating,      // 0..5
        String comments  // free text
) { }
