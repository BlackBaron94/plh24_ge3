package com.plh24.packageService;

import java.util.*;
import jakarta.persistence.*;

public class StatisticsService {

    private EntityManagerFactory emf;

    private EntityManagerFactory getEmf() {
        if (emf == null) {
            try {
                try {
                    emf = Persistence.createEntityManagerFactory("EapWikiPU");
                } catch (PersistenceException pe1) {
                    emf = Persistence.createEntityManagerFactory("EapWikiPU");
                }
            } catch (PersistenceException pe) {
                return null;
            }
        }
        return emf;
    }

    public long getTotalSavedArticles() {
        EntityManagerFactory emfLocal = getEmf();
        if (emfLocal == null) return 0L;
        EntityManager em = emfLocal.createEntityManager();
        try {
            Long c = em.createQuery("SELECT COUNT(a.title) FROM Article a", Long.class).getSingleResult();
            return (c == null) ? 0L : c;
        } finally {
            em.close();
        }
    }

    public Map<String, Long> getArticlesPerCategory(int limit) {
        EntityManagerFactory emfLocal = getEmf();
        if (emfLocal == null) return Collections.emptyMap();
        EntityManager em = emfLocal.createEntityManager();
        try {
                List<Object[]> rows = em.createQuery(
                    "SELECT a.category.name, COUNT(a.title) FROM Article a GROUP BY a.category.name ORDER BY COUNT(a.title) DESC", Object[].class)
                    .getResultList();
            LinkedHashMap<String, Long> map = new LinkedHashMap<>();
            int i = 0;
            for (Object[] r : rows) {
                if (limit > 0 && i++ >= limit) break;
                String name = (r[0] == null) ? "Uncategorized" : r[0].toString();
                Long cnt = (r[1] instanceof Number) ? ((Number) r[1]).longValue() : Long.valueOf(String.valueOf(r[1]));
                map.put(name, cnt);
            }
            return map;
        } finally {
            em.close();
        }
    }

    public double getAverageRating() {
        EntityManagerFactory emfLocal = getEmf();
        if (emfLocal == null) return 0.0;
        EntityManager em = emfLocal.createEntityManager();
        try {
            Double d = em.createQuery("SELECT AVG(a.rating) FROM Article a", Double.class).getSingleResult();
            return (d == null) ? 0.0 : d;
        } finally {
            em.close();
        }
    }

    public long getTotalCategories() {
        EntityManagerFactory emfLocal = getEmf();
        if (emfLocal == null) return 0L;
        EntityManager em = emfLocal.createEntityManager();
        try {
            Long c = em.createQuery("SELECT COUNT(c) FROM Category c", Long.class).getSingleResult();
            return (c == null) ? 0L : c;
        } finally {
            em.close();
        }
    }

    public Map<String, Double> getTopRatedCategories(int limit) {
        EntityManagerFactory emfLocal = getEmf();
        if (emfLocal == null) return Collections.emptyMap();
        EntityManager em = emfLocal.createEntityManager();
        try {
                List<Object[]> rows = em.createQuery(
                    "SELECT a.category.name, AVG(a.rating) FROM Article a WHERE a.rating IS NOT NULL GROUP BY a.category.name ORDER BY AVG(a.rating) DESC", Object[].class)
                    .getResultList();
            LinkedHashMap<String, Double> map = new LinkedHashMap<>();
            int i = 0;
            for (Object[] r : rows) {
                if (limit > 0 && i++ >= limit) break;
                String name = (r[0] == null) ? "Uncategorized" : r[0].toString();
                if (name == null || name.trim().isEmpty() || "null".equalsIgnoreCase(name.trim())) name = "Uncategorized";
                if (r[1] == null) continue; // skip ean no rating
                Double avg;
                if (r[1] instanceof Number) {
                    avg = ((Number) r[1]).doubleValue();
                } else {
                    try {
                        avg = Double.valueOf(String.valueOf(r[1]));
                    } catch (NumberFormatException nfe) {
                        continue;
                    }
                }
                map.put(name, avg);
            }
            return map;
        } finally {
            em.close();
        }
    }

    public Map<String, Long> getTopSearchKeywords(int limit) {
        EntityManagerFactory emfLocal = getEmf();
        if (emfLocal == null) return Collections.emptyMap();
        EntityManager em = emfLocal.createEntityManager();
        try {
            try {
                List<Object[]> rows = em.createQuery(
                    "SELECT s.searchTerm, COUNT(s.searchTerm) FROM SearchLog s GROUP BY s.searchTerm ORDER BY COUNT(s.searchTerm) DESC", Object[].class)
                        .getResultList();
                LinkedHashMap<String, Long> map = new LinkedHashMap<>();
                int i = 0;
                for (Object[] r : rows) {
                    if (limit > 0 && i++ >= limit) break;
                    String kw = (r[0] == null) ? "" : r[0].toString();
                    Long cnt = (r[1] instanceof Number) ? ((Number) r[1]).longValue() : Long.valueOf(String.valueOf(r[1]));
                    if (!kw.isEmpty()) map.put(kw, cnt);
                }
                return map;
            } catch (IllegalArgumentException iae) {
                return Collections.emptyMap();
            }
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf != null && emf.isOpen()) emf.close();
    }
}
