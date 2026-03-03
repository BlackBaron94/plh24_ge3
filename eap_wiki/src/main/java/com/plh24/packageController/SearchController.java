/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.plh24.packageController;

import com.plh24.packageEntities.Category;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dimitris
 */

public interface SearchController {

    record WikiHit(String title, int wordCount, String snippet) {}

    record SearchResults(Set<String> dbTitles, List<WikiHit> wikiHits) {}

    SearchResults runSearch(String keyword);

    void logSearchEvent(String keyword);

    Set<String> searchSavedArticleTitles(String keyword);

    boolean saveDefaultArticleIfNotExists(String title, Category category);
}