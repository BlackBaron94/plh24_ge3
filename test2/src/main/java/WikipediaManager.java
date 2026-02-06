

import com.google.gson.JsonObject;//για να κρατάμε ένα JSON αντικείμενο ως Map
import com.google.gson.JsonParser;//για μετατροπή String σε JsonObject
import java.net.URI;//για να φτιάξουμε URI απο το URL string
import java.net.URLEncoder;//για να κάνουμε encode το τίτλο
import java.net.http.HttpClient;//βιβλιοθήκη για HTTP 
import java.net.http.HttpRequest;//για τo request
import java.net.http.HttpResponse;//για το response στο request
import java.nio.charset.StandardCharsets;//για χρήση UTF_8 για ελληνικούς χαρακτήρες




public class WikipediaManager {
    

    public void fetchAndSave(String articleTitle, String categoryName) {
        try {
           
            
            String encodedTitle = URLEncoder.encode(articleTitle, StandardCharsets.UTF_8);//μετατρέπει το τίτλο σε format
                    //για URL, UTF_8 προσπαθεια επιλυσης προβληματος με τα ελληνικα

           
           
            String finalUrl = " https://el.wikipedia.org/w/api.php" + encodedTitle;//πλήρες URL για το API
            
            

            
            HttpClient client = HttpClient.newHttpClient();//Δημιουργία HttpClient
            HttpRequest request = HttpRequest.newBuilder()//δημιουργουμε το request
                    .uri(URI.create(finalUrl))//βάζουμε το URL στο request
                    .header("User-Agent", "PL24WikiApp/1.0 (Java Learning Project)")//βάζουμε User-Agent header
                            //αν δεν έχει user-agent μπορεί να απoρριφθεί το request από τη Wikipedia 
                    .build();//Ολοκλήρωση του request
            
            

            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());//παίρνουμε 
                //απάντηση σε string μορφή,στο request που στέλνουμε

            
            System.out.println("DEBUG RESPONSE: " + response.body());//έλεγχος λήψης JSON, error HTML  

            
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();//μετατροπή του 
                //JSON String σε JsonObject

            if (!jsonResponse.has("query")) { //αν δεν υπάρχει το key "query"
                System.err.println("Η Wikipedia δεν επέστρεψε αποτελέσματα για: " + articleTitle);
                return;
            }

            JsonObject pages = jsonResponse.getAsJsonObject("query").getAsJsonObject("pages");//πάρε απο το JSON
                //ένα αντικείμενο query στο οποίο υπάρχει το pages μέσα στο οποίο είναι τα άθρα που βρέθηκαν
            String firstKey = pages.keySet().iterator().next();//παίρνει τα keys του pages, ξεκινάει και τα διαβάζει 
                //και παίρνει το πρώτο

                
            if (firstKey.equals("-1")) {//αν δεν υπάρχει το αθρο το πρώτο key είναι -1
                System.out.println("Το άρθρο '" + articleTitle + "' δεν βρέθηκε στη Wikipedia.");
                return;
            }
            
            

            JsonObject pageData = pages.getAsJsonObject(firstKey);//παιρνουμε τα δεδομένα της σελίδας

            int pageId = pageData.get("pageid").getAsInt();//παίρνουμε το pageId από τη Wikipedia
            
            String title = pageData.get("title").getAsString();//παίρνουμε το τίτλο
            
            
            
            String extract;
            if (pageData.has("extract")) {//αν έχει περίληψη
                extract = pageData.get("extract").getAsString();//παίρνουμε τη περίληψη
            }
            else {
            extract = "Δεν υπάρχει περίληψη";
            }

            
            if (extract.length() > 3000) {//αν η περίληψη πολύ μεγάλη
                extract = extract.substring(0, 2999) + "..."; //το κόβουμε για να χωρέσει στο
                        //DB field. Στο entity Article έίχαμε ορίσει column length 300o
            }

          
            ArticleService service = new ArticleService();//Δημιουργία ενος αντικειμένου ArticleService 
            service.saveWikipediaArticle(pageId, title, extract, 1, categoryName);//Τα δεδομένα που έχουμε
                //από τη Wikipedia στέλνονται στο service για αποθυηκευση στη ΒΔ. Επέιδή δεν έχουμε ακομα 
                //καποιο rating το βαλαμε 1, θα μπει το rating στο μελλον.

            System.out.println("Επιτυχής αποθήκευση: " + title);

        } catch (Exception e) {//σε περιπτωση κάποιας εξαίρεσης
            System.err.println("Σφάλμα στον WikipediaManager: " + e.getMessage());
            e.printStackTrace();//εκτύπωση πληρους stack trace
        }
    }
}