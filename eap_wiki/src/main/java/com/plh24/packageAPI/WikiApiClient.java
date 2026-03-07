/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plh24.packageAPI;

/**
 *
 * @author Παναγιώτης Σοφιανόπουλος
 */
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
/**
 * WikiApiClient
 *
 * Απλός HTTP client για κλήσεις προς το Wikipedia API (el.wikipedia.org).
 * Παρέχει βοηθητικές μεθόδους για αναζήτηση και ανάκτηση περιεχομένου άρθρων
 * και επιστρέφει τα αποτελέσματα ως JSON string.
 */
public class WikiApiClient {
    private final OkHttpClient client = new OkHttpClient();
    
    /**
     * Εκτελεί αναζήτηση στη Wikipedia με τον δοθέντα όρο.
     *
     * @param searchTerm Ο όρος αναζήτησης,
     * @return Το αποτέλεσμα της αναζήτησης σε μορφή JSON ως String,
     * @throws IOException Σε περίπτωση σφάλματος
     */
    public String searchWikipedia(String searchTerm) throws IOException {
        String url = "https://el.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + searchTerm + "&format=json";
        
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "MyJavaWikiApp/1.0 (std156606@ac.eap.gr)") 
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Σφάλμα συστήματος: " +  response);
            }
            return response.body().string();
        }
    };

    /**
     * Ανακτά το κείμενο ενός άρθρου της Wikipedia με βάση τον τίτλο.
     *
     * @param title Ο τίτλος του άρθρου,
     * @return Το περιεχόμενο του άρθρου σε μορφή JSON ως String,
     * @throws IOException Σε περίπτωση σφάλματος
     */
    public String fetchArticle(String title) throws IOException {
        String url = "https://el.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&rvprop=content&rvslots=main&formatversion=2&explaintext=1&titles=" + title;

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "MyJavaWikiApp/1.0 (std156606@ac.eap.gr)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Σφάλμα συστήματος: " + response);
            }
            return response.body().string();
        }
    }
}
