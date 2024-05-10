package assignment4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Random;

public class ReplacePageEC {

    public static void main(String[] args) {
        String testRefStr = "70120304230321201701";
        int optPageFaults = opt(testRefStr, 3, true);
        System.out.println("OPT: total # of page faults is: " + optPageFaults);

        String randomRefStr = randomPageRef(1000);
        System.out.println("\nPage Replacement Analysis with 1000 character random reference string:");
        for (int frames = 1; frames <= 7; frames++) {
            int optFaults = opt(randomRefStr, frames, false);
            int fifoFaults = fifo(randomRefStr, frames, false);
            int lruFaults = lru(randomRefStr, frames, false);

            System.out.printf("%d: %d, %d(%.0f%%), %d(%.0f%%)\n",
                              frames,
                              optFaults,
                              fifoFaults,
                              100.0 * (fifoFaults - optFaults) / optFaults,
                              lruFaults,
                              100.0 * (lruFaults - optFaults) / optFaults);
        }
    }

    public static int fifo(String refString, int numFrames, boolean output) {
        Queue<Character> pages = new LinkedList<>();
        int pageFaults = 0;

        for (char c : refString.toCharArray()) {
            if (!pages.contains(c)) {
                if (pages.size() < numFrames) {
                    pages.add(c);
                } else {
                    pages.poll();
                    pages.add(c);
                }
                pageFaults++;
            }
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
                }
                pageFaults++;
            } else {
                pages.remove(c);
            }
            pages.add(c);
        }
        return pageFaults;
    }

    public static int opt(String refString, int numFrames, boolean output) {
        LinkedList<Character> futureRefs = new LinkedList<>();
        for (char c : refString.toCharArray()) {
            futureRefs.add(c);
        }

        LinkedHashSet<Character> pages = new LinkedHashSet<>();
        int pageFaults = 0;

        for (char c : refString.toCharArray()) {
            futureRefs.removeFirst();
            if (!pages.contains(c)) {
                if (pages.size() == numFrames) {
                    Iterator<Character> iter = pages.iterator();
                    char leastUseful = iter.next();
                    int farthestUsage = futureRefs.indexOf(leastUseful);
                    while (iter.hasNext()) {
                        char thisPage = iter.next();
                        int thisUsage = futureRefs.indexOf(thisPage);
                        if (thisUsage == -1) {
                            leastUseful = thisPage;
                            break;
                        } else if (thisUsage > farthestUsage) {
                            leastUseful = thisPage;
                            farthestUsage = thisUsage;
                        }
                    }
                    pages.remove(leastUseful);
                }
                pages.add(c);
                pageFaults++;
            }
        }
        return pageFaults;
    }

    public static String randomPageRef(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) ('0' + rand.nextInt(10));
            sb.append(c);
        }
        return sb.toString();
    }
}

