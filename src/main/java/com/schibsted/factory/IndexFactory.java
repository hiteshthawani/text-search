package com.schibsted.factory;

import com.schibsted.strategy.impl.DefaultIndexingStrategyImpl;
import com.schibsted.strategy.IndexingStrategy;

public class IndexFactory
{
    public enum IndexStrategyType
    {
        DEFAULT;
    }


    public static IndexingStrategy getIndexStrategy(IndexStrategyType indexStrategyType)
    {
        switch (indexStrategyType)
        {
            case DEFAULT:
            default:
                return new DefaultIndexingStrategyImpl();
        }

    }
}
