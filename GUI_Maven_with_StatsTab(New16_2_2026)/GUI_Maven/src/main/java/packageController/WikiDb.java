package packageController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Tiny embedded DB layer for the prototype (SQLite).
 * Stores:
 *  - saved articles (id/title/snippet/category/rating)
 *  - comments (article_id, username, body, created_at)
 *  - search log (keyword, searched_at) for Statistics (R5)
 */
public final class WikiDb {

    public record SavedArticle(String id, String title, String snippet, String category, int rating) {}
    public record Comment(String username, String body, String createdAt) {}
    public record KeywordStat(String keyword, int count) {}
    public record CategoryStat(String category, int count) {}

    private final String jdbcUrl;

    public WikiDb() {
        // project-local DB file under user-home (so it persists between runs)
        Path dir = Path.of(System.getProperty("user.home"), ".plh24_wikiviewer");
        try {
            Files.createDirectories(dir);
        } catch (Exception ignored) {
        }
        Path dbFile = dir.resolve("wikiviewer.db");
        this.jdbcUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath();
        initSchema();
    }

    private Connection open() throws SQLException {
        return DriverManager.getConnection(jdbcUrl);
    }

    private void initSchema() {
        try (Connection c = open(); Statement st = c.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS saved_articles (" +
                            "id TEXT PRIMARY KEY," +
                            "title TEXT NOT NULL," +
                            "snippet TEXT," +
                            "category TEXT," +
                            "rating INTEGER DEFAULT 0" +
                            ")"
            );
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS article_comments (" +
                            "comment_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "article_id TEXT NOT NULL," +
                            "username TEXT NOT NULL," +
                            "body TEXT NOT NULL," +
                            "created_at TEXT NOT NULL," +
                            "FOREIGN KEY(article_id) REFERENCES saved_articles(id) ON DELETE CASCADE" +
                            ")"
            );

            // R5: keyword search log
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS search_log (" +
                            "search_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "keyword TEXT NOT NULL," +
                            "searched_at TEXT NOT NULL" +
                            ")"
            );
        } catch (SQLException e) {
            // If schema init fails, we still want the GUI to run.
            e.printStackTrace();
        }
    }

    /**
     * R5: log search terms (splits by whitespace).
     * Stores each term as a separate keyword (lowercased) for frequency stats.
     */
    public void logSearchTerms(String query) {
        if (query == null) return;
        String q = query.trim();
        if (q.isEmpty()) return;

        String[] parts = q.split("\\s+");
        List<String> terms = new ArrayList<>();
        for (String p : parts) {
            String t = p.trim().toLowerCase();
            if (t.length() < 2) continue;
            terms.add(t);
        }
        if (terms.isEmpty()) return;

        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO search_log(keyword, searched_at) VALUES (?,?)")) {
            for (String t : terms) {
                ps.setString(1, t);
                ps.setString(2, LocalDateTime.now().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            // don't break GUI for stats logging
        }
    }

    /** R5: Top-N most frequent searched keywords. */
    public List<KeywordStat> getTopKeywords(int limit) throws SQLException {
        List<KeywordStat> out = new ArrayList<>();
        String sql = "SELECT keyword, COUNT(*) AS cnt FROM search_log GROUP BY keyword ORDER BY cnt DESC, keyword ASC LIMIT ?";
        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, Math.max(1, limit));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new KeywordStat(rs.getString(1), rs.getInt(2)));
                }
            }
        }
        return out;
    }

    /** R5: Saved articles count per category. */
    public List<CategoryStat> getSavedCountByCategory() throws SQLException {
        List<CategoryStat> out = new ArrayList<>();
        String sql = "SELECT COALESCE(category,'(χωρίς κατηγορία)') AS cat, COUNT(*) AS cnt " +
                "FROM saved_articles GROUP BY COALESCE(category,'(χωρίς κατηγορία)') ORDER BY cnt DESC, cat ASC";
        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new CategoryStat(rs.getString(1), rs.getInt(2)));
                }
            }
        }
        return out;
    }

    public void upsertSavedArticle(SavedArticle a) throws SQLException {
        String sql = "INSERT INTO saved_articles(id,title,snippet,category,rating) VALUES (?,?,?,?,?) " +
                "ON CONFLICT(id) DO UPDATE SET title=excluded.title, snippet=excluded.snippet, category=excluded.category, rating=excluded.rating";
        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.id());
            ps.setString(2, a.title());
            ps.setString(3, a.snippet());
            ps.setString(4, a.category());
            ps.setInt(5, a.rating());
            ps.executeUpdate();
        }
    }

    public void replaceComments(String articleId, List<Comment> comments) throws SQLException {
        try (Connection c = open()) {
            c.setAutoCommit(false);
            try (PreparedStatement del = c.prepareStatement("DELETE FROM article_comments WHERE article_id=?")) {
                del.setString(1, articleId);
                del.executeUpdate();
            }
            try (PreparedStatement ins = c.prepareStatement(
                    "INSERT INTO article_comments(article_id,username,body,created_at) VALUES (?,?,?,?)")) {
                for (Comment cm : comments) {
                    ins.setString(1, articleId);
                    ins.setString(2, cm.username());
                    ins.setString(3, cm.body());
                    ins.setString(4, cm.createdAt() == null ? LocalDateTime.now().toString() : cm.createdAt());
                    ins.addBatch();
                }
                ins.executeBatch();
            }
            c.commit();
            c.setAutoCommit(true);
        }
    }

    public List<SavedArticle> listSavedArticles() throws SQLException {
        List<SavedArticle> out = new ArrayList<>();
        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(
                "SELECT id,title,snippet,category,rating FROM saved_articles ORDER BY title COLLATE NOCASE")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new SavedArticle(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5)
                    ));
                }
            }
        }
        return out;
    }

    public SavedArticle getSavedArticle(String id) throws SQLException {
        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(
                "SELECT id,title,snippet,category,rating FROM saved_articles WHERE id=?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new SavedArticle(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            }
        }
    }

    public List<Comment> listComments(String articleId) throws SQLException {
        List<Comment> out = new ArrayList<>();
        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(
                "SELECT username, body, created_at FROM article_comments WHERE article_id=? ORDER BY comment_id")) {
            ps.setString(1, articleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
            }
        }
        return out;
    }
}
