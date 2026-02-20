package packageService;

import java.util.*;
import jakarta.persistence.*;

public class StatisticsService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("WikiPU");

    public long getTotalSavedArticles() {
        EntityManager em = emf.createEntityManager();
        try {
            Long c = em.createQuery("SELECT COUNT(a) FROM Article a", Long.class).getSingleResult();
            return (c == null) ? 0L : c;
        } finally {
            em.close();
        }
    }

    public Map<String, Long> getArticlesPerCategory(int limit) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT a.category.name, COUNT(a) FROM Article a GROUP BY a.category.name ORDER BY COUNT(a) DESC", Object[].class)
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
        EntityManager em = emf.createEntityManager();
        try {
            Double d = em.createQuery("SELECT AVG(a.rating) FROM Article a", Double.class).getSingleResult();
            return (d == null) ? 0.0 : d;
        } finally {
            em.close();
        }
    }

    public Map<String, Double> getTopRatedCategories(int limit) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT a.category.name, AVG(a.rating) FROM Article a GROUP BY a.category.name ORDER BY AVG(a.rating) DESC", Object[].class)
                    .getResultList();
            LinkedHashMap<String, Double> map = new LinkedHashMap<>();
            int i = 0;
            for (Object[] r : rows) {
                if (limit > 0 && i++ >= limit) break;
                String name = (r[0] == null) ? "Uncategorized" : r[0].toString();
                Double avg = (r[1] instanceof Number) ? ((Number) r[1]).doubleValue() : Double.valueOf(String.valueOf(r[1]));
                map.put(name, avg);
            }
            return map;
        } finally {
            em.close();
        }
    }

    public Map<String, Long> getTopSearchKeywords(int limit) {
        EntityManager em = emf.createEntityManager();
        try {
            try {
                List<Object[]> rows = em.createQuery(
                        "SELECT s.keyword, COUNT(s) FROM SearchLog s GROUP BY s.keyword ORDER BY COUNT(s) DESC", Object[].class)
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
        if (emf.isOpen()) emf.close();
    }
}
