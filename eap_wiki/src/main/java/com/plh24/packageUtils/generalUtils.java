/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageUtils;

import com.plh24.packageEntities.Article;
import com.plh24.packageEntities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NoResultException;
/**
 *
 * @author Equinox
 */
public class generalUtils {
    public static void saveArticle(String title, int rating, String category, String comments){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        EntityManager em = emf.createEntityManager();
        try {
            Query findCategoryByName = em.createNamedQuery("Category.findByName");
            findCategoryByName.setParameter("name", category);
            Category category_obj = (Category) findCategoryByName.getSingleResult();
            // TODO Έλεγχος αν υπάρχει ήδη στη βάση. 
            // TODO Μάλλον βγάζει νόημα αντί για category_id να κρατάμε το 
            // category_name στο Article, πιο καλό στο μάτι, αν γίνεται
            em.getTransaction().begin();
            Query findArticleByTitle = em.createNamedQuery("Article.findByTitle");
            findArticleByTitle.setParameter("title", title);
            Article article;
            try {
                article = (Article) findArticleByTitle.getSingleResult();
                System.out.println("Article already in DB");
                article.setCategory(category_obj);
                System.out.println("Setting new category...");
                if (!(comments.equals(""))){
                    article.setComments(comments);
                    System.out.println("setting new comments...");
                    System.out.println(comments);
                }
                if (rating != 0){
                    article.setRating(rating);
                    System.out.println("Setting new rating...");
                }
                
            } catch (NoResultException nre) {
                article = new Article(title, rating, category_obj, comments);
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
}
