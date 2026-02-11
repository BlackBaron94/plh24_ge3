package packageEntities;

/**
 * Association entity (many-to-many): Article <-> Category
 *
 * ==TO DO : Map to DB join table ARTICLE_CATEGORY==
 * Suggested columns:
 * - page_id (FK -> ARTICLE.page_id)
 * - category_id (FK -> CATEGORY.category_id)
 * PK: (page_id, category_id)
 */
public class ArticleCategory {

    private long pageId;
    private long categoryId;

    public ArticleCategory(long pageId, long categoryId) {
        this.pageId = pageId;
        this.categoryId = categoryId;
    }

    public long getPageId() { return pageId; }
    public long getCategoryId() { return categoryId; }
}
