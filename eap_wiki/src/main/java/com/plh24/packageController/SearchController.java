/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.plh24.packageController;
import java.util.Set;
/**
 *
 * @author Dimitris
 */

public interface SearchController {

    record WikiHit(String title, int wordCount, String snippet) {}

    record SearchResults(Set<String> dbTitles, java.util.List<WikiHit> wikiHits) {}

    SearchResults runSearch(String keyword);

    void logSearchEvent(String keyword);

    Set<String> searchSavedArticleTitles(String keyword);

    boolean saveDefaultArticleIfNotExists(String title);
}