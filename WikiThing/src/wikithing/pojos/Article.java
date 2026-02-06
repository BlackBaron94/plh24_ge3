/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikithing.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Equinox
 */
@Entity
@Table(name = "ARTICLE")
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findByArticleId", query = "SELECT a FROM Article a WHERE a.articleId = :articleId"),
    @NamedQuery(name = "Article.findByPageId", query = "SELECT a FROM Article a WHERE a.pageId = :pageId"),
    @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title"),
    @NamedQuery(name = "Article.findByNotes", query = "SELECT a FROM Article a WHERE a.notes = :notes"),
    @NamedQuery(name = "Article.findByRating", query = "SELECT a FROM Article a WHERE a.rating = :rating"),
    @NamedQuery(name = "Article.findBySavedAt", query = "SELECT a FROM Article a WHERE a.savedAt = :savedAt")})
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ARTICLE_ID")
    private Integer articleId;
    @Basic(optional = false)
    @Column(name = "PAGE_ID")
    private String pageId;
    @Basic(optional = false)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "NOTES")
    private String notes;
    @Column(name = "RATING")
    private Short rating;
    @Basic(optional = false)
    @Column(name = "SAVED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date savedAt;
    @OneToMany(mappedBy = "articleId")
    private List<ArticlesCategoriesMapping> articlesCategoriesMappingList;

    public Article() {
    }

    public Article(Integer articleId) {
        this.articleId = articleId;
    }

    public Article(Integer articleId, String pageId, String title, Date savedAt) {
        this.articleId = articleId;
        this.pageId = pageId;
        this.title = title;
        this.savedAt = savedAt;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public List<ArticlesCategoriesMapping> getArticlesCategoriesMappingList() {
        return articlesCategoriesMappingList;
    }

    public void setArticlesCategoriesMappingList(List<ArticlesCategoriesMapping> articlesCategoriesMappingList) {
        this.articlesCategoriesMappingList = articlesCategoriesMappingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articleId != null ? articleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.articleId == null && other.articleId != null) || (this.articleId != null && !this.articleId.equals(other.articleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wikithing.pojos.Article[ articleId=" + articleId + " ]";
    }
    
}
