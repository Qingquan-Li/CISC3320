package assignment2;

import java.io.*;

public class LaunchProcess {
    public static void main(String[] args) throws IOException {
        // Check if the user has provided the file name
        // If not, print the usage and return
        if (args.length < 1) {
            System.out.println("Usage: java LaunchProcess <filename>");
            return;
        }
        // args[0] means the first command line argument,
        // which will be the file name: ./english_words.txt
        String fileName = args[0];
        ProcessBuilder pb = new ProcessBuilder("java", "AnalyzeWords.java", fileName);
        pb.redirectErrorStream(true);

        long startTime = System.currentTimeMillis();
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        try {
            // Wait for the process to finish, 0 means normal termination
            // If the process is terminated by a signal, the exit value will be
            // 128 + signal number
            int exitValue = process.waitFor();
            System.out.println("Exit value is: " + exitValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time to finish all tasks: " + (endTime - startTime) + " ms");
    }
}

// Run this program with the following command:
// $ cd path/to/assignment2
// $ java LaunchProcess.java ./english_words.txt
// Average length = 8.100019171254074
// Longest word's length = 22
// Most frequent letter = e
// Exit value is: 0
// Time to finish all tasks: 317 ms
