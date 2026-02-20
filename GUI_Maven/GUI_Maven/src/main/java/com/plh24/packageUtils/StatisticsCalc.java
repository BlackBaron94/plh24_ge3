package com.plh24.packageUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsCalc {

    public static Map<String, Object> calcStatistics(List<?> articles, List<String> searchKeywords, int topCategories, int topKeywords) {
        Map<String, Object> stats = new LinkedHashMap<>();

        int total = (articles == null) ? 0 : articles.size();
        stats.put("totalSavedArticles", total);

        Map<String, Long> perCategory = new HashMap<>();
        if (articles != null) {
            for (Object a : articles) {
                String cname = getFirstCategoryName(a);
                perCategory.put(cname, perCategory.getOrDefault(cname, 0L) + 1L);
            }
        }
        LinkedHashMap<String, Long> perCategorySorted = perCategory.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
        stats.put("articlesPerCategory", perCategorySorted);

        double avgRating = 0.0;
        if (articles != null && !articles.isEmpty()) {
            double sum = 0.0;
            int cnt = 0;
            for (Object a : articles) {
                int r = getRating(a);
                if (r >= 0) {
                    sum += r;
                    cnt++;
                }
            }
            if (cnt > 0) avgRating = sum / cnt;
        }
        stats.put("averageRating", avgRating);

        Map<String, List<Integer>> categoryRatings = new HashMap<>();
        if (articles != null) {
            for (Object a : articles) {
                String cname = getFirstCategoryName(a);
                categoryRatings.computeIfAbsent(cname, k -> new ArrayList<>()).add(getRating(a));
            }
        }
        Map<String, Double> categoryAvg = new HashMap<>();
        for (Map.Entry<String, List<Integer>> e : categoryRatings.entrySet()) {
            List<Integer> list = e.getValue();
            double s = 0.0;
            for (int v : list) s += v;
            categoryAvg.put(e.getKey(), list.isEmpty() ? 0.0 : (s / list.size()));
        }
        LinkedHashMap<String, Double> topRatedCategories = categoryAvg.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(topCategories)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
        stats.put("topRatedCategories", topRatedCategories);

        Map<String, Integer> freq = new HashMap<>();
        if (searchKeywords != null) {
            for (String q : searchKeywords) {
                if (q == null) continue;
                String clean = q.toLowerCase().replaceAll("[^\\p{L}\\p{Nd} ]+", " ").trim();
                if (clean.isEmpty()) continue;
            }
        }
        LinkedHashMap<String, Integer> topKeywordshush = freq.entrySet().stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .limit(topKeywords)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
        stats.put("topSearchKeywords", topKeywordshush);

        return stats;
    }

    public static void writeStatisticsJson(Map<String, Object> stats, Path out) throws IOException {
        String json = toJson(stats);
        if (out.getParent() != null) Files.createDirectories(out.getParent());
        Files.write(out, json.getBytes(StandardCharsets.UTF_8));
    }

    private static String getFirstCategoryName(Object a) {
        if (a == null) return "Uncategorized";
        try {
            Object catsObj = null;
            try {
                java.lang.reflect.Method gm = a.getClass().getMethod("getCategories");
                catsObj = gm.invoke(a);
            } catch (NoSuchMethodException nsme) {
                return "Uncategorized";
            }
            if (catsObj instanceof List) {
                List<?> cats = (List<?>) catsObj;
                if (cats != null && !cats.isEmpty() && cats.get(0) != null) {
                    Object c = cats.get(0);
                    try {
                        java.lang.reflect.Method m = c.getClass().getMethod("getName");
                        Object name = m.invoke(c);
                        return (name == null) ? "Uncategorized" : String.valueOf(name);
                    } catch (NoSuchMethodException nsme) {
                        return String.valueOf(c);
                    }
                }
            }
        } catch (Exception ex) {}
        return "Uncategorized";
    }

    private static int getRating(Object a) {
        if (a == null) return 0;
        try {
            java.lang.reflect.Method m = a.getClass().getMethod("getRating");
            Object val = m.invoke(a);
            if (val == null) return 0;
            if (val instanceof Number) return ((Number) val).intValue();
            try {
                return Integer.parseInt(String.valueOf(val));
            } catch (NumberFormatException nfe) {
                return 0;
            }
        } catch (NoSuchMethodException nsme) {
            // no rating method
            return 0;
        } catch (Exception ex) {
            return 0;
        }
    }

    private static String toJson(Object o) {
        if (o == null) return "null";
        if (o instanceof Map) {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            Iterator<? extends Map.Entry<?, ?>> it = ((Map<?, ?>) o).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> e = it.next();
                sb.append('"').append(escape(String.valueOf(e.getKey()))).append('"').append(':').append(toJson(e.getValue()));
                if (it.hasNext()) sb.append(',');
            }
            sb.append('}');
            return sb.toString();
        } else if (o instanceof Collection) {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            Iterator<?> it = ((Collection<?>) o).iterator();
            while (it.hasNext()) {
                sb.append(toJson(it.next()));
                if (it.hasNext()) sb.append(',');
            }
            sb.append(']');
            return sb.toString();
        } else if (o instanceof String) {
            return '"' + escape((String) o) + '"';
        } else if (o instanceof Number || o instanceof Boolean) {
            return String.valueOf(o);
        } else {
            return '"' + escape(String.valueOf(o)) + '"';
        }
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }

    public static void generateAndWrite(List<?> articles, List<String> searches, Path outFile) throws IOException {
        Map<String, Object> stats = calcStatistics(articles, searches, 10, 20);
        writeStatisticsJson(stats, outFile);
    }
}
