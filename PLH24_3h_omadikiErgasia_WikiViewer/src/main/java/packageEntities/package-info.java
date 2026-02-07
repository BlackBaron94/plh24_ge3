/**
 * 
 * packageEntities (Domain Model / POJOs) — μόνο δεδομένα
 * packageEntities (Domain Model / Entities)
 *
 * Domain model / Entities (σκέτα δεδομένα - POJOs).
 *
 * Παραδείγματα:
 * - Article (id, title, extract/content, revisionInfo, lastFetchedAt)
 * - Category
 * - ArticleNote / ArticleMetadata (σχόλια, βαθμολογία, timestamps)
 * - SearchResult (id, title, snippet, source DB/API)
 *
 * Κανόνες:
 * - Χωρίς Swing/UI κώδικα.
 * - Χωρίς SQL/DB κώδικα.
 * - Χωρίς HTTP/API κώδικα.
 * - Δεν εξαρτάται από άλλα layers (κανένα import από GUI/Controller/Service/Repository/API).
 */


package packageEntities;

