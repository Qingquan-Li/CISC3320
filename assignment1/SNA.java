package assignment1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Stripping Non-ASCII characters
public class SNA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input filename: ");
        String inputFilename = scanner.nextLine();
        System.out.print("Enter out filename: ");
        String outputFilename = scanner.nextLine();

        int linesProcessed = 0;
        int nonAsciiStripped = 0;

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFilename));
            writer = new BufferedWriter(new FileWriter(outputFilename));

            String line;
            while ((line = reader.readLine()) != null) {
                // Process and write line to writer
                linesProcessed++;
                StringBuilder filteredLine = new StringBuilder();
                for (char c : line.toCharArray()) {
                    if (c >= 0 && c <= 127) { // ASCII characters
                        filteredLine.append(c);
                    } else {
                        nonAsciiStripped++;
                    }
                }
                writer.write(filteredLine.toString());
                writer.newLine();
            }
            System.out.println("Lines processed: " + linesProcessed);
            System.out.println("Non-ASCII characters stripped: " + nonAsciiStripped);
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
}


/**
 * Running result:
 * $ java SNA.java
 * Enter input filename: test.txt
 * Enter out filename: output.txt
 * Lines processed: 8
 * Non-ASCII characters stripped: 4
 */
