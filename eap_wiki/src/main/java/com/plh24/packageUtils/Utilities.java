/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageUtils;

import com.plh24.packageEntities.Article;
import com.plh24.packageEntities.Category;
import com.plh24.packageAPI.WikiApiClient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import jakarta.persistence.NoResultException;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Equinox
 */
public class Utilities {
    public static void saveArticle(String title, Integer rating, Category category, String comments){
        System.out.println("\n" + title);
        System.out.println("\n" + rating);
        System.out.println("\n" + category);
        System.out.println("\n" + comments);
        if (rating == 0){
            rating = null;
        }
        if (comments.equals("")){
            comments = null;
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query findArticleByTitle = em.createNamedQuery("Article.findByTitle");
            findArticleByTitle.setParameter("title", title);
            Article article;
            try {
                article = (Article) findArticleByTitle.getSingleResult();
                System.out.println("Article already in DB");
                article.setCategory(category);
                System.out.println("Setting new category...");
                article.setComments(comments);
                System.out.println("setting new comments...");
                System.out.println(comments);
                article.setRating(rating);
                
            } catch (NoResultException nre) {
                article = new Article(title, rating, category, comments);
                System.out.println("Not found exception");
            }
            System.out.println("Vrhka auto: " + article);
            em.persist(article);
            em.getTransaction().commit();
            System.out.println("Saving DONE");
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
    
    public static Article getArticle(String title){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query findArticleByTitle = em.createNamedQuery("Article.findByTitle");
            findArticleByTitle.setParameter("title", title);
            Article article;
            try {
                article = (Article) findArticleByTitle.getSingleResult();
                return article;
            } catch (NoResultException nre) {
                System.out.println("De to vrhka, epistrefw null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
        return null;
    }
    
    public static void parseAndPrintResults(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        JSONObject query = obj.getJSONObject("query");
        JSONArray searchResults = query.getJSONArray("search");

        System.out.println("Αποτελέσματα Αναζήτησης:");
        for (int i = 0; i < searchResults.length(); i++) {
            JSONObject item = searchResults.getJSONObject(i);
            String title = item.getString("title");
            int wordCount = item.getInt("wordcount");
            
            System.out.println((i + 1) + ". " + title + " (" + wordCount + " λέξεις)");
            System.out.println("\tΤμήμα που ταιριάζει με την αναζήτηση: \"..." + stripSnippetHTMLTags(item.getString("snippet")) + "...\"");
        }
    }

   

    // Αφαιρεί τα HTML Tags που εμφανίζονται στο snippet (opening & closing
    // tags για span με class searchmatch
    public static String stripSnippetHTMLTags(String snippet) {
        // Pattern seeker για τα tags
        Pattern openingTagPattern = Pattern.compile("<span class=\"searchmatch\">");
        Pattern closingTagPattern = Pattern.compile("</span>");
        // Matcher που βρίσκει το pattern και το αντικαθιστά με κενό String
        Matcher openingTagMatcher = openingTagPattern.matcher(snippet);
        // Ανανέωση του snippet
        snippet = openingTagMatcher.replaceAll("");
        // Το ίδιο για το closing tag
        Matcher closingTagMatcher = closingTagPattern.matcher(snippet);
        snippet = closingTagMatcher.replaceAll("");
        return snippet;
    }
    
    public static void parseArticleFetch(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        JSONObject query = obj.getJSONObject("query");
        JSONArray pages = query.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (!page.has("extract")) continue;
            String cleanText = page.getString("extract");
            System.out.println(cleanText);
        }
    }
    
    public static String fetchArticleCleanText(String title) throws IOException {
        WikiApiClient api = new WikiApiClient();
        String cleanText = "";
        String jsonResponse = api.fetchArticle(title);
        JSONObject obj = new JSONObject(jsonResponse);
        JSONObject query = obj.getJSONObject("query");
        JSONArray pages = query.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (!page.has("extract")) continue;
            cleanText += page.getString("extract");
        }
        return cleanText;
    }
    
    public static void initializeCategories() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        List<String> listOfNames = Arrays.asList(
            "Ιστορία",
            "Φιλοσοφία",
            "Λογοτεχνία",
            "Επιστήμες",
            "Πολιτική",
            "Τέχνη",
            "Γεωγραφία",
            "Τεχνολογία"
        );
        try {
            em.getTransaction().begin();
            for (String name : listOfNames) {
                Category c = new Category(name);
                em.persist(c);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
    
    public static boolean categoriesExist() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query findByCategoryId = em.createNamedQuery("Category.findByCategoryId");
            findByCategoryId.setParameter("categoryId", 1);
            try {
                Category category = (Category) findByCategoryId.getSingleResult();
                return true;
            } catch (NoResultException nre) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
        return false;
    }
}
