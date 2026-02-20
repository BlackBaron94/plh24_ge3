package packageAPI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiApiClient {

    private final OkHttpClient client = new OkHttpClient();

    public String searchWikipedia(String searchTerm) throws IOException {
        String encoded = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        String url = "https://el.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + encoded + "&format=json";

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "MyJavaWikiApp/1.0 (talepis@unipi.gr)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Σφάλμα συστήματος: " + response);
            }
            return response.body().string();
        }
    }

    public String fetchArticle(String title) throws IOException {
        String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String url = "https://el.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&formatversion=2&explaintext=1&titles=" + encoded;

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "MyJavaWikiApp/1.0 (talepis@unipi.gr)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Σφάλμα συστήματος: " + response);
            }
            return response.body().string();
        }
    }

    // --- DTO για hits (pageId + title) ---
    public static final class ApiSearchHit {
        public final long pageId;
        public final String title;

        public ApiSearchHit(long pageId, String title) {
            this.pageId = pageId;
            this.title = title;
        }
    }

    // --- Επιστρέφει λίστα hits (χωρίς org.json) ---
    public List<ApiSearchHit> searchHits(String searchTerm) throws IOException {
        String json = searchWikipedia(searchTerm);
        return parseSearchHits(json);
    }

    // Minimal parser (regex) για "pageid" + "title"
    private List<ApiSearchHit> parseSearchHits(String json) {
        List<ApiSearchHit> hits = new ArrayList<>();

        Pattern p = Pattern.compile("\"pageid\"\\s*:\\s*(\\d+)[\\s\\S]*?\"title\"\\s*:\\s*\"(.*?)\"");
        Matcher m = p.matcher(json);

        while (m.find()) {
            long pageId = Long.parseLong(m.group(1));
            String title = unescapeJson(m.group(2));
            hits.add(new ApiSearchHit(pageId, title));
        }
        return hits;
    }

    private String unescapeJson(String s) {
        return s.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\/", "/")
                .replace("\\\\", "\\");
    }
}