package packageController;

/**
 * SearchMode: καθορίζει από πού θα γίνει η αναζήτηση.
 *
 * REAL IMPLEMENTATION (later):
 * - DB_ONLY: query μόνο στη ΒΔ (Repository/DAO).
 * - DB_API : DB-first + (αν χρειάζεται) call Wikipedia API.
 * - Η πολιτική "DB-first + background refresh" θα μπει στον Controller/Service.
 */
public enum SearchMode {
    DB_ONLY,
    DB_API
}
