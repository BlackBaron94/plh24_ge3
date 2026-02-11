package packageEntities;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity: Article
 *
 * ==TO DO : Map to DB table ARTICLE==
 * Suggested columns:
 * - article_id (PK, surrogate) OR page_id (unique)
 * - title
 * - source (DB/API)
 * - full_text (CLOB)
 * - created_at / updated_at
 */
public class Article {

    private long pageId;                 // Wikipedia PageID
    private String title;
    private ArticleSource source;
    private String fullText;

    // many-to-many via ArticleCategory
    private final List<Category> categories = new ArrayList<>();

    public Article(long pageId, String title) {
        this.pageId = pageId;
        this.title = title;
        this.source = ArticleSource.DB;
    }

    public long getPageId() { return pageId; }
    public void setPageId(long pageId) { this.pageId = pageId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ArticleSource getSource() { return source; }
    public void setSource(ArticleSource source) { this.source = source; }

    public String getFullText() { return fullText; }
    public void setFullText(String fullText) { this.fullText = fullText; }

    public List<Category> getCategories() { return categories; }

    public void addCategory(Category c) {
        if (c != null && !categories.contains(c)) categories.add(c);
    }
}
