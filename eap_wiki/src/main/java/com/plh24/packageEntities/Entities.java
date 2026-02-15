package com.plh24.packageEntities;

import java.util.ArrayList;
import java.util.List;

public class Entities {

    public static class Article {
        private long pageId;
        private String title;
        private ArticleSource source;
        private String fullText;
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
        public void addCategory(Category c) { if (c != null && !categories.contains(c)) categories.add(c); }

        public Object getCategory() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
        }
    }

    public static class Category {
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

        @Override public String toString() { return name; }
    }

    public static enum ArticleSource { DB, API }
    public static class ArticleMetadata {
        private long pageId;
        private int rating;
        private String comments;

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

    public static class ArticleCategory {
        private long pageId;
        private long categoryId;

        public ArticleCategory(long pageId, long categoryId) {
            this.pageId = pageId;
            this.categoryId = categoryId;
        }

        public long getPageId() { return pageId; }
        public long getCategoryId() { return categoryId; }
    }
}
