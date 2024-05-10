package assignment4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.LinkedHashSet;

public class ReplacePage {

    public static void main(String[] args) {
        String testRefStr = "70120304230321201701";
        System.out.println("FIFO:");
        int fifoPageFaults = fifo(testRefStr, 3, true);
        System.out.println("FIFO: total # of page faults is: " + fifoPageFaults);

        System.out.println("\nLRU:");
        int lruPageFaults = lru(testRefStr, 3, true);
        System.out.println("LRU: total # of page faults is: " + lruPageFaults);

        // Testing Belady's Anomaly
        String testRefStr2 = "123412512345";
        System.out.println("\nBelady's Anomaly test for FIFO:");
        System.out.println("With 3 frames:");
        fifo(testRefStr2, 3, true);
        System.out.println("With 4 frames:");
        fifo(testRefStr2, 4, true);
    }

    public static int fifo(String refString, int numFrames, boolean output) {
        Queue<Character> pages = new LinkedList<>();
        int pageFaults = 0;

        for (char c : refString.toCharArray()) {
            if (!pages.contains(c)) {
                if (pages.size() < numFrames) {
                    pages.add(c);
                } else {
                    if (output) System.out.println(pages.peek() + ": " + queueToString(pages));
                    pages.poll();
                    pages.add(c);
                }
                pageFaults++;
            }
            if (output) System.out.println(c + ": " + queueToString(pages));
        }
        return pageFaults;
    }

    public static int lru(String refString, int numFrames, boolean output) {
        LinkedHashSet<Character> pages = new LinkedHashSet<>();
        int pageFaults = 0;

        for (char c : refString.toCharArray()) {
            if (!pages.contains(c)) {
                if (pages.size() == numFrames) {
                    char first = pages.iterator().next();
                    pages.remove(first);
                    if (output) System.out.println(first + ": " + setToString(pages));
                }
                pageFaults++;
            } else {
                pages.remove(c); // Move to end by removing and adding it again
            }
            pages.add(c);
            if (output) System.out.println(c + ": " + setToString(pages));
        }
        return pageFaults;
    }

    private static String queueToString(Queue<Character> queue) {
        return String.join("", queue.stream().map(String::valueOf).toArray(String[]::new));
    }

    private static String setToString(LinkedHashSet<Character> set) {
        return String.join("", set.stream().map(String::valueOf).toArray(String[]::new));
    }
}
