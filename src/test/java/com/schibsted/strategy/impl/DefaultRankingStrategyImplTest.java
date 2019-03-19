package com.schibsted.strategy.impl;

import com.schibsted.factory.RankingFactory;
import com.schibsted.model.RankDo;
import com.schibsted.model.SearchQuery;
import com.schibsted.strategy.RankingStrategy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultRankingStrategyImplTest
{
    private RankingStrategy defaultRankingStrategy;


    @Before
    public void setUp()
    {
        defaultRankingStrategy = RankingFactory.getIndexStrategy(RankingFactory.RankingStrategyType.DEFAULT);
    }


    @Test
    public void when_all_terms_present_rank_is_100()
    {
        final SearchQuery query = SearchQuery.from("hello world");
        final Map<String, Set<String>> termsToFiles = new HashMap<>();

        HashSet<String> files = new HashSet<>();
        files.add("file1.txt");

        termsToFiles.put("hello", files);
        termsToFiles.put("world", files);

        Set<RankDo> rankDos = defaultRankingStrategy.rankFiles(termsToFiles, query, 10);
        Assert.assertTrue(rankDos.contains(new RankDo("file1.txt", new BigDecimal(100))));

    }


    @Test
    public void when_half_of_terms_present_rank_is_50()
    {
        final SearchQuery query = SearchQuery.from("hello world");
        final Map<String, Set<String>> termsToFiles = new HashMap<>();

        HashSet<String> files = new HashSet<>();
        files.add("file1.txt");

        termsToFiles.put("hello", files);

        Set<RankDo> rankDos = defaultRankingStrategy.rankFiles(termsToFiles, query, 10);
        Assert.assertTrue(rankDos.contains(new RankDo("file1.txt", new BigDecimal(50))));

    }


    @Test
    public void when_files_contain_different_number_of_terms_rank_should_be_different()
    {
        final SearchQuery query = SearchQuery.from("hello world");
        final Map<String, Set<String>> termsToFiles = new HashMap<>();

        HashSet<String> term1FileSet = new HashSet<>();
        term1FileSet.add("file1.txt");
        HashSet<String> term2FileSet = new HashSet<>();
        term1FileSet.add("file1.txt");

        term2FileSet.add("file1.txt");
        term2FileSet.add("file2.txt");

        termsToFiles.put("hello", term1FileSet);
        termsToFiles.put("world", term2FileSet);

        Set<RankDo> rankDos = defaultRankingStrategy.rankFiles(termsToFiles, query, 10);
        Assert.assertTrue(rankDos.contains(new RankDo("file1.txt", new BigDecimal(100))));
        Assert.assertTrue(rankDos.contains(new RankDo("file2.txt", new BigDecimal(50))));

    }


    @Test
    public void number_of_results_returned_are_equal_to_max_results()
    {
        final SearchQuery query = SearchQuery.from("hello world");
        final Map<String, Set<String>> termsToFiles = new HashMap<>();
        final int MAX_RESULTS = 1;
        final HashSet<String> term1FileSet = new HashSet<>();
        final HashSet<String> term2FileSet = new HashSet<>();

        term1FileSet.add("file1.txt");

        term2FileSet.add("file1.txt");
        term2FileSet.add("file2.txt");

        termsToFiles.put("hello", term1FileSet);
        termsToFiles.put("world", term2FileSet);

        Set<RankDo> rankDos = defaultRankingStrategy.rankFiles(termsToFiles, query, MAX_RESULTS);
        Assert.assertEquals(rankDos.size(), 1);
        Assert.assertTrue(rankDos.contains(new RankDo("file1.txt", new BigDecimal(100))));

    }
}