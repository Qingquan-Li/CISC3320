package assignment2;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class AnalyzeWords {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        if (args.length < 1) {
            System.out.println("Usage: java AnalyzeWords <filename>");
            return;
        }

        List<String> words = Files.readAllLines(Paths.get(args[0]));
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Double> averageLength = executor.submit(() -> words.stream().mapToInt(String::length).average().orElse(0));
        Future<Integer> longestWord = executor.submit(() -> words.stream().mapToInt(String::length).max().orElse(0));
        Future<Map.Entry<Character, Long>> mostFrequentLetter = executor.submit(() -> 
            words.stream().flatMapToInt(String::chars).mapToObj(c -> (char)c)
            .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
            .entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null));

        System.out.println("Average length = " + averageLength.get());
        System.out.println("Longest word's length = " + longestWord.get());
        System.out.println("Most frequent letter = " + mostFrequentLetter.get().getKey());

        executor.shutdown();
    }
}

// Run this program with the following command:
// $ cd path/to/assignment2
// $ java AnalyzeWords.java ./english_words.txt
// Average length = 8.100019171254074
// Longest word's length = 22
// Most frequent letter = e
