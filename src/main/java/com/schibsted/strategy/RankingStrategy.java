package com.schibsted.strategy;

import com.schibsted.model.RankDo;
import com.schibsted.model.SearchQuery;
import java.util.Map;
import java.util.Set;

public interface RankingStrategy
{
    Set<RankDo> rankFiles(Map<String, Set<String>> termsToFileIndex, SearchQuery originalQuery, int maxResults);
}
