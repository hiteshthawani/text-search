package com.schibsted.factory;

import com.schibsted.strategy.impl.DefaultRankingStrategyImpl;
import com.schibsted.strategy.RankingStrategy;

public class RankingFactory
{
    public enum RankingStrategyType
    {
        DEFAULT;
    }


    public static RankingStrategy getIndexStrategy(RankingStrategyType indexStrategyType)
    {
        switch (indexStrategyType)
        {
            case DEFAULT:
            default:
                return new DefaultRankingStrategyImpl();
        }

    }
}
