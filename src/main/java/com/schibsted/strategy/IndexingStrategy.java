package com.schibsted.strategy;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface IndexingStrategy
{
    Map<String, Set<String>> indexDirectory(String directoryName) throws IOException;

}
