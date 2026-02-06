/**
 * packageRepository
 *
 * Πρόσβαση στη Βάση Δεδομένων (DAO/Repository).
 * Περιλαμβάνει:
 * - DataSource / σύνδεση DB (π.χ. DerbyDataSource)
 * - Repositories (ArticleRepository, CategoryRepository, ArticleNoteRepository)
 * - SQL/CRUD, queries, mapping ResultSet -> Entities
 *
 * Κανόνες:
 * - Καμία κλήση Swing.Καμία UI λογική.(Είναι στο GUI)
 * - Καμία HTTP κλήση προς Wikipedia.Καμία HTTP κλήση προς Wikipedia.(Είναι στο API)
 */

package packageRepository;
