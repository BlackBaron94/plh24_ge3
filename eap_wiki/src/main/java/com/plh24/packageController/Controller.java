package com.plh24.packageController;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    public static interface WikiController {
        List<SearchResultRow> search(String query, SearchMode mode, List<String> categoryFilter);
        List<SavedArticleRow> listSaved(List<String> categoryFilter);
        void saveArticle(long pageId, String title, String source, List<String> categories);
        void updateSavedMetadata(long pageId, int rating0to5, String comments, List<String> categories);
        void deleteSaved(long pageId);
        void clearAllSaved();
        ArticleDetails getDetails(long pageId);
        StatsSnapshot getStats();
        default StatsSnapshot loadStats() { return getStats(); }
    }

    public static enum SearchMode { DB_ONLY, DB_API }

    public static record ArticleDetails(
            String title,
            long pageId,
            String source,
            List<String> categories,
            String fullText,
            int rating,
            String comments
    ) { }

    public static final class KeywordStatRow {
        private final String keyword;
        private final int count;

        public KeywordStatRow(String keyword, int count) {
            this.keyword = keyword;
            this.count = count;
        }

        public String keyword() { return keyword; }
        public int count() { return count; }
    }

    public static record CategoryStatRow(String category, int articles) {}

    public static record SavedArticleRow(
            String title,
            long pageId,
            String savedAt,
            String source,
            List<String> categories,
            int rating,
            String comments
    ) { }

    public static record SearchResultRow(
            String title,
            long pageId,
            String source,
            List<String> categories
    ) { }

    public static record StatsSnapshot(
            List<KeywordStatRow> keywordStats,
            List<CategoryStatRow> categoryStats
    ) {}
    
    public static class WikiControllerImpl implements WikiController {

        private final List<SearchResultRow> dummySearchPool;
        private final List<SavedArticleRow> dummySaved;
        private final Map<Long, ArticleDetails> dummyDetailsById;
        private final StatsSnapshot dummyStats;

        public WikiControllerImpl() {
            dummySearchPool = List.of(
                    new SearchResultRow("Artificial intelligence", 45678L, "DB", List.of("Υπολογιστές", "ΑΙ", "Προγραμματισμός")),
                    new SearchResultRow("Machine learning", 22345L, "DB", List.of("Υπολογιστές", "ΑΙ")),
                    new SearchResultRow("Deep learning", 99999L, "API", List.of("Υπολογιστές", "ΑΙ")),
                    new SearchResultRow("Big data", 34567L, "DB", List.of("Υπολογιστές", "Δεδομένα")),
                    new SearchResultRow("Byzantine Empire", 11223L, "API", List.of("Ιστορία"))
            );

            dummySaved = new ArrayList<>(List.of(
                    new SavedArticleRow("Artificial intelligence", 45678L, "2026-02-05 15:33", "DB",
                            List.of("Υπολογιστές", "ΑΙ", "Προγραμματισμός"), 4,
                            "Πολύ καλό άρθρο.\nΝα δω το section 'Applications'."),
                    new SavedArticleRow("Machine learning", 22345L, "2026-02-07 10:01", "DB",
                            List.of("Υπολογιστές", "ΑΙ"), 5,
                            "Εξαιρετικό. Χρήσιμο για το project."),
                    new SavedArticleRow("Big data", 34567L, "2026-02-06 17:55", "DB",
                            List.of("Υπολογιστές", "Δεδομένα"), 3,
                            "Να προσθέσω references."),
                    new SavedArticleRow("Byzantine Empire", 11223L, "2026-02-04 09:30", "DB",
                            List.of("Ιστορία"), 2,
                            "Σύντομο. Θέλει καλύτερη περίληψη.")
            ));

            dummyDetailsById = new HashMap<>();
            for (SearchResultRow r : dummySearchPool) {
                dummyDetailsById.put(r.pageId(),
                        new ArticleDetails(
                                r.title(),
                                r.pageId(),
                                r.source(),
                                r.categories(),
                                makeLongText(r.title()),
                                0,
                                ""
                        ));
            }
            for (SavedArticleRow s : dummySaved) {
                dummyDetailsById.put(s.pageId(),
                        new ArticleDetails(
                                s.title(),
                                s.pageId(),
                                s.source(),
                                s.categories(),
                                makeLongText(s.title()),
                                s.rating(),
                                s.comments()
                        ));
            }

            dummyStats = new StatsSnapshot(
                    Arrays.asList(
                            new KeywordStatRow("Concurrency", 34),
                            new KeywordStatRow("Deep learning", 29),
                            new KeywordStatRow("Big data", 22),
                            new KeywordStatRow("Python", 18),
                            new KeywordStatRow("Machine learning", 15)
                    ),
                    Arrays.asList(
                            new CategoryStatRow("Χωρίς Κατηγορία", 7),
                            new CategoryStatRow("Πολιτισμός", 9),
                            new CategoryStatRow("Ιστορία", 11),
                            new CategoryStatRow("Υπολογιστές", 13)
                    )
            );
        }

        @Override
        public List<SearchResultRow> search(String keywords, SearchMode mode, List<String> checkedCategories) {
            return filterByCategories(dummySearchPool, checkedCategories);
        }

        @Override
        public List<SavedArticleRow> listSaved(List<String> checkedCategories) {
            return filterByCategoriesSaved(dummySaved, checkedCategories);
        }

        @Override
        public ArticleDetails getDetails(long pageId) {
            return dummyDetailsById.getOrDefault(pageId,
                    new ArticleDetails("Unknown", pageId, "DB", List.of("Χωρίς Κατηγορία"),
                            "No text (dummy).", 0, ""));
        }

        @Override
        public void saveArticle(long pageId, String title, String source, List<String> categories) {
            boolean exists = dummySaved.stream().anyMatch(s -> s.pageId() == pageId);
            if (exists) return;

            String savedAt = java.time.LocalDateTime.now().toString();
            List<String> cats = (categories == null || categories.isEmpty())
                    ? List.of("Χωρίς Κατηγορία")
                    : List.copyOf(categories);

            String src = (source == null || source.isBlank()) ? "DB" : source.trim();
            dummySaved.add(new SavedArticleRow(title, pageId, savedAt, src, cats, 0, ""));
        }

        @Override
        public void updateSavedMetadata(long pageId, int rating, String comments, List<String> categories) {
            for (int i = 0; i < dummySaved.size(); i++) {
                SavedArticleRow s = dummySaved.get(i);
                if (s.pageId() == pageId) {
                    List<String> cats = (categories == null || categories.isEmpty())
                            ? s.categories()
                            : List.copyOf(categories);
                    dummySaved.set(i, new SavedArticleRow(
                            s.title(),
                            s.pageId(),
                            s.savedAt(),
                            s.source(),
                            cats,
                            clampRating(rating),
                            comments == null ? "" : comments
                    ));
                    break;
                }
            }

            ArticleDetails d = dummyDetailsById.get(pageId);
            if (d != null) {
                List<String> cats = (categories == null || categories.isEmpty())
                        ? d.categories()
                        : List.copyOf(categories);
                dummyDetailsById.put(pageId, new ArticleDetails(
                        d.title(), d.pageId(), d.source(), cats, d.fullText(),
                        clampRating(rating),
                        comments == null ? "" : comments
                ));
            }
        }

        @Override
        public void deleteSaved(long pageId) { dummySaved.removeIf(s -> s.pageId() == pageId); }

        @Override
        public void clearAllSaved() { dummySaved.clear(); }

        @Override
        public StatsSnapshot getStats() { return dummyStats; }

        private static boolean isAllCategories(List<String> checkedCategories) {
            if (checkedCategories == null || checkedCategories.isEmpty()) return true;
            return checkedCategories.stream().anyMatch(c -> "Όλες οι κατηγορίες".equalsIgnoreCase(c));
        }

        private static List<SearchResultRow> filterByCategories(List<SearchResultRow> rows, List<String> checkedCategories) {
            if (isAllCategories(checkedCategories)) return rows;

            Set<String> checked = checkedCategories.stream()
                    .filter(c -> c != null && !c.isBlank())
                    .collect(Collectors.toSet());

            return rows.stream()
                    .filter(r -> r.categories() != null && r.categories().stream().anyMatch(checked::contains))
                    .toList();
        }

        private static List<SavedArticleRow> filterByCategoriesSaved(List<SavedArticleRow> rows, List<String> checkedCategories) {
            if (isAllCategories(checkedCategories)) return rows;

            Set<String> checked = checkedCategories.stream()
                    .filter(c -> c != null && !c.isBlank())
                    .collect(Collectors.toSet());

            return rows.stream()
                    .filter(r -> r.categories() != null && r.categories().stream().anyMatch(checked::contains))
                    .toList();
        }

        private static int clampRating(int r) { return Math.max(0, Math.min(5, r)); }

        private static String makeLongText(String title) {
            return "Long text for " + title + " (dummy)\n\nLorem ipsum...";
        }
        
        
    }
    
}
