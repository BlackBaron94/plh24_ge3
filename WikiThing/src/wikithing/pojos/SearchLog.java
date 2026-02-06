/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikithing.pojos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Equinox
 */
@Entity
@Table(name = "SEARCH_LOG")
@NamedQueries({
    @NamedQuery(name = "SearchLog.findAll", query = "SELECT s FROM SearchLog s"),
    @NamedQuery(name = "SearchLog.findBySearchId", query = "SELECT s FROM SearchLog s WHERE s.searchId = :searchId"),
    @NamedQuery(name = "SearchLog.findByKeyword", query = "SELECT s FROM SearchLog s WHERE s.keyword = :keyword"),
    @NamedQuery(name = "SearchLog.findBySearchedAt", query = "SELECT s FROM SearchLog s WHERE s.searchedAt = :searchedAt")})
public class SearchLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SEARCH_ID")
    private Integer searchId;
    @Basic(optional = false)
    @Column(name = "KEYWORD")
    private String keyword;
    @Basic(optional = false)
    @Column(name = "SEARCHED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date searchedAt;

    public SearchLog() {
    }

    public SearchLog(Integer searchId) {
        this.searchId = searchId;
    }

    public SearchLog(Integer searchId, String keyword, Date searchedAt) {
        this.searchId = searchId;
        this.keyword = keyword;
        this.searchedAt = searchedAt;
    }

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getSearchedAt() {
        return searchedAt;
    }

    public void setSearchedAt(Date searchedAt) {
        this.searchedAt = searchedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (searchId != null ? searchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SearchLog)) {
            return false;
        }
        SearchLog other = (SearchLog) object;
        if ((this.searchId == null && other.searchId != null) || (this.searchId != null && !this.searchId.equals(other.searchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wikithing.pojos.SearchLog[ searchId=" + searchId + " ]";
    }
    
}
