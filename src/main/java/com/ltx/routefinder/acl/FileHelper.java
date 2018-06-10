package com.ltx.routefinder.acl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class to read file line by line, parse every line, validate it, and apply it to custom processor/consumer.
 */
public class FileHelper {

    public static void readFileLineByLine(String filePath, Consumer<List<String>> consumer) {

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream
                    .filter(s -> !s.isEmpty())
                    .map(FileHelper::splitToPair)
                    .map(FileHelper::validatePair)
                    .forEach(consumer);

        } catch (IOException e) {
            throw new RuntimeException("Error while reading file", e);
        }
    }

    private static List<String> splitToPair(String line) {
        return Stream.of(line.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private static List<String> validatePair(List<String> pair) {
        if (pair.size() != 2) {
            throw new RuntimeException("Error while parsing file, incorrect pair of cities: " + pair);
        }
        return pair;
    }
}