package packageController;

import java.util.List;

/**
 * Συγκεντρωτικά στατιστικά για το Stats tab.
 */
public record StatsSnapshot(
        List<KeywordStatRow> keywordStats,
        List<CategoryStatRow> categoryStats
) {}
