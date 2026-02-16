/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.plh24.packageEntities;

import java.util.Arrays;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author Equinox
 */
public class CategoryCreator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("KALHMERA KAI KALH VRADIA");
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EapWikiPU");
        System.out.println("PHRA MANAGER FACTORY");
        EntityManager em = emf.createEntityManager();
        System.out.println("EFTIAKSA KAI MANAGER");
        Category category = new Category();
        
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
        em.getTransaction().begin();
        System.out.println("EKANA KAI BEGIN TRANSACTION");
        for (String name : listOfNames) {
            System.out.println("MEXRI EDW EFTASA META DEN KSERW TI EGINE");
            Category c = new Category(name);
            em.persist(c);
        }
        em.getTransaction().commit();
        em.close();
    }
    
}


