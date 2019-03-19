package com.schibsted;

import com.schibsted.factory.RankingFactory;
import com.schibsted.model.RankDo;
import com.schibsted.strategy.RankingStrategy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileSearcherTest
{

    private FileSearcher fileSearcher;
    private RankingStrategy defaultRankingStrategy;


    @Before
    public void setUp() throws Exception
    {
        final Map<String, Set<String>> termsToFiles = new HashMap<>();
        defaultRankingStrategy = RankingFactory.getIndexStrategy(RankingFactory.RankingStrategyType.DEFAULT);

        HashSet<String> fileSet1 = new HashSet<>();
        HashSet<String> fileSet2 = new HashSet<>();
        HashSet<String> fileSet3 = new HashSet<>();

        fileSet1.add("file1.txt");
        fileSet1.add("file2.txt");
        fileSet1.add("file3.txt");

        fileSet2.add("file1.txt");
        fileSet2.add("file2.txt");

        fileSet3.add("file1.txt");

        termsToFiles.put("hello", fileSet1);
        termsToFiles.put("quick", fileSet2);
        termsToFiles.put("brown", fileSet2);
        termsToFiles.put("fox", fileSet3);

        fileSearcher = new FileSearcher(termsToFiles, defaultRankingStrategy);
    }


    @Test
    public void search_PositiveQueryGiven_ShouldReturnFilesInDescendingOrderOfRank()
    {
        final String query = "hello Fox";
        Set<RankDo> expectedRankDoSet = new LinkedHashSet<>();

        expectedRankDoSet.add(new RankDo("file1.txt", new BigDecimal(100)));
        expectedRankDoSet.add(new RankDo("file2.txt", new BigDecimal(50)));
        expectedRankDoSet.add(new RankDo("file3.txt", new BigDecimal(50)));

        Set<RankDo> actualRankDoset = fileSearcher.search(query, 10);
        Assert.assertTrue(expectedRankDoSet.containsAll(actualRankDoset));

    }

}