package com.schibsted.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SearchQuery
{
    private static final int MAX_QUERY_LENGTH = 1000;

    private final String query;
    private final List<String> terms;


    public static SearchQuery from(String query)
    {
        return new SearchQuery(truncate(query));
    }


    private static String truncate(String query)
    {
        return query.substring(0, Math.min(MAX_QUERY_LENGTH, query.length())).toLowerCase();
    }


    private SearchQuery(String query)
    {
        this.query = query;
        this.terms = Arrays.asList(query.split(" "));
    }


    String getQuery()
    {
        return query;
    }


    public List<String> getTerms()
    {
        return terms;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof SearchQuery))
        {
            return false;
        }
        SearchQuery that = (SearchQuery) o;
        return getQuery().equals(that.getQuery()) &&
            getTerms().equals(that.getTerms());
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(getQuery(), getTerms());
    }


}
