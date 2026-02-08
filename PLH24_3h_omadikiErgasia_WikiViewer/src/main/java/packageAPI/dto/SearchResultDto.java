package packageAPI.dto;

/**
 * DTO για αποτέλεσμα αναζήτησης από το Wikipedia API (search endpoint).
 *
 * Χρήση στη ροή DB-first + refresh:
 * - WikipediaClient.search(query) επιστρέφει List<SearchResultDto>.
 * - Το Service κάνει mapping σε Entity (π.χ. SearchResult / Article-lite) και το GUI τα εμφανίζει.
 *
 * Σημείωση για revisionId & timestamp:
 * - Τα revisionId και revisionTimestamp ΔΕΝ περιλαμβάνονται συνήθως στα αποτελέσματα search.
 * - Τα παίρνουμε από το details endpoint μέσω ArticleDetailsDto (WikipediaClient.getDetails(pageId)).
 *   Αυτό ταιριάζει ιδανικά με τη λογική "DB-first + refresh":
 *   (1) δείχνω γρήγορα αποτελέσματα, 
 *   (2) όταν χρειαστεί λεπτομέρειες/refresh, φέρνω revision info.
 *
 * Σημείωση για snippet:
 * - Το snippet συχνά περιέχει HTML (π.χ. <span class="searchmatch">).
 * - Καθάρισμα/απογύμνωση HTML γίνεται σε packageUtils.TextUtils 
 *   (π.χ. stripHtml, decodeEntities).
 */
public class SearchResultDto {

    private long pageId;     // query.search[i].pageid
    private String title;    // query.search[i].title
    private String snippet;  // query.search[i].snippet (συχνά με HTML)
    private int wordCount;   // query.search[i].wordcount (προαιρετικό)

    public SearchResultDto() {}

    public SearchResultDto(long pageId, String title, String snippet, int wordCount) {
        this.pageId = pageId;
        this.title = title;
        this.snippet = snippet;
        this.wordCount = wordCount;
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    @Override
    public String toString() {
        return "SearchResultDto{" +
                "pageId=" + pageId +
                ", title='" + title + '\'' +
                '}';
    }
}
