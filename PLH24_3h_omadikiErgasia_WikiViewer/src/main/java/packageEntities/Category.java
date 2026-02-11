package packageEntities;

/**
 * Entity: Category
 *
 * ==TO DO : Map to DB table CATEGORY==
 * Suggested columns:
 * - category_id (PK)
 * - name (unique)
 */
public class Category {

    private long categoryId;
    private String name;

    public Category(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}
