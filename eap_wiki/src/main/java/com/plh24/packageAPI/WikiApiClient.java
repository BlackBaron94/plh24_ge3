/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageAPI;

/**
 *
 * @author Equinox
 */
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
public class WikiApiClient {
    private final OkHttpClient client = new OkHttpClient();
    
    public String searchWikipedia(String searchTerm) throws IOException {
        String url = "https://el.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + searchTerm + "&format=json";
        
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "MyJavaWikiApp/1.0 (talepis@unipi.gr)") 
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Σφάλμα συστήματος: " +  response);
            }
            return response.body().string();
        }
    };
    
    
}
