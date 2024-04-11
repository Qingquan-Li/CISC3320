package assignment3;

public class SyncDemo2 {
    private static int grandTotal = 0;
    // Create a lock object, which will be used to synchronize the access to
    // the grandTotal variable
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // Create an array of 1000 numbers
        int[] numbers = new int[1000];
        for (int i = 0; i < 1000; i++) {
            numbers[i] = i + 1;
        }

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int START = i * 200;
            final int END = (i + 1) * 200;
            // Create an anonymous class that implements Runnable
            // to calculate the subtotal of the numbers from START to END
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    int subtotal = 0;
                    for (int j = START; j < END; j++) {
                        subtotal += numbers[j];
                    }
                    // Synchronize the access to the grandTotal variable by
                    // using the lock object
                    synchronized (lock) {
                        grandTotal += subtotal;
                    }
                }
            });
            threads[i].start();
        }

        // Before printing the grand total, wait for all threads to finish
        for (Thread t : threads) {
            try {
                // Thread.join() is called to wait for the threads to finish
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Grand Total: " + grandTotal);
    }
}
