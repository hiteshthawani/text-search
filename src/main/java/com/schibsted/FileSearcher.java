package com.schibsted;

import com.schibsted.model.RankDo;
import com.schibsted.model.SearchQuery;
import com.schibsted.strategy.RankingStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class FileSearcher
{

    private final Map<String, Set<String>> termsToFileSet;
    private final RankingStrategy ranker;


    FileSearcher(Map<String, Set<String>> termsToFileSet, RankingStrategy ranker)
    {
        this.termsToFileSet = termsToFileSet;
        this.ranker = ranker;
    }


    Set<RankDo> search(String queryString, int maxResults)
    {
        final SearchQuery query = SearchQuery.from(queryString);

        Map<String, Set<String>> termToFiles = new HashMap<>();
        for (String term : query.getTerms())
        {
            if (termsToFileSet.containsKey(term))
            {
                termToFiles.put(term, termsToFileSet.get(term));
            }
        }

        return ranker.rankFiles(termToFiles, query, maxResults);
    }


}
