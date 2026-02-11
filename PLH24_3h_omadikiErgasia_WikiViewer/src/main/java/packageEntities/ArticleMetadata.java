package packageEntities;

/**
 * Entity: ArticleMetadata (rating + comments)
 *
 * ==TO DO : Map to DB table ARTICLE_METADATA==
 * Suggested columns:
 * - page_id (PK/FK to ARTICLE.page_id)
 * - rating (0..5)
 * - comments (CLOB)
 * - updated_at
 *
 * Note: local metadata must NOT be deleted when Article is refreshed from API.
 */
public class ArticleMetadata {

    private long pageId;
    private int rating;       // 0..5
    private String comments;  // multi-line text

    public ArticleMetadata(long pageId) {
        this.pageId = pageId;
        this.rating = 0;
        this.comments = "";
    }

    public long getPageId() { return pageId; }
    public void setPageId(long pageId) { this.pageId = pageId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = Math.max(0, Math.min(5, rating)); }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = (comments == null) ? "" : comments; }
}
