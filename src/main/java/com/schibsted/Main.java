package com.schibsted;

import com.schibsted.factory.IndexFactory;
import com.schibsted.factory.RankingFactory;
import com.schibsted.model.RankDo;
import com.schibsted.strategy.IndexingStrategy;
import com.schibsted.strategy.RankingStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class Main
{

    static final String PROPERTIES_FILE_PATH = "application.properties";


    public static void main(String[] args)
    {

        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH))
        {
            final Scanner scanner = new Scanner(System.in);
            final Properties prop = new Properties();
            final IndexingStrategy indexingStrategy = IndexFactory.getIndexStrategy(IndexFactory.IndexStrategyType.DEFAULT);
            final RankingStrategy rankingStrategy = RankingFactory.getIndexStrategy(RankingFactory.RankingStrategyType.DEFAULT);
            final Map<String, Set<String>> termsToFilesMap = indexingStrategy.indexDirectory(args[0]);
            FileSearcher fileSearcher = new FileSearcher(termsToFilesMap, rankingStrategy);

            prop.load(input);
            while (true)
            {
                System.out.println("search>");
                if (!scanner.hasNextLine() || scanner.hasNext(":quit"))
                {
                    break;
                }

                final String query = scanner.nextLine();
                if (!query.isEmpty())
                {
                    final Set<RankDo> searchResults = fileSearcher.search(query, Integer.parseInt(prop.getProperty("MAX_SEARCH_RESULTS")));
                    if (searchResults != null && !searchResults.isEmpty())
                    {
                        searchResults.forEach(System.out::println);
                    }
                    else
                    {
                        System.out.println("no matches found");
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error While Accessing the Resources");
        }
    }
}
