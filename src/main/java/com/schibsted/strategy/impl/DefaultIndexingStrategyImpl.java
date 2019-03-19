package com.schibsted.strategy.impl;

import com.schibsted.strategy.IndexingStrategy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class DefaultIndexingStrategyImpl implements IndexingStrategy
{
    private final Logger logger = Logger.getLogger(DefaultIndexingStrategyImpl.class.getName());
    private final AtomicInteger fileCount = new AtomicInteger(0);


    @Override
    public Map<String, Set<String>> indexDirectory(String directoryName) throws IOException
    {
        Map<String, Set<String>> termToFileSet;

        final Collector<Map.Entry<String, String>, Map<String, Set<String>>, Map<String, Set<String>>> collector = Collector.of(
            ConcurrentHashMap::new,
            (result, input) -> result.compute(input.getKey(), (key, value) ->
            {
                if (value == null)
                {
                    value = new HashSet<>();
                }
                value.add(input.getValue());
                return value;
            }),
            (result1, result2) -> {
                result1.putAll(result2);
                return result1;
            },
            Collector.Characteristics.CONCURRENT
        );

        try (Stream<Path> pathStream = Files.list(new File(directoryName).toPath()))
        {

            termToFileSet = pathStream.
                parallel().
                map(this::indexFile).
                flatMap(termToIndexDO -> termToIndexDO.entrySet().stream()).
                collect(collector);

        }

        System.out.println(fileCount + " files read in directory " + directoryName);
        return termToFileSet;
    }


    private Map<String, String> indexFile(Path filePath)
    {
        final String filename = filePath.getFileName().toString();
        final Map<String, String> termToFile = new ConcurrentHashMap<>();
        try (Stream<String> lines = Files.lines(filePath);)
        {
            lines
                .filter(s -> !s.isEmpty())
                .map(s -> s.replaceAll("[^a-zA-Z0-9 ]", "").trim().toLowerCase())  //cleaning text by removing non-alphanumeric characters
                .flatMap(s -> Stream.of(s.split("\\s+")))
                .forEach(
                    word -> termToFile.compute(word, (key, indexDo) -> filename)
                );
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, filename + " could not be indexed");
        }
        fileCount.incrementAndGet();
        return termToFile;
    }
}
