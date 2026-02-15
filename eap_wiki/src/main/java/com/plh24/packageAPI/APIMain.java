/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.plh24.packageAPI;

/**
 *
 * @author Equinox
 */
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;
public class APIMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WikiApiClient api = new WikiApiClient();
        try {
            String jsonResponse = api.searchWikipedia("Ελλάδα");
            System.out.println("--- Raw JSON Response ---");
            System.out.println(jsonResponse);
            System.out.println("--------------------------\n");
            parseAndPrintResults(jsonResponse);
            jsonResponse = api.fetchArticle("Ιωάννης Καποδίστριας");
            parsearticlefetchv2(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void printarticle(String title) {
        WikiApiClient api = new WikiApiClient();
        try {
            String json = api.fetchArticle(title);
            System.out.println("--- Raw JSON Response ---");
            System.out.println(json);
            System.out.println("------------------------");
            parsearticlefetch(json);
        } catch (IOException e) {
            e.printStackTrace();
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
    
    public static void parsearticlefetch(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        JSONObject query = obj.getJSONObject("query");
        JSONArray pages = query.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject page = pages.getJSONObject(i);
            if (!page.has("revisions")) continue;
            JSONArray revisions = page.getJSONArray("revisions");
            for (int j = 0; j < revisions.length(); j++) {
                JSONObject rev = revisions.getJSONObject(j);
                if (rev.has("slots")) {
                    JSONObject slots = rev.getJSONObject("slots");
                    if (slots.has("main")) {
                        JSONObject main = slots.getJSONObject("main");
                        String content = main.optString("content", "");
                        if (!content.isEmpty()) System.out.println(content);
                    }
                } else {
                    String content = rev.optString("content", rev.optString("*", ""));
                    if (!content.isEmpty()) System.out.println(content);
                }
            }
        }
    }
    public static void parsearticlefetchv2(String jsonString) {
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
}
