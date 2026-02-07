/**
 * 
 *  packageRepository (DAO / DB) — καλείται από Service, μιλάει με Entities
 * packageRepository (DAO / Repository - DB Access)
 *
 * Πρόσβαση στη Βάση Δεδομένων (CRUD + queries).
 *
 * Περιλαμβάνει:
 * - DataSource/Connection (π.χ. DerbyDataSource)
 * - Repositories (ArticleRepository, CategoryRepository, ArticleNoteRepository)
 * - SQL/CRUD, queries, mapping ResultSet -> Entities
 *
 * Κανόνες:
 * - Καλείται από Services (όχι απευθείας από Controllers/GUI).
 * - Καμία Swing/UI λογική.
 * - Καμία HTTP κλήση προς Wikipedia.
 * - Επιστρέφει/δέχεται Entities (domain objects).
 */

package packageRepository;
