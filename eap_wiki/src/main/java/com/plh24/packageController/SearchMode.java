package com.plh24.packageController;

/**
 * SearchMode: καθορίζει από πού θα γίνει η αναζήτηση.
 *
 * REAL IMPLEMENTATION (later):
 * - DB_ONLY: query μόνο στη ΒΔ (Repository/DAO).
 * - DB_API : DB-first + (αν χρειάζεται) call Wikipedia API.
 * - Η πολιτική "DB-first + background refresh" θα μπει στον Controller/Service.
 */
/** 
 * Εδώ ορίζω το enum <b>SearchMode</b> στον Controller [ελεγκτή] (λογική εφαρμογής).
 * <p>Στόχος μου είναι να κρατήσω το κομμάτι αυτό καθαρό και καλά σχολιασμένο, ώστε να μπορεί να συνεχίσει εύκολα άλλο μέλος της ομάδας.</p>
 */
public enum SearchMode {
    DB_ONLY,
    DB_API
}
