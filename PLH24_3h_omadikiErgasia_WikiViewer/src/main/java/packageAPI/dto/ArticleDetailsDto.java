package packageAPI.dto;

/**
 * DTO για λεπτομέρειες άρθρου από το Wikipedia API (details endpoint).
 *
 * Κρίσιμο για DB-first + refresh:
 * - revisionId: χρησιμοποιείται για σύγκριση “νεότερης έκδοσης”.
 *   Παράδειγμα: αν apiRevisionId > dbRevisionId => υπάρχει update.
 * - revisionTimestamp: συμπληρωματικό στοιχείο (audit/χρονοσήμανση/ενημέρωση UI).
 *
 * Update policy (όπως συμφωνήσαμε):
 * - Σε περίπτωση νεότερης έκδοσης, ενημερώνονται μόνο τα fetched fields:
 *   title/extract/revisionId/revisionTimestamp/lastFetchedAt.
 * - Τα τοπικά δεδομένα (σχόλια/βαθμολογία) ΔΕΝ διαγράφονται (είναι σε ArticleNote/Metadata).
 *
 * Σημείωση για extract:
 * - Αν χρησιμοποιήσεις explainttext=1 στο endpoint, το extract έρχεται χωρίς HTML.
 */
public class ArticleDetailsDto {

    private long pageId;               // query.pages[pageId].pageid
    private String title;              // query.pages[pageId].title
    private String extract;            // query.pages[pageId].extract
    private long revisionId;           // query.pages[pageId].revisions[0].revid
    private String revisionTimestamp;  // query.pages[pageId].revisions[0].timestamp

    public ArticleDetailsDto() {}

    public ArticleDetailsDto(long pageId, String title, String extract, long revisionId, String revisionTimestamp) {
        this.pageId = pageId;
        this.title = title;
        this.extract = extract;
        this.revisionId = revisionId;
        this.revisionTimestamp = revisionTimestamp;
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

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public long getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(long revisionId) {
        this.revisionId = revisionId;
    }

    public String getRevisionTimestamp() {
        return revisionTimestamp;
    }

    public void setRevisionTimestamp(String revisionTimestamp) {
        this.revisionTimestamp = revisionTimestamp;
    }

    @Override
    public String toString() {
        return "ArticleDetailsDto{" +
                "pageId=" + pageId +
                ", title='" + title + '\'' +
                ", revisionId=" + revisionId +
                ", revisionTimestamp='" + revisionTimestamp + '\'' +
                '}';
    }
}
