package com.schibsted.strategy.impl;

import com.schibsted.model.RankDo;
import com.schibsted.model.SearchQuery;
import com.schibsted.strategy.RankingStrategy;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultRankingStrategyImpl implements RankingStrategy
{

    @Override
    public Set<RankDo> rankFiles(Map<String, Set<String>> termsToFileIndex, SearchQuery originalQuery, int maxResults)
    {
        final long uniqueTerms = originalQuery.getTerms().parallelStream().distinct().count();

        Map<String, Long> collect = termsToFileIndex
            .values()
            .parallelStream()
            .flatMap(Set::stream)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return collect.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(maxResults).map(entry ->
            new RankDo(entry.getKey(), new BigDecimal(100 * entry.getValue() / uniqueTerms))
        ).collect(Collectors.toCollection(LinkedHashSet::new));

    }
}
