/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikithing.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Equinox
 */
@Entity
@Table(name = "ARTICLES_CATEGORIES_MAPPING")
@NamedQueries({
    @NamedQuery(name = "ArticlesCategoriesMapping.findAll", query = "SELECT a FROM ArticlesCategoriesMapping a"),
    @NamedQuery(name = "ArticlesCategoriesMapping.findByMappingId", query = "SELECT a FROM ArticlesCategoriesMapping a WHERE a.mappingId = :mappingId")})
public class ArticlesCategoriesMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MAPPING_ID")
    private Integer mappingId;
    @JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ARTICLE_ID")
    @ManyToOne
    private Article articleId;
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    @ManyToOne
    private Category categoryId;

    public ArticlesCategoriesMapping() {
    }

    public ArticlesCategoriesMapping(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mappingId != null ? mappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticlesCategoriesMapping)) {
            return false;
        }
        ArticlesCategoriesMapping other = (ArticlesCategoriesMapping) object;
        if ((this.mappingId == null && other.mappingId != null) || (this.mappingId != null && !this.mappingId.equals(other.mappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wikithing.pojos.ArticlesCategoriesMapping[ mappingId=" + mappingId + " ]";
    }
    
}
