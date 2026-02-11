package packageController;

import java.util.List;

/**
 * Row shown in Saved Articles table (DB list).
 *
 * Includes Rating/Comments metadata.
 */
public record SavedArticleRow(
        String title,
        long pageId,
        String savedAt,          // for UI (later: use LocalDateTime)
        String source,           // "DB"
        List<String> categories, // many-to-many
        int rating,              // 0..5
        String comments          // free text (may include multiple lines)
) { }
