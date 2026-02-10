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
import org.json.JSONArray;
import org.json.JSONObject;
public class APIMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WikiApiClient api = new WikiApiClient();
        try {
            String jsonResponse = api.searchWikipedia("Greece");
            System.out.println("--- Raw JSON Response ---");
            System.out.println(jsonResponse);
            System.out.println("--------------------------\n");
            parseAndPrintResults(jsonResponse);
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
        }
    }
    
}
