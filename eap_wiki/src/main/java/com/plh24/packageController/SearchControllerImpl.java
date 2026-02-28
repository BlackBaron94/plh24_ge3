/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageController;

import com.plh24.packageAPI.WikiApiClient;
import com.plh24.packageEntities.Article;
import com.plh24.packageEntities.Category;
import com.plh24.packageEntities.SearchLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.plh24.packageAPI.APIMain.stripSnippetHTMLTags;

/**
 *
 * @author Dimitris
 */

public class SearchControllerImpl implements SearchController {

    // Καλύτερα ένα EMF για όλη την εφαρμογή (όχι create/close συνέχεια)
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("EapWikiPU");

    private final WikiApiClient api = new WikiApiClient();

    @Override
    public SearchResults runSearch(String keyword) {
        logSearchEvent(keyword);
        Set<String> dbTitles = searchSavedArticleTitles(keyword);

        // Wiki API
        String wikiJson;
        try {
            wikiJson = api.searchWikipedia(keyword);
        } catch (Exception ex) {
            // αν “πέσει” το API, δίνουμε τουλάχιστον τα DB αποτελέσματα
            return new SearchResults(dbTitles, List.of());
        }

        List<WikiHit> hits = parseWikiHits(wikiJson);
        return new SearchResults(dbTitles, hits);
    }

    @Override
    public void logSearchEvent(String keyword) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new SearchLog(keyword)); // event log
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Set<String> searchSavedArticleTitles(String keyword) {
        EntityManager em = EMF.createEntityManager();
        try {
            List<String> titles = em.createQuery(
                    "SELECT a.title FROM Article a " +
                    "WHERE UPPER(a.title) LIKE :kw ORDER BY a.title",
                    String.class
            ).setParameter("kw", "%" + keyword.toUpperCase() + "%")
             .getResultList();

            return new LinkedHashSet<>(titles);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean saveDefaultArticleIfNotExists(String title) {
        EntityManager em = EMF.createEntityManager();
        try {
            Long cnt = em.createQuery(
                    "SELECT COUNT(a) FROM Article a WHERE a.title = :t",
                    Long.class
            ).setParameter("t", title)
             .getSingleResult();

            if (cnt != null && cnt > 0) return false;

            Category uncategorized = em.createNamedQuery("Category.findByName", Category.class)
                    .setParameter("name", "Χωρίς Κατηγορία")
                    .getSingleResult();

            em.getTransaction().begin();
            Article a = new Article(title, uncategorized, null); // rating=null, comments=null
            em.persist(a);
            em.getTransaction().commit();
            return true;

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static List<WikiHit> parseWikiHits(String wikiJson) {
        JSONObject obj = new JSONObject(wikiJson);
        JSONObject query = obj.getJSONObject("query");
        JSONArray searchResults = query.getJSONArray("search");

        java.util.ArrayList<WikiHit> list = new java.util.ArrayList<>();
        for (int i = 0; i < searchResults.length(); i++) {
            JSONObject item = searchResults.getJSONObject(i);
            String title = item.getString("title");
            int wordCount = item.getInt("wordcount");
            String snippet = stripSnippetHTMLTags(item.getString("snippet"));
            list.add(new WikiHit(title, wordCount, snippet));
        }
        return list;
    }
}