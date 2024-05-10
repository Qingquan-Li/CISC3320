package assignment4;

public class TranslateAddr {
    public static void main(String[] args) {
        // Ensure an argument is provided
        if (args.length < 1) {
            System.out.println("Error: Please provide a virtual address as a command-line argument.");
            return;
        }

        int address;
        try {
            address = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Error: Address must be an integer.");
            return;
        }

        // Check if the address is within the 32-bit integer range
        if (address < 0) {
            System.out.println("Error: Address must be a positive 32-bit integer.");
            return;
        }

        // Constants
        int pageSize = 4096; // 4KB page size
        int pageEntrySize = 4; // 4 bytes for each page table entry

        // Calculating page number and offset
        int pageNumber = address / pageSize;
        int pageOffset = address % pageSize;

        // Largest possible page number (2^32 / 4096 - 1)
        int maxPages = (1 << (32 - 12)) - 1; // 32 bits for address, 12 bits for page offset

        // Page table size in bytes
        int pageTableSize = (maxPages + 1) * pageEntrySize;

        // Output results for one-level page table
        System.out.println("The largest possible page number is: " + maxPages);
        System.out.println("The page table size is: " + pageTableSize + " bytes");
        System.out.println("Given the address of " + address);
        System.out.println("The page number is: " + pageNumber);
        System.out.println("The page offset is: " + pageOffset);

        // Calculating two-level page table details (10 bits each for p1 and p2)
        int outerPageNumber = pageNumber >> 10; // Shift right by 10 bits to get the first 10 bits
        int innerPageNumber = pageNumber & ((1 << 10) - 1); // Mask to get the lower 10 bits

        // Output results for two-level page table
        System.out.println("With a 2-level page table, the outer page number (p1) is: " + outerPageNumber);
        System.out.println("With a 2-level page table, the inner page number (p2) is: " + innerPageNumber);
    }
}

// Run the program with a virtual address as a command-line argument
// Example: java TranslateAddr 20221108
