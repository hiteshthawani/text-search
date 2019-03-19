package com.schibsted.strategy.impl;

import com.schibsted.factory.IndexFactory;
import com.schibsted.strategy.IndexingStrategy;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultIndexingStrategyImplTest
{

    IndexingStrategy indexingStrategy;


    @Before
    public void setUp()
    {
        indexingStrategy = IndexFactory.getIndexStrategy(IndexFactory.IndexStrategyType.DEFAULT);
    }


    @Test
    public void terms_must_be_mapped_with_correct_files_in_generated_index() throws IOException
    {
        final String RESOURCE_DIR = "src/test/resources";
        final Map<String, Set<String>> actual = indexingStrategy.indexDirectory(RESOURCE_DIR);
        Assert.assertTrue(actual.get("fox").contains("file1.txt"));
        Assert.assertTrue(actual.get("fox").contains("file2.txt"));
        Assert.assertTrue(!actual.get("brown").contains("file2.txt"));

    }
}