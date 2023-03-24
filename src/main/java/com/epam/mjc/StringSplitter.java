package com.epam.mjc;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source     source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        String combinedDelimiters = "[" + String.join("", delimiters) + "]";
        return Stream.of(source.split(combinedDelimiters))
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());
    }
}
