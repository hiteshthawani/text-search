package com.schibsted.model;

import java.math.BigDecimal;
import java.util.Objects;

public class RankDo implements Comparable<RankDo>
{
    private final String filename;
    private final BigDecimal score;


    public RankDo(String filename, BigDecimal score)
    {
        this.filename = filename;
        this.score = score;
    }


    @Override
    public String toString()
    {
        return
            "filename='" + filename + '\'' +
                ": score=" + score + "%";
    }


    private String getFilename()
    {
        return filename;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof RankDo))
        {
            return false;
        }
        RankDo rankDo = (RankDo) o;
        return getFilename().equals(rankDo.getFilename());
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(getFilename());
    }


    private BigDecimal getScore()
    {
        return score;
    }


    @Override
    public int compareTo(RankDo o)
    {
        if (!o.getScore().equals(this.getScore()))
        {
            return o.getScore().compareTo(this.score);
        }
        else
        {
            return o.getFilename().compareTo(this.filename);
        }
    }
}
